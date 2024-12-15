package com.utfpr.tikstok.api_estoques;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EstoquesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstoquesApplication.class, args);
	}

}
