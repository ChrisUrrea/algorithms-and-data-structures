package project5;import java.util.ArrayList;

import project5.CollisionsData.Node;

/**
 * CollisionData class is an implementation of
 * AVL tree that stores collision objects in order
 * of their zipcodes, dates and keys
 * 
 * Finds and collects data of nodes that meet zipcode,
 * start date and end date condition, parses it and
 * returns a formatted string ready for system printing
 * @author christian urrea
 *
 */
public class CollisionsData {

	// root of the tree
	protected Node root;
	// current number of nodes in the tree
	protected int numOfElements;
	// helper variable used by the remove methods
	private boolean found;
	
	
	/**
	 * Node class is used to represent nodes in a binary search tree.
	 * It contains a data item that has to implement Comparable interface
	 * and references to left and right subtrees. 
	 * @param <T> a reference type that implements Comparable<T> interface 
	 */
	protected static class Node implements Comparable <Collision> {
	
		protected Node left;  		//reference to the left subtree 
		protected Node right;	 	//reference to the right subtree
		protected Collision data;    //data item stored in the node
		protected int height; 		//height of node
			
		/**
		 * Constructs a BSTNode initializing the data part 
		 * according to the parameter and setting both s
		 * references to subtrees to null.
		 * @param data
		 * data to be stored in the node
		 */
		protected Node(Collision data) throws NullPointerException {
			if( data == null)
				throw new NullPointerException();
			this.data = data;
			left = null;
			right = null;
			height = 0; 
		}
		
		//returns left child
		public Node leftChild() {
			return this.left;
		}
		
		//returns right child
		public Node rightChild() {
			return this.right;
		}
		
		//return the collision object stored within node
		public Collision getData() {
			return this.data;
		}
		
		/**
		 * compares one node's collision object
		 * with other node collision objects
		 * @param other - another node to compare with
  	   	* @return positive integer, 0, negative integer if this object is greater than o,
  	   	* same as o, or less than o respectively
  	   	*/
		public int compareTo(Node other) {
			return this.data.compareTo(other.data);
		} 
		
		/**
		 * compares one node's collision object
		 * with other node collision objects
		 * @param o - collision object to compare with
  	   	* @return positive integer, 0, negative integer if this object is greater than o,
  	   	* same as o, or less than o respectively
  	   	*/
		@Override
		public int compareTo(Collision o) {
			return this.data.compareTo(o);
		}
		//End of Node class
	}

	/**
	 * create a new default
	 * CollisionsData object with root = null
	 */
	public CollisionsData() {
		this.root = null;
		numOfElements = 0;
	}
	
	//check to see if CollisionsData is empty
	 public boolean isEmpty() {
         return root == null;
     }
	 
	 	/**
	 	 * Gathers a report based off users input
	 	 * of zipcode, start date, end date
	 	 * creates and formats a string to print back to user
	 	 * @param zipcode - user input
	 	 * @param date1 - start date from user input
	 	 * @param date2 - end date from user input
	 	 * @return string formatted with all data ready to be printed
	 	 */
		public String getReport(String zipcode, Date date1, Date date2) {
			//use casualties to store and sum numerical data
			//in order, for the user report
		 	ArrayList<Integer> casualties=  findCollisions(zipcode, date1, date2);
		 	if(casualties == null) {
		 		String nullvalue;
		 		return nullvalue = "zipcode " + zipcode + " could not be located.\n";
		 	}
		 	String report = 
			String.format("Motor Vehicle Collisions for zipcode %s (%s - %s)\n", zipcode, date1.toString(), date2.toString()) +
			"====================================================================\n" +
			String.format("Total number of collisions: %d\n", casualties.get(0)) +
			String.format("      Number of fatalities: %d\n", casualties.get(1)) +
			String.format("               pedestrians: %d\n", casualties.get(2)) +
			String.format("                  cyclists: %d\n", casualties.get(3)) +
			String.format("                 motorists: %d\n", casualties.get(4)) +
			String.format("        Number of injuries: %d\n", casualties.get(5)) +
			String.format("               pedestrians: %d\n", casualties.get(6)) +
			String.format("                  cyclists: %d\n", casualties.get(7)) +
			String.format("                 motorists: %d\n", casualties.get(8));
		 	
		 	return report;
		}
	 
