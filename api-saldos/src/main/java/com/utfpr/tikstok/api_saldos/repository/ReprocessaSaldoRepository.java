package com.utfpr.tikstok.api_saldos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utfpr.tikstok.api_saldos.models.Saldos;
import com.utfpr.tikstok.api_saldos.models.SaldosKey;

public interface ReprocessaSaldoRepository extends JpaRepository<Saldos, SaldosKey> {
	@Query(	"SELECT idproduto, " +
			"		data, " + 
			"		q_anterior, " +
			"		v_anterior, " +
			"		q_entradas, " +
			"		v_entradas, " +
			"		q_saidas, " +
			"		v_saidas, " +
			"		q_atual, " +
			"		v_atual " + 
			"  FROM saldos s " +
			" WHERE s.idproduto = :idproduto " +
			"		AND s.data > :data")
    List<Saldos> findAllByNome(@Param("idproduto") Long idproduto, @Param("data") Date data);
}
