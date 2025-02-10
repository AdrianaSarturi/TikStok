package com.utfpr.tikstok.api_estoques.services;

import com.utfpr.tikstok.api_estoques.dtos.EstoqueDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@FeignClient(name="api-saldos", url="localhost:8083")
public interface SaldosFeignClient {
    @PostMapping("/saldos")
    void processarSaldo(@Valid @RequestBody EstoqueDTO estoqueDTO);

    @PutMapping("/saldos/{idProduto}/{data}")
    void alterarSaldo(@PathVariable Long idProduto, @PathVariable Date data, @Valid @RequestBody EstoqueDTO estoqueDTO);

    @DeleteMapping("/saldos/{idProduto}/{data}")
    void deletarSaldo(@PathVariable Long idProduto, @PathVariable Date data, @Valid @RequestBody EstoqueDTO estoqueDTO);
}
