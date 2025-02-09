package com.utfpr.tikstok.api_produtos.controllers;

import com.utfpr.tikstok.api_produtos.dtos.ProdutoDTO;
import com.utfpr.tikstok.api_produtos.dtos.ProdutoEstoqueUpdateDTO;
import com.utfpr.tikstok.api_produtos.models.Produto;
import com.utfpr.tikstok.api_produtos.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @Operation(
            summary = "Listar produtos",
            description = "Retorna uma lista de todos os produtos cadastrados no sistema."
    )
    public ResponseEntity<List<ProdutoDTO>> getAll() {
        return ResponseEntity.ok(produtoService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna os detalhes de um produto com base no seu ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Produto encontrado.",
                            content = @Content(schema = @Schema(implementation = ProdutoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Produto não encontrado."
                    )
            }
    )
    public ResponseEntity<ProdutoDTO> getById(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.getById(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar produto",
            description = "Cadastra um novo produto no sistema.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo produto.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProdutoDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Produto cadastrado com sucesso.",
                            content = @Content(schema = @Schema(implementation = Produto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos."
                    )
            }
    )
    public ResponseEntity<?> addOne(@Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = produtoService.createProduto(produtoDTO);
        if (produto != null)
            return ResponseEntity.created(null).body(produto);
        else
            return null;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os detalhes de um produto com base no ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do produto.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProdutoDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Produto atualizado com sucesso.",
                            content = @Content(schema = @Schema(implementation = Produto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Produto não encontrado."
                    )
            }
    )
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto produtoAtualizado = produtoService.updateProduto(id, produtoDTO);
        if (produtoAtualizado != null) {
            return ResponseEntity.ok(produtoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir produto",
            description = "Remove um produto com base no ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Produto excluído com sucesso."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Produto não encontrado."
                    )
            }
    )
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        boolean deleted = produtoService.deleteProduto(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/atualizar-estoque")
    @Operation(
            summary = "Atualizar estoque de produto",
            description = "Atualiza o estoque de um produto com base no ID, adicionando ou removendo quantidades.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para a atualização de estoque.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProdutoEstoqueUpdateDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de entrada:",
                                            value = "{\"tipo\": \"E\", \"qtdEstoque\": 10.0}",
                                            summary = "Entrada de estoque válida."
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estoque atualizado com sucesso.",
                            content = @Content(schema = @Schema(implementation = Produto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Produto não encontrado."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos para a atualização do estoque."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor."
                    )
            }
    )
    public ResponseEntity<?> atualizarEstoque(@PathVariable Long id,
                                              @RequestBody @Valid ProdutoEstoqueUpdateDTO estoqueDTO) {
        Produto produtoAtualizado = produtoService.atualizarEstoque(id, estoqueDTO.tipo(), estoqueDTO.qtdEstoque());
        return ResponseEntity.ok(produtoAtualizado);
    }

}
