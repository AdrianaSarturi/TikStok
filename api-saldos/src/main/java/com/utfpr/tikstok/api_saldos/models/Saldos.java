package com.utfpr.tikstok.api_saldos.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Saldos {
	private SaldosKey id;
	private double q_anterior;
	private double v_anterior;
	private double q_entradas;
	private double v_entradas;
	private double q_saidas;
	private double v_saidas;
	private double q_atual;
	private double v_atual;
}
