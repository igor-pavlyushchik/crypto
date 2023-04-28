package com.ip.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ip.crypto.controller.CryptoController;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CryptoApplicationTests {


	@Autowired
	CryptoController cryptoController;

	@Test
	void contextLoads() {
		assertNotNull(cryptoController);
	}



}
