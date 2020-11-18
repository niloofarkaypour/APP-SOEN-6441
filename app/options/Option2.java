package options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.TwitterApiSearchCallBean;
import models.OptionDetailsCustomObject;
import models.Tweet;
import repository.CachedData;

/**
 * @author Mayank Date: Nov. 7, 2020
 * 
 *         Implementation of option 2 of individual part
 * 
 */
public class Option2 {
	private String searchString;
	private String lowerCaseSearchString;

	public Option2(String searchString) {
		this.searchString = searchString;
		this.lowerCaseSearchString = this.searchString.toLowerCase();
	}

	/**
	 * Wait for the background tasks initiated by option 0 to get finished and then
	 * return the word frequency from the cache
	 * 
	 * @author Mayank
	 * @return CompletableFuture<LinkedHashMap<String, Long>> Future of word
	 *         frequency map
	 */
	public CompletableFuture<LinkedHashMap<String, Long>> option2Wrapper() {
		CompletableFuture<LinkedHashMap<String, Long>> finalResponse = CompletableFuture
				.supplyAsync(() -> new LinkedHashMap<String, Long>());
		if (CachedData.checkOption2(this.lowerCaseSearchString)) {
			CachedData.getOption2(this.lowerCaseSearchString).getOption2ResponseFuture().join();// Returns void blocking
																								// call
			finalResponse = CompletableFuture
					.supplyAsync(() -> CachedData.getOption2(this.lowerCaseSearchString).getWordFrequency());
		}
		return finalResponse;
	}

	/**
	 * This method is called by option0. Prepares the word frequency pair from the
	 * Tweets data and stores in the cache It converts whole tweet into lower case
	 * and then counts the word frequency
	 *
	 * @author Mayank
	 * 
	 */
	public void workerBackGround() {
		List<Tweet> tweetsList = new ArrayList<Tweet>();		
		tweetsList = CachedData.getOption0(this.lowerCaseSearchString).getOption0ResponseFuture().join();	

		LinkedHashMap<String, Long> linkedMapOfWordFrequency = tweetsList.stream()
				.map(tweet -> tweet.getTweetbody().toLowerCase())
				.flatMap(tweetString -> Arrays.stream(tweetString.split("\\s+")))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				// Map sorting and ordering
				.entrySet().stream().sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));		

		if (CachedData.checkOption2(this.lowerCaseSearchString)) {
			OptionDetailsCustomObject optionDetailsCustomObject = CachedData.getOption2(this.lowerCaseSearchString);
			optionDetailsCustomObject.setWordFrequency(linkedMapOfWordFrequency);
		} else {
			OptionDetailsCustomObject optionDetailsCustomObject = new OptionDetailsCustomObject(
					this.lowerCaseSearchString, linkedMapOfWordFrequency);
			CachedData.putOption2(this.lowerCaseSearchString, optionDetailsCustomObject);
		}
	}
}