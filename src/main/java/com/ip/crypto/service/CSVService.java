package com.ip.crypto.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ip.crypto.dao.CSVDAO;
import com.ip.crypto.helper.CSVHelper;
import com.ip.crypto.model.BestNormalizedCrypto;
import com.ip.crypto.model.Crypto;
import com.ip.crypto.model.CryptoEntry;
import com.ip.crypto.model.CryptoExtremes;
import com.ip.crypto.model.NormalizedCrypto;

/**
 * The service to process csv files.
 */
@Service
public class CSVService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CSVService.class);

    private final CSVDAO csvdao;

    public CSVService(final CSVDAO csvdao) {
        this.csvdao = csvdao;
    }

    /**
     * Saves csv file with crypto info to DB.
     * @param file the file to process.
     */
    public void save(MultipartFile file) {
        try {
            List<CryptoEntry> cryptos = CSVHelper.csvToCryptos(file.getInputStream());
            LOGGER.info("Number of cryptos received: {}", cryptos.size());
            List<Object[]> objects = cryptos.stream()
                    .filter(cryptoEntry -> ObjectUtils.containsConstant(Crypto.values(), cryptoEntry.getSymbol()))
                    .map(cryptoEntry -> new Object[]{
                    Instant.ofEpochMilli(cryptoEntry.getTimestamp())
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDateTime(),
                    cryptoEntry.getSymbol(),
                    cryptoEntry.getPrice()}).collect(Collectors.toList());
            LOGGER.info("Number of object arrays to be used in batch update {}.", objects.size());
            csvdao.batchInsert(objects);

            CryptoExtremes cryptoExtremes = CSVHelper.calculateMonthCryptoExtremes(cryptos);
            csvdao.insertExtremes(cryptoExtremes);
        } catch (Exception e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    /**
     * Get normalized crypto.
     * @return sorted list of normilized values for all cryptos.
     */
    public List<NormalizedCrypto> getNormalizedCrypto() {
        try {
            return csvdao.selectNormalizedCryptos();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve normalized values: " + e.getMessage());
        }
    }

    /**
     * Returns the oldest/newest/min/max price values for a requested crypto.
     *
     * @param symbol the symbol of the crypto.
     * @return extremes (oldest/newest/min/max price values) for the given crypto.
     */
    public List<CryptoExtremes> getCryptoExtremes(final String symbol) {
        try {
            return csvdao.selectExtremes(symbol);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve extremes values: " + e.getMessage());
        }
    }

    /**
     * Returns the crypto with the highest normalized range for a specific day.
     * @param localDate date for which to return the best normalized crypto.
     * @return the best normalized crypto for the given day and the value.
     */
    public BestNormalizedCrypto getBestNormalizedCrypto(final LocalDate localDate) {
        try {
            return csvdao.selectBestNormalizedCrypto(localDate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve extremes values: " + e.getMessage());
        }
    }
}
