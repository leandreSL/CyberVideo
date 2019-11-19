package NF;

import java.util.ArrayList;
import java.util.List;

public enum StatutDVD {
	EnMagasin("enmagasin"),
	EnAutomate("enautomate"),
	Emprunte("emprunte");

	private String nom;
	
	private StatutDVD(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public static StatutDVD getByName(String nom) {
		switch(nom) {
			case "enmagasin": return StatutDVD.EnMagasin;
			case "enautomate": return StatutDVD.EnAutomate;
			case "emprunte": return StatutDVD.Emprunte;
		}
		return null;
	}
	
	public static List<String> toStringArray(List<StatutDVD> statuts) {
		List<String> stringStatuts = new ArrayList<String>();
		for(StatutDVD statutDVD : statuts) {
			stringStatuts.add(statutDVD.getNom());
		}
		return stringStatuts;
	}

	public static List<StatutDVD> toGenreArray(String[] stringStatuts) {
		List<StatutDVD> statuts = new ArrayList<StatutDVD>();
		for(String sstatut : stringStatuts) {
			statuts.add(StatutDVD.getByName(sstatut));
		}
		return statuts;
	}
}
