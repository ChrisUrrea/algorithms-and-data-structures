package project3;

 /**
  *  Stores a set of spaces using 
  *  a circular array queue 
  *  used go search for the 
  *  way out of a maze algorithm.
  *  
  * 
  * @author Christian Urrea
  *
  */

public class PossibleLocationsQueue implements PossibleLocations {
	
	private Location list[]; //array that holds queue
	private int firstUp = 0; //index of first item in queue
	private int lastUp = 0; //index of last item in queue
	private int size = 0; // total items currently in queue
	
	/**
	 * Default constructor
	 * creates new queue with capacity of 10
	 */
	public PossibleLocationsQueue() {
		this(10);
	}
	
	/**
	 * Constructor creates new queue with a specified
	 * @param - capacity to initialize the size of the array
	 * @throws - illegalStateException
	 * if capacity specified is 0 or a negative value
	 * 
	 */
	public PossibleLocationsQueue (int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException();
		else
			list = new Location[capacity];
	}
	
	/**
	 * expands size of queue by doubling the current length of the array
	 * adds items to new queue starting from index 0, 
	 * regardless of what index it is stored in the previous array
	 * resets firstUp and lastUp values to their new respective locations
	 */
	private void expandSize() {
		Location[] new1 = new Location[this.list.length * 2 ];
		for(int i=0; i < this.getSize(); i++) {
			new1[i] = this.list[firstUp];
			this.firstUp = (firstUp + 1) % list.length; 
		}
		this.lastUp = this.getSize();
		this.firstUp = 0;
		this.list = new1;
	}
	
	/**
	 * @return the amount of spaces in the queue
	 */
	public int roomInLine() {
		return this.list.length;
	}
	
	/**
	 * @return the number of items currently in the queue
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * @return the first item in the queue
	 */
	
	public Location getFirst() {
		return this.list[firstUp];
	}
	
	/**
	 * @return the last item in the queue
	 */
	public Location getLast() {
		return this.list[lastUp];
	}
	
	
	/**
	 * Add a location object to the array in circular order
	 * @param s
	 *    location to be added
	 *  @throws NullPointerException
	 *  		when attempting to add null value to list
	 */	
	public void add ( Location s ) throws NullPointerException{
		if (s == null)
			throw new NullPointerException();
		if (this.getSize() >=  this.list.length -1 )
			this.expandSize();	
		this.list[lastUp] = s;
		lastUp = (lastUp + 1) % this.list.length;
		size++;
	}
	
	/**
	 * Remove the next object from the queue in a circurlar order. 
	 * The item returned is the item thats been 
	 * in the queue the longest (FILO)
	 * @return
	 * the next location, or null if set is empty
	 */
	
	public Location remove() {
		if(isEmpty()) 
			return null;
		Location answer = this.list[firstUp];
		this.list[firstUp] = null;
		this.firstUp = (firstUp +1) % this.list.length;
		this.size--;
		return answer;
	}

	
	/**
	 * Determines if set is empty or not.
	 * @return
	 *    true, if set is empty,
	 *    false, otherwise.
	 */
	public boolean isEmpty() {
		return this.list[firstUp] == null;
	}

}
