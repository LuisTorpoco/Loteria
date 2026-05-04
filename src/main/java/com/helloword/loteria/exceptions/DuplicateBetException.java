package com.helloword.loteria.exceptions;

// Esta se lanzará cuando el usuario repita una apuesta que ya tenía
// lanzada como WARNING, pero se permite registrarla, así que no es un error crítico
public class DuplicateBetException extends RuntimeException {
    public DuplicateBetException(String message) {
        super(message);
    }
}