package fr.plaisance.domaine;

public enum PerudoMessageType {

	INFO("info"), ERROR("error");
	
	private String type;
	
	private PerudoMessageType(String type){
		this.type = type;
	}
	
	public String type(){
		return type;
	}
}
