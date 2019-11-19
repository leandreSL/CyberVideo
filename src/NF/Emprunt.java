package NF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprunt {
	long cbEmprunteur;
	int idAbonne;
	Date date = new Date();
	Date dateRetour;
	DVD dvd;
	
	
	public Emprunt(long cbEmprunteur, int idAbonne, DVD dvd) {
		super();
		this.cbEmprunteur = cbEmprunteur;
		this.idAbonne = idAbonne;
		this.dvd.identifiantDVD = dvd;
	}

	@Override
	public String toString() {
		String chaineRestrictions = String.join("`", Genre.toStringArray(restrictions));
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateD = dateFormat.format(date);
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

