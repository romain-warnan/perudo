package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;

@SuppressWarnings("serial")
public class ExpiredIdentifierException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.expired.identifier");
	
	public ExpiredIdentifierException(){
		super(message);
	}
}
