package NF;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
	
	public String changerEtatDVD(int idDVD, String etat) {
		return bd.modifierDVD(idDVD, StatutDVD.getByName(etat));
	}
	
	
	/*
	 * 
	 * FONCTIONS NON ABONNES
	 * 
	 * */	
		
	//remettre le dvd dans l'automate
	public String rendreDVD(int idDVD) {
		Emprunt emprunt = bd.chercherEmprunt(idDVD);
		long differenceTemps = (new Date()).getTime() - emprunt.getDateEmprunt().getTime();
		long nbJours = TimeUnit.DAYS.convert(differenceTemps, TimeUnit.MILLISECONDS);
		
		double prix;
		if(abonneActif == null) {
			prix = 5*nbJours;
			Emprunteur emprunteur = bd.chercherEmprunteur(emprunt.getCbEmprunteur());//peut-être pas utile changer de place transaction cb?
			if(emprunteur.demanderTransactionCb(prix) == 0) {
				return "erreur paiement";
			} 
			else {
				bd.modifierDVD(idDVD, StatutDVD.EnAutomate);
				bd.AjouterDateRetourEmprunt(idDVD, emprunt.getDateEmprunt(), new Date());
				return "dvd rendu, "+prix+" euros ont été débités";
			}	
		}
		else {
			prix = 4*nbJours;
			if(abonneActif.getSolde()-prix > 0) {
				abonneActif.setSolde(abonneActif.getSolde()-prix);
				bd.modifierSoldeAbonne(abonneActif.getCarte(),abonneActif.getSolde());
				return "dvd rendu, "+prix+" euros ont été débités";
			}
			else {
				return "pas assez d'argent sur la carte abonnée";
			}
		}
		
		
		
		
	}
	
	//prendre le dvd
	public int ajouterAuPanier(int idDVD, long cb) {
		if(abonneActif == null && (bd.chercherNombreEmpruntsCBActuel(cb) < 1 || panier.size() > 0)) {
			return 0;//jsp si je mets une erreur détaillée comme "trop de dvds empruntés"
		}
		else if(abonneActif != null && (bd.chercherNombreEmpruntsAbonneActuel(abonneActif.getCarte()) < 1 || panier.size() >= 3)) {
			return 0;
		} 
		else {
			DVD dvd = bd.chercherDVD(idDVD);
			panier.add(dvd);
			return 1;
		}
		
	}
	
	public int valider(long cb) {
		//TODO : gestion des erreurs/annulation
		for(DVD dvd: panier) {
			bd.modifierDVD(dvd.getIdentifiantDVD(), StatutDVD.Emprunte);
			if(abonneActif != null) {
				bd.creerEmprunt(new Emprunt(abonneActif.getCarte(), dvd, new Date()));
			} else {
				bd.creerEmprunt(new Emprunt(cb, dvd, new Date()));
			}
		}
		panier = new ArrayList<DVD>();
		abonneActif = null;
		return 1;
	}
	
	
	//retourne la liste des films par genre ou tous les films si filtres = null
	public String filtreGenreFilm(List<Genre> filtres) {
		List<Film> filmsDeGenre = bd.chercherGenreFilm(filtres);
		String result="";
		for(Film f:filmsDeGenre) {
			result+=f.toString()+"\n";
		}
		return result;
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
	
	public int rechargerCarte(long cb, double montant) {
		if(montant<10) {
			return 0;
		}
		if(abonneActif == null) {
			return 0;
		} else {
			if(abonneActif.demanderTransactionCb(montant) == 0) {
				return 0;
			}
			double solde = abonneActif.getSolde()+montant;
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
		String result = "";
		for(DVD dvd : panier) {
			result += dvd.getFilm().toString();
		}
		return result;
	}
}
