package berichten;

import logicalcollections.LogicalList;

/**
 * @invar | getOuder() != null
 * @invar | isVerwijderd() || getOuder().getReacties().contains(this)
 */
public class Reactie extends Bericht {
	
	/**
	 * @invar | ouder != null
	 */
	private final Bericht ouder;
	
	/**
	 * @invar | isVerwijderdInternal() || getOuderInternal().getReactiesInternal().contains(this)
	 * 
	 * @post | result != null
	 * @immutable
	 * @peerObject (package-level)
	 */
	Bericht getOuderInternal() { return ouder; }
	
	/**
	 * @immutable
	 * @peerObject
	 */
	public Bericht getOuder() { return getOuderInternal(); }

	/**
	 * @throws IllegalArgumentException | auteur == null
	 * @throws IllegalArgumentException | ouder == null
	 * @mutates_properties | ouder.getReacties()
	 * @post | getAuteur().equals(auteur)
	 * @post | !isVerwijderd()
	 * @post | getReacties().isEmpty()
	 * @post | getOuder() == ouder
	 * @post | ouder.getReacties().equals(LogicalList.plus(old(ouder.getReacties()), this))
	 */
	public Reactie(String auteur, Bericht ouder) {
		super(auteur);
		
		if (ouder == null)
			throw new IllegalArgumentException("ouder is null");
		
		this.ouder = ouder;
		ouder.addReactie(this);
	}
	
	/**
	 * @pre | !isVerwijderd()
	 * @mutates_properties | isVerwijderd(), getOuder().getReacties()
	 * @post | isVerwijderd()
	 * @post | getOuder().getReacties().equals(LogicalList.minus(old(getOuder().getReacties()), this))
	 */
	@Override
	public void verwijder() {
		super.verwijder();
		ouder.removeReactie(this);
	}
	
}
