package com.helloword.loteria.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Requisito 3d: Apuesta no adecuada = ERROR
    @ExceptionHandler(InvalidBetException.class)
    public ResponseEntity<String> handleInvalidBet(InvalidBetException ex) {
        // En INFO no mostramos datos, pero aquí registramos el ERROR
        log.error("ERROR: Se ha intentado registrar una apuesta no válida (Reglas 1-49, 6 números)");
        // Devolvemos un mensaje de error al usuario, con un status de BAD_REQUEST
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Requisito 3e: Apuesta repetida = WARNING
    // Aquí registramos el WARNING, pero no es un error crítico, así que devolvemos un OK o 201
    @ExceptionHandler(DuplicateBetException.class)
    public ResponseEntity<String> handleDuplicateBet(DuplicateBetException ex) {
        log.warn("WARNING: El usuario ha repetido una combinación de apuesta");
        //el enunciado, se permite registrarla, así que devolvemos un OK o 201
        return new ResponseEntity<>("Apuesta repetida registrada con aviso", HttpStatus.OK);
    }
}