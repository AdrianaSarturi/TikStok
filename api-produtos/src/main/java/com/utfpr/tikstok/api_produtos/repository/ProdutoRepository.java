package com.utfpr.tikstok.api_produtos.repository;

import com.utfpr.tikstok.api_produtos.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Método personalizado para buscar todos os produtos com ordenação
    List<Produto> findAll(Sort sort);
}
