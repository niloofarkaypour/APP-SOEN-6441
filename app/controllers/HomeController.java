package controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import javax.inject.Inject;

import core.TwitterApiSearchCallBean;
import models.Tweet;
import options.Option1;
import options.Option2;
import options.Option3;
import options.Option33;
import options.Option333;



import play.libs.ws.WSClient;
import play.mvc.*;
import views.*; 

/**
 * This controller contains an action to handle HTTP requests to the
 * application's home page.
 * 
 * @author Mayank
 * 
 */
public class HomeController extends Controller {
	private WSClient wsClient;

	@Inject
	public HomeController(WSClient wsClient) {
		this.wsClient = wsClient;
	}

	/**
	 * An action that renders an HTML page with a welcome message. The configuration
	 * in the <code>routes</code> file means that this method will be called when
	 * the application receives a <code>GET</code> request with a path of
	 * <code>/</code>.
	 */

	/**
	 * Controller for getting the main plain page
	 * It does not perform any processing
	 * 
	 * @author Mayank
	 * @return CompletionStage<Result>
	 */
	public CompletionStage<Result> index() {
		// return CompletableFuture.completedFuture(ok(views.html.index.render()));
		// This should be fine as there are no calls to controller. It is simple page.
		return CompletableFuture.supplyAsync(() -> ok(views.html.index.render()));
	}

	/**
	 * Controller for getting the String response of the list of Tweets
	 * for a given search string on main page
	 * 
	 * @author Mayank
	 * @return CompletionStage<Result> - Returns the CompletionStage of page
	 */
	public CompletionStage<Result> returnSearchResponse(String searchString) {
    	return new TwitterApiSearchCallBean(searchString).tweetSeachAPICallWrapper(wsClient)
				.thenApply(stringResponse -> ok(stringResponse));
	}
	
	/**
	 * Controller for getting the Tweet Analytics for a given word
	 * It is controlled by creating object of Option2 and then
	 * invoking the wrapper of the class
	 * 
	 * @author Mayank
	 * @return CompletionStage<Result> - Returns the CompletionStage of tweetAnalytics page
	 */
	public CompletableFuture<Result> tweetAnalytics(String searchString) {
		return new Option2(searchString).option2Wrapper().thenApply(
				responCompletableFuture -> ok(views.html.tweetAnalytics.render(responCompletableFuture, searchString)));
	}
	
    //option1
    public CompletableFuture<Result> userInformation(String searchString,String userName) {    	
    	return new Option1(searchString).option1Wrapper(searchString,userName,wsClient).thenApply(stringResponse -> ok(views.html.userInformation.render(stringResponse)));
    }
    
/**
 * @author Niloofar
 * @param searchString
 * @param hashTag
 * @return CompletionStage<Result> - Returns the CompletionStage of tweetHashTags page
 */
    public CompletableFuture<Result> tweetHashTags(String searchString, String hashTag) {
 	return new Option3(searchString).option3Wrapper(searchString, hashTag, wsClient).thenApply((stringResponse)->ok(views.html.tweetHashTags.render(stringResponse)));
 }
}