package NF;

public class Emprunteur {
	CarteBancaire cb;
	
	void louerFilm() {
		
	}

	public Emprunteur(CarteBancaire cb) {
		super();
		this.cb = cb;
	}

	public CarteBancaire getCb() {
		return cb;
	}

	public void setCb(CarteBancaire cb) {
		this.cb = cb;
	}
	
	
}

