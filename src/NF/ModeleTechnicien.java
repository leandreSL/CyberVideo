package NF;

import java.util.List;

import NF.gestionfichier.BD;

public class ModeleTechnicien extends Modele{
	
	public void ajouterFilm(String titre, List<Genre> genres, String resume, 
			List<String> acteurs, String realisateur, int limiteAge, String cheminAffiche) {
		Film f = new Film(titre, genres, resume, acteurs, realisateur,limiteAge, cheminAffiche);
		if (!bd.stockerFilm(f))
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	
	
	public void supprimerFilm(String titre) {
		if(bd.supprimerFilm(titre)) 
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	
	//ajoute un dvd à l'automate - par d�faut, le DVD est ajout� au magasin
	public void ajouterDVD(int identifiantDVD, Film film) {
		DVD dvd = new DVD(identifiantDVD, film);
		if(!bd.stockerDVD(dvd))
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	
	//supprime un dvd de la liste
	public void supprimerDVD(int idDvd) {
		if(!bd.supprimerDVD(idDvd))
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	

	
	
	public void changerEtatDVD(int idDVD, String etat) {
		if(!bd.modifierStatutDVD(idDVD, StatutDVD.getByName(etat)))
			throw(new Exception("Erreur base de donn�e"));
		return;
	}
	
	public String donnerListeDVDs() {
		String result = "num�ro DVD, �tat, film, statut, recommandation\n";
		for(DVD dvd : bd.chercherEnsembleDVDs())
			result += dvd.toString() + "\n";
		return result;		
	}
	
	public String donnerListeFilms() {
		String result = "titre, r�sum�, genres, acteurs, r�alisateur, limite d'age\n";
		for(Film f : bd.chercherEnsembleFilms())
			result += f.toString() + "\n";
		return result;		
	}

	public String donnerListeAbonnes() {
		String result = "nom, pr�nom, restrictions, solde, numero d'abonn�, num�ro de carte bleue\n";
		for(Abonne a : bd.chercherEnsembleAbonnes())
			result += a.toString() + "\n";
		return result;
	}
	
	public String donnerListeRecommandations (){
		String result = "";
		for(String recommandation: bd.chercherRecommandations())
			result += recommandation + "\n";
		return result;		
	}
	
	
	public int donnerTempsEmpruntMoyen() {
		return bd.chercherTempsEmpruntMoyen();
	}
	
	public int donnerNombreEmprunt() {
		return bd.chercherNombreEmprunt();
	} 
	
	
	public void supprimerCompte(int idCarte) {
		if(!bd.supprimerAbonne(idCarte)) {
			throw(new Exception("Erreur base de donn�e"));
		}
		
		return;
	}

}
