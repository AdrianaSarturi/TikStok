package com.utfpr.tikstok.api_relatorios.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Relatórios",
                version = "1.0",
                description = "API para geração de relatórios no sistema TikStok."
        )
)
public class OpenAPIConfig {

}