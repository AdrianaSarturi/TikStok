package com.utfpr.tikstok.api_estoques.dtos;

import jakarta.validation.constraints.Positive;

import java.util.Date;

public record EstoqueUpdateDTO(
        Date dtMovimento,
        @Positive(message = "Quantidade deve ser maior do que zero!")
        Integer quantidade,
        @Positive(message = "Valor deve ser maior do que zero!")
        Double valorUnitario
) {
}
