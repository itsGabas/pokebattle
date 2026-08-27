package com.pokemon.pokebattle.repository;

import com.pokemon.pokebattle.model.Movimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
    List<Movimento> findByTipoId(Long tipoId);
}
