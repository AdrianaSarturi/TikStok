package com.utfpr.tikstok.api_produtos.services;

import com.utfpr.tikstok.api_produtos.dtos.ProdutoDTO;
import com.utfpr.tikstok.api_produtos.models.Produto;
import com.utfpr.tikstok.api_produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto createProduto(ProdutoDTO produtoDTO){
        Produto produto = new Produto();

        produto.setDescricao(produtoDTO.descricao());
        produto.setQtdEstoque(0);

        return this.repository.save(produto);
    }

    public List<ProdutoDTO> getAll(){
        List<ProdutoDTO> produtos = new ArrayList<>();

        for(Produto produto : this.repository.findAll()){
            ProdutoDTO produtoDTO = new ProdutoDTO(produto.getId(), produto.getDescricao(), produto.getQtdEstoque());
            produtos.add(produtoDTO);
        }

        return produtos;
    }
}
