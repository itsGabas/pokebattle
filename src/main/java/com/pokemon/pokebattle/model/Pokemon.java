package com.pokemon.pokebattle.model;

import jakarta.persistence.*;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Pokemon {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer hp;
    private Integer atk;
    private Integer def;
    private Integer spd;
    private Integer hpAtual;

    @ManyToMany
    @JoinTable (name = "pokemon_tipo",
                joinColumns = @JoinColumn(name = "pokemon_id"),
                inverseJoinColumns = @JoinColumn(name = "tipo_id"))

    private List<Tipo> tipos;

    @ManyToMany
    @JoinTable (name = "pokemon_movimento",
                joinColumns = @JoinColumn(name = "pokemon_id"),
                inverseJoinColumns = @JoinColumn(name = "movimento_id"))

    private List<Movimento> moves;

    @ManyToOne
    private Treinador treinador;

}
