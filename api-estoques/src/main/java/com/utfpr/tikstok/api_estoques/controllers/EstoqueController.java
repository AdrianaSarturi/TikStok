package com.utfpr.tikstok.api_estoques.controllers;

import com.utfpr.tikstok.api_estoques.dtos.EstoqueDTO;
import com.utfpr.tikstok.api_estoques.dtos.EstoqueUpdateDTO;
import com.utfpr.tikstok.api_estoques.models.Estoque;
import com.utfpr.tikstok.api_estoques.services.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private EstoqueService estoqueService;

    public EstoqueController(EstoqueService service){
        this.estoqueService = service;
    }

    @PostMapping
    @Operation(
            summary = "Lançar estoque",
            description = "Efetua o lançamento de uma movimentação de estoque no sistema. " +
                    "O campo `id` é gerado automaticamente pelo sistema, não sendo necessário no corpo da requisição. " +
                    "O `tipo` de movimentação pode ser entrada (E) ou saída (S).",
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
                            description = "Estoque lançado com sucesso.",
                            content = @Content(schema = @Schema(implementation = Estoque.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos."
                    )
            }
    )
    public ResponseEntity<?> lancarEstoque(@Valid @RequestBody EstoqueDTO estoqueDTO){
        Estoque estoque = estoqueService.lancarEstoque(estoqueDTO);

        if(estoque != null){
            return ResponseEntity.created(null).body(estoque);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro efetuar lançamento de estoque!");
        }
    }

    @GetMapping
    @Operation(
            summary = "Listar estoques",
            description = "Lista todas as movimentações de estoques registradas no sistema."
    )
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.estoqueService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar estoque",
            description = "Busca os dados de uma determinada movimentação com base no seu código identificador."
    )
    public ResponseEntity<?> getEstoqueById(@PathVariable Long id){
        Estoque estoqueBusca = estoqueService.getEstoqueById(id);
        if(estoqueBusca == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estoque não encontrado!");
        else
            return ResponseEntity.ok().body(estoqueBusca);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Alterar estoque",
            description = "Altera os dados de estoque de um determinado lançamento, com base em seu `id`. "+
                    "Os campos alteráveis são `dtMovimento`, `quantidade` e `valorUnitario`, mas nenhum deles é obrigatório. "+
                    "O campo que não for informado, não será alterado.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Corpo da requisição",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EstoqueUpdateDTO.class)
                    )
            )
    )
    public ResponseEntity<?> alterarEstoque(@PathVariable Long id, @Valid @RequestBody EstoqueUpdateDTO estoqueUpdateDTO){
        Estoque estoque = estoqueService.alterarEstoque(estoqueUpdateDTO, id);
        if(estoque != null){
            return ResponseEntity.ok().body(estoque);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível alterar o registro de estoque.");
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir estoque",
            description = "Deleta uma movimentação de estoque com base no seu código identificador.",
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
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if(this.estoqueService.deleteById(id))
            return ResponseEntity.ok().body("Lançamento de estoque deletado com sucesso!");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível deletar o registro!");
    }
}
