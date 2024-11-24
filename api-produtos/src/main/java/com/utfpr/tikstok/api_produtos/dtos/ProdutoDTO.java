package com.utfpr.tikstok.api_produtos.dtos;

import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
        Long id,
        @NotNull(message="Descrição não pode ser nula!")
        String descricao,
        Integer qtdEstoque
        ) {}
