package com.utfpr.tikstok.api_produtos.models;

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
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq")
    @SequenceGenerator(name="produto_seq", sequenceName = "produto_seq", allocationSize = 1)
    private Long id;
    private String descricao;
    private String unidade;
}
