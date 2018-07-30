package fr.plaisance.perudo.domaine;

import fr.plaisance.perudo.exception.IllegalFaceValueException;

import java.util.Arrays;
import java.util.Objects;

public enum Face {

	PACO	(1),
	TWO		(2),
	THREE	(3),
	FOUR	(4),
	FIVE	(5), 
	SIX		(6);
	
	private Integer value;

	private Face(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	
	@Override
	public String toString(){
		return String.valueOf(value);
	}

	public void setValue(Integer number) {
		this.value = number;
	}

	public static Face of(String value) {
	    return Arrays.stream(Face.values())
            .filter(f -> f.getValue().equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalFaceValueException(value));
    }
}
