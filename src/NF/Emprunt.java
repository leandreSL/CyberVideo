package NF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprunt {
	private long cbEmprunteur;
	private int idAbonne;
	private Date dateEmprunt = new Date();
	private Date dateRetour;
	private DVD dvd;
	
	
	public Emprunt(int idAbonne, DVD dvd, Date dateEmprunt) {
		this.idAbonne = idAbonne;
		this.dvd = dvd;
		this.dateEmprunt = dateEmprunt;
		this.cbEmprunteur = -1;
	}
	
	public Emprunt(long cbEmprunteur, DVD dvd, Date dateEmprunt) {
		this.cbEmprunteur = cbEmprunteur;
		this.dvd = dvd;
		this.dateEmprunt = dateEmprunt;
		this.idAbonne = -1;
	}
	
	public Emprunt(long cbEmprunteur, int idAbonne, Date dateEmprunt, Date dateRetour, DVD dvd) {
		this.cbEmprunteur = cbEmprunteur;
		this.idAbonne = idAbonne;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
		this.dvd = dvd;
	}

	public long getCbEmprunteur() {
		return cbEmprunteur;
	}

	public void setCbEmprunteur(long cbEmprunteur) {
		this.cbEmprunteur = cbEmprunteur;
	}

	public int getIdAbonne() {
		return idAbonne;
	}

	public void setIdAbonne(int idAbonne) {
		this.idAbonne = idAbonne;
	}

	public Date getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public DVD getDvd() {
		return dvd;
	}

	public void setDvd(DVD dvd) {
		this.dvd = dvd;
	}
	
	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateD = dateFormat.format(dateEmprunt);
		String dateR;
		if(dateRetour != null) {
			dateR = dateFormat.format(dateRetour); 
		} else {
			dateR = "";
		}
		return cbEmprunteur + "|" + idAbonne + "|" + dateD + "|" + dateR + "|" + dvd.getIdentifiantDVD() + "\n";
	}
	
	public String print() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateD = dateFormat.format(dateEmprunt);
		String dateR;
		if(dateRetour != null) {
			dateR = dateFormat.format(dateRetour); 
		} else {
			dateR = "encore emprunté";
		}
		return cbEmprunteur + " " + idAbonne + " " + dateD + " " + dateR + " " + dvd.getIdentifiantDVD();
	}
}


