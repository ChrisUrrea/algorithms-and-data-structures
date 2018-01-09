package colors;

/**
 * This class specifies the object Color
 * It creates color objects based off their RGB values
 * or hexadecimal values, as well they can also be saved with the unique color's name
 * 
 * This class implements Comaprable interface in order to comapare
 * Color's by their hexadecimal values.
 * 
 * @author Christian Urrea
 *
 */
public class Color implements Comparable<Color> {
	
	private int red; // holds red RGB value
	private int green; // holds green RGB value
	private int blue; // holds blue RGB value
	private String name; // holds this instance color's name
	private String hexValue; // holds this instance of color's hexadecimal value
	private static final String HEX_VALUES = "0123456789aAbBcCdDeEfF"; //all the possible hexadecimal values, of all caps
	
	
	/** constructs a color object based off a hexadecimal value
	 * 
	 * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IllegalArgumentException if the hexadecimal value is not a valid color parameter
	 */
	public Color ( String colorHexadecimal ) throws IllegalArgumentException {
		if (validHexValue(colorHexadecimal)) {
			this.hexValue = colorHexadecimal;
			this.hexToRGB(colorHexadecimal);
			this.name = "";
		} else 
			throw new IllegalArgumentException("This is not a valid hexadecimal value");
	}
	
	/**
	 * Constructs a new color object based off green, red, green
	 * RGB values
	 * 
	 * @param red - RGB value
	 * @param green - RGB value
	 * @param blue - RGB value
	 * @throws IllegalArgumentException if the any of the three green, red, blue values are invalid RGB values
	 */
	
	public Color(int red, int green, int blue) throws IllegalArgumentException  {
		if ( red > 255 || red < 0 || green > 255 || green < 0 || blue >255 || blue < 0) {
			throw new IllegalArgumentException("Illegal RGB value.");
		}
		this.name = "";
		this.red = red; this.green = green; this.blue = blue;
		this.hexValue = String.format("#%02x%02x%02x", red, green, blue);		
	 
	} 
	
	/**
	 * Constructs a Color object 
	 * based of it's hexadecimal value and it's name
	 * 
	 * @param colorHexValue - hexadecimal value of this color
	 * @param colorName - name of the color
	 * @throws IllegalArgumentException
	 */
	public Color( String colorHexValue, String colorName ) throws IllegalArgumentException  {
		if (!validHexValue(colorHexValue)) {
			throw new IllegalArgumentException("This is not a valid hexadecimal value");
		}
		else {
			this.hexValue = colorHexValue;
			this.hexToRGB(colorHexValue);
			this.name = colorName;
		}
	}
	
	/**
	 * This method receives a string hexadecimal value and returns 
	 * a string of RGB values based off that hexadecimal value
	 * 
	 * checks first to see if @param hexadecimal is a valid hexadecimal value
	 * 
	 * @param hexadecimal - the string of the hexadecimal value
	 * @return the RGB value in string format : "( R, G, B)"
	 */
	private String hexToRGB(String hexadecimal) {
	 String rgb = "";
	 if (validHexValue(hexadecimal)) {
		this.red =  Integer.valueOf( hexadecimal.substring( 1, 3 ), 16 );
    		this.green =  Integer.valueOf( hexadecimal.substring( 3, 5 ), 16 );
    		this.blue =  Integer.valueOf( hexadecimal.substring( 5, 7 ), 16 );
    		rgb = String.format("%3d, %3d, %3d", this.red, this.green, this.blue);
	 }
	 return rgb;
	}
	
	/**
	 * Checks to see if the specific String hexadecimal 
	 * is a valid hexadecimal value
	 * Uses the HEX_VALUES constant to see if it obtains a value equivalent 
	 * 
	 * @param hexadecimal value
	 * @return true if the hexadecimal value is valid, false if not
	 */
	public static boolean validHexValue(String hexadecimal) {
		/*Check whether char value '#' is valid */
		if ( (hexadecimal.length() == 7) && (hexadecimal.charAt(0) == '#')) {
			for (int i=1; i<7; i++) {
				char hexvalue = hexadecimal.charAt(i);
				if (HEX_VALUES.indexOf(Character.toString(hexvalue)) == -1) {
					return false;
				}
			}
			return true;
		}	
		return false;
	}

	//returns this color's red RGB value
	public int getRed() {
		return this.red;
	}
	
	//returns this color's green RGB value
	public int getGreen() {
		return this.green;
	}
	
	//returns this color's blue RGB value
	public int getBlue() {
		return this.blue;
	}
	
	//returns this color's name
	public String getName() {
		return this.name;
	}
	
	// returns this color's hexadecimal value
	public String getHexValue() {
		return this.hexValue;
	}
	
	/**
	 * Overrides Object class' toString() method
	 * 
	 *
	 * @return the hexadecimal value along with it's RGB value, and the color's name if it has one.
	 */
	@Override
	public String toString() {
		String string = "";
		if (this.getName() != "")
			string = String.format("%s, (%3d,%3d,%3d), %s", this.getHexValue(), this.getRed(), this.getGreen(), this.getBlue(), this.getName());
		else
			string = String.format("%s, (%3d,%3d,%3d)", this.getHexValue(), this.getRed(), this.getGreen(), this.getBlue());
		return string;
	}
	
	/**
	 *Overrides Object class' equals(Object o) method
	 *compares object based off their hexadecimal values, ignoring case of letters
	 *
	 *@param color2 - other Color object to compare this instance with
	 *@return true if this instance has the same hexadecimal value as color2, false if not.
	 */
	@Override
	public boolean equals (Object color2) throws NullPointerException{
		if (color2 == null || ((Color) color2).getHexValue() == null) 
			return false;
		return (this.getHexValue()).equalsIgnoreCase(((Color) color2).getHexValue());
	}
	
	/**
	 * Implements comparable interface method compareTo
	 * compares this two color object's hexadecimal values
	 * to determine which one is larger and smaller, or if
	 * they are equal.
	 * 
	 * @param color2 - the other color object
	 * @returns integer 1 if this instance has a larger hexadecimal value than color2, returns 0 if they are equal,
	 * and -1 if this instance's hexadecimal is smaller than color2's
	 */
	@Override
	public int compareTo (Color color2) {
		return (this.getHexValue()).compareToIgnoreCase(color2.getHexValue());
	}
}
