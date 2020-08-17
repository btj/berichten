package berichten;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BerichtUtils {
	
	public static int getDiepte(Bericht bericht) {
		if (bericht instanceof Reactie)
			return getDiepte(((Reactie)bericht).getOuder()) + 1;
		return 0;
	}
	
	public static Iterator<Bericht> getVoorouders(Bericht bericht0) {
		return new Iterator<Bericht>() {
			Bericht bericht = bericht0;
			public boolean hasNext() {
				return bericht instanceof Reactie;
			}
			public Bericht next() {
				bericht = ((Reactie)bericht).ouder;
				return bericht;
			}
		};
	}
	
	public static void forEachVoorouder(Bericht bericht0, Consumer<? super Bericht> consumer) {
		Bericht bericht = bericht0;
		while (bericht instanceof Reactie) {
			bericht = ((Reactie)bericht).ouder;
			consumer.accept(bericht);
		}
	}
	
	public static Stream<Bericht> getAllBerichten(Bericht bericht0) {
		return Stream.concat(Stream.of(bericht0), bericht0.getReacties().stream().flatMap(reactie -> getAllBerichten(reactie)));
	}
	
	public static Stream<Bericht> getBerichtenVanAuteur(Bericht bericht0, String auteur) {
		return getAllBerichten(bericht0).filter(bericht -> bericht.getAuteur().equals(auteur));
	}

}
