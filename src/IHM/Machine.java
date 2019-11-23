package IHM;

import java.util.ArrayList;


import NF.ModeleEmprunteur;
import NF.ModeleTechnicien;

public class Machine {
	enum State {ACCEUIL_NC,CREATION_COMPTE,LOCATION_NC,RECAP_LOCATION_NC,CONNEXION_RECAP,AFFICHAGE_PANIER,CONNEXION,ACCEUIL_C,
				INFO_COMPTE,RECHARGER_COMPTE,RECHARGER_COMPTE_PANIER,HISTORIQUE_EMPRUNT,LISTE_FILMS_LOUE,LOCATION_C,RECOMMANDATION_FILM,FIN_TRANSACTION_NC,
				FIN_TRANSACTION_C,AUTHENTIFICATION_RENDU,RECAP_RENDU_NC,RECAP_RENDU_C,ACCEUIL_TECH,MAJ_DVD_AUTOMATE,LISTE_RECOMANDATION}

	public State current_etat = State.ACCEUIL_NC;
	
	public ModeleEmprunteur modele_abo = ModeleEmprunteur.getInstance();
	public ModeleTechnicien modele_tech = ModeleTechnicien.getInstance();

	public String verifCompte(String nom, String prenom, long CB){
		try {
			//TODO : gestion des restrictions
			modele_abo.creationCompte(nom, prenom, null, 15, CB);
		}catch (Exception e) {
			return e.getMessage();
		}
		return "Création de compte réussie";
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
			case "Ren":
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
			default:
				//on demande dans l'ordre : nom | prenom | cb | et toute les infos necessaire a la creation du compte abonné, pour pas se faire chier on verifie juste les champs correspondant a une "clé" (s'il y en a)
				//si tout les champs ont été renseigné et sont correct (on doit etre en mesure de savoir ou on en est dans le remplissage des champs):
				//System.out.println("Compte créé avec succès, veuillez récupérer votre carte auprès du gérant de votre magasin");
				//current_etat = State.ACCEUIL_NC;
				//System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
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
				//si aucun film séléctionné on met une erreur et on reste dans le meme etat
				modele_abo.ajouterDVDNC(titre);
				current_etat = State.RECAP_LOCATION_NC;
				break;
			//TODO:autres cas a rajouter (ex : selection film ...)
				//en partant du principe qu'ici on a comme affichage : 1 - nom film | genre | .. | .. 
				// tout les choix possible sont les "nombre" affiché devant le film
				//lorsque l'utilisateur rentre un nombre, on met une variable "film selectionné" à la valeur choisie, si la valeur choisie est le meme que le fim deja selectionné, on met film selectionné a 0
				//si numero rentré ne correspond pas a un DVD qui est affiché on met une erreur
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
				//TODO:traitement de la CB : est ce que cette CB est deja associé a un emprunt ?
				//affichage : la transaction a bien été enregistré ?
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
			default:
				int id_abo;
				try {
					id_abo = Integer.parseInt(action);
				}catch (NumberFormatException e) {
					System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles ou rentrer un ID correct");
					break;
				}
				try {
					modele_abo.connexion(id_abo);
					current_etat= State.AFFICHAGE_PANIER;
				}catch (Exception e) {
					System.out.println(e.getMessage());
					break;
				}
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
				int id_abo;
				try {
					id_abo = Integer.parseInt(action);
				}catch (NumberFormatException e) {
					System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles ou rentrer un ID correct");
					break;
				}
				try {
					modele_abo.connexion(id_abo);
					if (id_abo != 1) {
						current_etat = State.ACCEUIL_C;
					}else {
						current_etat= State.ACCEUIL_TECH;
					}
					
				}catch (Exception e) {
					System.out.println(e.getMessage());
					break;
				}
				break;
			}
			break;
		case ACCEUIL_C:
			switch(action) {
			//traitement
			case "D":
				//on met la variable qui représente la personne actuellement connecté a NULL / 0
				current_etat = State.ACCEUIL_NC;
				break;
			case "L":
				//TODO:traitement de la CB (  est ce que ce commentaire est le résidus d'un copier coller ? )
				current_etat = State.LOCATION_C;
				break;
			case "I":
				current_etat = State.INFO_COMPTE;
				break;
			case "Ren":
				// s'il n'y a aucun emprunt sur ce compte abboné on emepche le passage a l'etat suivant ou juste on passe et ca sera vide a l'affichage dans la recap du rendu et il devra obligatoirement faire back ?
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
				//traitement suppression : on ecrit dasn un truc que le gerant peut voir l'id du compte, et c'est le technicien qui peut appeler la fonction "supressionCompte" depuis son interface ?
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
		case LISTE_FILMS_LOUE :// meme traitement que HISTORIQUE_EMPRUNT
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
			//la je pense que c'est bien d'avoir une validation après qu'il ait rentré le solde
			case "V":
				current_etat = State.INFO_COMPTE;
				break;
			default:
				//on fait rentrer une chaine de caractere composé de nombre uniquement (a tester sur la chaine rentré par l'utilisateur), puis si c'est une somme > 10 (ou 5, voir SRS), on crédite le compte de cette somme depuis la cb renseigné dans les infos du comptes de la personne connecté ( on fait une sorte de "demande de verification" a la banque ou osef ?)
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case LOCATION_C:
			switch(action) {
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
			default:
				//TODO:autres cas a rajouter (ex : selection film ...)
				//en partant du principe qu'ici on a comme affichage : 1 - nom film | genre | .. | .. 
				// tout les choix possible sont les "nombre" affiché devant le film
				//lorsque l'utilisateur rentre un nombre, on met une variable "film selectionné" à la valeur choisie, si la valeur choisie est le meme que le fim deja selectionné, on met film selectionné a 0
				//si numero rentré ne correspond pas a un DVD qui est affiché on met une erreur
				// c'est un abonné donc il peut séléctionné un certains nombre de films, on fait en fonction du nombre qu'il peut encore prendre ou on le tej a la fin de la transaction s'il en a choisit trop ?
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
			//TODO: gestion suppression ou deselection pour enlever un element du panier
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
			/*case "V":
				current_etat = State.AFFICHAGE_PANIER;
				break;*/
			default:
				//on fait rentrer une chaine de caractere composé de nombre uniquement (a tester sur la chaine rentré par l'utilisateur), puis si c'est une somme > 10 (ou 5, voir SRS), on crédite le compte de cette somme depuis la cb renseigné dans les infos du comptes de la personne connecté ( on fait une sorte de "demande de verification" a la banque ou osef ?)
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case RECOMMANDATION_FILM:
			switch(action) {
			//traitement
			case "V":
				// traitement s'il y a un film selectionné ou pas, si oui on recommande le film sinon erreur
				current_etat = State.LOCATION_C;
				break;
			//TODO: gestion du film recommandé selectionné ( meme fonctionnement que pour la location d'un film : on affiche la liste des films qui sont pas dans l'automate en DVD, et l'utilisateur rentre un nombre pour selectionné et appuie sur v pour valider
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;
			}
			break;
		case FIN_TRANSACTION_C:
			//affichage pour dire que c'est finit puis retour a l'acceuil ?
			current_etat = State.ACCEUIL_C;
			break;
			
		case AUTHENTIFICATION_RENDU	:
			//ici l'utilisateur rentre un numéro de CB ou une carte abonné, si correct changement d'état
			if (action=="b")
				current_etat=State.ACCEUIL_NC;
			else {
				if (/*chaine rentré correspond a une carte abonné*/) {
					current_etat=State.RECAP_RENDU_C;
					//mise a jour de l'abonné connecté sur la machine
				}
				else if (/*chaine rentré correspond a un cb avec un emprunt*/) {
					current_etat=State.RECAP_RENDU_NC;
					//mise a jour de la variable "non abonné entrain de rendre" -> besoin pour chercher emprunt correspondant
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
