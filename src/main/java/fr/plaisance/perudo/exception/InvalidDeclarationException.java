package fr.plaisance.perudo.exception;

import fr.plaisance.perudo.PerudoUtil;
import fr.plaisance.perudo.domaine.Declaration;

@SuppressWarnings("serial")
public class InvalidDeclarationException extends PerudoException {
	
	private static final String message = PerudoUtil.message("exception.invalid.declaration");
	
	public InvalidDeclarationException(Declaration declaration){
		super(String.format(message, declaration.getNumber() + "x" + declaration.getFace().getValue()));
	}
}
