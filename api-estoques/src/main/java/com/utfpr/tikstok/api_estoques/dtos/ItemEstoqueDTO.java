package com.utfpr.tikstok.api_estoques.dtos;

public record ItemEstoqueDTO(
        Long idProduto,
        Integer quantidade,
        Double valor
) {
}
