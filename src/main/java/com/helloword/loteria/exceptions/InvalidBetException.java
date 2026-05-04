package com.helloword.loteria.exceptions;

// Esta excepción se lanzará cuando los números no cumplan las reglas
// Se registrará como ERROR en el log, y se devolverá un mensaje de error al usuario
public class InvalidBetException extends RuntimeException {
    public InvalidBetException(String message) {
        super(message);
    }
}