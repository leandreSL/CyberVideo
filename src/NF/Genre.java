package NF;

import java.util.ArrayList;
import java.util.List;

public enum Genre {
	ACTION("action"),
	ANIME("anime"),
	DOCUMENTAIRE("documentaire"),
	FANTASTIQUE("fantastique"),
	HORREUR("horreur"),
	HUMOURISTIQUE("humoristique"),
	PEPLUM("peplum"),
	POLICIER("policier"),
	SENSATION("sensation"),
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
			case "documentaire": return Genre.DOCUMENTAIRE;
			case "fantastique": return Genre.FANTASTIQUE;
			case "horreur": return Genre.HORREUR;
			case "humoristique": return Genre.HUMOURISTIQUE;
			case "peplum": return Genre.PEPLUM;
			case "policier": return Genre.POLICIER;
			case "sensation": return Genre.SENSATION;
			case "western": return Genre.WESTERN;
		}
		return null;
	}
	
	public static List<String> toStringArray(List<Genre> genres) {
		List<String> stringGenres = new ArrayList<String>();
		if (!genres.contains(null)) {
			for(Genre genre : genres) {
				stringGenres.add(genre.getNom());
			}
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
