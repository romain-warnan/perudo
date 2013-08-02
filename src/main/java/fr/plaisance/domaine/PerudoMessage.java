package fr.plaisance.domaine;

public class PerudoMessage {

	private PerudoMessageType type;
	private String message;
	
	public PerudoMessage(PerudoMessageType type, String message) {
		super();
		this.type = type;
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getType(){
		return type.type();
	}
}
