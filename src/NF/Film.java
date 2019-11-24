package NF;

import java.util.List;

public class Film {
	private String Titre;
	private List<Genre> genres;
	private String resume;
	private List<String> acteurs;
	private String realisateur;
	private int limiteAge;
	private String cheminAffiche;
	private int recommandation;
	
	public Film(String titre, List<Genre> genres, String resume, List<String> acteurs, String realisateur, int limiteAge,
			String cheminAffiche) {
		Titre = titre;
		this.genres = genres;
		this.resume = resume;
		this.acteurs = acteurs;
		this.realisateur = realisateur;
		this.limiteAge = limiteAge;
		this.cheminAffiche = cheminAffiche;
		this.recommandation = 0;
	}
	
	
	public int getRecommandation() {
		return recommandation;
	}


	public void setRecommandation(int recommandation) {
		this.recommandation = recommandation;
	}


	public String getTitre() {
		return Titre;
	}
	public void setTitre(String titre) {
		Titre = titre;
	}
	public List<Genre> getGenre() {
		return genres;
	}
	public void setGenre(List<Genre> genre) {
		this.genres = genre;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public List<String> getActeurs() {
		return acteurs;
	}
	public void setActeurs(List<String> acteurs) {
		this.acteurs = acteurs;
	}
	public String getRealisateur() {
		return realisateur;
	}
	public void setRealisateur(String realisateur) {
		this.realisateur = realisateur;
	}
	public int getLimiteAge() {
		return limiteAge;
	}
	public void setLimiteAge(int limiteAge) {
		this.limiteAge = limiteAge;
	}
	public String getCheminAffiche() {
		return cheminAffiche;
	}
	public void setCheminAffiche(String cheminAffiche) {
		this.cheminAffiche = cheminAffiche;
	}
	
	
	@Override
	public String toString() {
		String chaineGenres = String.join("`", Genre.toStringArray(genres));
		String chaineActeurs = String.join("`", acteurs);
		return Titre + "|" + chaineGenres + "|" + resume + "|" + chaineActeurs
				+ "|" + realisateur + "|" + limiteAge + "|" + cheminAffiche + "\n";
	}
	
	public String print() {
		String chaineGenres = String.join("|", Genre.toStringArray(genres));
		String chaineActeurs = String.join("|", acteurs);
		return Titre + " " + resume + " " + chaineGenres + " " + chaineActeurs
				+ " " + realisateur + " " + limiteAge;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Titre == null) ? 0 : Titre.hashCode());
		result = prime * result + ((acteurs == null) ? 0 : acteurs.hashCode());
		result = prime * result + ((cheminAffiche == null) ? 0 : cheminAffiche.hashCode());
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + limiteAge;
		result = prime * result + ((realisateur == null) ? 0 : realisateur.hashCode());
		result = prime * result + recommandation;
		result = prime * result + ((resume == null) ? 0 : resume.hashCode());
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
		Film other = (Film) obj;
		if (Titre == null) {
			if (other.Titre != null)
				return false;
		} else if (!Titre.equals(other.Titre))
			return false;
		if (acteurs == null) {
			if (other.acteurs != null)
				return false;
		} else if (!acteurs.equals(other.acteurs))
			return false;
		System.out.println("test");

		if (cheminAffiche == null) {
			if (other.cheminAffiche != null)
				return false;
		} else if (!cheminAffiche.equals(other.cheminAffiche))
			return false;
		if (genres == null) {
			System.out.println("test");
			if (other.genres != null)
				return false;
		} else if (!genres.equals(other.genres))
			return false;
		if (limiteAge != other.limiteAge)
			return false;
		if (realisateur == null) {
			if (other.realisateur != null)
				return false;
		} else if (!realisateur.equals(other.realisateur))
			return false;
		if (recommandation != other.recommandation)
			return false;
		if (resume == null) {
			if (other.resume != null)
				return false;
		} else if (!resume.equals(other.resume))
			return false;
		return true;
	}
}
