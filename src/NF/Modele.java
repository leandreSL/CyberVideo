package NF;

import NF.gestionfichier.BD;

public abstract class Modele {
	protected BD bd;
	protected static Modele instance = null;
	
	protected Modele() {
		bd = BD.getInstance();
	}
}
