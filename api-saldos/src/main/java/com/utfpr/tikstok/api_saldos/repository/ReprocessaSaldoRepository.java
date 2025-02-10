package com.utfpr.tikstok.api_saldos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.utfpr.tikstok.api_saldos.models.Saldos;
import com.utfpr.tikstok.api_saldos.models.SaldosKey;

public interface ReprocessaSaldoRepository extends JpaRepository<Saldos, SaldosKey> {
	@Query(	value = "  SELECT s.idproduto, " +
					"		  s.data, " + 
					"		  s.q_anterior, " +
					"		  s.v_anterior, " +
					"		  s.q_entradas, " +
					"		  s.v_entradas, " +
					"		  s.q_saidas, " +
					"		  s.v_saidas, " +
					"		  s.q_atual, " +
					"	  	  s.v_atual " + 
					"    FROM saldos s " +
					"   WHERE s.idproduto = ?1 " +
					"		  AND s.data > ?2" + 
					"ORDER BY s.data", nativeQuery = true)
    List<Saldos> findAllBySaldosKey(Long idproduto, Date data);
}
