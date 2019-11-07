package NF;

import java.util.List;

public class Film {
	String Titre;
	List<Genre> genres;
	String resume;
	List<String> acteurs;
	String realisateur;
	int limiteAge;
	String cheminAffiche;
	
	public Film(String titre, List<Genre> genres, String resume, List<String> acteurs, String realisateur, int limiteAge,
			String cheminAffiche) {
		Titre = titre;
		this.genres = genres;
		this.resume = resume;
		this.acteurs = acteurs;
		this.realisateur = realisateur;
		this.limiteAge = limiteAge;
		this.cheminAffiche = cheminAffiche;
	}
	
	
	@Override
	public String toString() {
		String chaineGenre = "";
		for(Genre i: genres) {
			chaineGenre+=i+" ";
		}
		
		String chaineActeurs = "";
		for(Genre i: genres) {
			chaineActeurs+=i+" ";
		}
		
		return Titre + "|" + chaineGenre + "|" + resume + "|" + chaineActeurs
				+ "|" + realisateur + "|" + limiteAge + "|" + cheminAffiche + "\n";
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
}
