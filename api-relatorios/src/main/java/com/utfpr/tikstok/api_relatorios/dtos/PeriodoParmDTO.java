package com.utfpr.tikstok.api_relatorios.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record PeriodoParmDTO(
        @NotNull(message = "Informe a data inicial e final do período a ser considerado.")
        Date dtInicial,
        @NotNull(message = "Informe a data inicial e final do período a ser considerado.")
        Date dtFinal
) {
}
