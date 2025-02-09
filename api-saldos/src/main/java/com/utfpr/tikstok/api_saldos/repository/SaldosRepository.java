package com.utfpr.tikstok.api_saldos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tikstok.api_saldos.models.Saldos;
import com.utfpr.tikstok.api_saldos.models.SaldosKey;

public interface SaldosRepository extends JpaRepository<Saldos, SaldosKey> {

}
