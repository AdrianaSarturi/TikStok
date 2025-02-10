package com.utfpr.tikstok.api_relatorios.services;

import com.utfpr.tikstok.api_relatorios.dtos.ExtratoProdutoDTO;
import com.utfpr.tikstok.api_relatorios.dtos.PeriodoParmDTO;
import com.utfpr.tikstok.api_relatorios.dtos.ProdutoPeriodoParmDTO;
import com.utfpr.tikstok.api_relatorios.dtos.RelatorioExtratoProdDTO;
import com.utfpr.tikstok.api_relatorios.models.ExtratoProduto;
import com.utfpr.tikstok.api_relatorios.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    public RelatorioExtratoProdDTO getExtratoProduto(ProdutoPeriodoParmDTO parm){
        RelatorioExtratoProdDTO extrato = null;
        List<ExtratoProduto> extratoProdutos = relatorioRepository.getExtratoProduto(parm.idProduto(), parm.dtInicial(), parm.dtFinal());
        if(extratoProdutos != null){
            extrato = new RelatorioExtratoProdDTO(
                    "Extrato de estoque do produto "+parm.idProduto()+". Período: "+parm.dtInicial()+" a "+parm.dtFinal()+".",
                    extratoProdutos.stream()
                            .map(ep -> new ExtratoProdutoDTO(
                                    ep.getId(),
                                    ep.getTipo(),
                                    ep.getQuantidade(),
                                    ep.getValorUnitario(),
                                    ep.getValorTotal(),
                                    ep.getDtMovimento()
                            ))
                            .collect(Collectors.toList()));
                    return extrato;
        }
        return null;
    }

    public void getBalancoProduto(ProdutoPeriodoParmDTO parm){

    }

    public void getPrecosProduto(ProdutoPeriodoParmDTO parm){

    }

    public void getBalancoLista(PeriodoParmDTO parm){

    }

    public void getPrecosLista(PeriodoParmDTO parm){

    }
}
