package fr.plaisance.perudo.domaine;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class Player {

	private UUID id;
	private String name;
	private PerudoAction action;
	private Collection<Face> faces;
	private Boolean active;
	private Declaration declaration;
	private PerudoColor color;
	
	public Player(UUID id) {
		super();
		this.id = id;
	}

    public Player(Player that) {
	    this.id = that.id;
	    this.name = that.name;
	    this.action = that.action;
	    this.faces = (that.faces == null ? null : new ArrayList<>(that.faces));
	    this.active = that.active;
	    this.declaration = (that.declaration == null ? null : new Declaration(that.declaration));
	    this.color = that.color;
    }

    public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Face> getFaces() {
		return faces;
	}
	
	public void addFace(Face face){
		if(CollectionUtils.isEmpty(faces)){
			faces = new ArrayList<>();
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

    public void setFaces(Collection<Face> faces) {
        this.faces = faces;
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
        if (object instanceof Player) {
        	Player other = (Player)object;
            return Objects.equals(this.id, other.id);
        }
        return false;
    }
    
    @Override
    public String toString(){
    	return "Player : " + Objects.toString(this.id) + " " + faces;
    }
}
