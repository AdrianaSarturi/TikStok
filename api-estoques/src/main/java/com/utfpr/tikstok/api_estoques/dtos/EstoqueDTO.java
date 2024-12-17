package com.utfpr.tikstok.api_estoques.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public record EstoqueDTO(
        Long id,
        Date dtMovimento,
        @NotNull(message = "Informe o tipo da movimentação (E - entrada)/S - saída)!")
        String tipo,
        Double valorTotal,
        @NotNull(message = "Informe os itens do lançamento!")
        List<ItemEstoqueDTO> itens
) {
}
