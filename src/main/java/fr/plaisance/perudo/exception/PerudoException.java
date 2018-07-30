package fr.plaisance.perudo.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class PerudoException extends RuntimeException {

	public PerudoException(String message){
		super(message);
	}
}
