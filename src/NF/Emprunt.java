package NF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprunt {
	long cbEmprunteur;
	int idAbonne;
	Date dateEmprunt = new Date();
	Date dateRetour;
	DVD dvd;
	
	
	public Emprunt(int idAbonne, DVD dvd, Date dateEmprunt) {
		this.idAbonne = idAbonne;
		this.dvd = dvd;
		this.dateEmprunt = dateEmprunt;
	}
	
	public Emprunt(long cbEmprunteur, DVD dvd, Date dateEmprunt) {
		this.cbEmprunteur = cbEmprunteur;
		this.dvd = dvd;
		this.dateEmprunt = dateEmprunt;
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateD = dateFormat.format(dateEmprunt);
		String dateR = dateFormat.format(dateRetour);
		return cbEmprunteur + "|" + idAbonne + "|" + dateD + "|" + dateR + "|" + dvd
				+ "\n";
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
	
	
	
}

