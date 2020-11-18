package options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import models.OptionDetailsCustomObject;
import models.Tweet;
import repository.CachedData;
import play.libs.ws.*;



/**
 * @author Aida 
 * Returns a string which contains user profile information and
 * last then tweets of user
 * @param searchString takes the searched word and return the result for the
 *                     user associated to that searched word
 * @return the string which contains user information
 *
 */
public class Option1 {

	private final String bearerTokenStringOption1 = "Bearer AAAAAAAAAAAAAAAAAAAAAKkDJQEAAAAAf4NyxRhrWfz1HUCK2rZEkkqIrzA%3DAL4sVG7dUMpZv4srskFNuRuRKOVaQuRKoTLvEfHvtyrjPs1SwM";

	private List<String> userTweetList;
	final static private int MAX_NUMBER_TWEETS = 10;
	private static final int MAX_TWEET_DISPLAY = 10;
	private int actualNumberofTweetFetched = 0;
	private String searchString;
	private String lowerCaseSearchString;



	public Option1(String searchString) {
		this.userTweetList = new ArrayList<String>();
		this.searchString = searchString;
		this.lowerCaseSearchString = this.searchString.toLowerCase();

	}



	/**
	 * @author Aida
	 * Returns a string which contains user profile information and last then tweets of user from the cache
	 * @param searchString earched  keyword
	 * @param userNameWrapper  takes the user name and return the result for that specific user
	 * @param wsClient
	 * @return Calls the userProfileResult fomats the response and convert to the string which contains user information
	 *
	 */

	public CompletableFuture<String> option1Wrapper(String searchString, String userNameWrapper, WSClient wsClient) {
		

		return CompletableFuture.supplyAsync(() -> userProfileResult(searchString,userNameWrapper,wsClient));
		


	}

	/**
	 * @author Aida 
	 * Calls the twitter API user profile and another API for getting
	 * user tweets for each user name and call extract method for both of
	 * these APIs and extract the response from them
	 * @param searchString takes the searched word
	 * @param userName ,name of the user for specific searchString
	 * @param wsClient it is WSClient
	 *
	 */
	public String userProfileResult(String searchString, String userName, WSClient wsClient) {

			String searchUser = userName.substring(1);

			String listOfProfileFuture = getUserProfile(wsClient, searchUser);
				
			// user Tweets
			List<String> listOfUserTweetsFuture = getUserTweets(wsClient, searchUser);
			return prepareJsonStringResponse(searchUser,listOfProfileFuture,listOfUserTweetsFuture);
			
	}

	/**
	 * @author Aida 
	 * Calls the twitter API user profile information
	 * @param wsClient   it is WSClient
	 * @param searchUser takes the user name that wants to check it's profile
	 * @return String which call the API and gets the response
	 */
	public String getUserProfile(WSClient wsClient, String searchUser) {
		String tweetSearchAPI = "https://api.twitter.com/1.1/users/show.json";
		WSResponse wsUserResponse = null;
		try {
			wsUserResponse = wsClient.url(tweetSearchAPI).addHeader("Authorization", bearerTokenStringOption1)
					.addHeader("Accept", "/").addHeader("Accept-Encoding", "gzip, deflate, br")
					.addHeader("Connection", "keep-alive").addQueryParameter("screen_name", searchUser)
					.addQueryParameter("count", String.valueOf(MAX_NUMBER_TWEETS))// numberOfTweets converted to string
					.addQueryParameter("tweet_mode", "extended").get().toCompletableFuture().get();

		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Did not receive the response from twitter search API ...");
			e.printStackTrace();
			System.exit(0);
		}
		// Check for null before sending all the responses
		if (wsUserResponse == null) {
			System.out.println("Did not receive the response from twitter search API. Exiting normally ...");
			System.exit(0);
		}
		 return extractTweetFromUserProfile(wsUserResponse, searchUser);
		

	}

