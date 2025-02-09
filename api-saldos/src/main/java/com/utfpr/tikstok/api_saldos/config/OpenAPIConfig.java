package com.utfpr.tikstok.api_saldos.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Saldos",
                version = "1.0",
                description = "API para o gerenciamento dos Saldos no sistema TikStok."
        )
)
public class OpenAPIConfig {

}
