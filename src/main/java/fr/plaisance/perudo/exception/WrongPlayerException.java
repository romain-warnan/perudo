package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;

@SuppressWarnings("serial")
public class WrongPlayerException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.wrong.player");
	
	public WrongPlayerException(){
		super(message);
	}
}
