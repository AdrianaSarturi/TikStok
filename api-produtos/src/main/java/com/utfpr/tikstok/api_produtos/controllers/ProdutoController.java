package com.utfpr.tikstok.api_produtos.controllers;

import com.utfpr.tikstok.api_produtos.dtos.ProdutoDTO;
import com.utfpr.tikstok.api_produtos.models.Produto;
import com.utfpr.tikstok.api_produtos.repository.ProdutoRepository;
import com.utfpr.tikstok.api_produtos.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<?> addOne(@Valid @RequestBody ProdutoDTO produtoDTO){

        Produto produto = produtoService.createProduto(produtoDTO);

        if(produto != null)
            return ResponseEntity.created(null).body(produto);
        else
            return null;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAll(){
        return ResponseEntity.ok(produtoService.getAll());
    }
}
