package fr.plaisance.perudo.exception;

import fr.plaisance.PerudoUtil;

@SuppressWarnings("serial")
public class GameAlreadyStartedException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.game.already.started");
	
	public GameAlreadyStartedException(){
		super(message);
	}
}
