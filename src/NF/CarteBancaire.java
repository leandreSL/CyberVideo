package NF;

import java.util.Date;

public class CarteBancaire {
	private String nom;
	private long numeroCarte;
	private Date dateExpiration;
	
	
	public CarteBancaire(String nom, long numeroCarte, Date dateExpiration) {
		super();
		this.nom = nom;
		this.numeroCarte = numeroCarte;
		this.dateExpiration = dateExpiration;
	}


	public long getNumeroCarte() {
		return numeroCarte;
	}
	
	
}
