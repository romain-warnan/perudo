package fr.plaisance.perudo.rest;

import fr.plaisance.perudo.exception.PerudoException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PerudoException.class)
    @ResponseBody
    public String exception(PerudoException e) {
        return e.getMessage();
    }
}
