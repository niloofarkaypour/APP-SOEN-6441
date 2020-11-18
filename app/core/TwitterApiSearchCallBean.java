package core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import models.OptionDetailsCustomObject;
import models.Tweet;
import options.Option2;
import options.Option4;
import play.libs.ws.*;
import repository.CachedData;

/**
 * @author Mayank
 * 
 *         Provides bean for each search call For each request the controller
 *         will send the request to instance of this class.
 */
public class TwitterApiSearchCallBean {
	private final String bearerTokenString = "Bearer AAAAAAAAAAAAAAAAAAAAAHt8JAEAAAAANTHnHf4Q5KLYLy1Oj2FJwyIE4TE%3DjrvrdbRy5a6p884BaxynrYioE360KvIcPmJFsn0rd41RgTtkDV";
	private List<Tweet> tweetList;
	final static private int MAX_NUMBER_TWEETS = 100; // default gives 100 or less Tweets from API call
	private int actualNumberofTweetFetched = 0;
	private static final int MAX_TWEET_DISPLAY = 10;
	private String searchString;
	private String lowerCaseSearchString;

	/**
	 * Constructor taking the string as parameter
	 * 
	 * @param searchString
	 */
	public TwitterApiSearchCallBean(String searchString) {
		this.tweetList = new ArrayList<Tweet>();
		this.searchString = searchString;
		this.lowerCaseSearchString = this.searchString.toLowerCase();
	}

	/**
	 * Wrapper for the actual twitter search call If the search term data found in
	 * the cache then return the result from cache by calling
	 * extractTweetFromResponse method else calls the respective methods for: 1)
	 * sending the GET request to Twitter search 2) Extract the response 3) Launch
	 * the back ground future of other options 4) Call the option4 and format the
	 * final integrated response in required format and send the future of the
	 * response
	 * 
	 * All the tasks are pipelined through future of previous task
	 * 
	 * @author Mayank
	 * @param wsClient - Requires to send the GET request to remote API
	 * @return CompletableFuture<String> - Future of the response of GET search
	 *         query
	 */
	public CompletableFuture<String> tweetSeachAPICallWrapper(WSClient wsClient) {
		if (CachedData.checkOption0(this.lowerCaseSearchString)) {
			System.out.println("Option0: Returnig from cached data");
			return CompletableFuture.supplyAsync(() -> extractTweetFromResponse(null, true))
					.thenApply(this::prepareJsonStringResponse);
		}
		CachedData.putOption0(this.lowerCaseSearchString,
				new OptionDetailsCustomObject(this.lowerCaseSearchString, tweetList));
		CompletableFuture<WSResponse> wsResponseFuture = CompletableFuture.supplyAsync(() -> getTweets(wsClient));
		CompletableFuture<List<Tweet>> listOfTweetsFuture = wsResponseFuture
				.thenApply((wsResponse) -> extractTweetFromResponse(wsResponse, false));
		OptionDetailsCustomObject tweetListFuture = CachedData.getOption0(this.lowerCaseSearchString);
		tweetListFuture.setOption0ResponseFuture(listOfTweetsFuture); // Required for further task for eg. task2

		// Fire all the options here as List_of_tweets_future is available in cache now
		// Start Option2. Back-end work
		CompletableFuture.runAsync(this::startFutureForAllOptions); // This is to call future of all options.
		// End Option2

		return listOfTweetsFuture.thenApply(this::prepareJsonStringResponse);
	}

	/**
	 * Launch the future of the individual task(options) except option4 Stores the
	 * future of each task in the cache
	 * 
	 * @author Mayank
	 */
	private void startFutureForAllOptions() {
		CompletableFuture<Void> option2CompletableFuture = CompletableFuture
				.runAsync(() -> new Option2(lowerCaseSearchString).workerBackGround());
//		if (CachedData.checkOption2(this.lowerCaseSearchString)) {
//			OptionDetailsCustomObject optionDetailsCustomObject1 = CachedData.getOption2(this.lowerCaseSearchString);
//			optionDetailsCustomObject1.setOption2ResponseFuture(option2CompletableFuture);
//		} else 
//		{
			CachedData.putOption2(this.lowerCaseSearchString,
					new OptionDetailsCustomObject(this.lowerCaseSearchString, option2CompletableFuture, 2));
//		}
			
			
	}

