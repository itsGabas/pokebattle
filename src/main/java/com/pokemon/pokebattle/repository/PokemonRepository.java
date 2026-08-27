package com.pokemon.pokebattle.repository;

import com.pokemon.pokebattle.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findByTreinadorId (Long id);
}
