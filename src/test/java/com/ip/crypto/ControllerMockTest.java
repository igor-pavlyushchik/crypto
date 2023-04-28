package com.ip.crypto;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ip.crypto.controller.CryptoController;
import com.ip.crypto.model.BestNormalizedCrypto;
import com.ip.crypto.service.CSVService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Mock controller test.
 * Mostly to show using of mocksMvc, needs more tests.
 */
@WebMvcTest(CryptoController.class)
public class ControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CSVService csvService;

    @Test
    public void shouldReturnBestNormalizedCryptoForDate() throws Exception {
        LocalDate localDate = LocalDate.of(2022, 1, 5);
        BestNormalizedCrypto bestNormalizedCrypto = new BestNormalizedCrypto("BTC", 0.018435051657526767);

        when(csvService.getBestNormalizedCrypto(localDate)).thenReturn(bestNormalizedCrypto);

            this.mockMvc.perform(get("/best-crypto").param("date", "2022-01-05"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value("BTC"));
    }
}