	/**
	 * finds all nodes that meet user's criteria, 
	 * adds them to ArrayList, in order to print output
	 * @param zipcode - user's zipcode
	 * @param date1 - user's start date
	 * @param date2 - user's end date
	 * @return casualties ArrayList with summed data
	 * @throws NumberFormatException
	 */
	 private ArrayList<Integer> findCollisions(String zipcode, Date date1, Date date2) throws NumberFormatException{
		 	//creates an empty casualties ArraList<Integer> to store report's numbers
		 	ArrayList<Integer> casualties = new ArrayList<Integer>();
		 	for (int i=0; i<9; i++)
				 casualties.add(0);
		 	//parses zipcode string into a number
		 	//returns null zip string is not a number
		 	int zipcode2;
		 	try {
		 		zipcode2 = toNumber(zipcode);
		 	} catch (Exception e) {
		 		System.err.println("failed to parse zip");
		 		return null;
		 	}
		 	
		 	//find the first node that meets
		 	//user's conditions and collect from the first node's subtree 
		 	//of all the other nodes that meet the user's conditions
			ArrayList<Node> node_list = new ArrayList();
			Node firstnode = findFirstNode(this.root, zipcode2, date1, date2);
			if (firstnode == null)
				//failed to find any node within the tree that met the proper conditions
				return casualties;
			
			//if a legitimate first node is found, traverse the firstnode's subtree
			//and collect all further node's that meet the user's conditions
			//and add their data to Arraylist of Casualties
			else if (firstnode.getData().getDate().compareTo(date1) >= 0 && firstnode.getData().getDate().compareTo(date2) <= 0 && zipcode2 == firstnode.getData().getZipNumber()) {
					ArrayList<Node> collectNodes = traverseSubtree(this.root, zipcode2, date1, date2, node_list);
					casualties = collectDataFromNodes(collectNodes, casualties);
			}

			//collect the data from all the nodes collected
			return casualties;
		}
	 
	 /**
	  * traverses tree to find first node in tree
	  * that meets the users requirements
	  * @param root - tracker node that gets passed on
	  * @param zipcode - zipcode for user report
	  * @param date1 - start date for user report
	  * @param date2 - end date for user report
	  * @return the first node that meets conditions
	  */
	 private Node findFirstNode(Node root, int zipcode, Date date1, Date date2) {
		 if(root==null)
			 return null;
		 
		 if(zipcode > root.getData().getZipNumber()) {
			 root = findFirstNode(root.rightChild(), zipcode, date1, date2);
		 }
		 else if (zipcode < root.getData().getZipNumber()) {
			 root = findFirstNode(root.leftChild(), zipcode, date1, date2);
		 }
		 else { // same zip and not null
			 if(root.getData().getDate().compareTo(date2) > 0) {
				 root = findFirstNode(root.leftChild(), zipcode, date1, date2);
		 	}
			 else if(root.getData().getDate().compareTo(date1) < 0) {
		 		root = findFirstNode(root.rightChild(), zipcode, date1, date2);
	 		}
			 //within date range
		 	else if (root.getData().getDate().compareTo(date1) >= 0 && root.getData().getDate().compareTo(date2) <= 0)
		 		return root; //found node
		}
		 return root;
	 }
	 /**
	  * traverses subtree of first node in tree
	  * and collects all further nodes that meet user's conditions
	  * and stores them into  @param nodes
	  * @param root - the node within the subtree that meets the conditions
	  * @param zipcode - zipcode for user report
	  * @param date1 - start date for user report
	  * @param date2 - end date for user report
	  * @param nodes - Arraylist of nodes that meet user's conditions
	  * @return nodes
	  */
	 private ArrayList<Node> traverseSubtree(Node root, int zipcode, Date date1, Date date2, ArrayList<Node> nodes) {
	      if (root == null)
	           return nodes;
	      if (collectFromWalkers(root, date1, date2, zipcode))
				nodes.add(root);
	      //still within zip subtree
	      if(root.getData().getZipNumber() == zipcode) {
	    	  	traverseSubtree(root.leftChild(), zipcode, date1, date2, nodes);  
	    	  	traverseSubtree(root.rightChild(), zipcode, date1, date2, nodes);      
	      }
	      return nodes;
	 }   
	 
