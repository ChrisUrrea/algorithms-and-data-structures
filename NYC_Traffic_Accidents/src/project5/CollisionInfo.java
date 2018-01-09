package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Runs csv file with one argument
 * parses it into a arraylist string objects
 * creates collision objects and adds them to 
 * CollisionsData class
 * 
 * @author christian urrea
 *
 */

public class CollisionInfo {
	
	public static void main(String args[]) {
		CollisionsData collision_list = new CollisionsData();
		Scanner input = null;
		
		// check arg length is one
		if (args.length == 0) {
			System.err.println("Error: Missing the data input file.");
			System.exit(0); 
		}
		//attempt to open and read csv file if not catch error
			try {
				File file = new File(args[0]);
				input = new Scanner(file);
			} catch(FileNotFoundException e) {
				System.err.println("Error: the file " + args[0] + " could not be opened.");
			   	System.exit(0);
			} catch (Exception e) {
				System.err.println("Error: the file " + args[0] + " could not be scanned.");
			   	System.exit(0);
			}
			
			//counter to keep track which data line is being read
			int count =0;
			
				while (input.hasNextLine()) {
					count++;
					//loop over every csv line and create objects out of each
					//if one text line is invalid, catch the exception and continue
					try {
						ArrayList<String> data_parsed = splitCSVLine(input.nextLine());
						if (data_parsed == null) throw new NullPointerException();
						Collision new1 = new Collision(data_parsed);
						collision_list.add(new1);
					} catch (IllegalArgumentException e){
					} catch (NullPointerException e) {
					} catch (Exception e) {}
		}
		input.close();
		//start new scanner to read user input
		Scanner input2 = new Scanner(System.in);
		readUserInput(input2, collision_list);
		input2.close();
		//end program

	}
	/**
	 * read user's desired inputs and retrieves the sum of people injured/killed
	 * in certain zipcode from a start date to an end date (inclusive)
	 * @param input2 - scanner to read user's input
	 * @param data - CollisionData node BST tree to retrieve 
	 * report based of users zipcode, start date and end date
	 */
	private static void readUserInput(Scanner input2, CollisionsData data) {
		boolean finished = false;
		
		while (!finished) {
			//run loop until user types 'quit' for zipcode

			boolean dates= false;
			Date date1 = null;
			Date date2 = null;
				
			/*
			 * dates and zipcode must both be valid to proceed
			 * to print the complete report
			 */
			while (!finished && !dates) {
				boolean zip_condition = false;
				String zipcode = null;

				while(!zip_condition) {
					System.out.println("Enter a zip code (’quit’ to exit):  ");
					zipcode = input2.next();
					if (zipcode.equalsIgnoreCase("quit")) {
						finished = true; break;
					}
					//checks zipcode is valid
					else if(isValidZip(zipcode)) {
						zip_condition = true;
					}
					else 
						System.err.println("Invalid zip code. Try again.\n");
				}
				//quit program if 'quit' is typed
				if (zipcode.equalsIgnoreCase("quit")) {
					finished = true; break;
				}
				
				//retrieve dates and verify dates are valid
				System.out.println("Enter start date (MM/DD/YYYY):  ");
				String first_date = input2.next();
				System.out.println("Enter end date (MM/DD/YYYY):  ");
				String second_date = input2.next();
				
				//verifies if dates are valid to continue
				dates = isValidDate(first_date, second_date);
				//if dates are valid, then proceed to finding data
				//collecting nodes and printing out the report
				if(dates) {
					date1 = new Date(first_date);
					date2 = new Date(second_date);
					String report = data.getReport(zipcode, date1, date2);
					System.out.println(report);
				}
			} 
		}
	}

		/**
		 * checks to see if the dates are valid 
		 * for Collision objects by
		 * creating Date objects out of them
		 * 
		 * @param first_date - start date
		 * @param second_date - end date
		 * @return true if both dates are valid dates,
		 * false otherwise
		 */
	private static boolean isValidDate(String first_date, String second_date) {
		Date date1; Date date2;
		try {
			date1 = new Date(first_date);
			date2 = new Date(second_date);
			if (!(date1.compareTo(date2) < 0)) {
				System.err.println("Invalid start and end dates. Try again.");
				return false;
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid date format. Try again."); 
			return false;
		} catch (Exception e) {
			System.err.println("Invalid date format. Try again."); 
			return false;	
		} 
		return date1 != null && date2 != null && date1 instanceof Date && date2 instanceof Date && date1.compareTo(date2) < 0;
	}
	
	/**
	 * 
	 * checks whether the zipcode is valid
	 * to create collision object
	 * @param o = string zipcode
	 * @return true if valid zipcode
	 */
	private static boolean isValidZip(String o)  {  
		if (o == null) return false;
		String o2 = o.replaceAll("\\s","");
		if (o2.length() != 5) return false;
		 try  {  
			 int z = Integer.parseInt(o);  
		  }  
		  catch(NumberFormatException e)   {  
			  return false;  
		  }   
		  return true;  
		}

	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @param textLine	a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that line
	 */
	public static ArrayList<String> splitCSVLine(String textLine){
		
		ArrayList<String> entries = new ArrayList<String>(); 
		int lineLength = textLine.length(); 
		StringBuffer nextWord = new StringBuffer(); 
		char nextChar; 
		boolean insideQuotes = false; 
		boolean insideEntry= false;
		
		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			
			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {
					
				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false; 
					insideEntry = false;
				}else {
					insideQuotes = true; 
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
				// add it to the current entry 
					nextWord.append( nextChar );
				}else { // skip all spaces between entries
					continue; 
				}
			} else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar); 
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			} 
			
		}
		// add the last word ( assuming not empty ) 
		// trim the white space before adding to the list 
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}

}
