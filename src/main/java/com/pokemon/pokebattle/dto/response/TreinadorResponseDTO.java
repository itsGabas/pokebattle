package com.pokemon.pokebattle.dto.response;

import java.util.List;

public record TreinadorResponseDTO (Long id, String nome, List<String> pokemons) {
}
