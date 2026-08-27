package com.pokemon.pokebattle.model;

import com.pokemon.pokebattle.model.enums.StatusBatalha;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Batalha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pokemon pokemonA;

    @ManyToOne
    private Pokemon pokemonB;

    @Enumerated(EnumType.STRING)
    private StatusBatalha status;

    @OneToMany(mappedBy = "batalha")
    private List<Pokemon> turnos;
}
