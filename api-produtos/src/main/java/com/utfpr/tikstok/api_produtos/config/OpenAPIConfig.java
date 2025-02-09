package com.utfpr.tikstok.api_produtos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Produtos",
                version = "1.0",
                description = "API para o gerenciamento, listagem e exclusão de produtos no sistema TikStok."
        )
)
public class OpenAPIConfig {

}
