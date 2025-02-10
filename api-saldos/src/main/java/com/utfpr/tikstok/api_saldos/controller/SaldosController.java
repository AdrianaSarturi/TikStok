package com.utfpr.tikstok.api_saldos.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.tikstok.api_saldos.dtos.EstoqueDTO;
import com.utfpr.tikstok.api_saldos.models.Estoque;
import com.utfpr.tikstok.api_saldos.models.Saldos;
import com.utfpr.tikstok.api_saldos.models.SaldosKey;
import com.utfpr.tikstok.api_saldos.services.SaldosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/saldos")
public class SaldosController {
	private SaldosService saldoService;
	
	public SaldosController(SaldosService service) {
		this.saldoService = service;
	}
	
	@PostMapping
	@Operation(
            summary = "Lançar saldo",
            description = "Efetua o lançamento de uma movimentação nos saldos do sistema. " +
                    "Utiliza o campo idProduto e data como chave composta. ",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Corpo da requisição",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EstoqueDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Saldo lançado com sucesso.",
                            content = @Content(schema = @Schema(implementation = Estoque.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos."
                    )
            }
    )
    public ResponseEntity<?> lancarSaldo(@Valid @RequestBody EstoqueDTO estoqueDTO) throws Exception {
		Saldos saldo = saldoService.lancarSaldo(estoqueDTO);

        if(saldo != null){
            return ResponseEntity.created(null).body(saldo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao efetuar o lançamento do saldo!");
        }
    }
	
    @GetMapping("/{idProduto}/{data}")
    @Operation(
            summary = "Buscar Saldo",
            description = "Busca os dados de um determinado saldo com base no seu idProduto e a sua data."
    )
    public ResponseEntity<?> getEstoqueById(@PathVariable Long idProduto, @PathVariable Date data){
    	SaldosKey saldoId = new SaldosKey(idProduto, data);
        Saldos saldoBusca = saldoService.getSaldoById(saldoId);
        if(saldoBusca == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Saldo não encontrado!");
        else
            return ResponseEntity.ok().body(saldoBusca);
    }

    @PutMapping("/{idProduto}/{data}")
    @Operation(
            summary = "Alterar saldo",
            description = "Altera os dados de saldo baseado em um determinado lançamento de estoque, com base no seu idProduto e a sua data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Corpo da requisição",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EstoqueDTO.class)
                    )
            )
    )
    public ResponseEntity<?> alterarEstoque(@PathVariable Long idProduto, @PathVariable Date data, @Valid @RequestBody EstoqueDTO estoqueDTO){
        Saldos saldo = saldoService.alterarSaldo(idProduto, data, estoqueDTO);
        if(saldo != null){
            return ResponseEntity.ok().body(saldo);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível alterar o registro do saldo.");
    }

}
