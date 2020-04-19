package fr.plaisance.perudo.domaine;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Game {

	private final UUID id;
	private List<Player> players;
	private Boolean palifico;
	private List<PerudoMessage> messages;
	private PerudoResult result;
	
	public Game(UUID id){
		this.id = id;
        players = new ArrayList<>();
	}

	public Game(Game that) {
	    this.id = that.id;
	    this.players = (that.players == null ? null : that.players.stream().map(Player::new).collect(Collectors.toList()));
	    this.palifico = that.palifico;
	    this.messages = that.messages.stream().map(PerudoMessage::new).collect(Collectors.toList());
	    this.result = (that.result == null ? null :  new PerudoResult(that.result));
    }

	public UUID getId() {
		return id;
	}

	public List<Player> getPlayers() {
		return players;
	}

	
	public void addPlayer(Player player){
		if(CollectionUtils.isEmpty(players)){
			players = new ArrayList<>();
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
			this.messages = new ArrayList<>();
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
        return Objects.hash(this.id);
    }
	
    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object instanceof Game) {
        	Game other = (Game)object;
            return Objects.equals(this.id, other.id);
        }
        return false;
    }
    
    @Override
    public String toString(){
    	return "Game : " + Objects.toString(this.id);
    }
}