	/**
	 * Checks if the response is to be extracted from cache or through response
	 * through Extracts the response from the GET Twitter API search call response
	 * And creates the List<Tweet>
	 * 
	 * @param wsResponse - Response received from Twitter API search call
	 * @param isCached   - Condition for either taking the response from the cache
	 *                   or to be extracted from wsResponse
	 * @return List<Tweet> - List<Tweet> is formed from the response
	 */
	private List<Tweet> extractTweetFromResponse(WSResponse wsResponse, boolean isCached) {
		if (isCached) {
			return CachedData.getOption0(this.lowerCaseSearchString).getSearchTweetList();
		}

		JsonNode responseJson = wsResponse.asJson();
		//System.out.println("RESPONSE JSON IS "+ responseJson);
		actualNumberofTweetFetched = responseJson.get("statuses").size();
		this.tweetList = IntStream.rangeClosed(0, actualNumberofTweetFetched).boxed()
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
		OptionDetailsCustomObject option0DetailsCustomObject = CachedData.getOption0(this.lowerCaseSearchString);
		option0DetailsCustomObject.setSearchTweetList(tweetList);
		option0DetailsCustomObject.setActualNumberofTweetFetched(actualNumberofTweetFetched);
		return tweetList;
	}

	/**
	 * Makes GET Twitter search(v1.1) call based on the value of
	 * lowerCaseSearchString Using Bearer token authentication method
	 * 
	 * @param wsClient
	 * @return List<Tweet>
	 */
	public WSResponse getTweets(WSClient wsClient) {
		String tweetSearchAPI_1_1 = "https://api.twitter.com/1.1/search/tweets.json";
		// String tweetSearchAPI_2_0 = "https://api.twitter.com/2/tweets/search/recent";
		WSResponse wsResponse = null;
		try {
			wsResponse = wsClient.url(tweetSearchAPI_1_1).addHeader("Authorization", bearerTokenString)
					.addHeader("Accept", "*/*").addHeader("Accept-Encoding", "gzip, deflate, br")
					.addHeader("Connection", "keep-alive").addQueryParameter("q", this.lowerCaseSearchString)
					.addQueryParameter("count", String.valueOf(MAX_NUMBER_TWEETS))// numberOfTweets converted to string
					.addQueryParameter("tweet_mode", "extended").get().toCompletableFuture().get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			System.out.println("Did not receive the response from twitter search API ...");
			e.printStackTrace();
			System.exit(0);
		}
		// Check for null before sending all the responses, if required
		if (wsResponse == null) {
			System.out.println("Did not receive the response from twitter search API. Exiting normally ...");
			System.exit(0);
		}
		return wsResponse;
	}

	/**
	 * Prepares the final response from the List<Tweet> Executes the option4(Tweet
	 * sentiments) Using GSON library to convert the List<Tweet> to String response
	 * 
	 * @author Mayank, Yasaman
	 * @param inputTweets List of Tweets to be sent to the response
	 * @return String Final response in JSON format
	 */
	private String prepareJsonStringResponse(List<Tweet> inputTweets) {
		int firstNTweetsToDisplay = Math.min(inputTweets.size(), MAX_TWEET_DISPLAY);
		List<Tweet> subTweetList = inputTweets.subList(0, firstNTweetsToDisplay);
		String responseJSON = new Gson().toJson(subTweetList);
		new Option4(lowerCaseSearchString).generateWordSentiment();
		String emoji = CachedData.getOption4(lowerCaseSearchString).getMode();
		responseJSON = "{" + "\"emoji\"" + ":" + "\"" + emoji + "\"" + "," + "\"status\"" + ":" + "\"success\"" + ","
				+ "\"data\":" + responseJSON + "}";// Formatting
		return responseJSON;
	}
}