package com.utfpr.tikstok.api_saldos.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SaldosKey implements Serializable {
    private Long idProduto;
    private Date data;
}
