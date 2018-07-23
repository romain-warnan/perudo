package fr.plaisance.perudo.exception;

import fr.plaisance.PerudoUtil;

@SuppressWarnings("serial")
public class TooManyPlayersException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.too.many.player");
	
	public TooManyPlayersException(){
		super(message);
	}
}
