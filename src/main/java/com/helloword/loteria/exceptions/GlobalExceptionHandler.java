package com.helloword.loteria.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. REQUISITO 3.d: Cuando una apuesta no es adecuada -> ERROR
    @ExceptionHandler(InvalidBetException.class)
    public ResponseEntity<Object> handleInvalidBet(InvalidBetException ex) {
        // Registro del log de nivel ERROR en application.log
        log.error("ERROR: Intento de registro de apuesta no válida. Detalles de validación fallidos.");

        return new ResponseEntity<>(createBody(ex.getMessage()), HttpStatus.BAD_REQUEST); // 400
    }

    // 2. REQUISITO 3.e: Si un usuario repite su apuesta -> WARNING
    @ExceptionHandler(DuplicateBetException.class)
    public ResponseEntity<Object> handleDuplicateBet(DuplicateBetException ex) {
        // Registro del log de nivel WARN en application.log
        log.warn("WARNING: El usuario ha introducido una combinación que ya existe en sus apuestas.");

        // El enunciado dice que se indica el aviso, pero se suele permitir o informar con un 200 OK
        return new ResponseEntity<>(createBody(ex.getMessage()), HttpStatus.OK);
    }

    // 3. REQUISITO 1: No registrar varias veces el mismo ID -> CONFLICT
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserExists(UserAlreadyExistsException ex) {
        log.info("Intento de registro de usuario con ID ya existente.");
        return new ResponseEntity<>(createBody(ex.getMessage()), HttpStatus.CONFLICT); // 409
    }

    // 4. EXTRA: Usuario no encontrado (para los GET) -> NOT FOUND
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        log.info("Usuario no encontrado en el sistema.");
        return new ResponseEntity<>(createBody(ex.getMessage()), HttpStatus.NOT_FOUND); // 404
    }

    // 5. CAPTURA GENÉRICA: Para cualquier otro error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        log.error("ERROR CRÍTICO NO CONTROLADO: ", ex);
        return new ResponseEntity<>(createBody("Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Método auxiliar para crear un cuerpo de respuesta JSON ordenado
     */
    private Map<String, Object> createBody(String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        return body;
    }
}