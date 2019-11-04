package NF;

import java.util.List;

public class Film {
	String Titre;
	Genre genre;
	String resume;
	List<String> acteurs;
	String realisateur;
	int limiteAge;
	String cheminAffiche;
	
	public Film(String titre, Genre genre, String resume, List<String> acteurs, String realisateur, int limiteAge,
			String cheminAffiche) {
		super();
		Titre = titre;
		this.genre = genre;
		this.resume = resume;
		this.acteurs = acteurs;
		this.realisateur = realisateur;
		this.limiteAge = limiteAge;
		this.cheminAffiche = cheminAffiche;
	}
	
	
	public String getTitre() {
		return Titre;
	}
	public void setTitre(String titre) {
		Titre = titre;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
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
}
