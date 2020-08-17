package berichten.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import berichten.OB;
import berichten.Reactie;

class BerichtenTests {

	@Test
	void test() {
		OB ob = new OB("Auteur1");
		assertEquals("Auteur1", ob.getAuteur());
		assertEquals(List.of(), ob.getReacties());
		assertEquals(false, ob.isVerwijderd());
		
		Reactie r1 = new Reactie("Auteur2", ob);
		assertEquals("Auteur2", r1.getAuteur());
		assertEquals(List.of(), r1.getReacties());
		assertEquals(false, r1.isVerwijderd());
		assertEquals(List.of(r1), ob.getReacties());
		
		Reactie r2 = new Reactie("Auteur3", ob);
		assertEquals(List.of(r1, r2), ob.getReacties());
		
		Reactie r3 = new Reactie("Auteur2", r2);
		assertEquals(List.of(r3), r2.getReacties());
		
		r1.verwijder();
		assertTrue(r1.isVerwijderd());
		assertEquals(List.of(r2), ob.getReacties());
	}

}
