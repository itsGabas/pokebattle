package com.pokemon.pokebattle.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String mensagem) {
        super(mensagem);
    }
}
