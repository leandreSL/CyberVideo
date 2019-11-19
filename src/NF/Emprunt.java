package NF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprunt {
	long cbEmprunteur;
	int idAbonne;
	Date actuelle = new Date();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String dat = dateFormat.format(actuelle);
	Date dateRetour;
	DVD dvd;
	
	
	public Emprunt(long cbEmprunteur, int idAbonne, DVD dvd) {
		super();
		this.cbEmprunteur = cbEmprunteur;
		this.idAbonne = idAbonne;
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


	public Date getActuelle() {
		return actuelle;
	}


	public void setActuelle(Date actuelle) {
		this.actuelle = actuelle;
	}


	public DateFormat getDateFormat() {
		return dateFormat;
	}


	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}


	public String getDat() {
		return dat;
	}


	public void setDat(String dat) {
		this.dat = dat;
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

