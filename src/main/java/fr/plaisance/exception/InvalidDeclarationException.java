package fr.plaisance.exception;

import fr.plaisance.PerudoUtil;

@SuppressWarnings("serial")
public class InvalidDeclarationException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.invalid.declaration");
	
	public InvalidDeclarationException(){
		super(message);
	}
}
