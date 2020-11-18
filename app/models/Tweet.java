package models;


/**
 * @author Yasaman
 * Twitter model
 *
 */
//TODO it is for test, it should be changed
public class Tweet {
	public String username;
	public String tweetbody;
	public String displayName;
//	public String  hashtag;
	
	/**
	 * @param userName
	 * @param text
	 */
	public Tweet(String username, String tweetbody, String displayName) {
		super();
		this.username = username;
		this.tweetbody = tweetbody;
		this.displayName = displayName;
	}	

	public String getDisplayName() {
		return displayName;
	}

	public String getUsername() {
		return username;
	}

	public String getTweetbody() {
		return tweetbody;
	}
	
}
