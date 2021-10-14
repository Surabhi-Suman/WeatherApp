package com.weather.api.exception.handler;

public class InvalidZipcodeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidZipcodeException(String errorMessage) {
        super(errorMessage);
    }

}
