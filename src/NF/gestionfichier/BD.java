package NF.gestionfichier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import NF.DVD;
import NF.Film;
import NF.Genre;

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
	
	public Film chercherFilm(String nom) {
		String[] paramFilm = chercher(cheminFilm, nom, 0);
		Film film = null;
		if(paramFilm != null) {
			film = new Film(
					paramFilm[0],
					Genre.toGenreArray(paramFilm[1].split("`")),
					paramFilm[2],
					Arrays.asList(paramFilm[3]), 
					paramFilm[4],
					Integer.parseInt(paramFilm[5]),
					paramFilm[6]
							);
		}
		return film;
	}
	
	public void supprimerFilm(Film film){
		supprimer(cheminFilm, film.getTitre(), 0);
	}
	
	public void supprimerDVD(DVD dvd){
		supprimer(cheminDVD, dvd.getIdentifiantDVD()+"", 0);
	}
	
	public void stockerFilm(Film film) {
		stocker(cheminFilm, film.toString());
	}
	
	public void stockerDVD(DVD dvd) {
		stocker(cheminDVD, dvd.toString());
	}
	
	private void stocker(String chemin, String ajout) {
	    try(FileWriter fw = new FileWriter(chemin, true);
	    	    BufferedWriter bw = new BufferedWriter(fw);
	    	    PrintWriter out = new PrintWriter(bw))
	    	{
	    	    out.print(ajout);
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
		
	}
	
	private String[] chercher(String chemin, String idcherche, int idindex) {
		File fichierOriginal = new File(chemin);
		try {
			BufferedReader lecteur = new BufferedReader(new FileReader(fichierOriginal));

			String ligne, currentID;
			while((ligne = lecteur.readLine()) != null) {
			    String[] lineSplited = getLigneArray(ligne.trim());
			    currentID = lineSplited[idindex];
			    if(currentID.equals(idcherche)) return lineSplited;
			}
			lecteur.close(); 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void supprimer(String chemin, String idSuppression, int idindex) {
		File fichierOriginal = new File(chemin);
		File fichierTemp = new File(dossier + File.separator + "temp.txt");
		try {
			BufferedReader lecteur = new BufferedReader(new FileReader(fichierOriginal));
			BufferedWriter redacteur = new BufferedWriter(new FileWriter(fichierTemp));

			String ligne, currentID;
			while((ligne = lecteur.readLine()) != null) {
			    currentID = getLigneArray(ligne.trim())[idindex];
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
	}
	
	private String[] getLigneArray(String ligne) {
		return ligne.split("\\|");
	}

}
