package NF;

import java.util.ArrayList;
import java.util.List;

public enum StatutFilm {
	EnMagasin("enmagasin"),
	EnAutomate("enautomate"),
	Emprunte("emprunte");

	private String nom;
	
	private StatutFilm(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public static StatutFilm getByName(String nom) {
		switch(nom) {
			case "enmagasin": return StatutFilm.EnMagasin;
			case "enautomate": return StatutFilm.EnAutomate;
			case "emprunte": return StatutFilm.Emprunte;
		}
		return null;
	}
	
	public static List<String> toStringArray(List<StatutFilm> statuts) {
		List<String> stringStatuts = new ArrayList<String>();
		for(StatutFilm statutFilm : statuts) {
			stringStatuts.add(statutFilm.getNom());
		}
		return stringStatuts;
	}

	public static List<StatutFilm> toGenreArray(String[] stringStatuts) {
		List<StatutFilm> statuts = new ArrayList<StatutFilm>();
		for(String sstatut : stringStatuts) {
			statuts.add(StatutFilm.getByName(sstatut));
		}
		return statuts;
	}
}
