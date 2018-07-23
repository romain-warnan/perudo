package fr.plaisance.perudo.domaine;

public enum PerudoColor {

	BLUE	("b"),
	GREEN	("g"),
	RED		("r"),
	YELLOW	("y"),
	PURPLE	("p"),
	ORANGE	("o");
	
	private String colorId;
	
	private PerudoColor(String colorId){
		this.colorId = colorId;
	}
	
	public String getColorId(){
		return colorId;
	}
}
