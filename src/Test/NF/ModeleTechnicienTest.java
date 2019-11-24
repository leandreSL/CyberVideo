package Test.NF;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import NF.DVD;
import NF.Film;
import NF.Genre;
import NF.ModeleTechnicien;

class ModeleTechnicienTest {
	
	static ModeleTechnicien mt;
	static List<DVD> listeDVD = new ArrayList<DVD>();
	static List<Film> listeFilm = new ArrayList<Film>();

	
	
	@BeforeAll
	static void setUpBeforeAll() throws Exception {
		mt = ModeleTechnicien.getInstance();
		
		File film = new File("BDD"+ File.separator + "film");
		film.delete();
		
		
		List<Genre> g1 = new ArrayList<Genre>();
		g1.add(Genre.getByName("action"));
		g1.add(Genre.getByName("fantastique"));
		List<String> a1 = new ArrayList<String>();
		a1.add("leo");
		a1.add("kate");
		Film f1 = new Film("titanic", g1, "placeholder", a1, "", 12, "");
		
		List<Genre> g2 = new ArrayList<Genre>();
		g2.add(Genre.getByName("action"));
		List<String> a2 = new ArrayList<String>();
		a2.add("tom");
		Film f2 = new Film("mission impossible", g2, "lorem ipsum", a2, "de palma", 0, "");
		listeFilm.add(f1);
		listeFilm.add(f2);
		//listeDVD
	}

	@Test
	@Order(1)
	void testAjouterFilm() {
		try {
			for(Film f : listeFilm) {
				mt.ajouterFilm(f);
			}
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	@Order(2)
	void testSupprimerFilm() {
		try {
			mt.supprimerFilm("mission impossible");
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	@Order(3)
	void testDonnerListeFilms() {
		try {
			List<Film> ft = mt.donnerListeFilms();
			System.out.println(ft.size());
			for(int i = 0; i < ft.size(); i++) {
				Film f = ft.get(i);
				
				for(String g:f.getActeurs()) {
					System.out.println(g);
				}
				
				System.out.println(f);
				System.out.println(listeFilm.get(i));
				assert(f.equals(listeFilm.get(i)));
			}
			
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	
	void testAjouterDVD() {
		fail("Not yet implemented");
	}

	@Test
	void testSupprimerDVD() {
		fail("Not yet implemented");
	}

	@Test
	void testChangerEtatDVD() {
		fail("Not yet implemented");
	}

	@Test
	void testDonnerListeDVDs() {
		fail("Not yet implemented");
	}

	

	@Test
	void testDonnerListeAbonnes() {
		fail("Not yet implemented");
	}

	@Test
	void testDonnerListeRecommandations() {
		fail("Not yet implemented");
	}

	@Test
	void testDonnerTempsEmpruntMoyen() {
		fail("Not yet implemented");
	}

	@Test
	void testDonnerNombreEmprunt() {
		fail("Not yet implemented");
	}

	@Test
	void testSupprimerCompte() {
		fail("Not yet implemented");
	}

}
