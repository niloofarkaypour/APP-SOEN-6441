package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Custom object to store the required information based on the need of
 * different options
 * 
 * @author Mayank Data: Nov. 13, 2020
 */
public class OptionDetailsCustomObject {

	// Members used by Option 0
	private String searchString;
	private List<Tweet> searchTweetList;
	private int actualNumberofTweetFetched;
	private CompletableFuture<List<Tweet>> option0ResponseFuture;
	private CompletableFuture<Void> option2ResponseFuture;
	// private CompletableFuture<Void> option1ResponseFuture;

	// option 2
	private LinkedHashMap<String, Long> wordFrequency;

	// option 3

	// option 4
	private String mode;

	/**
	 * Constructor
	 * 
	 * @param searchString
	 * @param searchTweetList
	 */
	public OptionDetailsCustomObject(String searchString, List<Tweet> searchTweetList) {
		this.searchString = searchString;
		this.searchTweetList = searchTweetList;

	}

	/**
	 * Constructor
	 * 
	 * @param searchString
	 * @param searchTweetList
	 */
	public OptionDetailsCustomObject(String searchString, LinkedHashMap<String, Long> wordFrequency) {
		this.searchString = searchString;
		this.wordFrequency = wordFrequency;
	}

	/**
	 * Constructor
	 * 
	 * @param searchString
	 * @param searchTweetList
	 */
	public OptionDetailsCustomObject(String searchString, CompletableFuture<List<Tweet>> option0ResponseFuture) {
		this.option0ResponseFuture = option0ResponseFuture;
	}

	/**
	 * Constructor
	 * 
	 * @param searchString
	 * @param searchTweetList
	 */
	public OptionDetailsCustomObject(String searchString, CompletableFuture<Void> optionResponseFuture,
			int optionNumber) {
		this.searchString = searchString;
		if (optionNumber == 2)
			this.option2ResponseFuture = optionResponseFuture;
//		else if(optionNumber == 1) {
//			this.option1ResponseFuture = optionResponseFuture;
//		}
//		else if(optionNumber == 3) {
//			this.option3ResponseFuture = optionResponseFuture;
//		}
//		else if(optionNumber == 4) {// This is not required
//			this.option4ResponseFuture = optionResponseFuture;
//		}
	}

	/**
	 * Constructor
	 * 
	 * @param searchString
	 * @param searchTweetList
	 */
	public OptionDetailsCustomObject(String searchString, String mode) {
		this.searchString = searchString;
		this.mode = mode;

	}

	/**
	 * Setter for option0ResponseFuture
	 * 
	 * @param option0ResponseFuture
	 * @return CompletableFuture<List<Tweet>>
	 */
	public CompletableFuture<List<Tweet>> setOption0ResponseFuture(
			CompletableFuture<List<Tweet>> option0ResponseFuture) {
		this.option0ResponseFuture = option0ResponseFuture;
		System.out.println(searchString
				+ ": If this line is printed for the same search key twice then it's Risky. Report it to Mayank");
		return option0ResponseFuture;
	}

	/**
	 * Getter for getOption0ResponseFuture
	 * 
	 * @return CompletableFuture<List<Tweet>>
	 */
	public CompletableFuture<List<Tweet>> getOption0ResponseFuture() {
		return this.option0ResponseFuture;
	}

//	/**
//	 * Getter for getSearchString
//	 * 
//	 * @return String
//	 */
//	public String getSearchString() {
//		return searchString;
//	}

	/**
	 * Getter for getSearchTweetList
	 * 
	 * @return List<Tweet>
	 */
	public List<Tweet> getSearchTweetList() {
		return searchTweetList;
	}

	/**
	 * Setter for searchTweetList
	 * 
	 * @param option0ResponseFuture
	 */
	public void setSearchTweetList(List<Tweet> searchTweetList) {
		this.searchTweetList = searchTweetList;
	}

//	/**
//	 * Getter for actualNumberofTweetFetched
//	 * 
//	 * @return int
//	 */
//	public int getActualNumberofTweetFetched() {
//		return actualNumberofTweetFetched;
//	}

	/**
	 * Setter for searchTweetList
	 * 
	 * @param option0ResponseFuture
	 * 
	 */
	public void setActualNumberofTweetFetched(int actualNumberofTweetFetched) {
		this.actualNumberofTweetFetched = actualNumberofTweetFetched;
	}

	/**
	 * Getter for wordFrequency
	 * 
	 * @return LinkedHashMap<String, Long>
	 */
	public LinkedHashMap<String, Long> getWordFrequency() {
		return wordFrequency;
	}

	/**
	 * Setter for wordFrequency
	 * 
	 * @param wordFrequency
	 * 
	 */
	public void setWordFrequency(LinkedHashMap<String, Long> wordFrequency) {
		this.wordFrequency = wordFrequency;
	}

	/**
	 * Getter for mode
	 * 
	 * 
	 * @return LinkedHashMap<String, Long>
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Setter for mode
	 * 
	 * @param String
	 * 
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Getter for option2ResponseFuture
	 * 
	 * 
	 * @return CompletableFuture<Void>
	 */
	public CompletableFuture<Void> getOption2ResponseFuture() {
		return option2ResponseFuture;
	}

//	/**
//	 * Setter for option2ResponseFuture
//	 * 
//	 * @param CompletableFuture<Void>
//	 * 
//	 */
//	public void setOption2ResponseFuture(CompletableFuture<Void> option2ResponseFuture) {
//		this.option2ResponseFuture = option2ResponseFuture;
//	}

}