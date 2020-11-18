package repository;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

//import akka.http.scaladsl.model.headers.CacheDirectives.private;
import models.OptionDetailsCustomObject;

/**
 * Stores the information required by each options
 * in the Map for each options where the key is the search string
 * and value is OptionDetailObject which is a custom object having
 * the information about each options in custom types required by 
 * each option
 * 
 * @author Mayank
 * Data: Nov. 13, 2020
 */
public final class CachedData {

	private static ConcurrentHashMap<String, OptionDetailsCustomObject> option0Database = new ConcurrentHashMap<String, OptionDetailsCustomObject>();
	//private static ConcurrentHashMap<String, OptionDetailsCustomObject> option1Database = new ConcurrentHashMap<String, OptionDetailsCustomObject>();
	private static ConcurrentHashMap<String, OptionDetailsCustomObject> option2Database = new ConcurrentHashMap<String, OptionDetailsCustomObject>();
	public static LinkedHashMap<String, CompletableFuture<String>> option3CachedData = new LinkedHashMap<String,CompletableFuture<String>>();
	private static ConcurrentHashMap<String, OptionDetailsCustomObject> option4Database = new ConcurrentHashMap<String, OptionDetailsCustomObject>();
	

	/**
	 * Getter for option0Database for a search string
	 * 
	 * @author Mayank
	 * @param searchString String for which it's object is requested
	 * @return OptionDetailsCustomObject containing information for option0
	 */
	public static OptionDetailsCustomObject getOption0(String searchString) {
		return option0Database.get(searchString);
	}

	/**
	 * Check for option0Database for a given search string
	 * whether the entry for a search string is present
	 * or not
	 * 
	 * @author Mayank
	 * @param searchString String for which it's object is requested
	 * @return boolean
	 */
	public static boolean checkOption0(String seachString) {
		return option0Database.containsKey(seachString);
	}

	/**
	 * Setter for option0Database for a search string
	 * Sets the database for a search string to new 
	 * object having the information of option0
	 * 
	 * @author Mayank
	 * @param searchString String for which it's object is requested
	 * @return OptionDetailsCustomObject containing information for option0
	 */
	public static void putOption0(String seachString, final OptionDetailsCustomObject optionDetailsCustomObject) {
		option0Database.put(seachString, optionDetailsCustomObject);
	}

	//Option 2 methods
	/**
	 * Getter for option2Database for a given search string
	 * 
	 * @author Mayank
	 * @param searchString String for which it's object is requested
	 * @return OptionDetailsCustomObject containing information for option2
	 */
	public static OptionDetailsCustomObject getOption2(String searchString) {
		return option2Database.get(searchString);
	}

	/**
	 * Check for option2Database for a given search string
	 * whether the entry for a search string is present
	 * or not
	 * 
	 * @author Mayank
	 * @param searchString String for which it's object is requested
	 * @return boolean
	 */
	public static boolean checkOption2(String seachString) {
		return option2Database.containsKey(seachString);
	}

	/**
	 * Setter for option2Database for a search string Sets the database for a search
	 * string to new object having the information of option2
	 * 
	 * @author Mayank
	 * @param searchString String for which it's object is requested
	 * @return OptionDetailsCustomObject containing information for option2
	 */
	public static void putOption2(String seachString, OptionDetailsCustomObject optionDetailsCustomObject) {
		option2Database.put(seachString, optionDetailsCustomObject);
	}
	
	//Option 4 methods
	public static OptionDetailsCustomObject getOption4(String searchString) {
		return option4Database.get(searchString);
	}
			
	public static boolean checkOption4(String seachString) {
		return option4Database.containsKey(seachString);
	}

	public static void putOption4(String seachString, OptionDetailsCustomObject optionDetailsCustomObject) {
		option4Database.put(seachString, optionDetailsCustomObject);
	}			
	
}
