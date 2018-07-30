package fr.plaisance.perudo.rest;

import fr.plaisance.perudo.exception.ErrorDetails;
import fr.plaisance.perudo.exception.PerudoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class PerudoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PerudoException.class)
    public ResponseEntity<ErrorDetails> perudoException(PerudoException e) {
        return ResponseEntity
            .badRequest()
            .body(ErrorDetails.of(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> unexpectedException(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorDetails.of(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
