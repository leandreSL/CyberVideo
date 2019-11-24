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
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import NF.Abonne;
import NF.DVD;
import NF.Emprunt;
import NF.Film;
import NF.Genre;
import NF.Recommandation;
import NF.StatutDVD;

public class BD {

	private static BD instance = null;
	
	private static final String dossier = "BDD";
	
	private static final String cheminFilm = dossier + File.separator + "film";
	private static final String cheminDVD = dossier + File.separator + "dvd";
	private static final String cheminEmprunt = dossier + File.separator + "emprunt";
	private static final String cheminNabo = dossier + File.separator + "nonabonne";
	private static final String cheminAbo = dossier + File.separator + "abonne";
	
	private static final int champFILMtitre = 0;
	private static final int champFILMgenre = 1;
	private static final int champFILMresume = 2;
	private static final int champFILMacteur = 3;
	private static final int champFILMreal = 4;
	private static final int champFILMlimage = 5;
	private static final int champFILMaffiche = 6;
	
	private static final int champDVDid = 0;
	private static final int champDVDetat = 1;
	private static final int champDVDfilm = 2;
	private static final int champDVDstatut = 3;
	private static final int champDVDrecommendation = 4;
	
	private static final int champEMPRUNTcb = 0;
	private static final int champEMPRUNTidabo = 1;
	private static final int champEMPRUNTdateD = 2;
	private static final int champEMPRUNTdateR = 3;
	private static final int champEMPRUNTiddvd = 4;
	
