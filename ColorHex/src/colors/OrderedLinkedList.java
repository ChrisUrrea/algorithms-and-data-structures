package colors;

/**
 * This class stores objects in a sorted way
 * in a singly linked list manner,it implements 
 * the OrderedList<E> interface and extends Comparable<E> class
 * 
 * It overrides and provides it's own implementation 
 * of OrderedList interface methods.
 * 
 * @author Christian Urrea
 *
 * @param <E> object's with which we will collect and sort in order.
 */

public class OrderedLinkedList <E extends Comparable<E>> implements OrderedList<E> {
	
	
		Node<E> head = null; //reference to head node of the list
		int size = 0;        //number of nodes in the list
	
	/*
	 * Our nested node class of type <E>
	 * From here we create our SinglyLinkedList
	 * starting from head, with nodes
	 * connecting to each other in ordered fashion
	 * 
	 * each node contains an object of type <E>
	 * as well as the node that comes after (if there exists one)
	 */
	class Node<E> {
		
		E current;  // the Object or data the node holds
		Node<E> next; // the node directly following this one, null if last
		
		/**
		 * Creates a new node
		 * 
		 * @param data - 
		 * @param next -
		 */
		Node (E data, Node<E> next) {
			this.current = data;
			this.next = next;
		}
		
		E getData() {
			return current;
		}
		
		Node<E> getNext() {
			return this.next;
		}
		
		void setNext(Node<E> next) {
			this.next = next;
		}
		
		void setData(E data) {
			this.current = data;
		}	
	}
	
	/**
	 * Retrieves the head of the LinkedOrderedList needed
	 * 
	 * @return Node<E> first element of ordered list
	 */
	public Node<E> getHead() {
		return this.head;
	}
	
	/**
     * Adds the specified element to this list in a sorted order. 
     *
     * <p>The element added must implement Comparable<E> interface. This list 
     * does not permit null elements. 
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> if this collection changed as a result of the call
     * @throws ClassCastException if the class of the specified element
     *         does not implement Comparable<E> 
     * @throws NullPointerException if the specified element is null
     */
	@Override
	public boolean add(E e) throws NullPointerException {
		if (e == null) 
			throw new NullPointerException();
		
		if (isEmpty()) {
			this.head = new Node<E>(e, null);
			size++;
			return true;
		}
	
			Node<E> prev = null;
			Node<E> walker = this.head;
			do {
				if (e.compareTo(walker.getData()) < 0) {
					Node<E> new1 = new Node<E>(e, walker);
					if (prev == null)
						this.head = new1;
					else
						prev.setNext(new1);
					size++;
					return true;
				}
				
				prev = walker;
				walker = walker.getNext();
				
			} while (walker != null);
		
			//if in last node
			Node<E> new1 = new Node<E>(e, null);
			prev.setNext(new1);
			size++;
			return true;
		}
	
	/**
	 * this method clears the ordered list
	 * by resetting size to 0
	 * and by setting the current head to null, which 
	 * permanently loses the pointer to previous list.
	 */
	@Override
	public void clear() {
		this.size = 0;
		this.head = null;
	}
	
	 /**
     * Returns <tt>true</tt> if this list contains the specified element.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this list
     * @throws NullPointerException if the specified element is null 
     */
	@Override
	public boolean contains(Object o) throws NullPointerException {
		if (o == null)
			throw new NullPointerException();
		
		Node<E> runner = this.head;
			while(runner != null) {
				if ((runner.getData()).equals(o)) {
					return true;
				}
				runner = runner.getNext();
			}
		return false;
	}
	
	 /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range 
     * <tt>(index < 0 || index >= size())</tt>
     */
	@Override
	public E get(int index) throws IndexOutOfBoundsException, NullPointerException{
		if (index >= this.size || index < 0) 
			throw new IndexOutOfBoundsException();
		if (isEmpty())
			throw new NullPointerException();
		else {
			int counter = 0;
			Node<E> runner = this.head;
				while(!(counter == index) && runner != null) {
					runner = runner.getNext();
					counter++;
				}
			return runner.getData();
		}
	}
	
	/**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
	@Override
	public int indexOf(Object o) throws NullPointerException {
		if ( o == null)
			return -1;
		
		int counter = 0;
		Node<E> runner = this.head;
		do {
			if(o.equals(runner.getData()))
				return counter;
			counter++;
			runner = runner.getNext();
		} while(runner != null);
		
		return -1;
	}
	
	 /**
     * Removes the element at the specified position in this list.  Shifts any
     * subsequent elements to the left (subtracts one from their indices if such exist).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException  if the index is out of range 
     * <tt>(index < 0 || index >= size())</tt>
     */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index >= this.size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if(this.isEmpty())
			return null;
		
