package NF;

import java.util.ArrayList;
import java.util.List;

public enum Genre {
	ACTION("action"),
	ANIME("anime"),
	DOCUMENTAIRE("Documentaire"),
	FANTASTIQUE("Fantastique"),
	HORREUR("Horreur"),
	HUMOURISTIQUE("Humoristique"),
	PEPLUM("Peplum"),
	POLICIER("Policier"),
	SENSATION("Sensation"),
	WESTERN("western");
	
	private String nom;
	
	private Genre(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public static Genre getByName(String nom) {
		switch(nom) {
			case "action": return Genre.ACTION;
			case "anime": return Genre.ANIME;
			case "Documentaire": return Genre.DOCUMENTAIRE;
			case "Fantastique": return Genre.FANTASTIQUE;
			case "Horreur": return Genre.HORREUR;
			case "Humoristique": return Genre.HUMOURISTIQUE;
			case "Peplum": return Genre.PEPLUM;
			case "Policier": return Genre.POLICIER;
			case "Sensation": return Genre.SENSATION;
			case "western": return Genre.WESTERN;
		}
		return null;
	}
	
	public static List<String> toStringArray(List<Genre> genres) {
		List<String> stringGenres = new ArrayList<String>();
		for(Genre genre : genres) {
			stringGenres.add(genre.getNom());
		}
		return stringGenres;
	}

	public static List<Genre> toGenreArray(String[] stringGenres) {
		List<Genre> genres = new ArrayList<Genre>();
		for(String sgenre : stringGenres) {
			genres.add(Genre.getByName(sgenre));
		}
		return genres;
	}
}
