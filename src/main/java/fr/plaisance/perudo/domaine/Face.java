package fr.plaisance.perudo.domaine;

import fr.plaisance.perudo.exception.IllegalFaceValueException;

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
		return "Face : " + Objects.toString(value);
	}

	public void setValue(Integer number) {
		this.value = number;
	}

	public static Face of(Integer integer) {
	    switch (integer) {
            case 1:
                return Face.PACO;
            case 2:
                return Face.TWO;
            case 3:
                return Face.THREE;
            case 4:
                return Face.FOUR;
            case 5:
                return Face.FIVE;
            case 6:
                return Face.SIX;
        }
        throw new IllegalFaceValueException();
    }
}
