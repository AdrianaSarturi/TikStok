package com.utfpr.tikstok.api_saldos.dtos;

import java.util.Date;

public record SaldosDTO(
    Long idproduto,
    Date data,
    Double q_anterior,
    Double v_anterior,
    Double q_entradas,
    Double v_entradas,
    Double q_saidas,
    Double v_saidas,
    Double q_atual,
    Double v_atual

) {}
