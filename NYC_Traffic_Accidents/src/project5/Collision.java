package project5;

import java.security.InvalidParameterException;
import java.util.ArrayList;
/**
 * creates collision object out of an
 * ArrayList<String> of parsed data,
 * stores data about a particular colllision 
 * that occured in NYC. data such as
 * the date & zipcode it occured, as well as 
 * statistics of people injured/killed
 * 
 * @author christian urrea
 */

public class Collision implements Comparable<Collision> {

	 private ArrayList<String> entries; //stores list of data
	 Date date; //stores date object
	
	 /**
	  * 
	  * @param entry - ArrayList<String> with parsed data
	  * to integrate into collision object
	  * 	@throws IllegalArgumentException - thrown for any data conditions 
	  * that are not met by the entry passed 
	  * to create a correct form of collision object
	  */
	  public Collision (ArrayList<String> entry) throws IllegalArgumentException {
		  if (entry == null)
	           throw new IllegalArgumentException("failed- attempted to construct with null list"); 
		  if (entry.size() < 24)
	           throw new IllegalArgumentException("failed- attempted to construct with smaller list"); 
		  if (!isValidDate(entry.get(0)))
			throw new IllegalArgumentException("failed- invalid date");	
		  if(!isValidZip(entry.get(3)))
			throw new InvalidParameterException();	
		  if (!checkNumEntry(entry))
			throw new IllegalArgumentException("failed- invalid persons injured/killed numbers");
		  if(!checkKey(entry.get(23)))
			throw new IllegalArgumentException("failed invalid key");	
		  this.entries = entry;	 
		  date = new Date(entry.get(0));
	  }
	  
	  /**
	   * @return this collision objects key
	   * as a string
	   */
	  public String getKey() {
		  return entries.get(23);
	  }
	  
	  /**
	   * @return zipcode
	   * as a string
	   */
	  public String getZip() {
		  return entries.get(3); 
	  }
	  
	  /**
	   * @return  zipcode number
	   * as an integer
	   */
	  public int getZipNumber() {
		  return toNumber(entries.get(3)); 
	  }
	  
	  /**
	   * @return date of collision object
	   */
	  public Date getDate() {
		return this.date;
	  }
	
	  /**
	   * @return # of persons injured
	   */
	  public int getPersonsInjured() {
		  return toNumber(entries.get(10)); 
	  }
	  
	  /**
	   * @return # of pedestrians injured
	   */
	  public int getPedestriansInjured(){
		  return toNumber(entries.get(12)); 
	  }
	  
	  /**
	   * @return # of cyclists injured
	   */
	  public int getCyclistsInjured() {
		  return toNumber(entries.get(14));   
	  }
	  
	  /**
	   * @return # of motorists injured
	   */
	  public int getMotoristsInjured() {
		  return toNumber(entries.get(16)); 
	  }
	  
	  /**
	   * @return # of persons killed
	   */
	  public int  getPersonsKilled() {
		  return toNumber(entries.get(11)); 
	  }
	  
	  /**
	   * @return # of pedestrians killed
	   */
	  public int getPedestriansKilled() {
		  return toNumber(entries.get(13)); 
	  }
	  
	  /**
	   * @return # of cyclists killed
	   */
	  public int getCyclistsKilled() {
		  return toNumber(entries.get(15)); 
	  }
	  
	  /**
	   * @return # of motorists killed
	   */
  	  public int getMotoristsKilled() {
		  return toNumber(entries.get(17)); 
  	  }
  	  
  	  /**
  	   * compares two collision objects
  	   * based off their zipcode, date and key differences
  	   * @param o - a collision object to compare with
  	   * @return positive integer, 0, negative integer if this object is greater than o,
  	   * same as o, or less than o respectively
  	   */
  	  @Override
	public int compareTo(Collision o) {
		if (o == null)
			throw new NullPointerException();
		if (this.getZipNumber() != o.getZipNumber()) 
			return this.getZipNumber() - o.getZipNumber();
		if (!this.getDate().equals(o.getDate()))
			return this.getDate().compareTo(o.getDate());
		return this.getKey().compareToIgnoreCase(o.getKey()); 
	}
	
  	  /**
  	   * checks if objects are the same
  	   * @param o - a collision object
  	   * @return true if Collision objects are the same
  	   */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Collision))
			return false;
		return this.compareTo((Collision) o) == 0;
		
	}
  
	/**
	 * checks whether the zipcode is valid
	 * to create collision object
	 * @param o = string zipcode
	 * @return true if valid zipcode
	 */
	
	private static boolean isValidZip(String o)  {  
		if (o == null) return false;
		//if (o.trim().length() != 5) return false;
		int z =-1;
		 try  {  
			 z = Integer.parseInt(o.trim());  
		  }  
		  catch(NumberFormatException e)   {  
			  return false;  
		  }   
		  return z >= 0;  
		}
	
	/**
	 * checks to see if the dates are valid 
	 * for Collision objects by
	 * creating Date objects out of them
	 * 
	 * @param first_date - start date
	 * @return true if date is a valid date,
	 * false otherwise
	 */
	private static boolean isValidDate(String o) {
		Date date1;
		try {
		 date1 = new Date(o);
		} catch (IllegalArgumentException e) { return false; }
		return date1 != null;
	}
	
	/**
	 * parses string into integer
	 * @param o - string of a number
	 * @return - integer after parsing from string
	 * @throws NumberFormatException - if string is not a valid number
	 */
	private static int toNumber(String o) throws NumberFormatException{
		int z = 0;
		try { z = Integer.parseInt(o.trim()); }
		catch(NumberFormatException e) {
			throw new NumberFormatException("unable to integer parse " + o);
		}
		return z;
	}
	
	/**
	 * checks whether these are valid numbers
	 * @param o - string to be tested
	 * @return true if string is a valid integer
	 */
	private static boolean isValidNum(String o)  {  
	if (o==null) return false;
	 int z;
	  try  {  
		  z = Integer.parseInt(o.trim());  
	    if (z < 0) return false;
	  }  
	  catch(NumberFormatException e)  {  
	    return false;  
	  }  
	  return z >= 0;
	}
	 
	/**
	 * checks if numbers of people injured/killed
	 * are above or equal to 0
	 * @param entry - ArrayList of Collision data
	 * @return true if all numbers are >= 0
	 */
	private static boolean checkNumEntry(ArrayList<String> entry) {
			  for (int i=10; i <18; i++) {
				  if (!isValidNum(entry.get(i)))
			  			return false;
			  }
		   return true;
	  }

	/**
	 * checks to see if key is a nonempty string
	 * @param key - collision object's key
	 * @return true if valid key
	 */
	private static boolean checkKey(String key) {
		if (key != null && !key.isEmpty())
			return true;
		return false;
	}

}