		/**
		 * checks to see if the Collison object
		 * within node meets the users criteria
		 * @param walker - node to checked
		 * @param date1 - start date
		 * @param date2 - end date
		 * @param zipcode - user's zipcode
		 * @return true if node's collision object meets requirement
		 */
		private boolean collectFromWalkers(Node walker, Date date1, Date date2, int zipcode) {
			if(zipcode == walker.getData().getZipNumber() && date1.compareTo(walker.getData().getDate()) <= 0 && date2.compareTo(walker.getData().getDate()) >= 0)
				return true;
			else return false;
		}
		
	 /**
	  * collects the numberical data from list of nodes, 
	  * sums it and stores them into  @param casualties
	  * @param node_list - ArrayList of nodes to
	  * @param casualties - collection of persons injured/killed 
	  * statistics
	  * @return casualties - ArrayList<Integer> containg sum of
	  * numerical data organized by each classification
	  */
		private ArrayList<Integer> collectDataFromNodes(ArrayList<Node> node_list, ArrayList<Integer> casualties) {
			for(int i=0; i < node_list.size(); i++) {
				casualties= collectData(node_list.get(i), casualties);
			}
			return casualties;
		}

		/**
		 * accumulates specific data from a node's Collision object
		 * ond adds them to ArrayList<Integer> object
		 * @param node - node whose data is to be added
		 * @param casualties - ArrayList<Integer> that keeps track of each data field
		 * @return casualties with added elements from node
		 */
	 private ArrayList<Integer> collectData(Node node, ArrayList<Integer> casualties) {
		 Collision collision = node.getData();
		 casualties.set(0, casualties.get(0) +1);
		 casualties.set(1, casualties.get(1) +collision.getPersonsKilled());
		 casualties.set(2, casualties.get(2) +collision.getPedestriansKilled());
		 casualties.set(3, casualties.get(3) +collision.getCyclistsKilled());
		 casualties.set(4, casualties.get(4) +collision.getMotoristsKilled());
		 casualties.set(5, casualties.get(5) +collision.getPersonsInjured());
		 casualties.set(6, casualties.get(6) +collision.getPedestriansInjured());
		 casualties.set(7, casualties.get(7) +collision.getCyclistsInjured());
		 casualties.set(8, casualties.get(8) +collision.getMotoristsInjured()); 
		 return casualties;		 
	 }
	 
	/**
	 * @param a
	 * @param b
	 * @return whichever number is highest
	 */
	 private int max(int a, int b) {
		 return a > b ? a : b;
	 }
	 
	 /**
	  * @param node for which to calculate height
	  * @return  height difference between node's two subtrees
	  * if balanced
	  * value should be between -2 and 2 (inclusive)
	  */	
	 private int height(Node o) {
		if (o == null) 
			return -1;
		return o.height;
	 }	 
     	 
		/**
		 * Add the given data item to the tree. If item is null, the tree does not
		 * change. If item already exists, the tree does not change.
		 * 
		 * @param item
		 *            the new element to be added to the tree
		 */
		 public void add(Collision o) {
				if (o == null)
					throw new NullPointerException();
	      this.root = add(this.root, o);
	  }
		 
	/**
	 *
	 * Actual recursive implementation of add.  
	 * self balancing tree, if one side of node has 
	 * 2 height's different than the other
	 * @param root - node of a tree
	 * @param o - object to be add
	 * @return new root of the balanced tree
	 */
	private Node add(Node root, Collision o) { 
		if( root == null )
            root = new Node(o);
        else if( o.compareTo(root.getData()) < 0 )  {
            root.left = add(root.left, o);
            if( height(root.left) - height( root.right ) == 2 )
                if( o.compareTo(root.leftChild().getData()) < 0 )
                    root = rotateLeftCase( root );
                else
                    root = rotateLeftRightCase( root );
        }
        else if( o.compareTo(root.getData() ) > 0 ) {
            root.right = add(root.right, o);
            if(height(root.right) - height(root.left) == 2 )
                if( o.compareTo( root.rightChild().getData()) > 0)
                    root = rotateRightCase(root);
                else
                    root = rotateRightLeftCase(root);
        }
        root.height = max(height( root.left), height(root.right)) + 1;
        return root;
	}

	/**
	 * Left-Left case, correct by right shifting node a and replacing it with
	 * b.right
	 * @param a - node to be rotated
	 * @return
	 */
    private Node rotateLeftCase(Node a) {
    	// change node structure, make left node the parent, the parent the right child
        Node b = a.left;
        a.left = b.right;
        b.right = a;
        //update heights
        a.height = max( height(a.left), height(a.right) ) + 1;
        b.height = max( height(b.right), a.height ) + 1;
		return b;
    }
 
