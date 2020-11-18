package models;

/**
 * @author Yasaman
 * required model for Implementation of option 4 of individual part
 */
public class TweetParameters {
	
	public TweetParameters(String tweetMessage) {
		this.Text = tweetMessage;
	}
	
	public String Text;
	public String[] AllWords;
	public Long TotalWordsQty;
	public Long HappyWordsQty;
	public float HappyWordsRatio;
	public Long SadWordsQty;
	public float SadWordsRatio;
	public TweetModes TweetMode; 
	
	@Override
	public String toString() {
		return "Message: " + this.Text
				+ "\nHappy words Qty.: " + this.HappyWordsQty
				+ "\nSad words Qty.: " + this.SadWordsQty
				+ "\nMode: " + this.TweetMode;
	}

	public TweetModes getTweetMode() {
		return TweetMode;
	}

	
	
}