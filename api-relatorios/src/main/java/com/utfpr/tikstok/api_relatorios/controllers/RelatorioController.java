package com.utfpr.tikstok.api_relatorios.controllers;

import com.utfpr.tikstok.api_relatorios.dtos.ExtratoProdutoDTO;
import com.utfpr.tikstok.api_relatorios.dtos.PeriodoParmDTO;
import com.utfpr.tikstok.api_relatorios.dtos.ProdutoPeriodoParmDTO;
import com.utfpr.tikstok.api_relatorios.models.ExtratoProduto;
import com.utfpr.tikstok.api_relatorios.services.RelatorioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/extrato-produto")
    public ResponseEntity<?> getExtratoProduto(@Valid @RequestBody ProdutoPeriodoParmDTO parm){
        List<ExtratoProduto> extratoProd = relatorioService.getExtratoProduto(parm);
        if(extratoProd != null){
            List<ExtratoProdutoDTO> extratos = extratoProd.stream()
                    .map(ep -> new ExtratoProdutoDTO(
                            ep.getId(),
                            ep.getTipo(),
                            ep.getQuantidade(),
                            ep.getValorUnitario(),
                            ep.getValorTotal(),
                            ep.getDtMovimento()
                            )

                    ).collect(Collectors.toList());
            return ResponseEntity.ok().body(extratos);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto sem movimentações de estoque.");
    }

    @GetMapping("/balanco-produto")
    public ResponseEntity<?> getBalancoProduto(@Valid @RequestBody ProdutoPeriodoParmDTO parm){
        // relatorioService.getBalancoProduto(parm);
        return ResponseEntity.ok().body("Relatório ainda não implementado.");
    }

    @GetMapping("/precos-produto")
    public ResponseEntity<?> getPrecosProduto(@Valid @RequestBody ProdutoPeriodoParmDTO parm){
        // relatorioService.getPrecosProduto(parm);
        return ResponseEntity.ok().body("Relatório ainda não implementado.");
    }

    @GetMapping("/balanco-lista")
    public ResponseEntity<?> getBalancoLista(PeriodoParmDTO parm){
        // relatorioService.getBalancoLista(parm);
        return ResponseEntity.ok().body("Relatório ainda não implementado.");
    }

    @GetMapping("/precos-lista")
    public ResponseEntity<?> getPrecosLista(PeriodoParmDTO parm){
        // relatorioService.getPrecosLista(parm);
        return ResponseEntity.ok().body("Relatório ainda não implementado.");
    }
}
