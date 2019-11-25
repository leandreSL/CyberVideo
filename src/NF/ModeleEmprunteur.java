package NF;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import NF.gestionfichier.BD;

public class ModeleEmprunteur{	
	private Abonne abonneActif;
	private List<DVD> panier = new ArrayList<DVD>();
	
	private BD bd;
	private static ModeleEmprunteur instance = null;
	
	public ModeleEmprunteur() {
		bd = BD.getInstance();
	}
	
	public static ModeleEmprunteur getInstance() {
		if(instance == null) {
			instance = new ModeleEmprunteur();
		}
		return (ModeleEmprunteur) instance;
	}
	
	/*
	 * 
	 * FONCTIONS NON ABONNES
	 * 
	 * */	
	
	//TODO gestion retard/gele carte
	//TODO gestion etat(� voir en dernier)
	//remettre le dvd dans l'automate
	public double rendreDVD(int idDVD) throws Exception {
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
					throw(new Exception("Erreur base de donn�e"));
				}
				return prix;
				//return "dvd rendu, "+prix+" euros ont �t� d�bit�s";
			}	
		}
		else {
			prix = 4*nbJours;
			if(abonneActif.getSolde()-prix > 0) {
				abonneActif.setSolde(abonneActif.getSolde()-prix);
				if(!bd.modifierSoldeAbonne(abonneActif.getCarteAbonne(),abonneActif.getSolde())) {
					throw(new Exception("Erreur base de donn�e"));
				}
				return prix;
			}
			else {
				throw(new Exception("pas assez d'argent sur la carte abonn�e"));
			}
		}		
	}
	
	//prendre le dvd
	public void ajouterAuPanier(String titre, long cb) throws Exception {
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
		if (dvd == null) {
			throw(new Exception("Aucun DVD pour ce film n'est disponible, allez en boutique pour plus de précisions"));
		}else {
			panier.add(dvd);
		}
		
		return;
		
		
	}
	public void ajouterAuPanier(int idDVD, long cb) throws Exception {
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
		
		DVD dvd = bd.chercherDVD(idDVD);
		panier.add(dvd);
		return;
		
		
	}
	
	
	public void valider(long cb) throws Exception {
		Emprunt e;
		for(DVD dvd: panier) {
			if(!bd.modifierStatutDVD(dvd.getIdentifiantDVD(), StatutDVD.Emprunte)) {
				throw(new Exception("Erreur base de donn�e"));
			}
			if(abonneActif != null) {
				e = new Emprunt(abonneActif.getCarteAbonne(), dvd, new Date());
			} else {
				e = new Emprunt(cb, dvd, new Date());
			}
			if(!bd.creerEmprunt(e)) {
				throw(new Exception("Erreur base de donn�e"));
			}
		}
		panier = new ArrayList<DVD>();
		abonneActif = null;
		return;
	}
	
	
	//retourne la liste des films par genre ou tous les films si filtres = null
	public List<Film> filtreGenreFilm(List<Genre> filtres) {
		List<Film> filmsDeGenre = bd.chercherFilmParGenre(filtres);
		
		return filmsDeGenre;
		/*
		String result= "titre, r�sum�, genres, acteurs, r�alisateur, limite d'age\n";
		for(Film f:filmsDeGenre) {
			result+=f.print()+"\n";
		}
		return result;
		*/
	}
	
	/*
	 * 
	 * FONCTIONS ABONNES
	 * 
	 * */
	
	public void creationCompte(Abonne a) throws NumberFormatException{
		if(a.getSolde() < 15) {
			throw(new NumberFormatException("Vous devez mettre au moins 15 Euros sur la carte � sa cr�ation"));
		}
		
		if(!bd.CreerAbonne(a))
			throw(new NumberFormatException("Erreur base de donn�e"));
		abonneActif = a;
		//bidouillage pour l'ihm
		return;
	}
	
	
	public void connexion(int idCarte) throws NumberFormatException {
		Abonne abonne = bd.chercherAbonne(idCarte);
		if(abonne != null) {
			abonneActif = abonne;
			return;
		} else {
			throw(new NumberFormatException("Vous �tes d�j� connect�s"));
		}
	}
	
	public void deconnexion() throws Exception {
		if(abonneActif == null) {
			throw(new Exception("Erreur base de donn�e"));
		} else {
			abonneActif = null;
			return;
		}
	}
	
	public List<Emprunt> donnerListeEmprunts() throws Exception {
		if(abonneActif == null) {
			throw(new Exception("Vous devez avoir un compte abonne pour utiliser cette fonction"));
		} else {
			List<Emprunt> totalEmprunt = bd.chercherEmpruntsAbonne(abonneActif.getCarteAbonne());
			return totalEmprunt;
			/*
			String result = "carte bleue emprunteur, num�ro d'abonn�, date d�but emprunt, date fin emprunt, identifiant dvd\n";
			for(Emprunt e : TotalEmprunt) {
				result += e.print();
			}
			return result;
			*/
		}
	}
		
	
	public void rechargerCarte(long cb, double montant) throws Exception {
		if(abonneActif == null) {
			throw(new Exception("Vous devez avoir un compte abonne pour utiliser cette fonction"));
		} else if(montant<10) {
			throw(new Exception("Vous devez recharger votre carte avec plus de 10 Euros"));
		} else {
			if(!demanderTransactionCb(cb, montant)) {
				throw(new Exception("Erreur transaction cb"));
			}
			double solde = abonneActif.getSolde()+montant;
			abonneActif.setSolde(solde);
			if(!bd.modifierSoldeAbonne(abonneActif.getCarteAbonne(), solde)){
				throw(new Exception("Erreur base de donnee"));
			}
			return;
		}
	}

	public Abonne donnerInformationsAbonne() throws NumberFormatException{
		if(abonneActif == null) {
			throw(new NumberFormatException("Vous devez avoir un compte abonne pour utiliser cette fonction"));
		} else {
			return abonneActif;
			/*
			String result = "nom, pr�nom, restrictions, solde, numero d'abonn�, num�ro de carte bleue\n";
			return result+abonneActif.print();
			*/
		}
	}
	

	public String donnerSoldeAbonne() {	
		return Double.toString(abonneActif.getSolde());
	
	}
	
	public String donnerIdentificationAbonne() {	
		return abonneActif.getNomAbonne() + " " + abonneActif.getPrenomAbonne();
	}
	
	public long donnerCBAbonne() {
		return abonneActif.getCarteBleue();
	}

	public void recommanderFilm(String titre) throws Exception {
		if(abonneActif == null) {
			throw(new Exception("Vous devez avoir un compte abonne pour utiliser cette fonction"));
		} else if(!bd.recommanderFilm(titre)){
			throw(new Exception("Erreur base de donn�e"));
		}
		return;
	}
	
	public void viderPanier() {
		panier.clear();
	}

	public List<Film> afficherPanier() {
		List<Film> result = new ArrayList<Film>();
		for(DVD dvd:panier) {
			result.add(dvd.getFilm());
		}
		
		return result;
		/*
		String result = "titre, r�sum�, genres, acteurs, r�alisateur, limite d'age\n";
		for(DVD dvd : panier) {
			result += dvd.getFilm().print();
		}
		return result;
		*/
	}
	
	public List<Film> filmDispos(){
		return bd.chercherEnsembleFilms();
	}
	
	public Boolean existeDVDFilm(Film f) {
		if (bd.chercherDVD(f.getTitre())==null) {
			return false;
		}else {
			return true;
		}
	}
	
	private boolean demanderTransactionCb(long cb, double montant) {
		return true;
	}

	public boolean estConnecte() {
		if(abonneActif != null)
			return true;
		return false;
	}
}
