package NF.gestionfichier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	
	public ArrayList<Film> chercherFilm(String nom) {
		ArrayList<ArrayList<String>> paramFilm = chercher(cheminFilm, nom, 0);
		ArrayList<Film> films = null;
		for( int i = 0; i < paramFilm.size(); i++) {
			films.add(new Film(
					paramFilm.get(i).get(0),
					Genre.toGenreArray(paramFilm.get(i).get(1).split("`")),
					paramFilm.get(i).get(2),
					Arrays.asList(paramFilm.get(i).get(3)), 
					paramFilm.get(i).get(4),
					Integer.parseInt(paramFilm.get(i).get(5)),
					paramFilm.get(i).get(6)
							));
		}
		return films;
	}
	
	public Film[] chercherGenre(String genre){
		ArrayList<ArrayList<String>> listeFilms = chercher(cheminFilm, genre, 1);
		return null;
		
		
	}
	
	//retourne une liste de 
	public List<Film> chercherGenreFilm(List<Genre> filtres) {
		// TODO Auto-generated method stub
		return null;
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
	
	private ArrayList<ArrayList<String>> chercher(String chemin, String idcherche, int idindex) {
		File fichierOriginal = new File(chemin);
		try {
			BufferedReader lecteur = new BufferedReader(new FileReader(fichierOriginal));

			String ligne, currentID;
			ArrayList<ArrayList<String>> liste = new ArrayList<ArrayList<String>>();
			while((ligne = lecteur.readLine()) != null) {
			    ArrayList<String> lineSplited = getLigneArray(ligne.trim());
			    currentID = lineSplited.get(idindex);
			    if(currentID.contains(idcherche)) {
			    	liste.add(lineSplited);
			    }
			}
			lecteur.close(); 
			return liste;
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
	
	private ArrayList<String> getLigneArray(String ligne) {
		return new ArrayList<String>(Arrays.asList(ligne.split("\\|")));
	}



}