		if (index == 0) {
			E oldHead = this.head.getData();
			Node<E> newHead = this.head.getNext();
			this.head = newHead;	
			size--;
			return oldHead;
		}
		
		else {
			int counter = 1;
			Node<E> prev = this.head;
			Node<E> runner = prev.getNext();
			while (!(counter == index)) {
				prev = runner;
				runner = runner.getNext();
			}
				prev.setNext(runner.getNext());
				size--;
				return runner.getData();
			}		
		}
	
	/**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * {@code i} such that
     * <tt>(o.equals(get(i))</tt>
     * (if such an element exists).  Returns {@code true} if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     */
	@Override
	public boolean remove(Object o) throws NullPointerException{
		if (o == null) 
			throw new NullPointerException();
		if (!this.contains(o))
			return false;
		else if ((this.head.getData()).equals(o)) {
			Node<E> newHead = this.head.getNext();
			this.head = newHead;
			size--;
			return true;
		}
		else {
			Node<E> prev = this.head;
			Node<E> runner = this.head.getNext();
			while(!o.equals(runner.getData()) && runner != null) {
				prev = runner;
				runner = runner.getNext();
			}
			 if (o.equals(runner.getData())) {
				 prev.setNext(runner.getNext());
				 size--;
				 return true;
			 }		 
			 return false;
		}	
		
	}
	
	 /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks whether the OrderedLinkedList instance is empty
	 * 
	 * @return true if the list empty, false if otherwise
	 */
	public boolean isEmpty() {
		
		if (size > 0) 
			return false;
		else
			return true;
	}
	
	/**
	 * "Implementation based on 
	 * Chapter 3: Data Structures and Algorithms by Goodrich and Tamassia
	 * 
     * Compares the specified object with this list for equality.  Returns
     * {@code true} if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.  (Two elements {@code e1} and
     * {@code e2} are <i>equal</i> if {@code e1.equals(e2)}.)  
     * In other words, two lists are defined to be
     * equal if they contain the same elements in the same order.<p>
     *
     * @param o the object to be compared for equality with this list
     * @return {@code true} if the specified object is equal to this list
     */
	
	@Override
	 public boolean equals(Object o) throws NullPointerException {
	    if (o == null) 
	    		throw new NullPointerException();
	    if (this.getClass() != o.getClass())
	    		return false;
	    OrderedLinkedList<E> other = (OrderedLinkedList<E>) o;   // use nonparameterized type
	    if (this.size() != other.size()) 
	    		return false;
	    Node walkA = this.head;                               // traverse the primary list
	    Node walkB = other.head;                         // traverse the secondary list
	    while (walkA != null) {
	      if (!walkA.getData().equals(walkB.getData())) 
	    	  	return false; 
	      walkA = walkA.getNext();
	      walkB = walkB.getNext();
	    }
	    return true;   
	  }
	

	/**"Implementation based on 
	 * Chapter 3: Data Structures and Algorithms by Goodrich and Tamassia
	 *
     * Returns a shallow copy of this list. (The elements
     * themselves are not cloned.)
     *
     * @return a shallow copy of this list instance
     * @throws CloneNotSupportedException - if the object's class does 
     *         not support the Cloneable interface
     */
	@Override
	public Object clone() throws CloneNotSupportedException {
		OrderedLinkedList<E> other = (OrderedLinkedList<E>) super.clone(); // safe cast
		if (this.size > 0) { // we need independent chain of nodes 
			other.head = new Node<>(head.getData(), null);
			Node<E> walk = head.getNext(); 
			Node<E> otherTail = other.head;
				while (walk != null) {
					Node<E> newest = new Node<>(walk.getData(), null);
					otherTail.setNext(newest); 
					otherTail = newest;
					walk = walk.getNext();
				}
		}
		return ((Object) other);
	}
	
	
	   /** Returns a string representation of this list.  The string
	     * representation consists of a list of the list's elements in the
	     * order they are stored in this list, enclosed in square brackets
	     * (<tt>"[]"</tt>).  Adjacent elements are separated by the characters
	     * <tt>", "</tt> (comma and space).  Elements are converted to strings 
	     * by the {@code toString()} method of those elements.
	     *
	     *@return a string representation of this list
	     */ 
	@Override
	public String toString() {
		
		StringBuilder string = new StringBuilder("[");
		Node<E> walk = this.head;
		    
		  while (walk != null) {
		    string.append(walk.getData());
		      if (walk.getNext() != null) {
		        string.append(", ");
		      }
		      walk = walk.getNext();
		   }
		  
		string.append("]");
		return string.toString();
	}	   
}
