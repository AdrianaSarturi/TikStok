package com.utfpr.tikstok.api_estoques.dtos;

public record ProdutoEstoqueUpdateDTO(
    Long id,
    String tipo,
    Double qtdEstoque
) {
}
