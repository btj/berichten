package berichten;

public class OB extends Bericht {

	/**
	 * @throws IllegalArgumentException | auteur == null
	 * @mutates | this
	 * @post | getAuteur().equals(auteur)
	 * @post | getReacties().isEmpty()
	 * @post | !isVerwijderd()
	 */
	public OB(String auteur) {
		super(auteur);
	}
	
	/**
	 * @pre | !isVerwijderd()
	 * @mutates_properties | isVerwijderd()
	 * @post | isVerwijderd()
	 */
	@Override
	public void verwijder() {
		super.verwijder();
	}

}
