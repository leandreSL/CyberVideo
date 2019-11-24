package IHM;

import java.util.ArrayList;

import NF.Abonne;
import NF.ModeleEmprunteur;
import NF.ModeleTechnicien;

public class Machine {
	enum State {ACCEUIL_NC,CREATION_COMPTE,LOCATION_NC,RECAP_LOCATION_NC,CONNEXION_RECAP,AFFICHAGE_PANIER,CONNEXION,ACCEUIL_C,
				INFO_COMPTE,RECHARGER_COMPTE,RECHARGER_COMPTE_PANIER,HISTORIQUE_EMPRUNT,LISTE_FILMS_LOUE,LOCATION_C,RECOMMANDATION_FILM,FIN_TRANSACTION_NC,
				FIN_TRANSACTION_C,AUTHENTIFICATION_RENDU,RECAP_RENDU_NC,RECAP_RENDU_C,ACCEUIL_TECH,MAJ_DVD_AUTOMATE,LISTE_RECOMANDATION,STATS_TECH,SUPP_COMPTE,RENDU}

	public State current_etat = State.ACCEUIL_NC;
	
	public ModeleEmprunteur modele_abo = ModeleEmprunteur.getInstance();
	public ModeleTechnicien modele_tech = ModeleTechnicien.getInstance();

	public String verifCompte(String nom, String prenom,double solde, long CB){
		try {
			//TODO : gestion des restrictions
			Abonne a = new Abonne(nom,prenom,null,solde,CB);
			modele_abo.creationCompte(a);
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
				modele_abo.viderPanier();
				current_etat = State.ACCEUIL_NC;
				break;
			case "V":
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
				//current_etat= State.RECAP_RENDU_C;
                current_etat= State.RENDU;
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
				//current_etat = State.RECAP_RENDU_C;
				current_etat = State.RENDU;
				break;
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
            int solde=0;
			switch(action) {
			//traitement
			case "b":
				current_etat = State.INFO_COMPTE;
				break;
			//la je pense que c'est bien d'avoir une validation après qu'il ait rentré le solde
			case "V":
				current_etat = State.INFO_COMPTE;
                System.out.println("Rechargement de votre carte effectué, votre nouveau solde est de : " + modele_abo.donnerSoldeAbonne());
				break;
			default:
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
			case "V":
                current_etat = State.FIN_TRANSACTION_C;
                System.out.println("Rechargement de votre carte effectué, votre nouveau solde est de : " + modele_abo.donnerSoldeAbonne());
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
			
		/*case AUTHENTIFICATION_RENDU	:
			//ici l'utilisateur rentre un numéro de CB ou une carte abonné, si correct changement d'état
			if (action=="b")
				current_etat=State.ACCEUIL_NC;
			else {
				if (chaine rentré correspond a une carte abonné) {
					current_etat=State.RECAP_RENDU_C;
					//mise a jour de l'abonné connecté sur la machine
				}
				else if (chaine rentré correspond a un cb avec un emprunt) {
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
				if (chaine rentré correspond a un id de DVD emprunté avec ce compte) {
					if(  le solde du compte est suffisant ) {
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
				if (chaine rentré correspond a un id de DVD emprunté avec cette carte) {
					System.out.println("Rendu effectué avec succès, vous avez été débité");
					current_etat=State.ACCEUIL_NC; //on a ignoré la gestion de problème qui peuvent survenir sur un DVD, changement de transition ici au cas ou on finit par l'implémenter
				}
				else {
					System.out.println("Rendu impossible, aucun emprunts enregistré sur cette carte pour le DVD renseigné");
				}
			}
			break;*/
        case RENDU :
        	double prix;
			if (action=="b")
				current_etat=State.ACCEUIL_NC;
			else {
				try {
					prix = modele_abo.rendreDVD(Integer.parseInt(action));
				}
				catch (Exception e) {
					if (e.getMessage() == "pas assez d'argent sur la carte abonne"){
						System.out.println("Solde du compte insuffisant, veuillez recharger");
						current_etat=State.INFO_COMPTE;
					}
                    else 
                        System.out.println(e.getMessage());
                    break;
				}
				System.out.println("Votre compte à été débité de : " + Double.toString(prix));
				current_etat=State.ACCEUIL_NC;
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
			case "St":
				current_etat=State.STATS_TECH;
			case "S":
				current_etat=State.SUPP_COMPTE;
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
        case STATS_TECH:
			switch(action) {
			case "Ok":
				current_etat=State.ACCEUIL_TECH;
				break;
			default:
				System.out.println("Entrée incorrecte, veuillez respecter les commandes disponibles");
				break;	
			}
			break;
		case SUPP_COMPTE:
			//il faut avoir recup les compte qui sont en attente de suppression, puis appeler la fonction supprimerCompte(idcarte) en fonction de l'id selectionne		
            switch(action) {
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
