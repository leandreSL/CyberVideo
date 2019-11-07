package NF;

public class DVD {
	int identifiantDVD;
	String etat = "neuf"; //enum pour état
	Film film;
	StatutFilm statut = StatutFilm.EnMagasin;
	Integer recommandation = 0;
	
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
	
	public StatutFilm getStatut() {
		return statut;
	}

	public void setStatut(StatutFilm statut) {
		this.statut = statut;
	}

	public Integer getRecommandation() {
		return recommandation;
	}

	public void setRecommandation(Integer recommandation) {
		this.recommandation = recommandation;
	}
}
