package NF;

import java.util.List;

public class Abonne extends Emprunteur{
	String nomAbonne;
	String prenomAbonne;
	List<Genre> restrictions;
	double solde;
	int carte;
	
	void recommanderFilm(Film F) {
		
	}



	public Abonne(long cb, String nomAbonne, String prenomAbonne, List<Genre> restrictions, double solde,
			int carte) {
		super(cb);
		this.nomAbonne = nomAbonne;
		this.prenomAbonne = prenomAbonne;
		this.restrictions = restrictions;
		this.solde = solde;
		this.carte = carte;
	}

	@Override
	public String toString() {
		String chaineRestrictions = String.join("`", Genre.toStringArray(restrictions));
		return carte + "|" + cb + "|" + nomAbonne + "|" + prenomAbonne + "|" + chaineRestrictions
				+ "|" + solde + "\n";
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
	
	public double getSolde() {
		return solde;
	}



	public void setSolde(double solde) {
		this.solde = solde;
	}



	public int getCarte() {
		return carte;
	}



	public void setCarte(int carte) {
		this.carte = carte;
	}
	
}

