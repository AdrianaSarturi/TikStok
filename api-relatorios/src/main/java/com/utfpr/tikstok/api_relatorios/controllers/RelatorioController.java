package com.utfpr.tikstok.api_relatorios.controllers;

import com.utfpr.tikstok.api_relatorios.dtos.ExtratoProdutoDTO;
import com.utfpr.tikstok.api_relatorios.dtos.PeriodoParmDTO;
import com.utfpr.tikstok.api_relatorios.dtos.ProdutoPeriodoParmDTO;
import com.utfpr.tikstok.api_relatorios.dtos.RelatorioExtratoProdDTO;
import com.utfpr.tikstok.api_relatorios.models.ExtratoProduto;
import com.utfpr.tikstok.api_relatorios.services.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/extrato-produto")
    @Operation(
            summary = "Extrato do Produto",
            description = "Consulta as movimentações de estoque de um produto em um período específico.",
            parameters = {
                    @Parameter(name = "idProduto", description = "ID do produto", required = true),
                    @Parameter(name = "dtInicial", description = "Data inicial do período (formato: dd/MM/yyyy)", required = true),
                    @Parameter(name = "dtFinal", description = "Data final do período (formato: dd/MM/yyyy)", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de movimentações retornada com sucesso.",
                            content = @Content(schema = @Schema(implementation = ExtratoProdutoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Produto sem movimentações de estoque."
                    )
            }
    )
    public ResponseEntity<?> getExtratoProduto(
            @RequestParam Long idProduto,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date dtInicial,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date dtFinal
    ) {
        ProdutoPeriodoParmDTO parm = new ProdutoPeriodoParmDTO(idProduto, dtInicial, dtFinal);
        RelatorioExtratoProdDTO extratoProdDTO = relatorioService.getExtratoProduto(parm);
        if (extratoProdDTO != null && !extratoProdDTO.extrato().isEmpty()) {
            return ResponseEntity.ok().body(extratoProdDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto sem movimentações de estoque para o período informado.");
    }

    @GetMapping("/balanco-produto")
    @Operation(
            summary = "Balanço do Produto",
            description = "Consulta o balanço de estoque de um produto em um período específico."
    )
    public ResponseEntity<?> getBalancoProduto(@Valid @RequestBody ProdutoPeriodoParmDTO parm){
        // relatorioService.getBalancoProduto(parm);
        return ResponseEntity.ok().body("Relatório ainda não implementado.");
    }

    @GetMapping("/precos-produto")
    @Operation(
            summary = "Preços do Produto",
            description = "Consulta os preços de um produto em um período específico."
    )
    public ResponseEntity<?> getPrecosProduto(@Valid @RequestBody ProdutoPeriodoParmDTO parm){
        // relatorioService.getPrecosProduto(parm);
        return ResponseEntity.ok().body("Relatório ainda não implementado.");
    }

    @GetMapping("/balanco-lista")
    @Operation(
            summary = "Balanço Geral de Estoque",
            description = "Consulta o balanço geral de estoque em um período específico."
    )
    public ResponseEntity<?> getBalancoLista(PeriodoParmDTO parm){
        // relatorioService.getBalancoLista(parm);
        return ResponseEntity.ok().body("Relatório ainda não implementado.");
    }

    @GetMapping("/precos-lista")
    @Operation(
            summary = "Preços de Produtos",
            description = "Consulta os preços de todos os produtos em um período específico."
    )
    public ResponseEntity<?> getPrecosLista(PeriodoParmDTO parm){
        // relatorioService.getPrecosLista(parm);
        return ResponseEntity.ok().body("Relatório ainda não implementado.");
    }
}
