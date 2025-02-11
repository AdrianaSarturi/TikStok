package com.utfpr.tikstok.api_estoques.services;

import com.utfpr.tikstok.api_estoques.dtos.EstoqueDTO;
import com.utfpr.tikstok.api_estoques.dtos.EstoqueUpdateDTO;
import com.utfpr.tikstok.api_estoques.dtos.ProdutoDTO;
import com.utfpr.tikstok.api_estoques.models.Estoque;
import com.utfpr.tikstok.api_estoques.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EstoqueService {

    private EstoqueRepository estoqueRepository;
    private ProdutoFeignClient produtoFeignClient;

    @Autowired
    private SaldosFeignClient saldosFeignClient;

    public EstoqueService(EstoqueRepository repository, ProdutoFeignClient produtoFeignClient){
        this.estoqueRepository = repository;
        this.produtoFeignClient = produtoFeignClient;
    }

    public Estoque lancarEstoque(EstoqueDTO estoqueDTO) throws Exception {
        Estoque estoque = new Estoque();
        ProdutoDTO produtoBusca;

        estoque.setTipo(estoqueDTO.tipo());
        estoque.setDtMovimento(estoqueDTO.dtMovimento());

        // Verifica existência do produto
        try {
            produtoBusca = produtoFeignClient.getProdutoById(estoqueDTO.idProduto()).getBody();
        } catch (RuntimeException exp){
            throw new Exception("Produto "+estoqueDTO.idProduto()+" não encontrado!");
        }

        if(produtoBusca == null){
            throw new Exception("Produto "+estoqueDTO.idProduto()+" não encontrado!");
        }
        // Se for movimentação de saída, deve verificar se o produto possui saldo para venda
        //if(estoqueDTO.tipo().equals("S")){
            // rotina que verifica o saldo
            //throw new Exception("Produto "+estoqueDTO.idProduto()+"-"+produtoBusca.descricao()+" sem estoque disponível para venda!");
        //}

        estoque.setIdProduto(estoqueDTO.idProduto());
        estoque.setQuantidade(estoqueDTO.quantidade());
        estoque.setValorUnitario(estoqueDTO.valorUnitario());

        // Registra a movimentação de estoque no banco de dados
        Estoque estoqueSalvo = this.estoqueRepository.save(estoque);

        // Chama API dos Saldos para atualizar o saldo do produto no dia da movimentação
        if(estoqueSalvo != null)
            saldosFeignClient.processarSaldo(
                    new EstoqueDTO(
                            estoqueSalvo.getId(),
                            estoqueSalvo.getDtMovimento(),
                            estoqueSalvo.getTipo(),
                            estoqueSalvo.getIdProduto(),
                            estoqueSalvo.getQuantidade(),
                            estoqueSalvo.getValorUnitario()
                    )
            );

        return estoqueSalvo;
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

            saldosFeignClient.alterarSaldo(
                    estoque.getIdProduto(),
                    estoque.getDtMovimento(),
                    new EstoqueDTO(
                            estoque.getId(),
                            estoque.getDtMovimento(),
                            estoque.getTipo(),
                            estoque.getIdProduto(),
                            estoque.getQuantidade(),
                            estoque.getValorUnitario()
                    )
            );

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
