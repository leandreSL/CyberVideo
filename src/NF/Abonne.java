package NF;

import java.util.List;
import java.util.Random;

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
		this.carteAbonne = new Random().nextInt(Integer.MAX_VALUE);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + carteAbonne;
		result = prime * result + (int) (carteBleue ^ (carteBleue >>> 32));
		result = prime * result + ((nomAbonne == null) ? 0 : nomAbonne.hashCode());
		result = prime * result + ((prenomAbonne == null) ? 0 : prenomAbonne.hashCode());
		result = prime * result + ((restrictions == null) ? 0 : restrictions.hashCode());
		long temp;
		temp = Double.doubleToLongBits(solde);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abonne other = (Abonne) obj;
		if (carteAbonne != other.carteAbonne)
			return false;
		if (carteBleue != other.carteBleue)
			return false;
		if (nomAbonne == null) {
			if (other.nomAbonne != null)
				return false;
		} else if (!nomAbonne.equals(other.nomAbonne))
			return false;
		if (prenomAbonne == null) {
			if (other.prenomAbonne != null)
				return false;
		} else if (!prenomAbonne.equals(other.prenomAbonne))
			return false;
		if (restrictions == null) {
			if (other.restrictions != null)
				return false;
		} else if (!restrictions.equals(other.restrictions))
			return false;
		if (Double.doubleToLongBits(solde) != Double.doubleToLongBits(other.solde))
			return false;
		return true;
	}	
}

