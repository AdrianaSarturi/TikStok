package com.utfpr.tikstok.api_estoques.services;

import com.utfpr.tikstok.api_estoques.dtos.ProdutoDTO;
import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="api-produtos", url="localhost:8080")
public interface ProdutoFeignClient {

    @GetMapping("/produtos/{id}")
    ProdutoDTO getProdutoById(@PathVariable Long id);

    // adicionar endpoint da baixa de estoque
}
