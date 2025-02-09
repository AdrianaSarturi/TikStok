package com.utfpr.tikstok.api_produtos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ProdutoEstoqueUpdateDTO(
        @NotBlank(message = "O tipo é obrigatório (E para Entrada ou S para Saída).")
        @Pattern(regexp = "E|S", message = "O tipo deve ser 'E' - entrada ou 'S' - saída!")
        String tipo,
        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser maior que zero.")
        Double qtdEstoque
) {}
