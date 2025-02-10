package com.utfpr.tikstok.api_saldos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.utfpr.tikstok.api_saldos.models.Saldos;
import com.utfpr.tikstok.api_saldos.models.SaldosKey;

public interface SaldosRepository extends JpaRepository<Saldos, SaldosKey> {

	@Query(value =	"   SELECT s.id_produto, " +
		    		"	  	   s.data, " +
		    		"		   s.q_anterior, " +
		    		"		   s.v_anterior, " +
		    		"		   s.q_entradas, " +
		    		"		   s.v_entradas, " +
		    		"		   s.q_saidas, " +
		    		"		   s.v_saidas, " +
		    		"		   s.q_atual, " +
		    		"		   s.v_atual " +
		    		"     FROM SALDOS s " +
		    		"    WHERE s.id_produto = ?1" +
		    		"	   AND s.data <= ?2" +
		    		" ORDER BY s.data desc " +
		    		"    LIMIT 1", nativeQuery = true)
    public Saldos getSaldoAnterior(Long idproduto, Date data);

	@Query(	value = "  SELECT s.id_produto, " +
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
					"   WHERE s.id_produto = ?1 " +
					"		  AND s.data > ?2" + 
					"ORDER BY s.data", nativeQuery = true)
	public List<Saldos> findAllBySaldosKey(Long idproduto, Date data);

}
