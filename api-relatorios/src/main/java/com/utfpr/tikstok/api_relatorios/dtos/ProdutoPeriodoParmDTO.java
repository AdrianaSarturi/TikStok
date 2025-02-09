package com.utfpr.tikstok.api_relatorios.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record ProdutoPeriodoParmDTO(
        @NotBlank(message = "Código do produto é obrigatório.")
        Long idProduto,
        @NotBlank(message = "Informe a data inicial e final do período a ser considerado.")
        Date dtInicial,
        @NotBlank(message = "Informe a data inicial e final do período a ser considerado.")
        Date dtFinal
) {
}
