package com.utfpr.tikstok.api_estoques.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;
import java.util.List;

public record EstoqueDTO(
        Long id,
        Date dtMovimento,
        @NotBlank(message = "Informe o tipo da movimentação (E - entrada)/S - saída)!")
        @Pattern(regexp = "E|S", message = "O campo deve ser preenchido com 'E' - entrada ou 'S' - saída!")
        String tipo,
        Double valorTotal,
        @NotNull(message = "Informe os itens do lançamento!")
        List<ItemEstoqueDTO> itens
) {
}
