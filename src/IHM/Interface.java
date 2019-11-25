package IHM;

import java.util.List;
import java.util.Scanner;

import IHM.Machine.State;
import NF.Abonne;
import NF.DVD;
import NF.Film;

public class Interface {
	Machine m;
	Scanner sc = new Scanner(System.in);
	
	
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
		/*Scanner co = new Scanner(System.in);
		int num_carte_abo = co.nextInt();
		
		System.out.println("V : Valider");*/
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
			break;
		case LOCATION_NC:
			System.out.println("Location (Non Connectee) : ");
			System.out.println("Rentrer votre numéro de CB (rien ne sera débité avant la validation du panier) : ");
			
			try {
				cb_loc_nc = Long.valueOf(sc.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un numéro de CB correct");
				System.out.println("b : Retour Arriere");
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
				try {
					m.modele_abo.ajouterAuPanier(film_choisi_nc, cb_loc_nc);
				} catch (Exception e) {
					
					System.out.println(e.getMessage());
					System.out.println("b : Retour Arriere");
					break;
				}
			}
			introPage();
			
			System.out.println("b : Retour Arriere");
			System.out.println("V : Valider le choix");
			outroPage();
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
			break;
		case FIN_TRANSACTION_NC:
			System.out.println("Transacton (Non Connectee) : ");
			try {
				m.modele_abo.valider(cb_loc_nc);
			}catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("b : Retour Arriere");
				break;
			}
			System.out.println("------------");
			System.out.println("Transaction réussite");
			introPage();
			System.out.println("V : Revenir à l'acceuil");
			outroPage();
			//TODO : fonction de saisie de la CB
			//TODO : mise a jour de la bdd avec la location du film selectionné (donc 1 dvd en moins etc)
			// Need : fonction de maj pour mettre a jour la liste des dvds dispo
			break;
			
		//POUR LUES 2 CAS DE CONNEXION :
			// need : fonction qui cherche dans la bdd un abonne par rapport à son num carte et renvoie une instance d'abonne
			// ensuite faire : abonne_courant = *nomFonctionGetAbonne(num_carte)*
		case CONNEXION_RECAP:
			//TODO : même affichage que connexion
			// Variable du film selectionné en etant non connecté à enregistrer
			introPage();
			affichageConnexion();
			//outroPage();
			break;
		case CONNEXION:
			introPage();
			affichageConnexion();
			//outroPage();
			break;
		case ACCEUIL_C:
			System.out.println("Acceuil (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			introPage();
			//TODO : afficher la pub etc...
			System.out.println("D : Deconnexion");
			System.out.println("L : Location Film");
			System.out.println("I : Informations Compte");
			System.out.println("Ren : Rendu");
			outroPage();
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
			break;
		case LISTE_FILMS_LOUE :
			System.out.println("Informations Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			//MANQUE FONCTION POUR AVOIR LES EMPRUNTS
			introPage();
			System.out.println("b : Retour Arriere");
			outroPage();
			//TODO : affichage de la liste des films loués
			// need : fonction qui prend en paramètre un abonné et qui renvoie sa liste de films loues
			break;
		case HISTORIQUE_EMPRUNT:
			System.out.println("Informations Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			System.out.println("");
			try {
				System.out.println(m.modele_abo.donnerListeEmprunts());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			introPage();
			System.out.println("b : Retour Arriere");
			outroPage();
			break;
		
		case RECHARGER_COMPTE:
			System.out.println("Informations Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
			System.out.println("");
			long cb_r;
			try {
				cb_r = Long.valueOf(sc.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Veuillez rentrer un numéro de carte valide");
				break;
			}
			System.out.println("Montant à recharger (supérieur ou égal à 10€) : ");
			int montant=0;
			try {
				montant = sc.nextInt();
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
			break;
		case LOCATION_C:
			System.out.println("Informations Compte (Connecte en tant que : "+ m.modele_abo.donnerIdentificationAbonne() + " ) : ");
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
				if (film_choisi == "Vi") {
					m.modele_abo.viderPanier();
					System.out.println("Votre panier est vide");
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
			}
			introPage();
			System.out.println("V : Valider le choix");
			outroPage();
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
				System.out.println("b : Retour Arriere");
				break;
			}
			introPage();
			System.out.println("V : Revenir à l'acceuil");
			outroPage();
			
			break;

        case RENDU:
			System.out.println("Rendu de DVD: ");
			introPage();
			System.out.println("b : retour");
			System.out.println("id DVD : rend le DVD voulu");
			outroPage();
			break;
		case ACCEUIL_TECH:
			System.out.println("Acceuil Technicien: ");
			introPage();
			System.out.println("D : Deconnexion");
			System.out.println("Maj : Mettre a jour les DVD dans l'automate");
			System.out.println("Re : Voir les recommandations");
			outroPage();
			break;
		case MAJ_DVD_AUTOMATE:
			System.out.println("Mise a jour DVD automate ");
			introPage();
			//aled lélé
			//c cho
			outroPage();
			break;
		case LISTE_RECOMANDATION:
			System.out.println("Liste des recommandations ");
			introPage();
			//System.out.println(m.modele_tech.donnerListeRecommandations() );
			System.out.println("Ok : Retour acceuil");
			outroPage();
			break;
		case STATS_TECH:
			System.out.println("Liste des recommandations ");
			introPage();
			System.out.println("Ok : Retour acceuil");
			System.out.println("temps emprunts moyen : " + Integer.toString(m.modele_tech.donnerTempsEmpruntMoyen()));
			System.out.println("nombre emprunts: " + Integer.toString(m.modele_tech.donnerNombreEmprunt()));
			outroPage();
			break;
			
		default:
			break;
		}
	}
	public void traitementSaisie() {
		while(true) {
			System.out.flush();
			contenuPage();
			
			String str = sc.nextLine();
			m.handle(str);
		}
	}
}
