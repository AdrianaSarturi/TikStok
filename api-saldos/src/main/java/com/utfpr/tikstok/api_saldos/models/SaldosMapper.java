package com.utfpr.tikstok.api_saldos.models;

import com.utfpr.tikstok.api_saldos.dtos.SaldosDTO;

public class SaldosMapper {
	public static SaldosDTO toDTO(Saldos registro) {
		
		SaldosDTO retorno = new SaldosDTO(
			registro.getSaldosid().getIdProduto(),
			registro.getSaldosid().getData(),
			registro.getQ_anterior(),
			registro.getV_anterior(),
			registro.getQ_entradas(),
			registro.getV_entradas(),
			registro.getQ_saidas(),
			registro.getV_saidas(),
			registro.getQ_atual(),
			registro.getV_atual());
		return retorno;
	}
	public static Saldos toEntity(SaldosDTO registro) {
		SaldosKey SaldosId = new SaldosKey(
			registro.idproduto(),
			registro.data());
		Saldos retorno = new Saldos(
			SaldosId,
			registro.q_anterior(),
			registro.v_anterior(),
			registro.q_entradas(),
			registro.v_entradas(),
			registro.q_saidas(),
			registro.v_saidas(),
			registro.q_atual(),
			registro.v_atual());
		return retorno;
	}
}
