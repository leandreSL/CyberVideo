package NF;

import java.util.ArrayList;
import java.util.List;

import NF.gestionfichier.BD;

public class Automate {
	
	List<Film> filmsDisponibles = new ArrayList<Film>();
	List<DVD> dvdDisponibles = new ArrayList<DVD>();
	BD bd; 
	
	
	public Automate() {
		bd = BD.getInstance();
	}
	
	//retourne la liste des films par genre ou tous les films si filtres = null
	public List<Film> filtreGenreFilm(List<Genre> filtres) {
		
		//a voir, je peux le faire de ce coté avec différents appels bd
		List<Film> filmsDeGenre = bd.chercherGenreFilm(filtres);
		
		return filmsDeGenre;
	}
	
	//ajoute un film à l'automate
	void ajouterFilm(Film f) {
		
	}
	
	void supprimerFilm(Film f) {
		//appel supprimerDVD
	}
	
	//ajoute un dvd à l'automate
	void ajouterDVD(DVD dvd) {
		//ajoute à dvd total et dvd dispo
	}
	
	//supprime un dvd de la liste
	void supprimerDVD(DVD dvd) {
				
	}
	
	//fonction client
	void rendreDVD(DVD dvd) {
		//ajouter à dvd dispo
	}
	
	void retirerDVD(DVD dvd) {
		
	}
	
	
}
