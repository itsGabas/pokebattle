package com.pokemon.pokebattle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    @ManyToOne
    private Pokemon atacante;

    @ManyToMany
    private Movimento movimentoUsado;

    private Integer danoCausado;

    @ManyToOne
    private Batalha batalha;
}
