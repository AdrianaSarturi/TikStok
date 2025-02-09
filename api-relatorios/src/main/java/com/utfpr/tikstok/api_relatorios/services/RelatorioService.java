package com.utfpr.tikstok.api_relatorios.services;

import com.utfpr.tikstok.api_relatorios.dtos.PeriodoParmDTO;
import com.utfpr.tikstok.api_relatorios.dtos.ProdutoPeriodoParmDTO;
import com.utfpr.tikstok.api_relatorios.models.ExtratoProduto;
import com.utfpr.tikstok.api_relatorios.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    public List<ExtratoProduto> getExtratoProduto(ProdutoPeriodoParmDTO parm){
        return relatorioRepository.getExtratoProduto(parm.idProduto(), parm.dtInicial(), parm.dtFinal());
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
