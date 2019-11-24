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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((film == null) ? 0 : film.hashCode());
		result = prime * result + nbRecommandations;
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
		Recommandation other = (Recommandation) obj;
		if (film == null) {
			if (other.film != null)
				return false;
		} else if (!film.equals(other.film))
			return false;
		if (nbRecommandations != other.nbRecommandations)
			return false;
		return true;
	}
}
