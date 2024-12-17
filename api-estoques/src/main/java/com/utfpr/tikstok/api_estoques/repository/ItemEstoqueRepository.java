package com.utfpr.tikstok.api_estoques.repository;

import com.utfpr.tikstok.api_estoques.models.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
}
