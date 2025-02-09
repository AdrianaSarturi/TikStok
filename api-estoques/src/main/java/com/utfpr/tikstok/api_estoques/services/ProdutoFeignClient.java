package com.utfpr.tikstok.api_estoques.services;

import com.utfpr.tikstok.api_estoques.dtos.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="api-produtos", url="localhost:8081")
public interface ProdutoFeignClient {
    @GetMapping("/produtos/{id}")
    ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id);
}
