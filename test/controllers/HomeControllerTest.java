package controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import core.TwitterApiSearchCallBean;
import options.Option1;
import options.Option3;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import play.mvc.*;
import play.libs.ws.WSClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

import java.util.concurrent.CompletionStage;

import static play.test.Helpers.contentAsString;

public class HomeControllerTest extends WithApplication {

	@Mock
	TwitterApiSearchCallBean twitterApiSearchCallBean;
	
	final String searchString = SampleResponse.QUERY_STRING_OPTION0;
	final String lowerCaseSearchString = searchString.toLowerCase();

	@Mock
	WSResponse wsResponseMock;
	@Mock
	WSClient wsClientMock;

	@Spy
	HomeController homeController = new HomeController(wsClientMock);

	@Override
	protected Application provideApplication() {
		return new GuiceApplicationBuilder().build();
	}

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testIndex() {
		Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/");
		Result result = route(app, request);
		assertEquals(OK, result.status());
		assertEquals("text/html", result.contentType().get());
		assertEquals("utf-8", result.charset().get());
	}

//	@Test// This will hit live API
//	public void testreturnSearchResponse() {
//		Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/returnSearchResponse/concordia");
//		Result result = route(app, request);
//		
//		assertEquals(OK, result.status());
//		assertEquals(true, contentAsString(result).toLowerCase().contains("concordia"));
//		assertEquals("text/plain", result.contentType().get());
//		assertEquals("utf-8", result.charset().get());
//	}

	
/*
//@Test //Might not be a right approach
	public void testreturnSearchResponse() {

//		return TwitterApiSearchCallBean.factoryTwitterApiSearchCallBean().modifyTheAttributes(searchString)
//				.tweetSeachAPICallWrapper(wsClient).thenApply(stringResponse -> ok(stringResponse));
		String TempString = "concordia";
//		TwitterApiSearchCallBean in = TwitterApiSearchCallBean.factoryTwitterApiSearchCallBean();
//		TwitterApiSearchCallBean twitterApiSearchCallBean22 = Mockito.spy(in);

		System.out.println("Option2 111111");
		

//		try(MockedStatic<TwitterApiSearchCallBean> twitterApiSearchCallBeanMock = Mockito.mockStatic(TwitterApiSearchCallBean.class)){
//			
//			twitterApiSearchCallBeanMock.when(TwitterApiSearchCallBean::factoryTwitterApiSearchCallBean).thenReturn(value);
//		}

		//doReturn(twitterApiSearchCallBean22).when(twitterApiSearchCallBean22).factoryTwitterApiSearchCallBean();

		//doReturn(wsResponseMock).when(twitterApiSearchCallBean22).getTweets(wsClientMock);
		doReturn(SampleResponse.returnJsonNodeResponse(1)).when(wsResponseMock).asJson();

		Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/returnSearchResponse/concordia");
		
		try (MockedStatic<Helpers> helperMock = Mockito
				.mockStatic(Helpers.class)) {

			helperMock.when(() -> Helpers.route(app,request))
					.thenReturn(Results.ok("donald ++++++++++++++++++++++++++++++"));
		}

		//Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/returnSearchResponse/concordia");
		//Result result = Helpers.route(app, request);
		Result result = Helpers.route(app, request);
		System.out.println("THIS IS THE CONTENT =========="+contentAsString(result).toLowerCase());
		assertEquals(OK, result.status());
//		assertTrue(contentAsString(result).toLowerCase().contains("donald"));
//		//assertTrue(contentAsString(result).toLowerCase().contains("donald"));
//		assertEquals("text/plain", result.contentType().get());
//		assertEquals("utf-8", result.charset().get());
	}
*/	
	

	@Test
	public void testtweetAnalytics() {
		TwitterApiSearchCallBean in = new TwitterApiSearchCallBean(lowerCaseSearchString);
		TwitterApiSearchCallBean twitterApiSearchCallBean = Mockito.spy(in);
		doReturn(wsResponseMock).when(twitterApiSearchCallBean).getTweets(wsClientMock);
		doReturn(SampleResponse.returnJsonNodeResponse(1)).when(wsResponseMock).asJson();

		Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
				.uri("/tweetAnalytics/concordia%20university");
		Result result = route(app, request);
		assertEquals(OK, result.status());
		assertTrue(contentAsString(result).toLowerCase().contains("concordia"));
		assertTrue(contentAsString(result).toLowerCase().contains("university"));
		assertEquals("text/html", result.contentType().get());
		assertEquals("utf-8", result.charset().get());
	}
	
//	@Test
//	public void testuserInformation() {
//		Option1 in = new Option1(lowerCaseSearchString);
//		Option1 option1 = Mockito.spy(in);
//		doReturn("asdasfaf").when(option1).userProfileResult("concordia", "aida",wsClientMock);
//
//		Http.RequestBuilder request = new Http.RequestBuilder().method(GET)
//				.uri("/userInformation/concordia/aida");
//		Result result = route(app, request);
//		assertEquals(OK, result.status());
//		assertTrue(contentAsString(result).toLowerCase().contains("concordia"));
//		assertEquals("text/html", result.contentType().get());
//		assertEquals("utf-8", result.charset().get());
//	}
	
	
	


}
