package fr.plaisance.perudo.domaine;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Player {

	private Long playerId;
	private String playerName;
	private PerudoAction action;
	private Collection<Face> faces;
	private Boolean active;
	private Declaration declaration;
	private PerudoColor color;
	
	public Player(Long playerId) {
		super();
		this.playerId = playerId;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Collection<Face> getFaces() {
		return faces;
	}
	
	public void addFace(Face face){
		if(CollectionUtils.isEmpty(faces)){
			faces = new ArrayList<Face>();
		}
		faces.add(face);
	}
	
    public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(Declaration declaration) {
		this.declaration = declaration;
	}
	
	public PerudoAction getAction() {
		return action;
	}

	public void setAction(PerudoAction action) {
		this.action = action;
	}
	
	public PerudoColor getColor() {
		return color;
	}

	public void setColor(PerudoColor color) {
		this.color = color;
	}

	@Override
    public int hashCode() {
        return Objects.hash(this.playerId);
    }
	
    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object instanceof Player) {
        	Player other = (Player)object;
            return Objects.equals(this.playerId, other.playerId);
        }
        return false;
    }
    
    @Override
    public String toString(){
    	return "Player : " + Objects.toString(this.playerId) + " " + faces;
    }
}
