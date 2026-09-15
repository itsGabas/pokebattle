package com.pokemon.pokebattle.dto.response;

import java.util.List;

public record PokemonResponseDTO (Long id, String nome, Integer hp, Integer atk, Integer def, Integer spd, List<String> tipos, List<String> moves) {
}
