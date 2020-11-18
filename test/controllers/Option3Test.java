package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import options.Option3;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.WithApplication;
import repository.CachedData;

/**
 * test case for option 3
 * @author Niloofar
 *
 */

public class Option3Test extends WithApplication {
	final String searchString = "Canada";
	final String lowerCaseSearchString = searchString.toLowerCase();
	final String hashtag = "Canada";
	Option3 option3;

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
	

	@Mock
	WSResponse wsResponseMock;
	@Mock
	WSClient wsClientMock;
//	@Mock
//	WSClient wsClient;
	@Spy
	Option3 Option3Mock = new Option3(lowerCaseSearchString);

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Before
	public void createObject() {
		option3 = new Option3(lowerCaseSearchString);
	}

	@Override
	protected Application provideApplication() {
		return new GuiceApplicationBuilder().build();
	}


	@Test
	public void testOption3Wrapper() {
				
		LinkedHashMap<String, CompletableFuture<String>> hashtagInfo = new LinkedHashMap<String, CompletableFuture<String>>();
		hashtagInfo.put("Canada",CompletableFuture.completedFuture("Concordia is in Canada"));
		CachedData.option3CachedData.put("Canada",CompletableFuture.completedFuture("Concordia is in Canada") );
		CompletableFuture<String> resultTest = option3.option3Wrapper(lowerCaseSearchString, "Canada", wsClientMock);
		assertEquals(resultTest, CompletableFuture.completedFuture("Concordia is in Canada"));

	}
	
}