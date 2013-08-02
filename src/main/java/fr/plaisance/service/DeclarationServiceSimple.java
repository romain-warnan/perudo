package fr.plaisance.service;

import org.apache.commons.lang.StringUtils;

import fr.plaisance.PerudoUtil;
import fr.plaisance.domaine.Declaration;
import fr.plaisance.domaine.Face;
import fr.plaisance.exception.IllegalFaceValueException;

public class DeclarationServiceSimple implements DeclarationService {

	private static DeclarationService instance = null;
	private boolean strict = false;
	
	private DeclarationServiceSimple(){
		this.strict = StringUtils.equalsIgnoreCase(
			PerudoUtil.config("cannot.raise.both.value.number"),
			"true"
		);
	}
	
	public static DeclarationService getInstance(){
		if(instance == null){
			instance = new DeclarationServiceSimple();
		}
		return instance;
	}
	
	@Override
	public Boolean isAllowed(Declaration current, Declaration previous, Boolean palifico){
		
		// Cas d'une ouverture
		if(previous == null){
			if(palifico){
				return isAllowedOpeningPalifico(current);
			}
			else{
				return isAllowedOpeningFirst(current);
			} 
		}
		// Cas d'une annonce courante
		else{
			if(palifico){
				return isAllowedPalifico(current, previous);
			}
			else{
				return isAllowed(current, previous);
			}
		}
	}
	
	private Boolean isAllowedOpeningPalifico(Declaration current){
		return true;
	}
	
	private Boolean isAllowedOpeningFirst(Declaration current){
		return current.getFace() != Face.PACO;
	}
	
	private Boolean isAllowedPalifico(Declaration current, Declaration previous){
		
		if(current.getFace() == previous.getFace()){
			return current.getNumber() > previous.getNumber();
		}
		else{
			return false;
		}
	}
	
	private Boolean isAllowed(Declaration current, Declaration previous){
		
		switch (previous.getFace()) {
			// Paco
			case PACO:
				switch (current.getFace()) {
					// Paco
					case PACO:
						return current.getNumber() >= previous.getNumber() + 1;
					// Autre
					default:
						return current.getNumber() >= 2 * previous.getNumber() + 1;
				}	
			// Autre
			default:
				switch (current.getFace()) {
					// Paco
					case PACO:
						return current.getNumber() >= (previous.getNumber() + 1) / 2;
					// Autre
					default:
						// Valeur strictement inférieure
						if(current.getFace().getValue() < previous.getFace().getValue()){
							return false;
						}
						// Valeur strictement supérieure
						else if(current.getFace().getValue() > previous.getFace().getValue()){
							if(this.strict){
								return current.getNumber() == previous.getNumber();
							}
							return current.getNumber() >= previous.getNumber();
						}
						// Valeurs égales
						else if(current.getFace() == previous.getFace()){
							return current.getNumber() > previous.getNumber();
						}
						// Problème
						else{
							return false;
						}
				}
		}
	}

	@Override
	public Declaration createDeclaration(Face face, Integer number) {
		Declaration declaration = new Declaration();
		declaration.setFace(face);
		declaration.setNumber(number);
		return declaration;
	}

	@Override
	public Declaration createDeclaration(Integer value, Integer number) throws IllegalFaceValueException{
		for (Face face : Face.values()) {
			if(face.getValue() == value){
				return createDeclaration(face, number);
			}
		}
		throw new IllegalFaceValueException();
	}
}
