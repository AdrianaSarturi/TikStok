package com.utfpr.tikstok.api_produtos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoEstoqueUpdateDTO(
        @NotNull(message = "O ID é obrigatório.") Long id,
        @NotBlank(message = "O tipo é obrigatório (E para Entrada ou S para Saída).") String tipo,
        @NotNull(message = "A quantidade é obrigatória.") Double qtdEstoque
) {}
