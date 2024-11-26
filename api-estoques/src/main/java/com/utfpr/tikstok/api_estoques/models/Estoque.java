package com.utfpr.tikstok.api_estoques.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estoque_seq")
    @SequenceGenerator(name="estoque_seq", sequenceName = "estoque_seq", allocationSize = 1)
    private Long id;
    private Date dtMovimento;
    private String tipo; // [E] Entrada - [S] Saída
    private Double valorTotal;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL)
    private List<ItemEstoque> itens;
}
