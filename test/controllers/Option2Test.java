package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.Before;
import org.junit.Test;
import models.OptionDetailsCustomObject;
import models.Tweet;
import options.Option2;
import repository.CachedData;

public class Option2Test {

	final String searchString = SampleResponse.QUERY_STRING_OPTION2;
	final String lowerCaseSearchString = searchString.toLowerCase();
	Option2 option2;

	@Before
	public void createObject() {
		option2 = new Option2(lowerCaseSearchString);
	}

	@Test
	public void workerBackGroundTest() {
		List<Tweet> tweets = Arrays.asList(new Tweet("myUserName", "myTweetBody one two     two Three three ThreE", "myDisplayName"));
		CachedData.putOption0(lowerCaseSearchString,
				new OptionDetailsCustomObject(lowerCaseSearchString, CompletableFuture.completedFuture(tweets)));
		option2.workerBackGround();
		OptionDetailsCustomObject optionDetailsCustomObject = CachedData.getOption2(lowerCaseSearchString);
		LinkedHashMap<String, Long> linkedHashMap = optionDetailsCustomObject.getWordFrequency();
		assertEquals(linkedHashMap.entrySet().size(), 4);
		assertEquals((long) linkedHashMap.get("mytweetbody"), 1);
		assertEquals((long) linkedHashMap.get("one"), 1);
		assertEquals((long) linkedHashMap.get("two"), 2);
		assertEquals((long) linkedHashMap.get("three"), 3);

		List<Tweet> tweetsEmpty = Arrays.asList();
		CachedData.putOption0(lowerCaseSearchString,
				new OptionDetailsCustomObject(lowerCaseSearchString, CompletableFuture.completedFuture(tweetsEmpty)));
		option2.workerBackGround();
		OptionDetailsCustomObject optionDetailsCustomObjectEmpty = CachedData.getOption2(lowerCaseSearchString);
		LinkedHashMap<String, Long> linkedHashMapEmpty = optionDetailsCustomObjectEmpty.getWordFrequency();
		assertEquals(linkedHashMapEmpty.entrySet().size(), 0);
		assertTrue(linkedHashMapEmpty.isEmpty());
	}

	@Test
	public void option2WrapperTest() {
		LinkedHashMap<String, Long> wordFrequency = new LinkedHashMap<String, Long>();
		wordFrequency.put("concordia", 2L);
		wordFrequency.put("university", 1L);
		CompletableFuture<Void> option2Future = CompletableFuture.completedFuture(null);
		OptionDetailsCustomObject customObject = new OptionDetailsCustomObject(lowerCaseSearchString, option2Future, 2);
		customObject.setWordFrequency(wordFrequency);
		CachedData.putOption2(lowerCaseSearchString, customObject);
		LinkedHashMap<String, Long> linkedHashMap = option2.option2Wrapper().join();
		assertEquals(linkedHashMap.entrySet().size(), 2);
		assertEquals((long) linkedHashMap.get("concordia"), 2);
		assertEquals((long) linkedHashMap.get("university"), 1);

		LinkedHashMap<String, Long> wordFrequencyEmpty = new LinkedHashMap<String, Long>();
		CompletableFuture<Void> option2FutureEmpty = CompletableFuture.completedFuture(null);
		OptionDetailsCustomObject customEmptyObject = new OptionDetailsCustomObject(lowerCaseSearchString,
				option2FutureEmpty, 2);
		customEmptyObject.setWordFrequency(wordFrequencyEmpty);
		CachedData.putOption2(lowerCaseSearchString, customEmptyObject);
		LinkedHashMap<String, Long> linkedHashMapEmpty = option2.option2Wrapper().join();
		assertTrue(linkedHashMapEmpty.isEmpty());
	}

}