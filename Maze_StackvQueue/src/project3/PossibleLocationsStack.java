package project3;

/**
 *  Stores a set of spaces using 
 *  a singly linked list stack
 *  used go search for the 
 *  way out of a maze algorithm.
 *  
 * 
 * @author Christian Urrea
 *
 */
public class PossibleLocationsStack implements PossibleLocations {

	Node head; // retrieves the first element of the stack
	int size; // number of nodes in the list
	
	
	//Node class that creates singly linked list of nodes
	class Node {

		Location current; // the Location the node holds
		Node next; // the node directly following this one, null if last

		/**
		 * Creates a new node
		 * 
		 * @param data - location this Node contains
		 * @param next - the following node
		 */
		Node(Location data, Node next) {
			this.current = data;
			this.next = next;
		}
		
		/**
		 * retrieves this Node's data
		 * @return this Node's Location data.
		 */
		public Location getData() {
			return this.current;
		}
		
		/**
		 * 
		 * @return the following Node
		 * of the singly linked list
		 */
		public Node getNext() {
			return this.next;
		}
	}


	public void PossibleLocationsStack() {
		this.head = null;
		int size = 0;
	}
		
	public void PossibleLocationsStack(Location s) {}
	
	
	public int getSize() {
		return this.size;
	}

	/**
	 * Add a Location object to the set.
	 * 
	 * @param s object to be added
	 * @throws NullPointer exception when
	 * attempt to add null value to list
	 */
	
	public void add(Location s) throws NullPointerException {
		if (s == null)
			throw new NullPointerException();
		if (this.head == null) {
			this.head = new Node(s, null);
			size++;
		} else {
			Node new1 = new Node(s, head);
			this.head = new1;
			size++;
		}
	}

	/**
	 * Remove the next object from the set. The specific item
	 *  returned is the first item, in the front of the stack
	 * by the underlying structure of set representation.
	 * 
	 * @return the next object, or null if set is empty
	 */
	public Location remove() {
		if (isEmpty())
			return null;
		else if (this.size == 1) {
			Location answer = this.head.getData();
			this.head = null;
			size--;
			return answer;
		} else {
			Location answer = head.getData();
			this.head = this.head.getNext();
			size--;
			return answer;
		}

	}

	/**
	 * Determines if set is empty or not.
	 * 
	 * @return true, if set is empty, false, otherwise.
	 */
	public boolean isEmpty() {
		return this.head == null;
	}

}
