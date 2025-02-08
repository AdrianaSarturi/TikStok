package com.utfpr.tikstok.api_estoques.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Estoques",
                version = "1.0",
                description = "API para o lançamento, listagem e exclusão de movimentações de estoque no sistema TikStok."
        )
)
public class OpenAPIConfig {

}
