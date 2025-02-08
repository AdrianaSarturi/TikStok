package com.utfpr.tikstok.api_estoques.services;

import com.utfpr.tikstok.api_estoques.dtos.EstoqueDTO;
import com.utfpr.tikstok.api_estoques.dtos.EstoqueUpdateDTO;
import com.utfpr.tikstok.api_estoques.dtos.ProdutoDTO;
import com.utfpr.tikstok.api_estoques.models.Estoque;
import com.utfpr.tikstok.api_estoques.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    private EstoqueRepository estoqueRepository;
    private ProdutoFeignClient produtoFeignClient;

    public EstoqueService(EstoqueRepository repository, ProdutoFeignClient produtoFeignClient){
        this.estoqueRepository = repository;
        this.produtoFeignClient = produtoFeignClient;
    }

    public Estoque lancarEstoque(EstoqueDTO estoqueDTO){
        Estoque estoque = new Estoque();

        estoque.setTipo(estoqueDTO.tipo());
        estoque.setDtMovimento(new Date());

        // Verifica se o produto existe no banco de dados
        ProdutoDTO produtoBusca = produtoFeignClient.getProdutoById(estoqueDTO.idProduto());

        if(produtoBusca == null){
            return null;
        }
        // Se for movimentacao de saida, deve verificar se o produto possui saldo para movimentar
        /*if(estoqueDTO.tipo().equals("S")){
            if(estoqueDTO.quantidade() > produtoBusca.qtdEstoque())
                return null;
        }*/

        estoque.setIdProduto(estoqueDTO.idProduto());
        estoque.setQuantidade(estoqueDTO.quantidade());
        estoque.setValorUnitario(estoqueDTO.valorUnitario());

        return this.estoqueRepository.save(estoque);
    }

    public List<Estoque> getAll(){
        return this.estoqueRepository.findAll();
    }

    public Estoque getEstoqueById(Long idEstoque){
        return estoqueRepository.findById(idEstoque).orElse(null);
    }

    public Estoque alterarEstoque(EstoqueUpdateDTO estoqueUpdateDTO, Long idEstoque){
        Estoque estoque = estoqueRepository.findById(idEstoque).orElse(null);
        if(estoque != null){
            estoque.setDtMovimento(estoqueUpdateDTO.dtMovimento() != null ? estoqueUpdateDTO.dtMovimento(): estoque.getDtMovimento());
            estoque.setQuantidade(estoqueUpdateDTO.quantidade() != null ? estoqueUpdateDTO.quantidade() : estoque.getQuantidade());
            estoque.setValorUnitario(estoqueUpdateDTO.valorUnitario() != null ? estoqueUpdateDTO.valorUnitario() : estoque.getValorUnitario());

            return estoqueRepository.save(estoque);
        }
        return null;
    }

    public boolean deleteById(Long idEstoque){
        if(!this.estoqueRepository.existsById(idEstoque))
            return false;

        this.estoqueRepository.deleteById(idEstoque);

        return true;
    }

}
