package com.utfpr.tikstok.api_relatorios.dtos;

import java.util.Date;

public record ExtratoProdutoDTO(
        Long idEstoque,
        String tipoMovimento,
        Integer qtdProduto,
        Double valorUnitario,
        Double valorTotal,
        Date dtMovimento
) {
}
