package fr.plaisance.exception;

import fr.plaisance.PerudoUtil;

@SuppressWarnings("serial")
public class WrongPlayerException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.wrong.player");
	
	public WrongPlayerException(){
		super(message);
	}
}
