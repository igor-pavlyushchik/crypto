package com.ip.crypto.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ip.crypto.helper.CSVHelper;
import com.ip.crypto.model.BestNormalizedCrypto;
import com.ip.crypto.model.CryptoExtremes;
import com.ip.crypto.model.NormalizedCrypto;
import com.ip.crypto.model.ResponseMessage;
import com.ip.crypto.service.CSVService;

/**
 * The Controller to handle calls regarding crypto.
 */
@RestController
public class CryptoController {

    final CSVService csvService;

    public CryptoController(final CSVService fileService) {
        this.csvService = fileService;
    }

    /**
     * Just hello to check REST API is working.
     * @param name any name to say hello to.
     * @return hello message.
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }


    /**
     * Upload the csv file of the required format with crypto info.
     * @param file the csv file.
     * @return response message.
     */
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    /**
     * Returns a descending sorted list of all the cryptos,
     * comparing the normalized range (i.e. (max-min)/min).
     * Good to expand with time interval :-).
     * Example: http://localhost:8081/normalized
     */
    @GetMapping("/normalized")
    public ResponseEntity<List<NormalizedCrypto>> normalized() {
        List<NormalizedCrypto> normalizedCryptoList = csvService.getNormalizedCrypto();
        return ResponseEntity.status(HttpStatus.OK).body(normalizedCryptoList);
    }

    /**
     * Returns the oldest/newest/min/max price values for a requested crypto.
     * Example: http://localhost:8081/extremes?symbol=ETH
     * @param symbol the symbol of the crypto.
     * @return crypto price extremes (oldest/newest/min/max price).
     */
    @GetMapping("/extremes")
    public ResponseEntity<List<CryptoExtremes>> extremes(@RequestParam("symbol") final String symbol) {
        List<CryptoExtremes> cryptoExtremesList = csvService.getCryptoExtremes(symbol);
        return ResponseEntity.status(HttpStatus.OK).body(cryptoExtremesList);
    }

    /**
     * Returns the crypto with the highest normalized range for a specific day.
     * Example: http://localhost:8081/best-crypto?date=2022-01-01
     * @param localDate the date for which to return the best normalized crypto.
     * @return the best normalized crypto for the given day.
     */
    @GetMapping("/best-crypto")
    public ResponseEntity<BestNormalizedCrypto> extremes(@RequestParam("date") final LocalDate localDate) {
        BestNormalizedCrypto bestNormalizedCrypto = csvService.getBestNormalizedCrypto(localDate);
        return ResponseEntity.status(HttpStatus.OK).body(bestNormalizedCrypto);
    }
}
