package fr.plaisance.perudo.domaine;

public enum PerudoColor {

	BLUE	("b"),
	GREEN	("g"),
	RED		("r"),
	YELLOW	("y"),
	PURPLE	("p"),
	ORANGE	("o");
	
	private String id;
	
	private PerudoColor(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
}
