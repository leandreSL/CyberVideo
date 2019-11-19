package NF;

import java.util.ArrayList;
import java.util.List;

import NF.gestionfichier.BD;

public class Automate {
	
	//List<Film> filmsDisponibles = new ArrayList<Film>();
	//List<DVD> dvdDisponibles = new ArrayList<DVD>();
	BD bd; 
	
	Abonne abonneActif;
	private static Automate instance = null;
	List<DVD> panier = new ArrayList<DVD>();
	
	
	private Automate() {
		bd = BD.getInstance();
	}
	
	public static Automate getInstance() {
		if(instance == null) {
			instance = new Automate();
		}
		return instance;
	}
	
	
	/*
	 * 
	 * FONCTIONS TECHNICIEN
	 * 
	 * */
	
	//ajoute un film Ã  l'automate
	public int ajouterFilm(String titre, List<Genre> genres, String resume, List<String> acteurs, String realisateur, int limiteAge,
			String cheminAffiche) {
		Film f = new Film(titre, genres, resume, acteurs, realisateur,limiteAge, cheminAffiche);
		return bd.stockerFilm(f);
	}
	
	//supprime un film de l'automate
	public int supprimerFilm(String titre) {
		return bd.supprimerFilm(titre);
	}
	
	//ajoute un dvd Ã  l'automate - par défaut, le DVD est ajouté au magasin
	public int ajouterDVD(int identifiantDVD, Film film) {
		//condition: test si film du dvd dans bdd?
		return bd.stockerDVD(dvd);
	}
	
	//supprime un dvd de la liste
	public int supprimerDVD(DVD dvd) {
		return bd.supprimerDVD(dvd);
	}
	

	public String donnerRecommandations (){
		bd.chercherRecommandations();
	}
	
	//?TODO : toutes les fonctions pour bouger les dvd d'un statut à un autre
	
	
	/*
	 * 
	 * FONCTIONS NON ABONNES
	 * 
	 * */	
		
	//remettre le dvd dans l'automate
	int rendreDVD(int idDVD) {
		return bd.modifierDVD(idDVD, StatutDVD.EnAutomate);
		
	}
	
	//prendre le dvd
	public int retirerDVD(int idDVD, long cb) {
		if(abonneActif == null && bd.chercherNombreEmpruntsCBActuel(cb) < 1) {
			return 0;//jsp si je mets une erreur détaillée comme "trop de dvds empruntés"
		}
		else if(abonneActif != null) {
			DVD dvd = bd.chercherDVD(idDVD);
			if(panier.size() >= 5) {
				return 0;
			}
			else {
				panier.add(dvd);
				return 1;
			}
		} 
		else {
			return bd.modifierDVD(idDVD, StatutDVD.Enprunte);
		}
		
	}
	
	//retourne la liste des films par genre ou tous les films si filtres = null
	public List<Film> filtreGenreFilm(List<Genre> filtres) {
		List<Film> filmsDeGenre = bd.chercherGenreFilm(filtres);
		
		return filmsDeGenre;
	}
	
	
	/*
	 * 
	 * FONCTIONS ABONNES
	 * 
	 * */
	
	public int connexion(int carte) {
		Abonne abonne = bd.chercherAbonne(carte);
		if(abonne != null) {
			abonneActif = abonne;
			return 1;
		} else {
			return 0;
		}
	}
	
	public int deconnexion() {
		if(abonneActif == null) {
			return 0;
		} else {
			abonneActif = null;
			return 1;
		}
	}
	
	public String donnerListeFilmsLoues() {
		if(abonneActif == null) {
			return "0";
		} else {
			String result = "";
			List<Film> filmsLoues = bd.chercherFilmsLoues(abonneActif.getCarte());
			for(Film f : filmsLoues) {
				result += f.toString();
			}
			return result;
		}
	}
	
	public String donnerHistoriqueEmpruntsAbonne() {
		if(abonneActif == null) {
			return "0";
		} else {
			String result = "";
			List<Film> filmsLoues = bd.chercherFilmsempruntes(abonneActif.getCarte());
			for(Film f : filmsLoues) {
				result += f.toString();
			}
			return result;
		}
	}	
	
	public int rechargerCarte(double argent) {
		if(abonneActif == null) {
			return 0;
		} else {
			int solde = abonneActif.getSolde()+argent;
			bd.modifierSoldeAbonne(abonneActif.getCarte(), solde);
			return 1;
		}
	}
	
	public int supprimerCompte() {
		if(abonneActif != null) {
			if(bd.supprimerAbonne(abonneActif) == 1) {
				return deconnexion();
			}
		}
		return 0;
	}
	
	public String donnerInformationsAbonne(){
		if(abonneActif == null) {
			return "0";
		} else {		
			return abonneActif.toString();
		}
	}

	public int recommanderFilm(String titre) {
		if(abonneActif == null) {
			return 0;
		} else {		
			return bd.recommanderFilm(titre);
		}
	}

	public String afficherPanier() {
		//TODO
	}
	
	public String valider() {
		//TODO
	}
}
