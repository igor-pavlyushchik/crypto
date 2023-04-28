package com.ip.crypto.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.ip.crypto.model.CryptoEntry;
import com.ip.crypto.model.CryptoExtremes;

/**
 * Helper to parse csv file using Apache Commons CSV.
 */
public class CSVHelper {
    public static final String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<CryptoEntry> csvToCryptos(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<CryptoEntry> cryptos = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CryptoEntry crypto = new CryptoEntry(
                        Long.parseLong(csvRecord.get("timestamp")),
                        csvRecord.get("symbol"),
                        Double.parseDouble(csvRecord.get("price"))
                );

                cryptos.add(crypto);
            }

            return cryptos;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    /**
     * Help calculate max/min and newest/oldest prices for the crypto batch for given month.
     * @param cryptos crypto entries to process.
     * @return entity with crypto extremes.
     */
    public static CryptoExtremes calculateMonthCryptoExtremes(final List<CryptoEntry> cryptos) {
        final CryptoExtremes cryptoExtremes = new CryptoExtremes();

        cryptoExtremes.setSymbol(cryptos.get(0).getSymbol());
        cryptoExtremes.setTimeInterval(prepareTimeInterval(cryptos.get(0)));

        List<CryptoEntry> sortedByTimeStamp = cryptos.stream().sorted(Comparator.comparing(CryptoEntry::getTimestamp)).collect(Collectors.toList());
        cryptoExtremes.setOldestPrice(sortedByTimeStamp.get(0).getPrice());
        cryptoExtremes.setNewestPrice(sortedByTimeStamp.get(sortedByTimeStamp.size() - 1).getPrice());

        List<CryptoEntry> sortedByPrice = cryptos.stream().sorted(Comparator.comparing(CryptoEntry::getPrice)).collect(Collectors.toList());
        cryptoExtremes.setMinPrice(sortedByPrice.get(0).getPrice());
        cryptoExtremes.setMaxPrice(sortedByPrice.get(sortedByPrice.size() - 1).getPrice());
        return cryptoExtremes;
    }

    private static String prepareTimeInterval(final CryptoEntry cryptoEntry) {
        LocalDateTime ldt = Instant.ofEpochMilli(cryptoEntry.getTimestamp())
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
        String timeInterval = ldt.getYear() + ldt.getMonth().toString();
        return timeInterval;
    }
}
