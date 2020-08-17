package berichten.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import berichten.Bericht;
import berichten.BerichtUtils;
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
		
		assertEquals(0, BerichtUtils.getDiepte(ob));
		assertEquals(2, BerichtUtils.getDiepte(r3));
		
		{
			ArrayList<Bericht> berichten = new ArrayList<>();
			for (Iterator<Bericht> iterator = BerichtUtils.getVoorouders(r3); iterator.hasNext(); )
				berichten.add(iterator.next());
			assertEquals(List.of(r2, ob), berichten);
		}
		
		{
			ArrayList<Bericht> berichten = new ArrayList<>();
			BerichtUtils.forEachVoorouder(r3, bericht -> berichten.add(bericht));
			assertEquals(List.of(r2, ob), berichten);
		}
		
		assertEquals(Set.of(r1, r3), BerichtUtils.getBerichtenVanAuteur(ob, "Auteur2").collect(Collectors.toSet()));
		
		r1.verwijder();
		assertTrue(r1.isVerwijderd());
		assertEquals(List.of(r2), ob.getReacties());
	}

}
