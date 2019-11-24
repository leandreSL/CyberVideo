package Test.NF;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import NF.Abonne;
import NF.DVD;
import NF.Emprunt;
import NF.Film;
import NF.Genre;
import NF.ModeleTechnicien;

@TestMethodOrder(OrderAnnotation.class)
class ModeleTechnicienTest {
	
	static ModeleTechnicien mt;
	static List<DVD> listeDVD = new ArrayList<DVD>();
	static List<Film> listeFilm = new ArrayList<Film>();

	
	
	@BeforeAll
	static void setUpBeforeAll() throws Exception {
		mt = ModeleTechnicien.getInstance();
		File film = new File("BDD"+ File.separator + "film");
		File dvd = new File("BDD"+ File.separator + "dvd");
		File abonne = new File("BDD"+ File.separator + "abonne");
		File emprunt = new File("BDD"+ File.separator + "emprunt");
		film.delete();
		dvd.delete();
		abonne.delete();
		emprunt.delete();
		
		List<Genre> g1 = new ArrayList<Genre>();
		g1.add(Genre.getByName("action"));
		g1.add(Genre.getByName("fantastique"));
		List<String> a1 = new ArrayList<String>();
		a1.add("leo");
		a1.add("kate");
		Film f1 = new Film("titanic", g1, "placeholder", a1, "", 12, "a");
		
		List<Genre> g2 = new ArrayList<Genre>();
		g2.add(Genre.getByName("action"));
		List<String> a2 = new ArrayList<String>();
		a2.add("tom");
		Film f2 = new Film("mission impossible", g2, "lorem ipsum", a2, "de palma", 0, "b");
		listeFilm.add(f1);
		listeFilm.add(f2);
			
		DVD d1 = new DVD(123, f1);
		DVD d2 = new DVD(1234, f1);
		listeDVD.add(d1);
		listeDVD.add(d2);
	}

	@Test
	@Order(1)
	void testAjouterFilm() throws Exception {
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
	void testSupprimerFilm() throws Exception {
		mt.supprimerFilm("mission impossible");
	}
	
	@Test
	@Order(3)
	void testDonnerListeFilms() throws Exception {
		List<Film> ft = mt.donnerListeFilms();
		for(int i = 0; i < ft.size(); i++) {
			Film f = ft.get(i);

			assert(f.equals(listeFilm.get(i)));
		}
	}

	@Test
	@Order(4)
	void testAjouterDVD() throws Exception {
		for(DVD d:listeDVD) {
			mt.ajouterDVD(d);
		}
	}

	@Test
	@Order(5)
	void testSupprimerDVD() throws Exception {
		mt.supprimerDVD(listeDVD.get(1).getIdentifiantDVD());
	}

/*
	@Test
	@Order(6)
	void testChangerEtatDVD() throws Exception {
		mt.changerEtatDVD(listeDVD.get(0).getIdentifiantDVD(), "abimé");

	}
*/
	
	@Test
	@Order(7)
	void testDonnerListeDVDs() throws Exception {
		List<DVD> dt = mt.donnerListeDVDs();
		for(int i = 0; i < dt.size(); i++) {
			DVD d = dt.get(i);
			assert(d.equals(listeDVD.get(i)));
		}
	}


	@Test
	@Order(8)
	void testDonnerListeAbonnes() throws Exception {
		BufferedWriter redacteur = new BufferedWriter(new FileWriter("BDD"+ File.separator + "abonne"));
		redacteur.write("Potter|Harry|horreur|200.0|859577759|394739825");
		redacteur.close();
		List<Genre> g = new ArrayList<Genre>();
		g.add(Genre.HORREUR);
		Abonne a = new Abonne("Potter", "Harry",  g, 200.0, 859577759, 394739825);
		assertEquals(a, mt.donnerListeAbonnes().get(0));
	}

	@Test
	@Order(9)
	void testSupprimerCompte() throws Exception {
		mt.supprimerCompte(859577759);
		assertEquals(new ArrayList<Abonne>(), mt.donnerListeAbonnes());
	}

	@Test
	@Order(10)
	void testDonnerTempsEmpruntMoyen() throws Exception {
/*		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = sdf.parse("12/12/2019");
		Date d2 = sdf.parse("14/12/2019");
		Date d3 = sdf.parse("05/11/2012");
		Date d4 = sdf.parse("09/11/2012");
		Emprunt e1 = new Emprunt(-1, 859577759, d1, d2, listeDVD.get(0));
		Emprunt e2 = new Emprunt(394739825, -1, d3, d4, listeDVD.get(0));
*/	
		
		BufferedWriter redacteur = new BufferedWriter(new FileWriter("BDD"+ File.separator + "emprunt"));
		redacteur.write("-1|859577759|12/12/2019|14/12/2019|123"+ System.getProperty("line.separator"));
		redacteur.write("394739825|-1|09/11/2012|05/11/2012|123"+ System.getProperty("line.separator"));
		redacteur.close();
		
		assertEquals(3, mt.donnerTempsEmpruntMoyen());	
	}

	@Test
	@Order(11)
	void testDonnerNombreEmprunt() throws Exception {
		assertEquals(2, mt.donnerNombreEmprunt());	
	}



}
