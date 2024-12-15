package com.utfpr.tikstok.api_estoques.dtos;

public record ProdutoDTO(
        Long id,
        String descricao,
        String unidade,
        Double qtdEstoque
) {}
