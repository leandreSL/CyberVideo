package NF;

public class Emprunteur {
	private long cb;

	public Emprunteur(long cb) {
		super();
		this.cb = cb;
	}

	public long getCb() {
		return cb;
	}

	public void setCb(long cb) {
		this.cb = cb;
	}
	
	public int demanderTransactionCb(double montant) {
		return 1;
	}
	
}

