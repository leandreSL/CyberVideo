package IHM;

import java.util.ArrayList;

import NF.Abonne;
import NF.DVD;
public class Machine {
	enum State {ACCEUIL_NC,CREATION_COMPTE,LOCATION_NC,RECAP_LOCATION_NC,CONNEXION_RECAP,AFFICHAGE_PANIER,CONNEXION,ACCEUIL_C,
				INFO_COMPTE,RECHARGER_COMPTE,RECHARGER_COMPTE_PANIER,HISTORIQUE_EMPRUNT,LISTE_FILMS_LOUE,LOCATION_C,RECOMMANDATION_FILM,FIN_TRANSACTION_NC,
				FIN_TRANSACTION_C,AUTHENTIFICATION_RENDU,RECAP_RENDU_NC,RECAP_RENDU_C,ACCEUIL_TECH,MAJ_DVD_AUTOMATE,LISTE_RECOMANDATION}

	public State current_etat = State.ACCEUIL_NC;
	Abonne abonne_courant;
	public ArrayList<DVD> panier = new ArrayList<DVD>();
	
	public int verifCompte(String nom, String prenom, long CB) {
		//TODO
		return 0;
	}
	
	public void handle(String action) {
		switch(this.current_etat) {
		case ACCEUIL_NC:
			//transitions possible
			switch(action) {
			//traitement
			case "L":
				current_etat = State.LOCATION_NC;
				break;
			case "Co":
				current_etat = State.CONNEXION;
				break;
			case "Cr":
				current_etat = State.CREATION_COMPTE;
				break;
			case "R":
				current_etat = State.AUTHENTIFICATION_RENDU;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
				
			}
			break;
		case CREATION_COMPTE:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.ACCEUIL_NC;
				break;
			case "V":
				current_etat = State.ACCEUIL_NC;
				break;
			//TODO: gestion des infos utilisateur
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
				
			}
			break;
		case LOCATION_NC:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.ACCEUIL_NC;
				break;
			case "V":
				current_etat = State.RECAP_LOCATION_NC;
				break;
			//TODO:autres cas a rajouter (ex : selection film ...)
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
				
			}
			break;
		case RECAP_LOCATION_NC:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.LOCATION_NC;
				break;
			case "Co":
				current_etat = State.CONNEXION_RECAP;
				break;
			case "V":
				current_etat = State.FIN_TRANSACTION_NC;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
				
			}
			break;
		case FIN_TRANSACTION_NC:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.RECAP_LOCATION_NC;
				break;
			case "CB":
				//TODO:traitement de la CB
				current_etat = State.ACCEUIL_NC;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case CONNEXION_RECAP:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.RECAP_LOCATION_NC;
				break;
			case "V":
				//TODO:traitement de la connexion
				current_etat = State.AFFICHAGE_PANIER;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case CONNEXION:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.ACCEUIL_NC;
				break;
			default:
				if (/* la chaine rentré est un id de carte abonné*/) {
					System.out.println("ID rentré correct, connexion ...");
					if (/*id rentré  est 1*/) {
						current_etat= State.ACCEUIL_TECH;
					}
					else {
						current_etat = State.ACCEUIL_C;
					}
				}
				else {
					System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles ou rentrer un ID correct");
				}
				break;
			}
			break;
		case ACCEUIL_C:
			switch(action) {
			//traitement
			case "D":
				current_etat = State.ACCEUIL_NC;
				break;
			case "L":
				//TODO:traitement de la CB
				current_etat = State.LOCATION_C;
				break;
			case "I":
				current_etat = State.INFO_COMPTE;
				break;
			case "Ren":
				current_etat= State.RECAP_RENDU_C;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case INFO_COMPTE:
			switch(action) {
			//traitement
			case "S":
				//traitement suppression
				current_etat = State.ACCEUIL_NC;
				break;
			case "H":
				current_etat = State.HISTORIQUE_EMPRUNT;
				break;
			case "Li":
				current_etat = State.LISTE_FILMS_LOUE;
				break;
			case "Rec":
				current_etat = State.RECHARGER_COMPTE;
				break;
			case "Ren":
				current_etat = State.RECAP_RENDU_C;
				break;break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case LISTE_FILMS_LOUE :
		case HISTORIQUE_EMPRUNT:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.INFO_COMPTE;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		
		case RECHARGER_COMPTE:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.INFO_COMPTE;
				break;
			case "V":
				current_etat = State.INFO_COMPTE;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case LOCATION_C:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.ACCEUIL_C;
				break;
			case "Re":
				current_etat = State.RECOMMANDATION_FILM;
				//TODO: gestion selection
				break;
			case "V":
				current_etat = State.AFFICHAGE_PANIER;
				break;
			//TODO: gestion selection dvd ou deselection
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case AFFICHAGE_PANIER:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.LOCATION_C;
				break;
			case "Rec":
				current_etat = State.RECHARGER_COMPTE_PANIER;
				break;
			case "V":
				current_etat = State.FIN_TRANSACTION_C;
				//TODO : verification du nombre de loc et restriction
				break;
			//TODO: gestion suppression ou deselection
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case RECHARGER_COMPTE_PANIER:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.AFFICHAGE_PANIER;
				break;
			case "V":
				current_etat = State.AFFICHAGE_PANIER;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case RECOMMANDATION_FILM:
			switch(action) {
			//traitement
			case "V":
				current_etat = State.LOCATION_C;
				break;
			//TODO: gestion du film recommandé selectionné
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case FIN_TRANSACTION_C:
			current_etat = State.ACCEUIL_C;
			break;
			
		case AUTHENTIFICATION_RENDU	:
			//ici l'utilisateur rentre un numéro de CB ou une carte abonné, si correct changement d'état
			if (action=="b")
				current_etat=State.ACCEUIL_NC;
			else {
				if (/*chaine rentré correspond a une carte abonné*/) {
					current_etat=State.RECAP_RENDU_C;
				}
				else if (/*chaine rentré correspond a un cb avec un emprunt*/) {
					current_etat=State.RECAP_RENDU_NC;
				}
				else {
					System.out.println("Rendu impossible, aucun emprunts enregistré sur cette carte, ou erreur lors de la saisie pour s'identifier");
				}
			}
			break;
		case RECAP_RENDU_C:
			if (action=="b")
				current_etat=State.INFO_COMPTE;
			else {
				if (/*chaine rentré correspond a un id de DVD emprunté avec ce compte*/) {
					if( /* le solde du compte est suffisant */ ) {
						System.out.println("Rendu effectué avec succès, vous avez été débité");
						//TODO débiter le compte de l'abonné
						current_etat=State.ACCEUIL_NC; //on a ignoré la gestion de problème qui peuvent survenir sur un DVD, changement de transition ici au cas ou on finit par l'implémenter
					}
					else {
						System.out.println("Solde du compte insuffisant, veuillez recharger");
						current_etat=State.INFO_COMPTE;
					}
				}
				else {
					System.out.println("Rendu impossible, aucun emprunts enregistré sur ce compte pour le DVD renseigné");
				}
			}
			break;
		case RECAP_RENDU_NC:
			if (action=="b")
				current_etat=State.AUTHENTIFICATION_RENDU;
			else {
				if (/*chaine rentré correspond a un id de DVD emprunté avec cette carte*/) {
					System.out.println("Rendu effectué avec succès, vous avez été débité");
					current_etat=State.ACCEUIL_NC; //on a ignoré la gestion de problème qui peuvent survenir sur un DVD, changement de transition ici au cas ou on finit par l'implémenter
				}
				else {
					System.out.println("Rendu impossible, aucun emprunts enregistré sur cette carte pour le DVD renseigné");
				}
			}
			break;
		case ACCEUIL_TECH:
			switch(action) {
			case "Maj":
				current_etat=State.MAJ_DVD_AUTOMATE;
				break;
			case "Re":
				current_etat=State.LISTE_RECOMANDATION;
				break;
			case "D":
				current_etat=State.ACCEUIL_NC;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case MAJ_DVD_AUTOMATE:
			// aled lélé
			break;
		case LISTE_RECOMANDATION:
			switch (action) {
			case "Ok":
				current_etat=State.ACCEUIL_TECH;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;	
			}
			break;
		}
		
	}
	

}
