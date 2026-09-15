package com.pokemon.pokebattle.dto.request;

import java.util.List;

public record PokemonRequestDTO (String nome, Integer hp, Integer atk, Integer def, Integer spd, List<Long> tipoIds, List<Long> moveIds, Long treinadorId){
}
