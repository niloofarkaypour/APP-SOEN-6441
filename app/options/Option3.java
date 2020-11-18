package options;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import play.libs.ws.*;
import repository.CachedData;
import models.Tweet;

/**
 * @author Niloofar
 * Date: Nov 10, 2020
 * Implementation of option 3 of individual task, 
 * getting the hashtag and search 10 recent tweets 
 * based on the selected hashtag
 */

public class Option3 {
	
	private String searchString;
	private String lowerCaseSearchString;
	private final String bearerTokenString = "Bearer AAAAAAAAAAAAAAAAAAAAAHt8JAEAAAAANTHnHf4Q5KLYLy1Oj2FJwyIE4TE%3DjrvrdbRy5a6p884BaxynrYioE360KvIcPmJFsn0rd41RgTtkDV";
	final static private int MAX_NUMBER_TWEETS = 100; // default gives 100 tweets to fetch in a call
	private static final int MAX_TWEET_DISPLAY = 10;
	private int actualNumberofTweetFetched = 0;
	private List<Tweet> tweetListOfHashTag;

	/**
	 * constructor
	 * @param searchString
	 */
	public Option3(String searchString) {
		
		this.searchString = searchString;
		this.lowerCaseSearchString = this.searchString.toLowerCase();
		this.tweetListOfHashTag = new ArrayList<Tweet>();		
	}
	
	/**
	 * 
	 * @param searchString
	 * @param hashTag
	 * @param wsClient
	 * @return CompletableFuture<String> of selected hashtag tweet result
	 */
	public CompletableFuture<String> option3Wrapper(String searchString, String hashTag, WSClient wsClient) {
		
		CompletableFuture<String> userFinalResponseProfile;
		
		if (CachedData.option3CachedData.containsKey(hashTag)) {
			userFinalResponseProfile = CachedData.option3CachedData.get(hashTag);
			return userFinalResponseProfile;
		}
		else {
			userFinalResponseProfile = workBackGroundOption3(searchString, hashTag, wsClient);	
			return userFinalResponseProfile;	
		}
		
	}
		
	/**
	 * this method is called by CompletableFuture, all the background work is done here
	 * first getTweets3 method is called then the tweets will extract by 
	 * extractTweetFromResponse3 method, and finally the string result will be ready by 
	 * calling prepareJsonStringResponse3. 
	 * @param searchString
	 * @param hashTag
	 * @param wsClient
	 * @return
	 */
	public CompletableFuture<String> workBackGroundOption3(String searchString, String hashTag, WSClient wsClient) {
		
		String searchHashTag = hashTag;
		CompletableFuture<String> result =  CompletableFuture.supplyAsync(()-> getTweets3(wsClient, searchHashTag)).thenApply((wsResponse) -> extractTweetFromResponse3(wsResponse)).thenApply(this::prepareJsonStringResponse3);
		
//		Add received tweet to cache data
		CachedData.option3CachedData.put(hashTag, result);
		System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,DATA"+CachedData.option3CachedData);
				
		return result;		
	}
	
	/**
	 * Makes GET Twitter search(v1.1) call based on the value of
	 * lowerCaseSearchString Using Bearer token authentication method		
	 * @param wsClient
	 * @param hashTag
	 * @return WSResponse
	 */
	private WSResponse getTweets3(WSClient wsClient, String hashTag) {
		String tweetSearchAPI_1_1 = "https://api.twitter.com/1.1/search/tweets.json";
		WSResponse wsResponse = null;
		
		try {
			wsResponse =wsClient.url(tweetSearchAPI_1_1).addHeader("Authorization", bearerTokenString)
					.addHeader("Accept", "*/*").addHeader("Accept-Encoding", "gzip, deflate, br")
					.addHeader("Connection", "keep-alive").addQueryParameter("q", hashTag)
					.addQueryParameter("count", String.valueOf(MAX_NUMBER_TWEETS))
					.addQueryParameter("tweet_mode", "extended").get().toCompletableFuture().get();
			
		} 
		catch (InterruptedException | ExecutionException e) {
			System.out.println("Did not receive the response from twitter search API ...");
			e.printStackTrace();
			System.exit(0);
		}
		if (wsResponse == null) 
		{
			System.out.println("Did not receive the response from twitter search API. Exiting normally ...");
			System.exit(0);
		}				
		return wsResponse;
	}

    /**
     * Checks if the response is to be extracted from cache or through response
     * through Extracts the response from the GET Twitter API search call response
     * And creates the List<Tweet>
     * @param wsResponse
     * @return List<Tweet> tweetListOfHashTag
     */
	private List<Tweet> extractTweetFromResponse3(WSResponse wsResponse) {

		JsonNode responseJson = wsResponse.asJson();
		actualNumberofTweetFetched = responseJson.get("statuses").size();
		
		this.tweetListOfHashTag = IntStream.rangeClosed(0, actualNumberofTweetFetched).boxed()
				.map(number -> responseJson.get("statuses").get(number))
				.filter(eachTweetContent1 -> eachTweetContent1 != null).map(eachTweetContent -> {
					String tweetBody = "";
					if (eachTweetContent.has("retweeted_status")) {
						tweetBody = eachTweetContent.get("retweeted_status").get("full_text").asText();
					} else {
						tweetBody = eachTweetContent.get("full_text").asText();
					}
					String userName = "@" + eachTweetContent.get("user").get("screen_name").asText();
					String displayName = eachTweetContent.get("user").get("name").asText();
					return new Tweet(userName, tweetBody, displayName);
				}).collect(Collectors.toList());
		
		return tweetListOfHashTag;
	}
	
	/**
	 * Prepares the final response from the List<Tweet> Executes the option4(Tweet
	 * sentiments) Using GSON library to convert the List<Tweet> to String response
	 * @param tweetListOfHashTag
	 * @return String responseJSON
	 */
	private String prepareJsonStringResponse3(List<Tweet> tweetListOfHashTag) {
		
		int firstNTweetsToDisplay = Math.min(tweetListOfHashTag.size(), MAX_TWEET_DISPLAY);
		List<Tweet> subTweetList = tweetListOfHashTag.subList(0, firstNTweetsToDisplay);
		String responseJSON = new Gson().toJson(subTweetList);
		responseJSON = "{" + "\"status\"" + ":" + "\"success\"" + "," + "\"data\":" + responseJSON + "}";// Formatting
		
		return responseJSON;
	}
}
