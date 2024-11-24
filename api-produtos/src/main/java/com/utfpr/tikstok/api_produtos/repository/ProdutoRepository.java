package com.utfpr.tikstok.api_produtos.repository;

import com.utfpr.tikstok.api_produtos.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
