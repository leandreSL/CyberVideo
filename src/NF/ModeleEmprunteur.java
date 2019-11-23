package NF;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import NF.gestionfichier.BD;

public class ModeleEmprunteur {
	BD bd; 
	
	Abonne abonneActif;
	private static ModeleEmprunteur instance = null;
	List<DVD> panier = new ArrayList<DVD>();
	
	
	private ModeleEmprunteur() {
		bd = BD.getInstance();
	}
	
	public static ModeleEmprunteur getInstance() {
		if(instance == null) {
			instance = new ModeleEmprunteur();
		}
		return instance;
	}
	
	
	/*
	 * 
	 * FONCTIONS NON ABONNES
	 * 
	 * */	
	
	//TODO gestion retard/gele carte
	//TODO gestion etat(à voir en dernier)
	//remettre le dvd dans l'automate
	public String rendreDVD(int idDVD) {
		Emprunt emprunt = bd.chercherEmpruntActuel(idDVD);
		long differenceTemps = (new Date()).getTime() - emprunt.getDateEmprunt().getTime();
		long nbJours = TimeUnit.DAYS.convert(differenceTemps, TimeUnit.MILLISECONDS);
		
		double prix;
		if(abonneActif == null) {
			prix = 5*nbJours;
			if(!demanderTransactionCb(emprunt.getCbEmprunteur(),prix)) {
				throw(new Exception("Erreur transaction cb"));
			} 
			else {
				if(!bd.modifierStatutDVD(idDVD, StatutDVD.EnAutomate) || 
						!bd.AjouterDateRetourEmprunt(idDVD, emprunt.getDateEmprunt(), new Date())) {
					throw(new Exception("Erreur base de donnée"));
				}
				return "dvd rendu, "+prix+" euros ont été débités";
			}	
		}
		else {
			prix = 4*nbJours;
			if(abonneActif.getSolde()-prix > 0) {
				abonneActif.setSolde(abonneActif.getSolde()-prix);
				if(!bd.modifierSoldeAbonne(abonneActif.getCarteAbonne(),abonneActif.getSolde())) {
					throw(new Exception("Erreur base de donnée"));
				}
				return "dvd rendu, "+prix+" euros ont été débités";
			}
			else {
				throw(new Exception("pas assez d'argent sur la carte abonnée"));
			}
		}		
	}
	
	//TODO
	//prendre le dvd
	public void ajouterAuPanier(String titre, long cb) {
		int nbEmpruntMax;
		int nbEmpruntActuels;
		
		if(abonneActif == null) { 
			nbEmpruntMax = 1;
			nbEmpruntActuels = bd.chercherNombreEmpruntsActuelsCB(cb);
		}
		else{
			nbEmpruntMax = 3;
			nbEmpruntActuels = bd.chercherNombreEmpruntsActuelsAbonne(abonneActif.getCarteAbonne());
		}
		
		
		
		
		if(panier.size() >=  nbEmpruntMax) {
			throw(new Exception("Votre panier est plein"));
		}
		if((panier.size() + nbEmpruntActuels) >= nbEmpruntMax) {
			throw(new Exception("Vous avez trop de DVDs non rendus avec cette carte"));
		}
		
		DVD dvd = bd.chercherDVD(titre);
		panier.add(dvd);
		return;
		
		
	}
	
	public void valider(long cb) {
		Emprunt e;
		for(DVD dvd: panier) {
			if(!bd.modifierStatutDVD(dvd.getIdentifiantDVD(), StatutDVD.Emprunte)) {
				throw(new Exception("Erreur base de donnée"));
			}
			if(abonneActif != null) {
				e = new Emprunt(abonneActif.getCarteAbonne(), dvd, new Date());
			} else {
				e = new Emprunt(cb, dvd, new Date());
			}
			if(!bd.creerEmprunt(e)) {
				throw(new Exception("Erreur base de donnée"));
			}
		}
		panier = new ArrayList<DVD>();
		abonneActif = null;
		return;
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
	
	public void creationCompte(String nomAbonne, String prenomAbonne, List<Genre> restrictions, double solde, long carteBleue) throws Exception{
		if(solde < 15) {
			throw(new Exception("Vous devez mettre au moins 15 Euros sur la carte à sa création"));
		}
		Abonne a = new Abonne(nomAbonne, prenomAbonne, restrictions, solde, carteBleue);
		if(!bd.chercherAbonne(a))
			throw(new Exception("Erreur base de donnée"));
		return;
	}
	
	
	public void connexion(int idCarte) {
		Abonne abonne = bd.chercherAbonne(idCarte);
		if(abonne != null) {
			abonneActif = abonne;
			return;
		} else {
			throw(new Exception("Vous êtes déjà connectés"));
		}
	}
	
	public void deconnexion() throws Exception {
		if(abonneActif == null) {
			throw(new Exception("Erreur base de donnée"));
		} else {
			abonneActif = null;
			return;
		}
	}
	
	public String donnerListeEmprunts() {
		if(abonneActif == null) {
			throw(new Exception("Vous devez avoir un compte abonne pour utiliser cette fonction"));
		} else {
			String result = "";
			List<Emprunt> TotalEmprunt = bd.chercherEmprunts(abonneActif.getCarteAbonne());
			for(Emprunt e : TotalEmprunt) {
				result += e.toString();
			}
			return result;
		}
	}
		
	
	public void rechargerCarte(long cb, double montant) {
		if(abonneActif == null) {
			throw(new Exception("Vous devez avoir un compte abonne pour utiliser cette fonction"));
		} else if(montant<10) {
			throw(new Exception("Vous devez reharger votre carte avec plus de 10 Euros"));
		} else {
			if(!demanderTransactionCb(cb, montant)) {
				throw(new Exception("Erreur transaction cb"));
			}
			double solde = abonneActif.getSolde()+montant;
			if(!bd.modifierSoldeAbonne(abonneActif.getCarteAbonne(), solde)){
				throw(new Exception("Erreur base de donnée"));
			}
			return;
		}
	}

	public String donnerInformationsAbonne() throws Exception{
		if(abonneActif == null) {
			throw(new Exception("Vous devez avoir un compte abonne pour utiliser cette fonction"));
		} else {		
			return abonneActif.toString();
		}
	}

	public void recommanderFilm(String titre) {
		if(abonneActif == null) {
			throw(new Exception("Vous devez avoir un compte abonne pour utiliser cette fonction"));
		} else if(!bd.recommanderFilm(titre)){
			throw(new Exception("Erreur base de donnée"));
		}
		return;
	}

	public String afficherPanier() {
		String result = "";
		for(DVD dvd : panier) {
			result += dvd.getFilm().toString();
		}
		return result;
	}
	
	
	//TODO creation compte abonne
	
	
	private boolean demanderTransactionCb(long cb, double montant) {
		return true;
	}
}
