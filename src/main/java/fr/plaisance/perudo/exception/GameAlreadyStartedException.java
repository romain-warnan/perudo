package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;
import fr.plaisance.perudo.domaine.Game;

@SuppressWarnings("serial")
public class GameAlreadyStartedException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.game.already.started");
	
	public GameAlreadyStartedException(Game game){
		super(String.format(message, game.getId()));
	}
}
