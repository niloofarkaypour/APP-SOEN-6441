package controllers;

import javax.inject.Inject;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import core.TwitterApiSearchCallBean;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.twirl.api.Content;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import static play.test.Helpers.contentAsString;

import java.util.LinkedHashMap;

public class viewTest {
	// final String searchString = SampleResponse.QUERY_STRING;
	@Mock
	WSResponse wsResponseMock;
	@Mock
	WSClient wsClientMock;
	@Spy
	// TwitterApiSearchCallBean twitterApiSearchCallBeanMock = new
	// TwitterApiSearchCallBean(searchString);

	@Inject
	WSClient wsClientInject;
	@Inject
	HomeController homeControllerInject;

	@Test
	public void renderTemplateIndex() {
		Content html = views.html.index.render();
		assertEquals("text/html", html.contentType());
		assertTrue(contentAsString(html).toLowerCase().contains("tweeterlytics"));

	}

	@Test
	public void renderTemplatetweetAnalytics() {

		LinkedHashMap<String, Long> linkedHashMap1 = new LinkedHashMap<String, Long>();// Empty map
		LinkedHashMap<String, Long> linkedHashMap2 = new LinkedHashMap<String, Long>();// Filled map
		linkedHashMap2.put("concordia", 2L);

		Content html1 = views.html.tweetAnalytics.render(linkedHashMap1, "Concordia");
		assertEquals("text/html", html1.contentType());

		Content html2 = views.html.tweetAnalytics.render(linkedHashMap2, "Concordia");
		assertEquals("text/html", html2.contentType());
		assertTrue(contentAsString(html1).toLowerCase().contains("search"));
	}

	@Test
	public void renderTemplateuserInformation() {

		Content html = views.html.userInformation.render("userName");
		assertEquals("text/html", html.contentType());
		assertTrue(contentAsString(html).toLowerCase().contains("and"));
	}
	
	@Test
	public void renderTemplateTweetHashTags() {

		Content html = views.html.tweetHashTags.render("hashTag");
		assertEquals("text/html", html.contentType());
		assertTrue(contentAsString(html).toLowerCase().contains("hashtag"));
	}

}
