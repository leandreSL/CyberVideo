package IHM;

import java.util.Scanner;

import IHM.Machine.State;

public class Interface {
	Machine m;
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

	public void contenuPage() {
		switch(m.current_etat) {
		case ACCEUIL_NC:
			System.out.println("Acceuil (Non Connecte) : ");
			introPage();
			//TODO : afficher la pub etc...
			System.out.println("Co : Connexion");
			System.out.println("L : Location Film");
			System.out.println("Cr : Creation Compte");
			outroPage();
			break;
		case CREATION_COMPTE:
			System.out.println("Creation Compte : ");
			System.out.println("--------------");
			Scanner in = new Scanner(System.in);
			System.out.println("Rentrer votre nom : ");
			String nom = in.nextLine();
			System.out.println("Rentrer votre prenom : ");
			String prenom = in.nextLine();
			System.out.println("Rentrer votre numéro de CB : ");
			long cb = Long.valueOf(in.nextLine());
			if (m.verifCompte(nom, prenom, cb) == 0) {
				System.out.println("Votre compte a bien été créer, vous recevrez votre carte à l'acceuil de votre magasin");
			}else {
				System.out.println("Il y a eu un problème dans la création de votre compte");
				System.out.println("b : Retour Arriere");
				break;
			}
			System.out.println("");
			System.out.println("b : Retour Arriere");
			System.out.println("V : Valider");
			outroPage();
			break;
		case LOCATION_NC:
			System.out.println("Location (Non Connectee) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			System.out.println("V : Valider le choix");
			outroPage();
			//TODO : fonction d'affichage de liste DVD
			break;
		case RECAP_LOCATION_NC:
			System.out.println("Recapitulatif Location (Non Connectee) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			System.out.println("V : Valider");
			System.out.println("Co : Connexion");
			outroPage();
			break;
		case FIN_TRANSACTION_NC:
			System.out.println("Transacton (Non Connectee) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			System.out.println("CB : Finalisation de la transaction");
			outroPage();
			//TODO : fonction de saisie de la CB
			break;
		case CONNEXION_RECAP:
			//TODO : même affichage que connexion
			break;
		case CONNEXION:
			System.out.println("Connexion : ");
			introPage();
			System.out.println("Entrer votre numéro de carte");
			System.out.println("b : Retour Arriere");
			//TODO : fonction qui traite les données utilisateur
			System.out.println("V : Valider le choix");
			outroPage();
			break;
		case ACCEUIL_C:
			//TODO : savoir qui est connecté
			System.out.println("Acceuil (Connecte en tant que : TODO) : ");
			introPage();
			//TODO : afficher la pub etc...
			System.out.println("D : Deconnexion");
			System.out.println("L : Location Film");
			System.out.println("I : Informations Compte");
			outroPage();
			break;
		case INFO_COMPTE:
			System.out.println("Informations Compte (Connecte en tant que : TODO) : ");
			//TODO : affichage des infos liées au compte
			break;
		case LISTE_FILMS_LOUE :
			System.out.println("Liste des films loués (Connecte en tant que : TODO) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			outroPage();
			//TODO : affichage de la liste des films loués
			break;
		case HISTORIQUE_EMPRUNT:
			System.out.println("Historique des emprunts (Connecte en tant que : TODO) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			outroPage();
			//TODO : affichage de l'historique des emprunts
			break;
		
		case RECHARGER_COMPTE:
			System.out.println("Rechargement Compte (Connecte en tant que : TODO) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			//TODO : fonction qui traite le rechargement
			System.out.println("V : Valider le choix");
			outroPage();
			break;
		case LOCATION_C:
			System.out.println("Location (Connecte en tant que : TODO) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			System.out.println("Re : Recommandation");
			//TODO : VOIR TODO location NC + traiter les recommandations
			System.out.println("V : Valider le choix");
			outroPage();
			break;
		case AFFICHAGE_PANIER:
			System.out.println("Affichage Panier (Connecte en tant que : TODO) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			System.out.println("R : Recharger compte");
			System.out.println("V : Valider le choix");
			outroPage();
			//TODO : afficher le panier
			break;
		case RECHARGER_COMPTE_PANIER:
			System.out.println("Rechargement Compte (Connecte en tant que : TODO) : ");
			introPage();
			System.out.println("b : Retour Arriere");
			//TODO : fonction qui traite le rechargement
			System.out.println("V : Valider le choix");
			outroPage();
			break;
		case RECOMMANDATION_FILM:
			//TODO
			break;
		case FIN_TRANSACTION_C:
			
			//TODO
			break;
		}
	}
	public void traitementSaisie() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			contenuPage();
			
			String str = sc.nextLine();
			m.handle(str);
		}
	}
}
