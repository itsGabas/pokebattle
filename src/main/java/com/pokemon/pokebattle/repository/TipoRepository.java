package com.pokemon.pokebattle.repository;

import com.pokemon.pokebattle.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
    Tipo findByNome(String nome);
}
