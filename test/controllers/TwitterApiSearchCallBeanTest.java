
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

import static play.test.Helpers.contentAsString;

public class TwitterApiSearchCallBeanTest {
	final String searchString = SampleResponse.QUERY_STRING_OPTION0;
	final String lowerCaseSearchString = searchString.toLowerCase();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void option0Test1() throws InterruptedException, ExecutionException {
		
		WSResponse wsResponseMock1 = Mockito.mock(WSResponse.class);
		WSClient wsClientMock1 = Mockito.mock(WSClient.class);
		
		TwitterApiSearchCallBean in = new TwitterApiSearchCallBean(lowerCaseSearchString);
		TwitterApiSearchCallBean twitterApiSearchCallBean = Mockito.spy(in);
		doReturn(wsResponseMock1).when(twitterApiSearchCallBean).getTweets(wsClientMock1);
		doReturn(SampleResponse.returnJsonNodeResponse(1)).when(wsResponseMock1).asJson();
		String returnedValueFromVirtualCall = twitterApiSearchCallBean.tweetSeachAPICallWrapper(wsClientMock1).join();
		assertTrue(returnedValueFromVirtualCall.toLowerCase().contains("concordia"));
		String returnedValueFromCache = twitterApiSearchCallBean.tweetSeachAPICallWrapper(wsClientMock1).join();
		assertTrue(returnedValueFromCache.toLowerCase().contains("concordia"));
	}

//	@Test
//	public void option0Test2() throws InterruptedException, ExecutionException {
//		
//		WSResponse wsResponseMock2 = Mockito.mock(WSResponse.class);
//		WSClient wsClientMock2 = Mockito.mock(WSClient.class);
//		
//		TwitterApiSearchCallBean in = new TwitterApiSearchCallBean(lowerCaseSearchString);
//		TwitterApiSearchCallBean twitterApiSearchCallBean = Mockito.spy(in);
//		doReturn(wsResponseMock2).when(twitterApiSearchCallBean).getTweets(wsClientMock2);
//		doReturn(SampleResponse.returnJsonNodeResponse(0)).when(wsResponseMock2).asJson();
//		String returnedValueFromVirtualCall = twitterApiSearchCallBean.tweetSeachAPICallWrapper(wsClientMock2).join();
//		assertTrue(returnedValueFromVirtualCall.toLowerCase().contains("\"data\":[]"));
//
//	}

}