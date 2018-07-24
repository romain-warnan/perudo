package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;

@SuppressWarnings("serial")
public class NotEnoughPlayersException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.not.enough.player");
	
	public NotEnoughPlayersException(){
		super(message);
	}
}
