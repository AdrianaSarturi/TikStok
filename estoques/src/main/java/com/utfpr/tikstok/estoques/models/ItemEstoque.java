package com.utfpr.tikstok.estoques.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ItemEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemestoque_seq")
    @SequenceGenerator(name="itemestoque_seq", sequenceName = "itemestoque_seq", allocationSize = 1)
    private Long id;
    private Long idProduto;
    private Integer quantidade;
    private Double valor;

    @ManyToOne
    @JoinColumn(name="estoque_id")
    @JsonIgnore
    private Estoque estoque;
}
