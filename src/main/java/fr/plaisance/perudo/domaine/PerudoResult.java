package fr.plaisance.perudo.domaine;

public class PerudoResult {

	private PerudoResultType type;
	private Player player;
	
	public PerudoResult(PerudoResultType type, Player player) {
		super();
		this.type = type;
		this.player = player;
	}

	public PerudoResult(PerudoResult that) {
		this.type = that.type;
		this.player = new Player(that.player);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public PerudoResultType getType(){
		return type;
	}
}
