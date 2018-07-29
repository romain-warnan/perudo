package fr.plaisance.perudo.rest;

import fr.plaisance.perudo.exception.PerudoException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PerudoException.class)
    public String exception(PerudoException e) {
        return e.getMessage();
    }
}
