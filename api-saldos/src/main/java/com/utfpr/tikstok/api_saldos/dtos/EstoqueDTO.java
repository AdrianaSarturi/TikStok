package com.utfpr.tikstok.api_saldos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record EstoqueDTO(
        Long id,
        Date dtMovimento,
        @NotBlank(message = "Informe o tipo da movimentação (E - entrada)/S - saída)!")
        @Pattern(regexp = "E|S", message = "O campo deve ser preenchido com 'E' - entrada ou 'S' - saída!")
        String tipo,
        Long idProduto,
        @NotNull(message = "Quantidade não pode ser nula!")
        @Positive(message = "Quantidade deve ser maior do que zero!")
        Integer quantidade,
        @NotNull(message = "Valor não pode ser nulo!")
        @Positive(message = "Valor deve ser maior do que zero!")
        Double valorUnitario
) {
}
