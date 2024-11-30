package com.utfpr.tikstok.api_estoques.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
