package com.utfpr.tikstok.api_produtos.dtos;

import jakarta.validation.constraints.NotBlank;

public record ProdutoDTO(
        Long id,
        @NotBlank(message = "Descrição não pode ser nula ou vazia!")
        String descricao,
        @NotBlank(message = "Unidade não pode ser nula ou vazia!")
        String unidade,
        Double qtdEstoque
) {}