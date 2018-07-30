package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;
import fr.plaisance.perudo.domaine.Player;

@SuppressWarnings("serial")
public class RookieMistakeException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.rookie.mistake");
	
	public RookieMistakeException(Player player){
		super(String.format(message, player.getName()));
	}
}
