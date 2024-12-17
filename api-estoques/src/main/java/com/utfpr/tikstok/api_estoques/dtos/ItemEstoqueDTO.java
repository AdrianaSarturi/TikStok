package com.utfpr.tikstok.api_estoques.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemEstoqueDTO(
        Long idProduto,
        @NotNull(message = "Quantidade não pode ser nula!")
        @Positive(message = "Quantidade deve ser maior do que zero!")
        Integer quantidade,
        @NotNull(message = "Valor não pode ser nulo!")
        @Positive(message = "Valor deve ser maior do que zero!")
        Double valor
) {
}
