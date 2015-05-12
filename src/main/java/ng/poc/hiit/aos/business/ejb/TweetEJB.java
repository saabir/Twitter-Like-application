package ng.poc.hiit.aos.business.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ng.poc.hiit.aos.entity.Tweet;

@Stateless
public class TweetEJB {
	@PersistenceContext(unitName = "defaultPersistenceUnit")
	private EntityManager em;

	public Tweet saveTweet(Tweet tweet) {
		em.persist(tweet);
		return tweet;
	}

	/**
	 * @return a {@link List} of all user {@link Tweet}
	 */
	public List<Tweet> findAllTweets() {
		final Query query = em
				.createQuery("SELECT b FROM Tweet b ORDER BY b.accountId DESC");
		List<Tweet> entries = query.getResultList();
		if (entries == null) {
			entries = new ArrayList<Tweet>();
		}
		return entries;
	}

	/**
	 * Retrieve all {@link Tweet} for user id
	 * 
	 * @param accountId
	 * @return {@link List} of {@link Tweet}
	 */
	public List<Tweet> getAllTweetsForAccountId(Long accountId) {
		final Query query = em
				.createQuery("SELECT b FROM Tweet b  WHERE b.account.id=:accountId");
		query.setParameter("accountId", accountId);
		List<Tweet> entries = query.getResultList();

		return entries;
	}

	/**
	 * Retrieve all user {@link Tweet}
	 * 
	 * @return {@link List}
	 */
	public List<Tweet> getAllTweets() {
		final Query query = em.createQuery("SELECT b FROM Tweet b");
		List<Tweet> entries = query.getResultList();
		return entries;
	}

	/**
	 * delete {@link Tweet}
	 * 
	 * @param tweet
	 */
	public void deleteTweet(Tweet tweet) {
		tweet = em.merge(tweet);
		em.remove(tweet);
	}
}
