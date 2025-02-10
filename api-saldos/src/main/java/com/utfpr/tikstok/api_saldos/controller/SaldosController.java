package com.utfpr.tikstok.api_saldos.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<?> consultaSaldo(@PathVariable Long idProduto, @PathVariable Date data){
    	SaldosKey saldoId = new SaldosKey();
    	saldoId.setIdProduto(idProduto);
    	saldoId.setData(this.dataSemHora(data));
    	
        Saldos saldoBusca = saldoService.consultaSaldo(saldoId);
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
    public ResponseEntity<?> alterarSaldo(@PathVariable Long idProduto, @PathVariable Date data, @Valid @RequestBody EstoqueDTO estoqueDTO){
        Saldos saldo = saldoService.alterarSaldo(idProduto, data, estoqueDTO);
        if(saldo != null){
            return ResponseEntity.ok().body(saldo);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível alterar o registro do saldo.");
    }
    @DeleteMapping("/{idProduto}/{data}")
    @Operation(
            summary = "Excluir saldo",
            description = "Deleta o saldo que foi movimentado, com base no seu idProduto e a sua data.",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Corpo da requisição",
                            required = true,
                            content = @Content(
                                    schema = @Schema(implementation = EstoqueDTO.class)
                            )
                    ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estoque deletado com sucesso."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Não foi possível deletar o registro."
                    )
            }
    )
    public ResponseEntity<?> excluirSaldo(@PathVariable Long idProduto, @PathVariable Date data, @Valid @RequestBody EstoqueDTO estoqueDTO){
    	Saldos saldo = this.saldoService.excluirSaldo(idProduto, data, estoqueDTO);
        if(saldo != null){
            return ResponseEntity.ok().body(saldo);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível deletar o registro!");
    }

	private Date dataSemHora(Date dataComHora) {
        // Converter Date para LocalDateTime
        LocalDateTime localDateTime = dataComHora.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Zerar horas, minutos e segundos
        LocalDateTime dataZerada = localDateTime.toLocalDate().atStartOfDay();

        // Converter de volta para Date (se necessário)
        Date novaData = Date.from(dataZerada.atZone(ZoneId.systemDefault()).toInstant());
        
        return novaData;
	}

}