    /** Right-Right case, correct by left shifting 
     * node a and replacing it with b.left
    * @param a - node to be rotated
    * @return
    */
    private Node rotateRightCase(Node a) {
    	// change node structure, make right node the parent, the parent the right child
        Node b = a.right;
        a.right = b.left;
        b.left = a;
        a.height = max( height(a.left), height(a.right) ) + 1;
        b.height = max( height(b.right), a.height ) + 1;
        return b;
    }
    /**
     * rotate left-right case by
     * first shifting a's left node right
     * and then right shifting a
     * @param a - node to be rotated
     * @return a
     */
    private Node rotateLeftRightCase(Node a) {
    	  a.left = rotateRightCase( a.left );
      a = rotateLeftCase(a);
    	  return a;
    		
    }
    /**
     * rotate right-left case by
     * first shifting a's right node left
     * and then left shifting a
     * @param a - node to be rotated
     * @return a
     */
    private Node rotateRightLeftCase(Node a) {
    		a.right = rotateLeftCase( a.right );
        a = rotateRightCase(a);
     	return a;
    }

	/**
	 * Remove the item from the tree. If item is null the tree remains unchanged. If
	 * item is not found in the tree, the tree remains unchanged. 
	 * @param target - the item to be removed from this tree
	 */
	public boolean remove(Collision target) {
		root = recRemove(target, root);
		if (found) numOfElements--;
		return found;
	}
	/**
	 * Actual recursive implementation of remove method: find the node to remove. 
	 * @param target the item to be removed from this tree
	 */
	private Node recRemove(Collision target, Node node) {
		if (node == null)
			found = false;
		else if (target.compareTo(node.data) < 0)
			node.left = recRemove(target, node.left);
		else if (target.compareTo(node.data) > 0)
			node.right = recRemove(target, node.right);
		else {
			node = removeNode(node);
			found = true;
		}
		return node;
	}

	/**
	 * Actual recursive implementation of remove method: perform the removal.
	 * @param target the item to be removed from this tree
	 * @return a reference to the node itself, or to the modified subtree
	 */
	private Node removeNode(Node node) {
		Collision data;
		if (node.left == null)
			return node.right;
		else if (node.right == null)
			return node.left;
		else {
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}
	
	/**
	 * Returns the information held in the rightmost node of subtree 
	 * @param subtree root of the subtree within which to search for the rightmost
	 * node 
	 * @return returns data stored in the rightmost node of subtree
	 */
	private Collision getPredecessor(Node subtree) {
		if (subtree == null)
			throw new NullPointerException("getPredecessor called with an empty subtree");
		Node temp = subtree;
		while (temp.right != null)
			temp = temp.right;
		return temp.data;
	}

	/**
	 * Determines the number of elements stored in this BST.
	 * @return number of elements in this BST
	 */
	public int size() {
		return numOfElements;
	}

	/**
	 * Returns a string representation of this tree using an inorder traversal
	 * @see java.lang.Object#toString()
	 * @return string representation of this tree
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		inOrderPrint(root, s);
		return s.toString();
	}

	/*
	 * Actual recursive implementation of inorder traversal to produce string
	 * representation of this tree. 
	 * @param tree the root of the current subtree
	 * @param s the string that accumulated the string representation of this BST
	 */
	private void inOrderPrint(Node tree, StringBuilder s) {
		if (tree != null) {
			inOrderPrint(tree.left, s);
			s.append(tree.data.toString() + "  ");
			inOrderPrint(tree.right, s);
		}
	}

	/**
	 * Produces tree like string representation of this BST. 
	 * @return string containing tree-like representation of this BST.
	 */
	public String toStringTreeFormat() {

		StringBuilder s = new StringBuilder();
		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * Actual recursive implementation of preorder traversal to produce tree-like
	 * string representation of this tree. 
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to
	 * determine the indentation of each item 
	 * @param output the string that accumulated the string representation of this
	 * BST
	 */
	private void preOrderPrint(Node tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right, level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}
	
	/**
	 * @param o - string to be turned into number 
	 * @return integer from parsed string
	 */
	private static int toNumber(String o) {
		String o2 = o.replaceAll("\\s","");
		if (o2 == "")
			return -1;
		try {
		Integer.parseInt(o2);
		} catch (NumberFormatException e) {	
			throw new NumberFormatException();
		}
			return Integer.parseInt(o2);
	}


}


