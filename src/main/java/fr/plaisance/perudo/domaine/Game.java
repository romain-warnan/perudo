package fr.plaisance.perudo.domaine;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {

	private Long gameId;
	private List<Player> players;
	private Boolean palifico;
	private List<PerudoMessage> messages;
	private PerudoResult result;
	
	public Game(Long gameId){
		this.gameId = gameId;
	}

	public Long getGameId() {
		return gameId;
	}

	public List<Player> getPlayers() {
		return players;
	}

	
	public void addPlayer(Player player){
		if(CollectionUtils.isEmpty(players)){
			players = new ArrayList<Player>();
		}
		players.add(player);
	}
	
	public Boolean isPalifico() {
		return palifico;
	}

	public void setPalifico(Boolean palifico) {
		this.palifico = palifico;
	}
	
	public List<PerudoMessage> getMessages() {
		return messages;
	}
	
	public void addMessage(PerudoMessageType type, String message){
		if(CollectionUtils.isEmpty(messages)){
			this.messages = new ArrayList<PerudoMessage>();
		}
		this.messages.add(0, new PerudoMessage(type, message));
	}
	
	public PerudoResult getResult() {
		return result;
	}

	public void setResult(PerudoResult result) {
		this.result = result;
	}
	
	public void setResult(PerudoResultType type, Player player) {
		this.result = new PerudoResult(type, player);
	}

	@Override
    public int hashCode() {
        return Objects.hash(this.gameId);
    }
	
    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object instanceof Game) {
        	Game other = (Game)object;
            return Objects.equals(this.gameId, other.gameId);
        }
        return false;
    }
    
    @Override
    public String toString(){
    	return "Game : " + Objects.toString(this.gameId);
    }
}
