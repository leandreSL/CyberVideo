package gestionfichier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import NF.DVD;
import NF.Film;

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
	
	public void ajouterFilm(Film film) {
		ajouter(cheminFilm, film.toString());
	}
	
	public void ajouterDVD(DVD dvd) {
		ajouter(cheminDVD, dvd.toString());
	}
	
	private void ajouter(String chemin, String ajout) {
		 // Convert the string to a
	    // byte array.
	    System.out.println("test1");
	    try(FileWriter fw = new FileWriter(chemin, true);
	    	    BufferedWriter bw = new BufferedWriter(fw);
	    	    PrintWriter out = new PrintWriter(bw))
	    	{
	    		System.out.println("ecriture");
	    	    out.print(ajout);
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
		
	}
	
	public static BD getInstance() {
		if(instance == null) {
			instance = new BD();
		}
		return instance;
	}
}
