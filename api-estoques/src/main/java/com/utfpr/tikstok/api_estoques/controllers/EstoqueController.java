package com.utfpr.tikstok.api_estoques.controllers;

import com.utfpr.tikstok.api_estoques.dtos.EstoqueDTO;
import com.utfpr.tikstok.api_estoques.models.Estoque;
import com.utfpr.tikstok.api_estoques.services.EstoqueService;
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
    public ResponseEntity<?> lancarEstoque(@Valid @RequestBody EstoqueDTO estoqueDTO){
        Estoque estoque = estoqueService.lancarEstoque(estoqueDTO);

        if(estoque != null){
            return ResponseEntity.ok(estoque);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro efetuar lançamento de estoque!");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.estoqueService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEstoqueById(@PathVariable Long id){
        Estoque estoqueBusca = estoqueService.getEstoqueById(id);
        if(estoqueBusca == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estoque não encontrado!");
        else
            return ResponseEntity.ok().body(estoqueBusca);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if(this.estoqueService.deleteById(id))
            return ResponseEntity.ok().body("Lançamento de estoque deletado com sucesso!");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível deletar o registro!");
    }
}
