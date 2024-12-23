package com.utfpr.tikstok.api_estoques.dtos;

import java.util.Date;
import java.util.List;

public record EstoqueDTO(
        Long id,
        Date dtMovimento,
        String tipo,
        Double valorTotal,
        List<ItemEstoqueDTO> itens
) {
}
