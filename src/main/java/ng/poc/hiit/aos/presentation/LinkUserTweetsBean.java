package ng.poc.hiit.aos.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ng.poc.hiit.aos.business.ejb.TweetEJB;
import ng.poc.hiit.aos.entity.Tweet;

@Named(value = "linkUserTweetsBean")
@RequestScoped
public class LinkUserTweetsBean {
	@Inject
	private TweetEJB tweetEJB;
	private Tweet tweet = new Tweet();
	private List<Tweet> tweets = new ArrayList<Tweet>();

	/**
	 * @return all tweets in system
	 */
	public List<Tweet> getAllTweets() {
		return tweetEJB.findAllTweets();
	}

	/**
	 * return all tweets for particular user
	 * 
	 * @param userid
	 * @return list of tweets
	 */
	public String allTweetsForUserId() {
		Long id = Long.valueOf(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("userid"));
		tweets = tweetEJB.getAllTweetsForAccountId(id);
		return "listgenerated";
	}

	/**
	 * @return the tweet
	 */
	public Tweet getTweet() {
		return tweet;
	}

	/**
	 * @param tweet
	 *            the tweet to set
	 */
	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	public String saveTweet() {
		tweetEJB.saveTweet(tweet);
		return "success";
	}

	public void delete(Tweet tweet) {
		tweetEJB.deleteTweet(tweet);
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

}
