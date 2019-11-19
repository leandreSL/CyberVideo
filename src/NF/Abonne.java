package NF;

import java.util.List;

public class Abonne extends Emprunteur{
	String nomAbonne;
	String prenomAbonne;
	List<Genre> restrictions;
	int carte;
	
	void recommanderFilm(Film F) {
		
	}

	public Abonne(CarteBancaire cb, String nomAbonne, String prenomAbonne, List<Genre> restrictions, int carte) {
		super(cb);
		this.nomAbonne = nomAbonne;
		this.prenomAbonne = prenomAbonne;
		this.restrictions = restrictions;
		this.carte = carte;
	}

	public String getNomAbonne() {
		return nomAbonne;
	}

	public void setNomAbonne(String nomAbonne) {
		this.nomAbonne = nomAbonne;
	}

	public String getPrenomAbonne() {
		return prenomAbonne;
	}

	public void setPrenomAbonne(String prenomAbonne) {
		this.prenomAbonne = prenomAbonne;
	}

	public List<Genre> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<Genre> restrictions) {
		this.restrictions = restrictions;
	}
	
}
