package NF.gestionfichier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import NF.Abonne;
import NF.DVD;
import NF.Emprunt;
import NF.Film;
import NF.Genre;
import NF.StatutDVD;

public class BD {

	private static BD instance = null;
	
	private static final String dossier = "BDD";
	
	private static final String cheminFilm = dossier + File.separator + "film";
	private static final String cheminDVD = dossier + File.separator + "dvd";
	private static final String cheminEmprunt = dossier + File.separator + "emprunt";
	private static final String cheminNabo = dossier + File.separator + "nonabonne";
	private static final String cheminAbo = dossier + File.separator + "abonne";
	
	private BD(){
		
	}
	
	public static BD getInstance() {
		if(instance == null) {
			instance = new BD();
		}
		return instance;
	}
	
	// - Fonction film
	
	public Film chercherFilmParNom(String nom) {
		List<String> sFilm = chercherUnique(cheminFilm, nom, 0);
		return sFilm==null?null:strToFilm(sFilm);
	}
	
	public List<Film> chercherEnsembleFilms(){
		List<List<String>> paramFilm = chercherPlusieurs(cheminFilm, "", 0);
		List<Film> films = new ArrayList<Film>();
		for( int i = 0; i < paramFilm.size(); i++) {
			films.add( strToFilm(paramFilm.get(i)));
		}
		return films;
	}
	
	public List<Film> chercherFilmParGenre(List<Genre> filtres) {
		List<Film> films = new ArrayList<Film>();
		List<List<String>> listeFilms = chercherPlusieurs(cheminFilm, filtres.get(0).getNom(), 1);
		ListIterator<List<String>> iterFilms = listeFilms.listIterator();
		while(iterFilms.hasNext()) {
			List<String> sfilm = iterFilms.next();
			bfiltre:for(int i=1; i< filtres.size(); i++) {
				if( !sfilm.get(1).contains( filtres.get(i).getNom() ) ) {
					iterFilms.remove();
					break bfiltre;
				}
			}
		}
		for( int i = 0; i < listeFilms.size(); i++) {
			films.add( strToFilm(listeFilms.get(i)));
		}
		return films;
	}
	
	public boolean supprimerFilm(Film film){
		return supprimer(cheminFilm, film.getTitre(), 0);
	}
	
	public boolean stockerFilm(Film film) {
		return stocker(cheminFilm, film.toString());
	}
	
	public List<String> chercherRecommandations(){
		return null;
	}
	
	public boolean recommanderFilm(String titre) {
		return true;
	}

	
	// - Fonction DVD
	
	public boolean stockerDVD(DVD dvd) {
		return stocker(cheminDVD, dvd.toString());
	}
	
	public boolean supprimerDVD(DVD dvd){
		return supprimer(cheminDVD, dvd.getIdentifiantDVD()+"", 0);
	}	
	
	public boolean modifierStatutDVD(DVD dvd, StatutDVD emprunte) {
		return supprimerDVD(dvd) && stockerDVD(dvd);
	}
	
	public DVD chercherDVD(int idDVD) {
		List<String> dvd = chercherUnique(cheminDVD, idDVD+"", 0);
		return dvd==null?null:new DVD(idDVD, chercherFilmParNom(dvd.get(1)));
	}
	
	public List<DVD> chercherEnsembleDVDs(){
		List<List<String>> paramDVD = chercherPlusieurs(cheminDVD, "", 0);
		List<DVD> DVDs = new ArrayList<DVD>();
		for( int i = 0; i < paramDVD.size(); i++) {
			List<String> dvd = paramDVD.get(i);
			DVDs.add( new DVD(Integer.parseInt(dvd.get(0)), chercherFilmParNom(dvd.get(1))) );
		}
		return DVDs;
	}

	// - Fonction Emprunt
	
	public boolean creerEmprunt(Emprunt e) {
		return stocker(cheminEmprunt, e.toString());
	}
	
	//retourne l'emprunt pour le dvd avec la date la plus récente
	public Emprunt chercherEmpruntActuel(int IDVD){
		return null;
	}
	
	//actualise l'emprunt en rajoutant la date de retour du dvd
	public boolean AjouterDateRetourEmprunt(Emprunt e, Date dateRetour) {
		return supprimer(cheminEmprunt, e.getDvd().getIdentifiantDVD()+"", 0) && creerEmprunt(e);
	}
	
	//retourne le nombre de dvd actuellement empruntés pour une carte bleue
	public int chercherNombreEmpruntsActuelsCB(long idCB){
		return chercherPlusieurs(cheminEmprunt, idCB+"", 0).size();
	}
	
	public int chercherNombreEmpruntsActuelsAbonne(int idCarte) {
		return chercherPlusieurs(cheminEmprunt, idCarte+"", 1).size();
	}
	//retourne tous les emprunts effectués par l'abonné
	public List<Emprunt> chercherEmpruntsAbonne(int idCarte) {
		List<List<String>> paramEmprunt = chercherPlusieurs(cheminEmprunt, idCarte+"", 1);
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		for( int i = 0; i < paramEmprunt.size(); i++) {
			List<String> emprunt = paramEmprunt.get(i);
			emprunts.add( strToEmprunt(emprunt) );
		}
		return emprunts;
	}
	 //retourne le nombre de jours moyen des emprunts sur 1 an
	public int chercherTempsEmpruntMoyen() {
		return 0;
	}
	//retourne le nombre d’emprunt de la machine (total ou une moyenne?)
	public int chercherNombreEmprunt() {
		return 0;
	}

