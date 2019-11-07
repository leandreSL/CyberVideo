package IHM;

public class Machine {
	enum State {ACCEUIL_NC,CREATION_COMPTE,LOCATION_NC,RECAP_LOCATION_NC,CONNEXION_RECAP,AFFICHAGE_PANIER,CONNEXION,ACCEUIL_C,
				INFO_COMPTE,RECHARGER_COMPTE,RECHARGER_COMPTE_PANIER,HISTORIQUE_EMPRUNT,LISTE_FILMS_LOUE,LOCATION_C,RECOMMANDATION_FILM,FIN_TRANSACTION_NC,
				FIN_TRANSACTION_C}

	State current_etat = State.ACCEUIL_NC;
	
	
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
			default:
				//TODO:Affichage des actions possibles avec une fonction
				
			}
			break;
		case CREATION_COMPTE:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.ACCEUIL_NC;
				break;
			//TODO: gestion des infos utilisateur
			default:
				//TODO:Affichage des actions possibles avec une fonction
				
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
				//TODO:Affichage des actions possibles avec une fonction
				
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
				//TODO:Affichage des actions possibles avec une fonction
				
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
				//TODO:Affichage des actions possibles avec une fonction
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
				//TODO:Affichage des actions possibles avec une fonction
			}
			break;
		case CONNEXION:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.ACCEUIL_NC;
				break;
			case "V":
				//TODO:traitement de la connexion
				//TODO: gestion du gérant 
				current_etat = State.ACCEUIL_C;
				break;
			default:
				//TODO:Affichage des actions possibles avec une fonction
			}
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
			default:
				//TODO:Affichage des actions possibles avec une fonction
			}
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
			case "R":
				current_etat = State.RECHARGER_COMPTE;
				break;
			default:
				//TODO:Affichage des actions possibles avec une fonction
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
				//TODO:Affichage des actions possibles avec une fonction
			}
			break;
			
		case RECHARGER_COMPTE:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.INFO_COMPTE;
				break;
			default:
				//TODO:Affichage des actions possibles avec une fonction
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
				//TODO:Affichage des actions possibles avec une fonction
			}
			break;
		case AFFICHAGE_PANIER:
			switch(action) {
			//traitement
			case "b":
				current_etat = State.LOCATION_C;
				break;
			case "R":
				current_etat = State.RECHARGER_COMPTE_PANIER;
				break;
			case "V":
				current_etat = State.FIN_TRANSACTION_C;
				//TODO : verification du nombre de loc et restriction
				break;
			//TODO: gestion suppression ou deselection
			default:
				//TODO:Affichage des actions possibles avec une fonction
			}
			break;
		case RECH
				
			
		}
	}

}
