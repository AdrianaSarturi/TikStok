package com.utfpr.tikstok.api_saldos.repository;

import com.utfpr.tikstok.api_saldos.models.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}
