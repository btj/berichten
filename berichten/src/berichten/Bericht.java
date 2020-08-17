package berichten;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import logicalcollections.LogicalList;
import logicalcollections.LogicalSet;

/**
 * @invar | getAuteur() != null
 * @invar | getReacties() != null
 * @invar | getReacties().stream().allMatch(reactie -> reactie != null && !reactie.isVerwijderd() && reactie.getOuder() == this)
 * @invar | LogicalList.distinct(getReacties())
 * @invar | !getAncestors().contains(this)
 */
public class Bericht {
	
	/**
	 * @invar | auteur != null
	 * @invar | reacties != null
	 * @invar | reacties.stream().allMatch(reactie -> reactie != null && !reactie.verwijderd && reactie.ouder == this)
	 * @invar | LogicalList.distinct(reacties)
	 * @invar | !getAncestorsInternal().contains(this)
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
	
	Set<Bericht> getAncestorsInternal() {
		return LogicalSet.matching(ancestors ->
			(!(this instanceof Reactie) || ancestors.contains(((Reactie)this).ouder)) &&
			ancestors.allMatch(ancestor -> !(ancestor instanceof Reactie) || ancestors.contains(((Reactie)ancestor).ouder))
		);
	}
	
	/**
	 * @post | Objects.equals(result, LogicalSet.matching(ancestors ->
	 *       |     (!(this instanceof Reactie) || ancestors.contains(((Reactie)this).getOuder())) &&
	 *       |     ancestors.allMatch(ancestor ->
	 *       |         !(ancestor instanceof Reactie) || ancestors.contains(((Reactie)ancestor).getOuder()))
	 *       | ))
	 */
	public Set<Bericht> getAncestors() {
		return getAncestorsInternal();
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
