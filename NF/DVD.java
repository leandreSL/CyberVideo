package NF;

public class DVD {
	int identifiantDVD;
	String etat = "neuf"; //enum pour état
	Film film;
	
	public DVD(int identifiantDVD, Film film) {
		this.identifiantDVD = identifiantDVD;
		this.film = film;
	}
	
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public int getIdentifiantDVD() {
		return identifiantDVD;
	}
	public Film getFilm() {
		return film;
	}
}
