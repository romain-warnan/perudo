package fr.plaisance.exception;

import fr.plaisance.PerudoUtil;

@SuppressWarnings("serial")
public class GameNotStartedException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.game.not.started");
	
	public GameNotStartedException(){
		super(message);
	}
}
