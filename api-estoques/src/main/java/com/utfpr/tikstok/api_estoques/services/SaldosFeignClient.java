package com.utfpr.tikstok.api_estoques.services;

import com.utfpr.tikstok.api_estoques.dtos.EstoqueDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="api-saldos", url="localhost:8083")
public interface SaldosFeignClient {
    @PostMapping("/saldos")
    void processarSaldo(@Valid @RequestBody EstoqueDTO estoqueDTO);

    @PutMapping("/saldos")
    void alterarSaldo(@Valid @RequestBody EstoqueDTO estoqueDTO);

    @DeleteMapping("/saldos")
    void deletarSaldo(@Valid @RequestBody EstoqueDTO estoqueDTO);
}
