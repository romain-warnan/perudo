package fr.plaisance.perudo.exception;

import fr.plaisance.PerudoUtil;

@SuppressWarnings("serial")
public class IllegalFaceValueException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.illegal.face.value");
	
	public IllegalFaceValueException(){
		super(message);
	}
}
