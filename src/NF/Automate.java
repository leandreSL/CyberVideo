package NF;

import java.util.ArrayList;
import java.util.List;

import NF.gestionfichier.BD;

public class Automate {
	
	List<Film> filmsDisponibles = new ArrayList<Film>();
	List<DVD> dvdDisponibles = new ArrayList<DVD>();
	BD bd; 
	
	Abonne abonneActif;
	
	public Automate() {
		bd = BD.getInstance();
	}
	
	
	//retourne la liste des films par genre ou tous les films si filtres = null
	public List<Film> filtreGenreFilm(List<Genre> filtres) {
		
		//a voir, je peux le faire de ce coté avec différents appels bd
		List<Film> filmsDeGenre = bd.chercherGenreFilm(filtres);
		
		return filmsDeGenre;
	}
	
	
	// !!! les types de paramètres peuvent changer
	
	//fonction technicien - ajoute un film à l'automate
	void ajouterFilm(Film f) {
		bd.stockerFilm(f);
	}
	
	//fonction technicien - supprime un film de l'automate
	void supprimerFilm(Film f) {
		bd.supprimerFilm(f);
	}
	
	//fonction technicien - ajoute un dvd à l'automate
	void ajouterDVD(DVD dvd) {
		//condition: test si film du dvd dans bdd
		bd.stockerDVD(dvd);
	}
	
	//fonction technicien - supprime un dvd de la liste
	void supprimerDVD(DVD dvd) {
		bd.supprimerDVD(dvd);
	}
	
	//fonction client - remettre le dvd dans l'automate
	int rendreDVD(int idDVD) {
		return bd.modifierDVD(idDVD, StatutFilm.EnAutomate);
		
	}
	
	
	/*
	 * FONCTIONS ABONNES
	 * 
	 * */
	
	int connexion(int carte) {
		Abonne abonne = bd.chercherAbonne(carte);
		if(abonne != null) {
			abonneActif = abonne;
			return 1;
		} else {
			return 0;
		}
	}
	
	int deconnexion() {
		if(abonneActif == null) {
			return 0;
		} else {
			abonneActif = null;
			return 1;
		}
	}
	
	int donnerListeFilmsLoues() {
		if(abonneActif != null) {
			return 0;
		} else {
			abonneActif = null;
			return 1;
		}
	}
	
	
	//fonction client - prendre le dvd
	int retirerDVD(int idDVD) {
		return bd.modifierDVD(idDVD, StatutFilm.EnAutomate);
	}
	
	int afficherFilmLoues() {
		
	}
	
}
