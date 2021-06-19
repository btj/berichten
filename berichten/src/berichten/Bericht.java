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
	 * @invar | reacties.stream().allMatch(reactie -> reactie != null)
	 * @invar | LogicalList.distinct(reacties)
	 */
	private final String auteur;
	/**
	 * @representationObject
	 */
	private final List<Reactie> reacties = new ArrayList<>();
	private boolean verwijderd;
	
	/**
	 * @post | result != null
	 * @immutable
	 */
	String getAuteurInternal() { return auteur; }
	
	/**
	 * @invar | getReactiesInternal().stream().allMatch(reactie ->
	 *        |     !reactie.isVerwijderdInternal() && reactie.getOuderInternal() == this)
	 * @invar | !getAncestorsInternal().contains(this)
	 * 
	 * @post | result != null
	 * @post | LogicalList.distinct(result)
	 * @creates | result
	 * @peerObjects (package-level)
	 */
	List<Reactie> getReactiesInternal() { return List.copyOf(reacties); }
	
	boolean isVerwijderdInternal() { return verwijderd; }
	
	/**
	 * @invar | !getAncestorsInternal().contains(this)
	 * 
	 * @post | Objects.equals(result, LogicalSet.matching(ancestors ->
	 *       |     (!(this instanceof Reactie) || ancestors.contains(((Reactie)this).getOuderInternal())) &&
     *       |     ancestors.allMatch(ancestor -> !(ancestor instanceof Reactie) || ancestors.contains(((Reactie)ancestor).getOuderInternal()))))
	 */
	Set<Bericht> getAncestorsInternal() {
		return LogicalSet.matching(ancestors ->
			(!(this instanceof Reactie) || ancestors.contains(((Reactie)this).getOuderInternal())) &&
			ancestors.allMatch(ancestor -> !(ancestor instanceof Reactie) || ancestors.contains(((Reactie)ancestor).getOuderInternal()))
		);
	}
	
	/**
	 * @throws IllegalArgumentException | auteur == null
	 * 
	 * @post | getAuteurInternal().equals(auteur)
	 * @post | getReactiesInternal().isEmpty()
	 * @post | !isVerwijderdInternal()
	 */
	Bericht(String auteur) {
		if (auteur == null)
			throw new IllegalArgumentException("auteur is null");
		
		this.auteur = auteur;
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
	public String getAuteur() { return getAuteurInternal(); }
	
	/**
	 * @creates | result
	 * @peerObjects
	 * @basic
	 */
	public List<Reactie> getReacties() { return getReactiesInternal(); }

	/**
	 * @basic
	 */
	public boolean isVerwijderd() { return isVerwijderdInternal(); }

	/**
	 * @pre | !isVerwijderd()
	 * @mutates | this
	 * @post | isVerwijderd()
	 */
	public void verwijder() {
		verwijderd = true;
	}

	/**
	 * @pre | reactie != null
	 * @mutates_properties | getReactiesInternal()
	 * @post | getReactiesInternal().equals(LogicalList.plus(old(getReactiesInternal()), reactie))
	 */
	void addReactie(Reactie reactie) {
		reacties.add(reactie);
	}
	
	/**
	 * @mutates_properties | getReactiesInternal()
	 * @post | getReactiesInternal().equals(LogicalList.minus(old(getReactiesInternal()), reactie))
	 */
	void removeReactie(Reactie reactie) {
		reacties.remove(reactie);
	}
	
}
