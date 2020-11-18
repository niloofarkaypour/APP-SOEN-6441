package options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.*;
import repository.CachedData;

/**
 * @author Yasaman Implementation of option 4 of individual part
 */
public class Option4 {

	private String searchString = "";
	private String lowerCaseSearchString = "";
//	private List<String> positiveWords = new ArrayList<>();
//	private List<String> negativeWords = new ArrayList<>();

	public Option4(String searchString) {
		this.searchString = searchString;
		this.lowerCaseSearchString = this.searchString.toLowerCase();
	}

	/**
	 * @author Yasaman
	 * 
	 *         Get the all the tweets from the cash and and define the mode of each
	 *         tweet first and then calculate the overall mode for all tweets and
	 *         cash it based on search key
	 *
	 */
	public void generateWordSentiment() {

		List<Tweet> tweetsList = new ArrayList<Tweet>();
		
		String emoji = "";

		tweetsList = CachedData.getOption0(lowerCaseSearchString).getOption0ResponseFuture().join();		

		long totalHappyTweets = 0;
		long totalSadTweets = 0;
		long totalNeutralTweets = 0;

		if (tweetsList.size() != 0) {

			List<TweetParameters> tweetsParamsList = tweetsList.stream().map(tweet -> tweet.getTweetbody())
					.map(x -> new TweetParameters(x)).map(t -> {
						t.AllWords = t.Text.split("\\s+");
						t.TotalWordsQty = (long) t.AllWords.length;
						return t;
					}).map(t -> {
						t.HappyWordsQty = Arrays.stream(t.AllWords).filter(w -> ProcessedTweet.HappyWords.contains(w))
								.count();
						t.SadWordsQty = Arrays.stream(t.AllWords).filter(w -> ProcessedTweet.SadWords.contains(w))
								.count();
						return t;
					}).map(t -> {
						Long sum = t.HappyWordsQty + t.SadWordsQty;

						if (sum != 0) {
							t.HappyWordsRatio = (float) t.HappyWordsQty / sum;
							t.SadWordsRatio = (float) t.SadWordsQty / sum;
						}
						return t;
					}).map(t -> {
						t.TweetMode = t.HappyWordsRatio > 0.7 ? TweetModes.Happy
								: t.SadWordsRatio > 0.7 ? TweetModes.Sad : TweetModes.Neutral;
						return t;
					}).collect(Collectors.toList());

			totalHappyTweets = tweetsParamsList.stream().filter(t -> t.TweetMode == TweetModes.Happy).count();
			totalSadTweets = tweetsParamsList.stream().filter(t -> t.TweetMode == TweetModes.Sad).count();
			totalNeutralTweets = tweetsParamsList.stream().filter(t -> t.TweetMode == TweetModes.Neutral).count();
			final int sizeArr = tweetsParamsList.size();

//        System.out.println("total Happy:----->" + totalHappyTweets);
//        System.out.println("total sad:----->" + totalSadTweets);
//        System.out.println("total Neutral:----->" + totalNeutralTweets);
//        System.out.println("total Size:----->" + sizeArr);

			TweetModes MapOfTweetModes = tweetsParamsList.stream()
					.collect(Collectors.groupingBy(TweetParameters::getTweetMode, Collectors.counting())).entrySet()
					.stream().filter(e -> ((float) e.getValue()) / sizeArr >= (float) 0.7).map(Map.Entry::getKey)
					.findFirst().orElse(TweetModes.Neutral);

			if (MapOfTweetModes == TweetModes.Happy)
				emoji = "😀";
			else if (MapOfTweetModes == TweetModes.Sad)
				emoji = "🙁";
			else
				emoji = "😐";
		}

		else
			emoji = "";

//        System.out.println("-------TWEETS-------");
//        for(TweetParameters t : tweetsParamsList)
//        	System.out.println(t.toString() + "\n--------------");
//        System.out.println("-------END-------");

		if (CachedData.checkOption4(lowerCaseSearchString)) {
			OptionDetailsCustomObject optionDetailsCustomObject = CachedData.getOption4(lowerCaseSearchString);
			optionDetailsCustomObject.setMode(emoji);
		} else {
			OptionDetailsCustomObject optionDetailsCustomObject = new OptionDetailsCustomObject(lowerCaseSearchString,
					emoji);
			CachedData.putOption4(lowerCaseSearchString, optionDetailsCustomObject);
		}

	}

}