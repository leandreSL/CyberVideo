package NF;

import java.util.List;

import NF.gestionfichier.BD;

public class ModeleTechnicien{
	private BD bd;
	private static ModeleTechnicien instance = null;
	
	public ModeleTechnicien() {
		bd = BD.getInstance();
	}
	
	public static ModeleTechnicien getInstance() {
		if(instance == null) {
			instance = new ModeleTechnicien();
		}
		return (ModeleTechnicien) instance;
	}
	
	public void ajouterFilm(Film f) throws Exception {
		if (!bd.stockerFilm(f))
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	
	
	public void supprimerFilm(String titre) throws Exception {
		if(!bd.supprimerFilm(titre)) 
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	
	//ajoute un dvd à l'automate - par d�faut, le DVD est ajout� au magasin
	public void ajouterDVD(DVD dvd) throws Exception {
		if(!bd.stockerDVD(dvd))
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	
	//supprime un dvd de la liste
	public void supprimerDVD(int idDvd) throws Exception {
		if(!bd.supprimerDVD(idDvd))
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	

	
/*	
	public void changerEtatDVD(int idDVD, String etat) throws Exception {
		if(!bd.modifierEtatDVD(idDVD, StatutDVD.getByName(etat)))
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
*/
	
	public List<DVD> donnerListeDVDs() {
		return bd.chercherEnsembleDVDs();
		/*
		String result = "num�ro DVD, �tat, film, statut, recommandation\n";
		for(DVD dvd : bd.chercherEnsembleDVDs())
			result += dvd.toString() + "\n";
		return result;
		*/
	}
	
	public List<Film> donnerListeFilms() {
		return bd.chercherEnsembleFilms();
		/*
		String result = "titre, r�sum�, genres, acteurs, r�alisateur, limite d'age\n";
		for(Film f : bd.chercherEnsembleFilms())
			result += f.toString() + "\n";
		return result;
		*/
	}
	
	public List<Film> donnerListeRecommandations(){
		//TODO : renvoyer vraiment les recommendations
		return bd.chercherEnsembleFilms();
	}

	public List<Abonne> donnerListeAbonnes() {
		return bd.chercherEnsembleAbonnes();
		/*
		String result = "nom, pr�nom, restrictions, solde, numero d'abonn�, num�ro de carte bleue\n";
		for(Abonne a : bd.chercherEnsembleAbonnes())
			result += a.toString() + "\n";
		return result;
		*/
	}
	
	public int donnerTempsEmpruntMoyen() {
		return bd.chercherTempsEmpruntMoyen();
	}
	
	public int donnerNombreEmprunt() {
		return bd.chercherNombreEmprunt();
	} 
	
	
	public void supprimerCompte(int idCarte) throws Exception {
		if(!bd.supprimerAbonne(idCarte)) {
			throw(new Exception("Erreur base de donn�e"));
		}
		return;
	}
}
