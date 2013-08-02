package fr.plaisance.service;

import fr.plaisance.domaine.Declaration;
import fr.plaisance.domaine.Face;
import fr.plaisance.exception.IllegalFaceValueException;

public interface DeclarationService {

	Boolean isAllowed(Declaration current, Declaration previous, Boolean palifico);
	Declaration createDeclaration(Face face, Integer number);
	Declaration createDeclaration(Integer value, Integer number) throws IllegalFaceValueException;
}