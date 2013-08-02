package fr.plaisance.exception;

import fr.plaisance.PerudoUtil;

@SuppressWarnings("serial")
public class ExpiredIdentifierException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.expired.identifier");
	
	public ExpiredIdentifierException(){
		super(message);
	}
}
