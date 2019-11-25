package IHM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import IHM.Machine.State;
import NF.Abonne;
import NF.DVD;
import NF.Film;
import NF.Genre;

public class Interface {
	Machine m;
	Scanner sc = new Scanner(System.in);
	String entree;
	
	public Interface() {
		m = new Machine();
	}
	
	public void introPage() {
		System.out.println("--------------");
		System.out.println("Veuillez rentrer une lettre correspondant à l'action que vous souhaitez effecutée");
		System.out.println("--------------");
	}
	
	public void outroPage() {
		System.out.println("--------------");
		System.out.println("Saisir l'action :");
	}
	
	public void affichageConnexion() {
		System.out.println("Connexion : ");
		System.out.println("Entrer votre numéro de carte : ");
		envoyerEntree();
	}
	
	public void afficherPanier() {
		List<Film> films = m.modele_abo.afficherPanier();
		for (Film f : films) {
			System.out.println(f);
		}
	}
	
	public void affichageFilmsDispos() {
		List<Film> films = m.modele_abo.filmDispos();
		int i=1;
		for (Film f : films ) {
			if (m.modele_abo.existeDVDFilm(f)) {
				System.out.println(Integer.toString(i) + " : " + f);
			}else {
				System.out.println(Integer.toString(i) + " : " + f + " (Aucun DVD disponible pour ce film, voir en boutique)");
				System.out.println("");
			}
			i++;
		}
		System.out.println("Vi : Vider le panier");
		System.out.println("Back : Revenir à l'acceuil");
	}
	
	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( NumberFormatException e ) {
	        return false;
	    }
	}
	
	public void envoyerEntree() {
		entree = sc.nextLine();
		m.handle(entree);
	}
	
	public void affichageDVDs() {
		List<DVD> dvds = m.modele_tech.donnerListeDVDs();
		for (DVD d : dvds) {
			System.out.println(d);
		}
	}
	
	public void contenuPage() {
		long cb_loc_nc = 0;
		switch(m.current_etat) {
		case ACCEUIL_NC:
			System.out.println("Acceuil (Non Connecte) : ");
			introPage();
			//TODO : afficher la pub etc...
			System.out.println("Co : Connexion");
			System.out.println("L : Location Film");
			System.out.println("Ren : Rendu Film");
			System.out.println("Cr : Creation Compte");
			outroPage();
			envoyerEntree();
			break;
		case CREATION_COMPTE:
			System.out.println("Creation Compte : ");
			System.out.println("--------------");
			System.out.println("Rentrer votre nom : ");
			String nom = sc.nextLine();
			System.out.println("Rentrer votre prenom : ");
			String prenom = sc.nextLine();
			System.out.println("Rentrer votre numéro de CB : ");
			long cb;
			try {
				cb = Long.valueOf(sc.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un numéro de CB correct");
				System.out.println("b : Retour Arriere");
				break;
			}
			System.out.println("Nouveau solde de votre compte (minimum 15€ lors de l'inscription) : ");
			double solde;
			try {
				solde = Double.valueOf(sc.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un solde correct");
				System.out.println("b : Retour Arriere");
				break;
			}
			String test_creation_compte = m.verifCompte(nom, prenom,solde, cb);
			System.out.println(test_creation_compte); //verification si il y a deja un abonne avec les meme infos, verif aussi du solde qui n'est pour l'instant pas gérer 
			if (test_creation_compte.contains("Erreur")) {
				System.out.println("b : Retour");
			}else {
				System.out.println("V : Valider");
			}
			outroPage();
			envoyerEntree();
			break;
		case LOCATION_NC:
			System.out.println("Location (Non Connectee) : ");
			System.out.println("Rentrer votre numéro de CB (rien ne sera débité avant la validation du panier) : ");
			
			try {
				cb_loc_nc = Long.valueOf(sc.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un numéro de CB correct");
				break;
			}
			affichageFilmsDispos();
			System.out.println("Rentrer le titre du film ou l'action souhaitée : ");
			String film_choisi_nc = sc.nextLine();
			if (isInteger(film_choisi_nc)) {
				int idDVD = Integer.valueOf(film_choisi_nc);
				try {
					m.modele_abo.ajouterAuPanier(idDVD, cb_loc_nc);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					break;
				}
			}else {
				if(film_choisi_nc.contains("Back")) {
					m.handle("b");
					break;
				}
				try {
					m.modele_abo.ajouterAuPanier(film_choisi_nc, cb_loc_nc);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					break;
				}
			}
			introPage();
			
			System.out.println("b : Retour Arriere");
			System.out.println("V : Valider le choix");
			outroPage();
			envoyerEntree();
			//TODO : fonction d'affichage de liste DVD :
			// Need : fonction BDD qui renvoie une ArrayList de Films qu'on va parcourir et afficher les infos liés a chaque film (fct toString de Film)
			
			break;
		case RECAP_LOCATION_NC:
			System.out.println("Recapitulatif Location (Non Connectee) : ");
			System.out.println("");
			afficherPanier();
			introPage();
			System.out.println("b : Revenir à la location");
			System.out.println("V : Valider (Vous allez être débiter après cette action)");
			System.out.println("Co : Connexion");
			outroPage();
			envoyerEntree();
			break;
		case FIN_TRANSACTION_NC:
			System.out.println("Transacton (Non Connectee) : ");
			try {
				m.modele_abo.valider(cb_loc_nc);
			}catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
			System.out.println("------------");
			System.out.println("Transaction réussite");
			introPage();
			System.out.println("V : Revenir à l'acceuil");
			outroPage();
			envoyerEntree();
			break;
			
		case CONNEXION_RECAP:
		case CONNEXION:
			affichageConnexion();
			break;
		case ACCEUIL_C:
			System.out.println("Acceuil (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			introPage();
			System.out.println("D : Deconnexion");
			System.out.println("L : Location Film");
			System.out.println("I : Informations Compte");
			System.out.println("Ren : Rendu");
			outroPage();
			envoyerEntree();
			break;
		case INFO_COMPTE:
			System.out.println("Informations Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			System.out.println("");
			try {
				System.out.println(m.modele_abo.donnerInformationsAbonne());
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("");
			introPage();
			System.out.println("S : Demande supression compte");
			System.out.println("H : Afficher Historique des emprunts");
			System.out.println("Li : Liste des filsm loués");
			System.out.println("Rec : Recharger Compte");
			System.out.println("Ren : Rendre un DVD");
			System.out.println("b : Retour à l'acceuil");
			outroPage();
			envoyerEntree();
			break;
		case LISTE_FILMS_LOUE :
			System.out.println("Liste des films loues (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			//MANQUE FONCTION POUR AVOIR LES EMPRUNTS
			introPage();
			System.out.println("b : Retour Arriere");
			outroPage();
			envoyerEntree();
			break;
		case HISTORIQUE_EMPRUNT:
			System.out.println("Informations Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			System.out.println("");
			try {
				System.out.println(m.modele_abo.donnerListeEmprunts());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
			introPage();
			System.out.println("b : Retour Arriere");
			outroPage();
			envoyerEntree();
			break;
		
		case RECHARGER_COMPTE:
			System.out.println("Informations Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			System.out.println("");
			System.out.println("Rentrer votre numéro de CB : ");
			long cb_r;
			Scanner recharger = new Scanner(System.in);
			try {
				cb_r = Long.valueOf(recharger.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un numéro de carte valide");
				break;
			}
			System.out.println("Montant à recharger (supérieur ou égal à 10€) : ");
			int montant=0;
			try {
				montant = recharger.nextInt();
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un montant valide");
				break;
			}
			try {
				m.modele_abo.rechargerCarte(cb_r, montant);
			}catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
			introPage();
			System.out.println("b : Retour Arriere");
			//TODO : fonction qui traite le rechargement
			// need : fonction qui prend en paramètre une abonne et un int (rechargement) et qui met a jour le solde du compte (renvoie 0 ou 1 selon l'exec)
			// fais dans machine (dis moi si ca te va )
            System.out.println("V : Valider le choix");
			outroPage();
			envoyerEntree();
			break;
		case LOCATION_C:
			System.out.println("Location (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			affichageFilmsDispos();
			
			System.out.println("Rentrer le titre du film ou l'action souhaitée : ");
			String film_choisi = sc.nextLine();
			if (isInteger(film_choisi)) {
				int idDVD = Integer.valueOf(film_choisi);
				try {
					m.modele_abo.ajouterAuPanier(idDVD, m.modele_abo.donnerCBAbonne());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					break;
				}
			}else {
				if (film_choisi.contains("Vi")) {
					m.modele_abo.viderPanier();
					System.out.println("Votre panier est vide");
					break;
				}
				if(film_choisi.contains("Back")) {
					m.handle("b");
					break;
				}
				try {
					m.modele_abo.ajouterAuPanier(film_choisi, m.modele_abo.donnerCBAbonne());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					break;
				}
			}
			//TODO : VOIR TODO location NC et need
			//TODO : peut-etre faire en sorte de supprimer un article du panier
			introPage();
			System.out.println("V : Valider le choix");
			System.out.println("b : Retour Arriere");
			System.out.println("Re : Recommandation");
			outroPage();
			envoyerEntree();
			break;
		case AFFICHAGE_PANIER:
			System.out.println("Informations Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			System.out.println("");
			afficherPanier();
			introPage();
			System.out.println("b : Retour Arriere");
			System.out.println("Rec : Recharger compte");
			System.out.println("V : Valider le choix");
			outroPage();
			envoyerEntree();
			break;
		case RECHARGER_COMPTE_PANIER:
			System.out.println("Rechargement Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			System.out.println("");
			long cb_rc;
			try {
				cb_r = Long.valueOf(sc.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un numéro de carte valide");
				break;
			}
			System.out.println("Montant à recharger (supérieur ou égal à 10€) : ");
			int montant_rc=0;
			try {
				montant = sc.nextInt();
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un montant valide");
				break;
			}
			try {
				m.modele_abo.rechargerCarte(cb_r, montant_rc);
			}catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
			introPage();
			System.out.println("V : Valider le choix");
			outroPage();
			envoyerEntree();
			break;
		case RECOMMANDATION_FILM:
			//TODO
			break;
		case FIN_TRANSACTION_C:
			System.out.println("Transacton (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			try {
				m.modele_abo.valider(m.modele_abo.donnerCBAbonne());
				System.out.println("------------");
				System.out.println("Transaction réussite");
			}catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
			introPage();
			System.out.println("V : Revenir à l'acceuil");
			outroPage();
			envoyerEntree();
			break;

        case RENDU:
			System.out.println("Rendu de DVD: ");
			introPage();
			System.out.println("b : retour");
			System.out.println("id DVD : rend le DVD voulu");
			outroPage();
			envoyerEntree();
			break;
		case ACCEUIL_TECH:
			System.out.println("Acceuil Technicien: ");
			introPage();
			System.out.println("D : Deconnexion");
			System.out.println("Maj : Mettre a jour les DVD dans l'automate");
			System.out.println("Re : Voir les recommandations");
			outroPage();
			envoyerEntree();
			break;
		case MAJ_DVD_AUTOMATE:
			System.out.println("Mise a jour DVD automate ");
			introPage();
			affichageDVDs();
			System.out.println("+ : Ajouter DVD");
			System.out.println("- : Supprimer DVD");
			System.out.println("b : Retour");
			System.out.println("Action : ");
			Scanner maj_dvd = new Scanner(System.in);
			String choix = maj_dvd.nextLine();
			switch(choix) {
			case "+":
				System.out.println("ID du DVD à créer : ");
				String id_str_ajout = maj_dvd.nextLine();
				int id_dvd_ajout;
				try {
					id_dvd_ajout = Integer.valueOf(id_str_ajout);
				}catch(NumberFormatException e) {
					System.out.println("Veuillez rentrer un id correct");
					break;
				}
				System.out.println("Titre du film associé : ");
				String titre_film = maj_dvd.nextLine();
				Film f = m.modele_tech.chercherFilm(titre_film);
				if (f==null) {
					System.out.println("Film introuvable dans la base, création du film ...");
					System.out.println("Résumé du film : ");
					String resume = maj_dvd.nextLine();
					System.out.println("Réalisateur (1 seul) : ");
					String real = maj_dvd.nextLine();
					System.out.println("Limite d'age");
					String limite_str = maj_dvd.nextLine();
					int limite_age;
					try {
						limite_age = Integer.valueOf(limite_str);
					}catch (NumberFormatException e) {
						System.out.println("Rentrer une limite correcte");
						break;
					}
					ArrayList genres = new ArrayList<Genre>();
					Genre genre = Genre.ACTION ;
					genres.add(genre);
					ArrayList<String> acteurs = new ArrayList<>();
					String acteur = "arnold schwarzenegger";
					acteurs.add(acteur);
					Film new_f = new Film(titre_film,genres,resume,acteurs,real,limite_age,"affiche.png");
					DVD d = new DVD(id_dvd_ajout,f);
					try {
						m.modele_tech.ajouterDVD(d);
					}catch (Exception e){
						System.out.println(e.getMessage());
					}
				}else {
					DVD d = new DVD(id_dvd_ajout,f);
					try {
						m.modele_tech.ajouterDVD(d);
					}catch (Exception e){
						System.out.println(e.getMessage());
					}
				}
				
				break;
			case "-":
				System.out.println("ID du DVD à supprimer : ");
				String id_str = maj_dvd.nextLine();
				int id_dvd;
				try {
					id_dvd = Integer.valueOf(id_str);
				}catch(NumberFormatException e) {
					System.out.println("Veuillez rentrer un id correct");
					break;
				}
				try {
					m.modele_tech.supprimerDVD(id_dvd);
					System.out.println("V : Valider le choix ");
				}catch(Exception e) {
					System.out.println(e.getMessage());
					break;
				}
				break;
			case "b":
				break;
			}
			outroPage();
			break;
		case LISTE_RECOMANDATION:
			System.out.println("Liste des recommandations ");
			introPage();
			//System.out.println(m.modele_tech.donnerListeRecommandations() );
			System.out.println("Ok : Retour acceuil");
			outroPage();
			envoyerEntree();
			break;
		case STATS_TECH:
			System.out.println("Liste des recommandations ");
			introPage();
			System.out.println("Ok : Retour acceuil");
			System.out.println("temps emprunts moyen : " + Integer.toString(m.modele_tech.donnerTempsEmpruntMoyen()));
			System.out.println("nombre emprunts: " + Integer.toString(m.modele_tech.donnerNombreEmprunt()));
			outroPage();
			envoyerEntree();
			break;
			
		default:
			break;
		}
	}
	public void traitementSaisie() {
		while(true) {
			System.out.flush();
			contenuPage();
			
			
		}
	}
}
