package fr.plaisance.domaine;

public class PerudoResult {

	private PerudoResultType type;
	private Player player;
	
	public PerudoResult(PerudoResultType type, Player player) {
		super();
		this.type = type;
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public PerudoResultType getType(){
		return type;
	}
}
