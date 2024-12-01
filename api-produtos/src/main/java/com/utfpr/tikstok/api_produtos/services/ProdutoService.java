package com.utfpr.tikstok.api_produtos.services;

import com.utfpr.tikstok.api_produtos.dtos.ProdutoDTO;
import com.utfpr.tikstok.api_produtos.models.Produto;
import com.utfpr.tikstok.api_produtos.repository.ProdutoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<ProdutoDTO> getAll() {
        // Ordena os produtos por ID em ordem crescente pelo id
        List<Produto> produtos = this.repository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        List<ProdutoDTO> produtosDTO = new ArrayList<>();
        for (Produto produto : produtos) {
            ProdutoDTO produtoDTO = new ProdutoDTO(
                    produto.getId(),
                    produto.getDescricao(),
                    produto.getUnidade()
            );
            produtosDTO.add(produtoDTO);
        }

        return produtosDTO;
    }

    public ProdutoDTO getById(Long id) {
        return repository.findById(id)
                .map(produto -> new ProdutoDTO(
                        produto.getId(),
                        produto.getDescricao(),
                        produto.getUnidade()
                ))
                .orElse(null);
    }

    public Produto createProduto(@NotNull ProdutoDTO produtoDTO) {
        Produto produto = new Produto();

        produto.setDescricao(produtoDTO.descricao());
        produto.setUnidade(produtoDTO.unidade());

        return this.repository.save(produto);
    }

    public Produto updateProduto(Long id, ProdutoDTO produtoDTO) {
        return repository.findById(id).map(produto -> {
            produto.setDescricao(produtoDTO.descricao());
            produto.setUnidade(produtoDTO.unidade());
            return repository.save(produto);
        }).orElse(null);
    }

    public boolean deleteProduto(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}