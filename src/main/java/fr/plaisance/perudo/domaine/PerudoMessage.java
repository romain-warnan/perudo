package fr.plaisance.perudo.domaine;

public class PerudoMessage {

	private PerudoMessageType type;
	private String message;
	
	public PerudoMessage(PerudoMessageType type, String message) {
		super();
		this.type = type;
		this.message = message;
	}

	public PerudoMessage(PerudoMessage that) {
	    this.type = that.type;
	    this.message = that.message;
    }
	
	public String getMessage(){
		return message;
	}
	
	public String getType(){
		return type.type();
	}
}
