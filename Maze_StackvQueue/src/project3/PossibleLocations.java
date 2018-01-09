package project3;

/**
 * Represents a set of spaces used for the 
 * search for the way out of a maze algorithm.
 * 
 * @author Joanna Klukowski
 *
 */
public interface PossibleLocations {
	
	/**
	 * Add a SquarePosition object to the set.
	 * @param s
	 *    object to be added
	 */
	
	void add ( Location s );
	
	/**
	 * Remove the next object from the set. The specific
	 * item returned is determined by the underlying structure
	 * of set representation.
	 * @return
	 *    the next object, or null if set is empty
	 */
	
	Location remove ();
	
	/**
	 * Determines if set is empty or not.
	 * @return
	 *    true, if set is empty,
	 *    false, otherwise.
	 */
	boolean isEmpty();
}
