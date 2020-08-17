package berichten;

import java.util.ArrayList;
import java.util.List;

/**
 * @invar | getAuteur() != null
 * @invar | getReacties() != null
 * @invar | getReacties().stream().allMatch(reactie -> reactie != null && !reactie.isVerwijderd() && reactie.getOuder() == this)
 */
public class Bericht {
	
	/**
	 * @invar | auteur != null
	 * @invar | reacties != null
	 * @invar | reacties.stream().allMatch(reactie -> reactie != null && !reactie.verwijderd && reactie.ouder == this)
	 */
	final String auteur;
	/**
	 * @representationObject
	 * @peerObjects
	 */
	final List<Reactie> reacties = new ArrayList<>();
	boolean verwijderd;
	
	Bericht(String auteur) {
		if (auteur == null)
			throw new IllegalArgumentException("auteur is null");
		
		this.auteur = auteur;
	}
	
	/**
	 * @immutable
	 */
	public String getAuteur() { return auteur; }
	
	/**
	 * @creates | result
	 * @peerObjects
	 * @basic
	 */
	public List<Reactie> getReacties() { return List.copyOf(reacties); }

	/**
	 * @basic
	 */
	public boolean isVerwijderd() { return verwijderd; }

	/**
	 * @pre | !isVerwijderd()
	 * @mutates | this
	 * @post | isVerwijderd()
	 */
	public void verwijder() {
		verwijderd = true;
	}
	
}
