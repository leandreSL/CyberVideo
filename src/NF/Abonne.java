package NF;

import java.util.List;

public class Abonne{
	private String nomAbonne;
	private String prenomAbonne;
	private List<Genre> restrictions;
	private double solde;
	private int carteAbonne;
	private long carteBleue;
	
	public Abonne(String nomAbonne, String prenomAbonne, List<Genre> restrictions, double solde, int carteAbonne,
			long carteBleue) {
		this.nomAbonne = nomAbonne;
		this.prenomAbonne = prenomAbonne;
		this.restrictions = restrictions;
		this.solde = solde;
		this.carteAbonne = carteAbonne;
		this.carteBleue = carteBleue;
	}

	public Abonne(String nomAbonne, String prenomAbonne, List<Genre> restrictions, double solde, long carteBleue) {
		this.nomAbonne = nomAbonne;
		this.prenomAbonne = prenomAbonne;
		this.restrictions = restrictions;
		this.solde = solde;
		this.carteBleue = carteBleue;
		this.carteAbonne = -1;

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

	public long getCarteBleue() {
		return carteBleue;
	}

	public void setCarteBleue(long carteBleue) {
		this.carteBleue = carteBleue;
	}

	public int getCarteAbonne() {
		return carteAbonne;
	}

	public void setCarteAbonne(int carteAbonne) {
		this.carteAbonne = carteAbonne;
	}

	@Override
	public String toString() {
		String chaineRestrictions = String.join("`", Genre.toStringArray(restrictions));
		return nomAbonne + "|" + prenomAbonne + "|" + chaineRestrictions + "|" + solde + "|" + carteAbonne + "|" + carteBleue + "\n";
	}

	public String print() {
		String chaineRestrictions = String.join("|", Genre.toStringArray(restrictions));;
		return nomAbonne + " " + prenomAbonne + " " + chaineRestrictions
				+ " " + solde + " " + carteAbonne + " " + carteBleue;
	}	
}

