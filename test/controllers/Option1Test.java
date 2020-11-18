package controllers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.fasterxml.jackson.databind.JsonNode;

import core.TwitterApiSearchCallBean;
import models.OptionDetailsCustomObject;
import models.Tweet;
import options.Option1;
import options.Option2;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.mvc.Http.RequestBuilder;
import repository.CachedData;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import core.TwitterApiSearchCallBean;
import models.Tweet;
import play.Application;

import play.libs.ws.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import play.twirl.api.Content;

//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;

class Option1Test extends WithApplication {
	final String searchString = "California";
	final String lowerCaseSearchString = searchString.toLowerCase();
	Option1 option1;

	final String responseTweet = "{\"data\":[{\"id\":\"1327234836218560519\","
			+ "\"text\":\"This animation shows how California’s fire season weather has changed over time."
			+ " It has become drier and hotter, paving the way for large bushfires. \\nThis year’s fire season was the worst on record."
			+ "\\nby @BerkeleyEarth\\nhttps://t.co/rXRxwDG2sY\"},{\"id\":\"1327107135809830913\","
			+ "\"text\":\"@SHamiltonian Are you sure it’s not the Stockholm syndrome?\"},"
			+ "{\"id\":\"1326482410192556032\",\"text\":\"This is brilliant: how to improve your writing."
			+ " https://t.co/bQ7osQU9en\"},{\"id\":\"1326145513205362689\",\"text\":\"How the market reacted to"
			+ " the news of the incoming Pfizer vaccine.\\nhttps://t.co/ebFOHLRrvr https://t.co/bJB0xBJNXu\"},"
			+ "{\"id\":\"1326138416879202304\",\"text\":\"@SylvainCF @pquirion1 Ca me rappelle Baudrillard dans le livre de Sokal et Bricmont."
			+ " https://t.co/4wN1mWWiXm\"},{\"id\":\"1326084026189324289\",\"text\":\"RT @cesifoti: "
			+ "Don’t believe everything that you see ... https://t.co/PeYMZx0Ode\"},"
			+ "{\"id\":\"1326024406980702208\",\"text\":\"@AlfredoPaloyo @agnesquis I think it is exaggerated. "
			+ "If you think in terms of coordination equilibrium, with the calls by Fox News, and major Republicans like Bush,"
			+ " it is game over. Courts are independent. Military leaders would have nothing to win backing Trump and soldiers would"
			+ " most likely not obey.\"},{\"id\":\"1325783617608458241\",\"text\":\"*Alzheimer\"},{\"id\":\"1325782859844526082"
			+ "\",\"text\":\"Our memories are embodied in ourselves and tied to emotions. "
			+ "This video is such a beautiful illustration of it. Marta González has Alzeimer."
			+ " But the sound of #Tchaikovsky’s music reignites her and her memories as former Prima Ballerina."
			+ "\\nht @Marco_Zanoli\\nhttps://t.co/n6b6i41wcz\"},{\"id\":\"1325774923139174400\",\"text\":\"RT @Keir_Starmer:"
			+ " This is really encouraging news.\\n\\nhttps://t.co/W6tLqym0Qi\"}],\"meta\":{\"newest_id\":\"1327234836218560519"
			+ "\",\"oldest_id\":\"1325774923139174400\",\"result_count\":10,\"next_token\":\"b26v89c19zqg8o3fosbu90bfzmqqlqtiqat88799evbzx\"}}";
	final String responseUser = "{\"id\":817270221236879360,\"id_str\":\"817270221236879360\",\"name\":\"Lionel Page\",\"screen_name\":\"page_eco\",\"location\":\"Sydney\",\"profile_location\":null,\"description\":\"Professor @UTS_Economics. Author of forthcoming 'Optimally Irrational'. Tweet on behavioural economics, science and society.\",\"url\":\"https:\\/\\/t.co\\/N1WdAtJbhb\",\"entities\":{\"url\":{\"urls\":[{\"url\":\"https:\\/\\/t.co\\/N1WdAtJbhb\",\"expanded_url\":\"https:\\/\\/sites.google.com\\/site\\/liopage\\/home\",\"display_url\":\"sites.google.com\\/site\\/liopage\\/h\\u2026\",\"indices\":[0,23]}]},\"description\":{\"urls\":[]}},\"protected\":false,\"followers_count\":24861,\"friends_count\":395,\"listed_count\":342,\"created_at\":\"Fri Jan 06 07:22:59 +0000 2017\",\"favourites_count\":12442,\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":true,\"verified\":false,\"statuses_count\":4960,\"lang\":null,\"status\":{\"created_at\":\"Fri Nov 13 13:00:18 +0000 2020\",\"id\":1327234836218560519,\"id_str\":\"1327234836218560519\",\"text\":\"This animation shows how California\\u2019s fire season weather has changed over time. It has become drier and hotter, pa\\u2026 https:\\/\\/t.co\\/BpfcFbRpaH\",\"truncated\":true,\"entities\":{\"hashtags\":[],\"symbols\":[],\"user_mentions\":[],\"urls\":[{\"url\":\"https:\\/\\/t.co\\/BpfcFbRpaH\",\"expanded_url\":\"https:\\/\\/twitter.com\\/i\\/web\\/status\\/1327234836218560519\",\"display_url\":\"twitter.com\\/i\\/web\\/status\\/1\\u2026\",\"indices\":[117,140]}]},\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/iphone\\\" rel=\\\"nofollow\\\"\\u003eTwitter for iPhone\\u003c\\/a\\u003e\",\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"retweet_count\":2,\"favorite_count\":9,\"favorited\":false,\"retweeted\":false,\"possibly_sensitive\":false,\"lang\":\"en\"},\"contributors_enabled\":false,\"is_translator\":false,\"is_translation_enabled\":false,\"profile_background_color\":\"F5F8FA\",\"profile_background_image_url\":null,\"profile_background_image_url_https\":null,\"profile_background_tile\":false,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/817273891928145920\\/a1P9FSnS_normal.jpg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/817273891928145920\\/a1P9FSnS_normal.jpg\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/817270221236879360\\/1546605732\",\"profile_link_color\":\"1DA1F2\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"has_extended_profile\":false,\"default_profile\":true,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null,\"translator_type\":\"none\"}";

	@Mock
	WSResponse wsResponseMock;
	@Mock
	WSClient wsClientMock;
	@Mock
	WSClient wsClient;
	@Spy
	Option1 Option1Mock = new Option1(lowerCaseSearchString);

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Before
	public void createObject() {
		option1 = new Option1(lowerCaseSearchString);
	}

	@Override
	protected Application provideApplication() {
		return new GuiceApplicationBuilder().build();
	}


	@Test
	public void testUserProfileResult() {
		String searchUser = "page_eco";
		  option1.userProfileResult(lowerCaseSearchString, searchUser, wsClient);
		  String returnedValue = Option1Mock.option1Wrapper(lowerCaseSearchString, "@page_eco", wsClient).join();
		  assertEquals(returnedValue, "page_eco" );
//		  assertEquals(responseTweet.contains("page_eco"));
		
	}

	@Test
	public void testOption1Wrapper() {
		  option1.userProfileResult(lowerCaseSearchString, "page_eco", wsClient);
		  String returnedValue = Option1Mock.option1Wrapper(lowerCaseSearchString, "@page_eco", wsClient).join();
		  assertEquals(returnedValue, "page_eco" );
//		assertEquals(responseTweet.contains("page_eco"));
	}
		  
		

}



