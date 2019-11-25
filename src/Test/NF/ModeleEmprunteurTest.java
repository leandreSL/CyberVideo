package Test.NF;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import NF.Abonne;
import NF.DVD;
import NF.Emprunt;
import NF.Film;
import NF.Genre;
import NF.ModeleEmprunteur;
import NF.ModeleTechnicien;

class ModeleEmprunteurTest {

	static ModeleEmprunteur mE;
	
	@BeforeAll
	static void setUpBeforeAll() throws Exception {
	}

	@BeforeEach
	void setUpBeforeEach() throws Exception {
		mE = ModeleEmprunteur.getInstance();
		mE.viderPanier();
		
		File film = new File("BDD"+ File.separator + "film");
		File dvd = new File("BDD"+ File.separator + "dvd");
		File abonne = new File("BDD"+ File.separator + "abonne");
		File emprunt = new File("BDD"+ File.separator + "emprunt");
		film.delete();
		dvd.delete();
		abonne.delete();
		emprunt.delete();
		
		BufferedWriter redacteur = new BufferedWriter(new FileWriter("BDD"+ File.separator + "abonne"));
		redacteur.write("Potter|Harry|horreur|200.0|859577759|394739825");
		redacteur.close();
		
		redacteur = new BufferedWriter(new FileWriter("BDD"+ File.separator + "film"));
		redacteur.write("MonFilm1|action`western|Ceci est mon résumé un|acteur1`acteur2`acteur3|Real1|3|affiche.jpg" 
				+System.getProperty("line.separator"));
		redacteur.write("MonFilm2|action`fantastique`|Ceci est mon résumé deux|acteur1`acteur2|Real2|3|affiche.jpg" 
				+System.getProperty("line.separator"));	
		redacteur.close();
		
		redacteur = new BufferedWriter(new FileWriter("BDD"+ File.separator + "dvd"));
		redacteur.write("4402|neuf|MonFilm2|enautomate|0" +System.getProperty("line.separator"));
		redacteur.write("4377|neuf|MonFilm1|enautomate|0" +System.getProperty("line.separator"));	
		redacteur.write("4387|neuf|MonFilm1|emprunte|0" +System.getProperty("line.separator"));	
		redacteur.close();
		
		redacteur = new BufferedWriter(new FileWriter("BDD"+ File.separator + "emprunt"));
		redacteur.write("-1|859577759|12/12/2019||123"+ System.getProperty("line.separator"));
		redacteur.write("1111|-1|23/11/2019||4387"+ System.getProperty("line.separator"));
		redacteur.close();
	}
	
	@AfterEach 
	void setUpAfterEach() throws Exception {
		if(mE.estConnecte())
			mE.deconnexion();
	}
	
	@Test
	void testRendreDVD() throws Exception {
		mE.rendreDVD(4387);
	}

	@Test
	void testAjouterAuPanierLong() throws Exception {
		mE.ajouterAuPanier("MonFilm1", 12345);
		assertThrows(Exception.class,() -> {mE.ajouterAuPanier("MonFilm2", 12345);});

		mE.connexion(859577759);
		mE.ajouterAuPanier("MonFilm1", -1);
		mE.ajouterAuPanier("MonFilm2", -1);
	}

	@Test
	void testValider() throws Exception {
		mE.connexion(859577759);
		mE.ajouterAuPanier("MonFilm1", -1);
		mE.ajouterAuPanier("MonFilm2", -1);		
		mE.valider(0);
		
		assertThrows(Exception.class, ()->{mE.ajouterAuPanier("MonFilm1", -1);});
	}

	@Test
	void testFiltreGenreFilm() {
		List<Genre> lg = new ArrayList<Genre>();
		lg.add(Genre.getByName("action"));
		lg.add(Genre.getByName("fantastique"));
		List<Film> lf = mE.filtreGenreFilm(lg);
		assert(lf.size()==1);
		
		List<String> la = new ArrayList<String>();
		la.add("acteur1");
		la.add("acteur2");
		Film fexpected = new Film("MonFilm2",lg,"Ceci est mon résumé deux",la,"Real2",3,"affiche.jpg");
		
		assertEquals(fexpected, lf.get(0));
	}

	@Test
	void testCreationCompte() throws Exception {
		assertThrows(Exception.class, ()-> {mE.creationCompte(new Abonne("prenom1", "nom1",  new ArrayList<Genre>(), 10.0, 12345, 394739826));});
		mE.creationCompte(new Abonne("prenom1", "nom1",  new ArrayList<Genre>(), 20.0, 12345, 394739826));
	}

	@Test
	void testConnexion() throws Exception {
		mE.connexion(859577759);
	}

	@Test
	void testDeconnexion() throws Exception {
		mE.connexion(859577759);
		mE.deconnexion();
	}

	@Test
	void testDonnerListeEmprunts() throws Exception {
		mE.connexion(859577759);
		List<Emprunt> le = mE.donnerListeEmprunts();
		assertEquals(1,le.size());
	}

	@Test
	void testRechargerCarte() throws Exception {
		mE.connexion(859577759);
		mE.rechargerCarte(564, 10);
		assertEquals(210.0, mE.donnerSoldeAbonne());
	}

	@Test
	void testDonnerInformationsAbonne() throws Exception {
		mE.connexion(859577759);
		assertEquals("Potter|Harry|horreur|200.0|859577759|394739825\n", mE.donnerInformationsAbonne().toString());
	}

	@Test
	void testDonnerSoldeAbonne() {
		mE.connexion(859577759);
		assertEquals(200.0, mE.donnerSoldeAbonne());
	}

	@Test
	void testDonnerIdentificationAbonne() {
		mE.connexion(859577759);
		assertEquals("Potter Harry", mE.donnerIdentificationAbonne());	
	}

	@Test
	void testDonnerCBAbonne() {
		mE.connexion(859577759);
		assertEquals(394739825, mE.donnerCBAbonne());		
	}

	@Test
	void testRecommanderFilm() throws Exception {
		mE.connexion(859577759);
		mE.recommanderFilm("MonFilm1");
	}

	@Test
	void testViderPanier() throws Exception {
		mE.ajouterAuPanier("MonFilm1", 2354);
		mE.viderPanier();
		mE.ajouterAuPanier("MonFilm2", 2354);
	}

	@Test
	void testAfficherPanier() throws Exception {
		List<Genre> lg = new ArrayList<Genre>();
		lg.add(Genre.getByName("action"));
		lg.add(Genre.getByName("fantastique"));
		List<Film> lf =  new ArrayList<Film>();
		
		List<String> la = new ArrayList<String>();
		la.add("acteur1");
		la.add("acteur2");
		
		Film fexpected = new Film("MonFilm2",lg,"Ceci est mon résumé deux",la,"Real2",3,"affiche.jpg");
		
		lf.add(fexpected);
		mE.ajouterAuPanier("MonFilm2", 2354);
		assertEquals(lf, mE.afficherPanier());
	}

	@Test
	void testDonnerListeFilms() {
		assertEquals(2, mE.donnerListeFilms().size());
	}
}
