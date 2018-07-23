package fr.plaisance.perudo.domaine;

public enum PerudoAction {

	DUDO("dudo"),
	CALZA("calza"),
	BET("bet");
	
	private String action;
	
	private PerudoAction(String action){
		this.action = action;
	}

	public String action() {
		return action;
	}
}
