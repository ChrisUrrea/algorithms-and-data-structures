package colors;

/**
 * A class that hold Color objects, in a sorted order. 
 * This class contains methods to retrieve Color objects
 * by color name or by color hex value.
 * 
 *
 * @author Christian Urrea
 *
 */

public class ColorList extends OrderedLinkedList<Color> {
	
	//Default constructor, created an empty ordered ColorLsit
	public ColorList() {
	}

	/**
	 * Retrieves a color object from list by the color's name
	 * returns null if there is no color with specified name.
	 * 
	 * uses super to refer to OrderedList<Color> class
	 * 
	 * @param string name of color
	 * @return Color object with name
	 */
	public Color getColorByName(String colorName) {
		Color color = null;
		for(int i=0; i<super.size(); i++) {
			if ( (super.get(i).getName()).equalsIgnoreCase(colorName))
				color = super.get(i);
		}
		return color;
	}
	
	 /**Retrieves a color object from list by the color's name
	 * returns null if there is no color with specified hexadecimal value.
	 *
	 *uses super to refer to OrderedList<Color> class
	 * 
	 * @param string of  color's hexadecimal value
	 * @return object of color
	 */
	public Color getColorByHexValue (String colorHexValue) {
		Color color = null;
		for(int i=0; i<super.size(); i++) {
			if ( (super.get(i).getHexValue()).equalsIgnoreCase(colorHexValue))
				color = super.get(i);
		}
		return color;
	}
	
	
}
