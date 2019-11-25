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
		this.statut = StatutDVD.EnAutomate;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result + ((film == null) ? 0 : film.hashCode());
		result = prime * result + identifiantDVD;
		result = prime * result + ((recommandation == null) ? 0 : recommandation.hashCode());
		result = prime * result + ((statut == null) ? 0 : statut.hashCode());
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
		DVD other = (DVD) obj;
		if (etat == null) {
			if (other.etat != null)
				return false;
		} else if (!etat.equals(other.etat))
			return false;
		if (film == null) {
			if (other.film != null)
				return false;
		} else if (!film.equals(other.film))
			return false;
		if (identifiantDVD != other.identifiantDVD)
			return false;
		if (recommandation == null) {
			if (other.recommandation != null)
				return false;
		} else if (!recommandation.equals(other.recommandation))
			return false;
		if (statut != other.statut)
			return false;
		return true;
	}
}
