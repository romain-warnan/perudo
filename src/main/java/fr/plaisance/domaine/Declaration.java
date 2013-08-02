package fr.plaisance.domaine;

import java.util.Objects;

public class Declaration {

	private Integer number;
	private Face face;
	
	public Declaration(){
		// Default constructor
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Face getFace() {
		return face;
	}
	
	public void setFace(Face face) {
		this.face = face;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(this.number, this.face);
    }
	
    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object instanceof Declaration) {
        	Declaration other = (Declaration)object;
            return Objects.equals(this.number, other.number) && Objects.equals(this.face, other.face);
        }
        return false;
    }
    
    @Override
    public String toString(){
    	return "Declaration : " + Objects.toString(this.number) + "x" + Objects.toString(this.face.getValue());
    }
}
