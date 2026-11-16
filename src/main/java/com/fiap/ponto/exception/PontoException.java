package com.fiap.ponto.exception;

public class PontoException extends RuntimeException{
    private final String message;

    public PontoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
