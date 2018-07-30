package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;
import fr.plaisance.perudo.domaine.Player;

@SuppressWarnings("serial")
public class WrongPlayerException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.wrong.player");
	
	public WrongPlayerException(Player player){
		super(String.format(message, player.getName()));
	}
}
