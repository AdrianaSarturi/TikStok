package com.utfpr.tikstok.api_saldos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class ApiSaldosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSaldosApplication.class, args);
	}

}
