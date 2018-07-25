package fr.plaisance.perudo.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import fr.plaisance.perudo.PerudoUtil;
import fr.plaisance.perudo.domaine.Declaration;
import fr.plaisance.perudo.domaine.Face;
import fr.plaisance.perudo.exception.IllegalFaceValueException;

@Service
public class DeclarationService {

	private boolean strict = StringUtils.equalsIgnoreCase(PerudoUtil.config("cannot.raise.both.value.number"), "true");
	
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
								return current.getNumber().equals(previous.getNumber());
							}
							return current.getNumber() >= previous.getNumber();
						}
						// Valeurs égales
						else if(current.getFace() == previous.getFace()){
							return current.getNumber().intValue() > previous.getNumber().intValue();
						}
						// Problème
						else{
							return false;
						}
				}
		}
	}

	public Declaration createDeclaration(Face face, Integer number) {
		Declaration declaration = new Declaration();
		declaration.setFace(face);
		declaration.setNumber(number);
		return declaration;
	}

	public Declaration createDeclaration(Integer value, Integer number) throws IllegalFaceValueException {
		for (Face face : Face.values()) {
			if(face.getValue().equals(value)){
				return createDeclaration(face, number);
			}
		}
		throw new IllegalFaceValueException();
	}
}
