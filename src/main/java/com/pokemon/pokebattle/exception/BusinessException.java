package com.pokemon.pokebattle.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
