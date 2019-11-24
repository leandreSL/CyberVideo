package NF;	

public class Recommandation {
	private Film film;
	private int nbRecommandations = 0;
	
	public Recommandation(Film film, int nbRecommandations) {
		this.film = film;
		this.nbRecommandations = nbRecommandations;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public int getNbRecommandations() {
		return nbRecommandations;
	}

	public void setNbRecommandations(int nbRecommandations) {
		this.nbRecommandations = nbRecommandations;
	}

	@Override
	public String toString() {
		return film + "|" + nbRecommandations + "\n";
	}
	
	public String print() {
		return film + " " + nbRecommandations;
	}
}
