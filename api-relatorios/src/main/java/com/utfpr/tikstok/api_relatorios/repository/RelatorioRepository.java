package com.utfpr.tikstok.api_relatorios.repository;

import com.utfpr.tikstok.api_relatorios.models.ExtratoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RelatorioRepository extends JpaRepository<ExtratoProduto, Long> {

    @Query(value =
            "SELECT " +
                    "e.id, e.tipo, e.id_produto, e.quantidade, e.valor_unitario, e.valor_unitario * e.quantidade AS valor_total, e.dt_movimento " +
            "FROM ESTOQUE e " +
             "WHERE e.id_produto = ?1 AND e.dt_movimento BETWEEN ?2 AND ?3 " +
             "ORDER BY e.dt_movimento, e.id"
            , nativeQuery = true)
    List<ExtratoProduto> getExtratoProduto(Long idProduto, Date dtInicial, Date dtFinal);
}
