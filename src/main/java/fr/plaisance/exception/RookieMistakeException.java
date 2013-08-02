package fr.plaisance.exception;

import fr.plaisance.PerudoUtil;

@SuppressWarnings("serial")
public class RookieMistakeException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.rookie.mistake");
	
	public RookieMistakeException(){
		super(message);
	}
}
