package NF;

import NF.gestionfichier.BD;

public class Modele {
	protected BD bd;
	protected static Modele instance = null;
	
	protected Modele() {
		bd = BD.getInstance();
	}
	
	public static Modele getInstance() {
		if(instance == null) {
			instance = new Modele();
		}
		return instance;
	}
}