	/**
	 * @author Aida 
	 * Extracts response from user profile
	 * @param wsResponse
	 * @param userNameProfile takes the user name that wants to check it's profile
	 * @return profileInfo, a string contains user profile information
	 */
	private String extractTweetFromUserProfile(WSResponse wsResponse, String userNameProfile) {
		JsonNode userProfileInfo = wsResponse.asJson();
		String location = "";
		String userName = "@" + userProfileInfo.get("screen_name").asText();
		String displayName = userProfileInfo.get("name").asText();
		String description = userProfileInfo.get("description").asText();
		String createdAt = userProfileInfo.get("created_at").asText();
		String followers_count = userProfileInfo.get("followers_count").asText();
		String friends_count = userProfileInfo.get("friends_count").asText();
		if ((userProfileInfo.get("location")) != null) {
			location = userProfileInfo.get("location").asText();
		}

		String profileInfo = ("UserName: " + userName + ", Name: " + displayName + ", Profile Description: "
				+ description + ", Profile creation date: " + createdAt + ", Number of followers: " + followers_count
				+ ", Number of followers: " + friends_count + ", location: " + location);

		String userNamePRofileFinal = "@" + userNameProfile;
		return profileInfo;
		
		
	}

	/**
	 * @author Aida 
	 * Calls Twitter API for get user's Tweets
	 * @param wsClient   it is WSClient
	 * @param searchUser takes the user name that wants to check it's profile
	 * @return list of strings which call the API and gets the response
	 */
	public List<String> getUserTweets(WSClient wsClient, String searchUser) {
		String tweetSearchAPI = "https://api.twitter.com/2/tweets/search/recent";
		WSResponse wsUserResponse = null;
		String searchUserTweet = "from:" + searchUser;
		try {
			wsUserResponse = wsClient.url(tweetSearchAPI).addHeader("Authorization", bearerTokenStringOption1)
					.addHeader("Accept", "/").addHeader("Accept-Encoding", "gzip, deflate, br")
					.addHeader("Connection", "keep-alive").addQueryParameter("query", searchUserTweet).get()
					.toCompletableFuture().get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Did not receive the response from twitter search API ...");
			e.printStackTrace();
			System.exit(0);
		}
		// Check for null before sending all the responses, if required
		if (wsUserResponse == null) {
			System.out.println("Did not receive the response from twitter search API. Exiting normally ...");
			System.exit(0);
		}
		return extractTweetFromUserTweet(wsUserResponse, searchUser);

	}

	/**
	 * @author Aida 
	 * Extracts response from user's Tweets
	 * @param wsResponse
	 * @param userNameTweet takes the user name that wants to check it's profile
	 * @return userTweetList, a list of user tweets
	 */
	private List<String> extractTweetFromUserTweet(WSResponse wsResponse, String userNameTweet) {

		JsonNode responseJson = wsResponse.asJson();
		this.userTweetList = IntStream.rangeClosed(0, 10).boxed().map(number -> responseJson.get("data").get(number))
				.filter(eachTweetContent1 -> eachTweetContent1 != null).map(eachTweetContent -> {
					String tweetBody = "";
					tweetBody = eachTweetContent.get("text").asText();
					return tweetBody;
				}).limit(10).collect(Collectors.toList());

		String userNameTweetFinal = "@" + userNameTweet;
		return userTweetList;
	}

	/**
	 * @author Aida 
	 * Gets the result from the cache and data from user profile and
	 * user tweets and convert them to string Creates the format of final
	 * response
	 * @param searchUser
	 * @param listOfProfileFuture
	 * @param listOfUserTweetsFuture
	 * @return responseJSONFinal, a string that contains final response
	 */
	private String prepareJsonStringResponse(String searchUser,String listOfProfileFuture, List<String> listOfUserTweetsFuture) {
//		String userProfileRes = userFinalResponseProfile.getUserProfile().get(userNameWrapper);
//		List<String> userProfileTweet = userFinalResponseProfile.getUserTweets().get(userNameWrapper);

//		int firstNTweetsToDisplay = MAX_TWEET_DISPLAY;
		String subUserProfile = listOfProfileFuture;

		List<String> subTweetList = listOfUserTweetsFuture;
		String responseJSON = new Gson().toJson(subTweetList);

		String responseJSONProfile = new Gson().toJson(subUserProfile);
		responseJSONProfile = "{" + "\"status\"" + ":" + "\"success\"" + "," + "\"data\":" + responseJSONProfile + "}";// Formatting
		String responseJSONTweet = new Gson().toJson(subTweetList);
		responseJSONTweet = "{" + "\"status\"" + ":" + "\"success\"" + "," + "\"data\":" + responseJSONTweet + "}";// Formatting

		String responseJSONFinal = searchUser+responseJSONProfile + responseJSONTweet;
		//System.out.println(responseJSONFinal);
		return responseJSONFinal;
	}

}