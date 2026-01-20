package com.estacionamento_api.exception;

public class codigoUniqueViolationException extends RuntimeException {
    public codigoUniqueViolationException(String message) {
        super(message);
    }
}
