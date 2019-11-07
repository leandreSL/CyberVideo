package NF;

import java.util.List;

public class Automate {
	
	List<Film> filmsMagasin;
	List<Film> filmsDisponibles;
	List<DVD> dvddisponibles; 
	List<DVD> dvdtotal;
	
	
	//retourne la liste des films par genre ou tous les films si filtres = null
	public List<Film> filtreGenreFilm(List<Genre> filtres) {
		
		return null;
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
	
	//ajoute un film au magasin
	void ajouterFilmMagasin(Film f) {
			
	}
		
	void supprimerFilmMagasin(Film f) {
			
	}
	
	
}