	// - Fonctions Abonne
	 
	// il faut déterminer le numéro de carte dans la fonction
	public boolean CreerAbonne(Abonne a) {
		return stocker(cheminAbo, a.toString());
	}
	public boolean modifierSoldeAbonne(int idCarte, double solde) {
		Abonne abo = chercherAbonne(idCarte);
		return supprimerAbonne(idCarte) && CreerAbonne(abo);
	}
	public Abonne chercherAbonne(int idCarte) {
		List<String> sAbo = chercherUnique(cheminAbo, idCarte+"", 0);
		return strToAbo(sAbo);
	}
	public boolean supprimerAbonne(int idCarte) {
		return supprimer(cheminAbo, idCarte+"", 0);
	}
	
	public List<Abonne> chercherEnsembleAbonnes() {
		List<List<String>> paramAbo = chercherPlusieurs(cheminAbo, "", 0);
		List<Abonne> abonnes = new ArrayList<Abonne>();
		for( int i = 0; i < paramAbo.size(); i++) {
			List<String> abo = paramAbo.get(i);
			abonnes.add( strToAbo(abo) );
		}
		return abonnes;
	}

	
	/* ---------------- PRIVATE -------------- */
	
	private boolean stocker(String chemin, String ajout) {
	    try(FileWriter fw = new FileWriter(chemin, true);
	    	    BufferedWriter bw = new BufferedWriter(fw);
	    	    PrintWriter out = new PrintWriter(bw))
	    	{
	    	    out.print(ajout);
	    	    out.close();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    		return false;
	    	}
		return true;
	}
	
	private List<String> chercherUnique(String chemin, String idcherche, int idindex) {
		File fichierOriginal = new File(chemin);
		try {
			BufferedReader lecteur = new BufferedReader(new FileReader(fichierOriginal));

			String ligne, currentID;
			while((ligne = lecteur.readLine()) != null) {
			    List<String> lineSplited = getLigneArray(ligne.trim());
			    currentID = lineSplited.get(idindex);
			    if(currentID.contains(idcherche)) {
			    	return lineSplited;
			    }
			}
			lecteur.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<List<String>> chercherPlusieurs(String chemin, String idcherche, int idindex) {
		List<List<String>> liste = new ArrayList<List<String>>();
		File fichierOriginal = new File(chemin);
		try {
			BufferedReader lecteur = new BufferedReader(new FileReader(fichierOriginal));

			String ligne, currentID;
			while((ligne = lecteur.readLine()) != null) {
			    List<String> lineSplited = getLigneArray(ligne.trim());
			    currentID = lineSplited.get(idindex);
			    if(currentID.contains(idcherche)) {
			    	liste.add(lineSplited);
			    }
			}
			lecteur.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return liste;
	}
	
	private boolean supprimer(String chemin, String idSuppression, int idindex) {
		File fichierOriginal = new File(chemin);
		File fichierTemp = new File(dossier + File.separator + "temp.txt");
		try {
			BufferedReader lecteur = new BufferedReader(new FileReader(fichierOriginal));
			BufferedWriter redacteur = new BufferedWriter(new FileWriter(fichierTemp));

			String ligne, currentID;
			while((ligne = lecteur.readLine()) != null) {
			    currentID = getLigneArray(ligne.trim()).get(idindex);
			    if(currentID.equals(idSuppression)) continue;
			    redacteur.write(ligne + System.getProperty("line.separator"));
			}
			redacteur.close(); 
			lecteur.close(); 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		fichierOriginal.delete();
		boolean successful = fichierTemp.renameTo(fichierOriginal);
		if(!successful) System.out.println("Renomage impossible");
		return successful;
	}
	
	private Film strToFilm(List<String> sfilm){
		return new Film(
				sfilm.get(0),
				Genre.toGenreArray(sfilm.get(1).split("`")),
				sfilm.get(2),
				Arrays.asList(sfilm.get(3)), 
				sfilm.get(4),
				Integer.parseInt(sfilm.get(5)),
				sfilm.get(6)
						);
	}
	
	private Emprunt strToEmprunt(List<String> sEmp) {
		return new Emprunt(Long.parseLong(sEmp.get(0)), Integer.parseInt(sEmp.get(1)),
				parseDate(sEmp.get(2)), parseDate(sEmp.get(3)), chercherDVD(Integer.parseInt(sEmp.get(4))));
	}
	
	private Abonne strToAbo(List<String> sAbo) {
		return new Abonne(sAbo.get(0), sAbo.get(1),
				Genre.toGenreArray(sAbo.get(2).split("`")),
				Double.parseDouble(sAbo.get(3)), Integer.parseInt(sAbo.get(4)), Long.parseLong(sAbo.get(5)));
	}
	
	private List<String> getLigneArray(String ligne) {
		return new ArrayList<String>(Arrays.asList(ligne.split("\\|")));
	}
	
	private static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd/MM/yyyy").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
}
