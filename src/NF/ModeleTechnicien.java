package NF;

import java.util.List;

import NF.gestionfichier.BD;

public class ModeleTechnicien {
	BD bd;
	private static ModeleTechnicien instance = null;

	private ModeleTechnicien() {
		bd = BD.getInstance();
	}
	
	public static ModeleTechnicien getInstance() {
		if(instance == null) {
			instance = new ModeleTechnicien();
		}
		return instance;
	}
	
	
	public void ajouterFilm(String titre, List<Genre> genres, String resume, 
			List<String> acteurs, String realisateur, int limiteAge, String cheminAffiche) {
		Film f = new Film(titre, genres, resume, acteurs, realisateur,limiteAge, cheminAffiche);
		if (!bd.stockerFilm(f))
			throw(new Exception("Erreur base de donnée"));
		return;
	}
	
	
	public void supprimerFilm(String titre) {
		if(bd.supprimerFilm(titre)) 
			throw(new Exception("Erreur base de donnée"));
		return;
	}
	
	//ajoute un dvd Ã  l'automate - par défaut, le DVD est ajouté au magasin
	public void ajouterDVD(int identifiantDVD, Film film) {
		if(!bd.stockerDVD(dvd))
			throw(new Exception("Erreur base de donnée"));
		return;
	}
	
	//supprime un dvd de la liste
	public void supprimerDVD(int idDvd) {
		if(!bd.supprimerDVD(idDvd))
			throw(new Exception("Erreur base de donnée"));
		return;
	}
	

	public String donnerListeRecommandations (){
		String result = "";
		for(String recommandation: bd.chercherEnsembleRecommandations())
			result += recommandation + "\n";
		return result;		
	}
	
	public void changerEtatDVD(int idDVD, String etat) {
		if(bd.modifierStatutDVD(idDVD, StatutDVD.getByName(etat)))
			throw(new Exception());
		return;
	}
	
	public String donnerListeDVDs() {
		String result = "";
		for(DVD dvd : bd.chercherEnsembleDVDs())
			result += dvd.toString() + "\n";
		return result;		
	}
	
	public String donnerListeFilms() {
		String result = "";
		for(Film f : bd.chercherEnsembleFilms())
			result += f.toString() + "\n";
		return result;		
	}
	
	abonne chercherEnsembleAbonne()

}
