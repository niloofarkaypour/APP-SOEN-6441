package controllers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Test;

import models.OptionDetailsCustomObject;
import models.Tweet;
import options.Option4;
import repository.CachedData;

public class Option4Test {

	final String searchString = SampleResponse.QUERY_STRING_OPTION4;
	final String lowerCaseSearchString = searchString.toLowerCase();
	Option4 option4;

	@Before
	public void createObject() {
		option4 = new Option4(lowerCaseSearchString);
	}
	
	
	@Test
	public void option4GenerateWordSentimentTest() {
		List<Tweet> tweets = Arrays.asList(new Tweet("myUserName", "happy happy happy", "myDisplayName"));
		CachedData.putOption0(lowerCaseSearchString,
				new OptionDetailsCustomObject(lowerCaseSearchString, CompletableFuture.completedFuture(tweets)));
		
		option4.generateWordSentiment();
		OptionDetailsCustomObject optionDetailsCustomObject = CachedData.getOption4(lowerCaseSearchString);
		String emoji = optionDetailsCustomObject.getMode();
		assertEquals(emoji, "😀");
		
		List<Tweet> tweetsSad = Arrays.asList(new Tweet("myUserName", "sad sad sad", "myDisplayName"));
		CachedData.putOption0(lowerCaseSearchString,
				new OptionDetailsCustomObject(lowerCaseSearchString, CompletableFuture.completedFuture(tweetsSad)));
		
		option4.generateWordSentiment();
		OptionDetailsCustomObject optionDetailsCustomObjectSad = CachedData.getOption4(lowerCaseSearchString);
		String emojiSad = optionDetailsCustomObjectSad.getMode();
		assertEquals(emojiSad, "🙁");
		
		List<Tweet> tweetsNeutral = Arrays.asList(new Tweet("myUserName", "hello hello hello", "myDisplayName"));
		CachedData.putOption0(lowerCaseSearchString,
				new OptionDetailsCustomObject(lowerCaseSearchString, CompletableFuture.completedFuture(tweetsNeutral)));
		
		option4.generateWordSentiment();
		OptionDetailsCustomObject optionDetailsCustomObjectNeutral = CachedData.getOption4(lowerCaseSearchString);
		String emojiNeutral = optionDetailsCustomObjectNeutral.getMode();
		assertEquals(emojiNeutral, "😐");
		
		
		List<Tweet> tweetsEmpty = Arrays.asList();
		CachedData.putOption0(lowerCaseSearchString,
				new OptionDetailsCustomObject(lowerCaseSearchString, CompletableFuture.completedFuture(tweetsEmpty)));
		option4.generateWordSentiment();
		OptionDetailsCustomObject optionDetailsCustomObjectEmpty = CachedData.getOption4(lowerCaseSearchString);
		String emojiEmpty = optionDetailsCustomObjectEmpty.getMode();
		assertEquals(emojiEmpty, "");
		
		
	}

}
