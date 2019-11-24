package NF;

public class DVD {
	private int identifiantDVD;
	private String etat; //enum pour état
	private Film film;
	private StatutDVD statut;
	private Integer recommandation;
	
	public DVD(int identifiantDVD, Film film) {
		this.identifiantDVD = identifiantDVD;
		this.film = film;
		this.statut = StatutDVD.EnMagasin;
		this.etat = "neuf";
		this.recommandation = 0;
		
	}
	
	public DVD(int identifiantDVD, String etat, Film film, StatutDVD statut, Integer recommandation) {
		super();
		this.identifiantDVD = identifiantDVD;
		this.etat = etat;
		this.film = film;
		this.statut = statut;
		this.recommandation = recommandation;
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
	
	public StatutDVD getStatut() {
		return statut;
	}

	public void setStatut(StatutDVD statut) {
		this.statut = statut;
	}

	public Integer getRecommandation() {
		return recommandation;
	}

	public void setRecommandation(Integer recommandation) {
		this.recommandation = recommandation;
	}
	
	
	public String toString() {
		return identifiantDVD+"|"+etat+"|"+film.getTitre()+"|"+statut.getNom()+"|"+recommandation+"\n";
	}

	
	public String print() {
		return identifiantDVD + " " + etat + " " + film.getTitre() + " " + statut
				+ " " + recommandation;	
	}
}
