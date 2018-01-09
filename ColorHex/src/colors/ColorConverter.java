package colors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the main class that contains the main method
 * It expects to receive a list of colors as command line argument
 * and promptly reads the data, and adds the colors to a colorList in a sorted order
 * 
 * Continues with asking user for color values to add to a ColorList
 * reads user input, and adds user's color until user prompts program to terminate.
 * 
 * @author Christian Urrea
 *
 */
public class ColorConverter {
	
	public static void main (String[] args) {
		
		//creates new ColorList
		ColorList colorList = new ColorList();
		  
			//checks if program starts with a file, if no file program exits with error message
		    if( args.length == 0) {
		   	System.err.println("Usage Error: the program expects file name as an argument.");
		   	System.exit(0); 
		   }
		   
		/*
		 * For each file the program is initiated with, 
		 * the data is extracted and a new color is created
		 * and added to the ColorList, in sorted order
		 * 
		 * catches errors if the color is a null object or if the hexadecimal value is a not a valid color specification.
		 */
		 
		for (int i=0; i<args.length;i++) { 
			try {
				File file = new File(args[i]);
				Scanner input = new Scanner(file);
				
				while (input.hasNext()) {
					String[] dataLine = input.nextLine().split(",");
					String colorName = dataLine[0].trim();
					String hexValue = dataLine[1].trim();
					try {
					Color temp = new Color(hexValue, colorName);
					colorList.add(temp);
					} catch (IllegalArgumentException e){
						System.err.println("Error:" + hexValue + "is not a valid color specification.");
					} catch (NullPointerException e) {
						System.err.println("Error: a color could not be added");
					}
				}
				
				input.close();
		    //If the file doesn't exist, or directions are incorrect, program terminates
			} catch (FileNotFoundException e) {
				System.err.println("Error: the file " + args[i] + " cannot be opened.");
			   	System.exit(0);
			} 
		}
		
		//Start scanner to read user input
		Scanner input2 = new Scanner(System.in);	
		String hexColor = "";
		
		/*Read user input to create a color and add to ColorList
		 * continues until user types 'quit'
		 * 
		 * catches errors if the input for a color a user provides
		 * is a null object or if the hexadecimal value is a not a valid color specification.
		 */
		while (!(hexColor.equalsIgnoreCase("quit"))) {
			System.out.println("Enter the color in HEX format (#RRGGBB) or \"quit\" to stop:");
			hexColor = input2.next();
			if (hexColor.equalsIgnoreCase("quit"))
				break;
			try {
				Color temp = new Color(hexColor);
				System.out.println("Color Information:");
				System.out.println(temp.toString());
			} catch (IllegalArgumentException e) {
				System.err.println("Error: This is not a valid color specification.");
			} catch (NullPointerException e) {
				System.err.println("Error: a color could not be added");
			}
			
		}
		input2.close();
	}
	
}
