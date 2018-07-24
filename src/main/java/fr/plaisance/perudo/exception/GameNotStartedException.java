package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;

@SuppressWarnings("serial")
public class GameNotStartedException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.game.not.started");
	
	public GameNotStartedException(){
		super(message);
	}
}
