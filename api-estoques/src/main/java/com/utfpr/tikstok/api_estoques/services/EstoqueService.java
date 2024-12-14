package com.utfpr.tikstok.api_estoques.services;

import com.utfpr.tikstok.api_estoques.dtos.EstoqueDTO;
import com.utfpr.tikstok.api_estoques.dtos.ItemEstoqueDTO;
import com.utfpr.tikstok.api_estoques.dtos.ProdutoDTO;
import com.utfpr.tikstok.api_estoques.models.Estoque;
import com.utfpr.tikstok.api_estoques.models.ItemEstoque;
import com.utfpr.tikstok.api_estoques.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EstoqueService {

    private EstoqueRepository estoqueRepository;
    private ProdutoFeignClient produtoFeignClient;

    public EstoqueService(EstoqueRepository repository, ProdutoFeignClient produtoFeignClient){
        this.estoqueRepository = repository;
        this.produtoFeignClient = produtoFeignClient;
    }

    public Estoque lancarEstoque(EstoqueDTO estoqueDTO){
        Double valorTotal = 0.0;
        Estoque estoque = new Estoque();

        estoque.setTipo(estoqueDTO.tipo());
        estoque.setDtMovimento(new Date());

        List<ItemEstoque> itens = new ArrayList<>();

        for(ItemEstoqueDTO itemEstoqueDTO : estoqueDTO.itens()){

            ProdutoDTO produtoBusca = produtoFeignClient.getProdutoById(itemEstoqueDTO.idProduto());

            if(produtoBusca == null){
                return null;
            }
            // Se for movimentacao de saida, deve verificar se o produto possui saldo para movimentar
            if(estoqueDTO.tipo().equals("S")){
                if(itemEstoqueDTO.quantidade() > produtoBusca.qtdEstoque())
                    return null;
            }

            ItemEstoque itemEstoque = new ItemEstoque();

            itemEstoque.setIdProduto(itemEstoqueDTO.idProduto());
            itemEstoque.setQuantidade(itemEstoqueDTO.quantidade());
            itemEstoque.setValor(itemEstoqueDTO.valor());
            itemEstoque.setEstoque(estoque);

            itens.add(itemEstoque);

            valorTotal += itemEstoqueDTO.valor();
        }
        estoque.setValorTotal(valorTotal);

        estoque.setItens(itens);

        return this.estoqueRepository.save(estoque);
    }

    public List<Estoque> getAll(){
        return this.estoqueRepository.findAll();
    }

}
