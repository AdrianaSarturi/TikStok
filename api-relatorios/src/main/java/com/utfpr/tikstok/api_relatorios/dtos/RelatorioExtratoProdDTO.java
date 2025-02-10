package com.utfpr.tikstok.api_relatorios.dtos;

import java.util.List;

public record RelatorioExtratoProdDTO(
        String header,
        List<ExtratoProdutoDTO> extrato
) {
}
