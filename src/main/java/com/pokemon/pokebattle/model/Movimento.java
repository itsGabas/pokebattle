package com.pokemon.pokebattle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Movimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer danoBase;
    private Integer precisao;

    @ManyToOne
    private Tipo tipo;

    @ManyToMany(mappedBy = "moves")
    private List<Pokemon> pokemons;

}