	private static final int champABOnom = 0;
	private static final int champABOprenom = 1;
	private static final int champABOrestriction = 2;
	private static final int champABOsolde = 3;
	private static final int champABOidcarte = 4;
	private static final int champABOcb = 5;

	
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
		List<String> sFilm = chercherUnique(cheminFilm, nom, champFILMtitre);
		return sFilm==null?null:strToFilm(sFilm);
	}
	
	public List<Film> chercherEnsembleFilms(){
		List<List<String>> paramFilm = chercherPlusieurs(cheminFilm, "", champFILMtitre);
		List<Film> films = new ArrayList<Film>();
		for( int i = 0; i < paramFilm.size(); i++) {
			films.add( strToFilm(paramFilm.get(i)));
		}
		return films;
	}
	
	public List<Film> chercherFilmsDisponible(){
		List<List<String>> paramFilm = chercherPlusieurs(cheminFilm, "", champFILMtitre);
		List<Film> films = new ArrayList<Film>();
		for( int i = 0; i < paramFilm.size(); i++) {
			List<String> sfilm = paramFilm.get(i);
			if( chercherDVD(sfilm.get(champFILMtitre)) != null ) {
				films.add( strToFilm(sfilm));
			}
		}
		return films;
	}
	
	public List<Film> chercherFilmParGenre(List<Genre> filtres) {
		List<Film> films = new ArrayList<Film>();
		List<List<String>> listeFilms = chercherPlusieurs(cheminFilm, filtres.get(0).getNom(), champFILMgenre);
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
	
	public boolean supprimerFilm(String titre){
		return supprimer(cheminFilm, titre, champFILMtitre);
	}
	
	public boolean stockerFilm(Film film) {
		return stocker(cheminFilm, film.toString());
	}
	@Deprecated
	public List<Recommandation> chercherRecommandations(){
		return null;
	}
	
	public boolean recommanderFilm(String titre) {
		boolean succes = false;
		Film f = chercherFilmParNom(titre);
		if( f!=null ) {
			f.setRecommandation(f.getRecommandation()+1);
			if( supprimerFilm(titre) && stockerFilm(f)) {
				succes = true;
			}
		}
		return succes;
	}

	
	// - Fonction DVD
	
	public boolean stockerDVD(DVD dvd) {
		return stocker(cheminDVD, dvd.toString());
	}
	
	public boolean supprimerDVD(int idDVD){
		return supprimer(cheminDVD, idDVD+"", champDVDid);
	}	
	
	public boolean modifierStatutDVD(int idDVD, StatutDVD emprunte) {
		boolean succes= false;
		DVD dvd = null;
		if( (dvd = chercherDVD(idDVD)) != null ) {
			dvd.setStatut(emprunte);
			if( supprimerDVD(idDVD) && stockerDVD(dvd))
				succes = true;
		}
		return succes;
	}
	
	public DVD chercherDVD(int idDVD) {
		List<String> dvd = chercherUnique(cheminDVD, idDVD+"", champDVDid);
		return dvd==null?null:strToDVD(dvd);
	}
	
	public DVD chercherDVD(String titre) {
		List<List<String>> dvds = chercherPlusieurs(cheminDVD, titre, champDVDfilm);
		Optional<List<String>> dvdLibre = dvds.stream().filter( s -> s.get(champDVDstatut).equals(StatutDVD.EnAutomate.getNom())).findFirst();
		if(dvdLibre.isPresent()) {
			return strToDVD(dvdLibre.get());
		}else {
			return null;
		}
	}
	
	public List<DVD> chercherEnsembleDVDs(){
		List<List<String>> paramDVD = chercherPlusieurs(cheminDVD, "", champDVDid);
		List<DVD> DVDs = new ArrayList<DVD>();
		for( int i = 0; i < paramDVD.size(); i++) {
			List<String> dvd = paramDVD.get(i);
			DVDs.add( strToDVD(dvd) );
		}
		return DVDs;
	}

	// - Fonction Emprunt
	
	public boolean creerEmprunt(Emprunt e) {
		return stocker(cheminEmprunt, e.toString());
	}
	
	//retourne l'emprunt pour le dvd avec la date la plus récente
	public Emprunt chercherEmpruntActuel(int idDVD){
		List<List<String>> emprunts = chercherPlusieurs(cheminEmprunt, idDVD+"", champEMPRUNTiddvd);
		Optional<List<String>> sansRetour = emprunts.stream().filter( s -> s.get(champEMPRUNTdateR).equals("")).findFirst();
		if(sansRetour.isPresent()) {
			return strToEmprunt(sansRetour.get());
		}else {
			return null;
		}
	}
	
	//actualise l'emprunt en rajoutant la date de retour du dvd
	public boolean AjouterDateRetourEmprunt(int idDVD, Date dateEmprunt, Date dateRetour) {
		boolean succes= false;
		Emprunt e = null;
		if( (e = chercherEmpruntActuel(idDVD)) != null ) {
			e.setDateRetour(dateRetour);
			if( supprimer(cheminEmprunt, idDVD+"", champEMPRUNTiddvd) && creerEmprunt(e))
				succes = true;
		}
		return succes;
	}
	
	//retourne le nombre de dvd actuellement empruntés pour une carte bleue
	public int chercherNombreEmpruntsActuelsCB(long idCB){
		return (int)chercherPlusieurs(cheminEmprunt, idCB+"", champEMPRUNTcb).stream().filter(e -> e.get(champEMPRUNTdateR).equals("")).count();
	}
	
	public int chercherNombreEmpruntsActuelsAbonne(int idCarte) {
		return (int)chercherPlusieurs(cheminEmprunt, idCarte+"", champEMPRUNTidabo).stream().filter(e -> e.get(champEMPRUNTdateR).equals("")).count();
	}
	//retourne tous les emprunts effectués par l'abonné
	public List<Emprunt> chercherEmpruntsAbonne(int idCarte) {
		List<List<String>> paramEmprunt = chercherPlusieurs(cheminEmprunt, idCarte+"", champEMPRUNTidabo);
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		for( int i = 0; i < paramEmprunt.size(); i++) {
			List<String> emprunt = paramEmprunt.get(i);
			emprunts.add( strToEmprunt(emprunt) );
		}
		return emprunts;
	}
	 //retourne le nombre de jours moyen des emprunts sur 1 an
	public int chercherTempsEmpruntMoyen() {
		LocalDate anneeDerniere = LocalDate.now().minusYears(1);
	    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
	    
		List<List<String>> emprunts = chercherPlusieurs(cheminEmprunt, "", champEMPRUNTdateD).stream()
				.filter(e -> !e.get(champEMPRUNTdateR).equals("") && 
						LocalDate.parse(e.get(champEMPRUNTdateD), formatters).isAfter(anneeDerniere) )
				.collect(Collectors.toList());
		long temps = 0;
		LocalDate dateD, dateR;
		for( List<String> emprunt : emprunts) {
			dateD = LocalDate.parse(emprunt.get(champEMPRUNTdateD), formatters);
			dateR = LocalDate.parse(emprunt.get(champEMPRUNTdateR), formatters);
			temps += Duration.between(dateD.atStartOfDay(), dateR.atStartOfDay()).toDays();
		}
		return (int)(temps/emprunts.size());
	}
	//retourne le nombre d’emprunt de la machine (total ou une moyenne?)
	public int chercherNombreEmprunt() {
		return compter(cheminEmprunt);
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
		List<String> sAbo = chercherUnique(cheminAbo, idCarte+"", champABOidcarte);
		return strToAbo(sAbo);
	}
	public boolean supprimerAbonne(int idCarte) {
		return supprimer(cheminAbo, idCarte+"", champABOidcarte);
	}
	
	public List<Abonne> chercherEnsembleAbonnes() {
		List<List<String>> paramAbo = chercherPlusieurs(cheminAbo, "", champABOidcarte);
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
	
	private int compter(String chemin) {
		File fichierOriginal = new File(chemin);
		int compte = 0;
		try {
			BufferedReader lecteur = new BufferedReader(new FileReader(fichierOriginal));
			while((lecteur.readLine()) != null)
				compte++;
			lecteur.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compte;
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
				sfilm.get(champFILMtitre),
				Genre.toGenreArray(sfilm.get(champFILMgenre).split("`")),
				sfilm.get(champFILMresume),
				Arrays.asList(sfilm.get(champFILMacteur).split("`")), 
				sfilm.get(champFILMreal),
				Integer.parseInt(sfilm.get(champFILMlimage)),
				sfilm.get(champFILMaffiche)
						);
	}
	
	private DVD strToDVD(List<String> dvd) {
		return new DVD(Integer.parseInt(dvd.get(champDVDid)), dvd.get(champDVDetat), chercherFilmParNom(dvd.get(champDVDfilm)),
				StatutDVD.getByName(dvd.get(champDVDstatut)), Integer.parseInt(dvd.get(champDVDrecommendation)));
	}
	
	private Emprunt strToEmprunt(List<String> sEmp) {
		return new Emprunt(Long.parseLong(sEmp.get(champEMPRUNTcb)), Integer.parseInt(sEmp.get(champEMPRUNTidabo)),
				parseDate(sEmp.get(champEMPRUNTdateD)), parseDate(sEmp.get(champEMPRUNTdateR)), chercherDVD(Integer.parseInt(sEmp.get(champEMPRUNTiddvd))));
	}
	
	private Abonne strToAbo(List<String> sAbo) {
		return new Abonne(sAbo.get(champABOnom), sAbo.get(champABOprenom),
				Genre.toGenreArray(sAbo.get(champABOrestriction).split("`")),
				Double.parseDouble(sAbo.get(champABOsolde)), Integer.parseInt(sAbo.get(champABOidcarte)), Long.parseLong(sAbo.get(champABOcb)));
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
