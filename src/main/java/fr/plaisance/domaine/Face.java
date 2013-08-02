package fr.plaisance.domaine;

import java.util.Objects;

public enum Face {

	PACO		(1),
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
		return "Face : " + Objects.toString(value);
	}

	public void setValue(Integer number) {
		this.value = number;
	}
}
