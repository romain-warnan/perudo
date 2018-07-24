package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;

@SuppressWarnings("serial")
public class RookieMistakeException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.rookie.mistake");
	
	public RookieMistakeException(){
		super(message);
	}
}
