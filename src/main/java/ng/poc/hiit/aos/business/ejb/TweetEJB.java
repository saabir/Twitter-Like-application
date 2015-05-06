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

	public List<Tweet> findAllTweets() {
		final Query query = em
				.createQuery("SELECT b FROM Tweet b ORDER BY b.accountId DESC");
		List<Tweet> entries = query.getResultList();
		if (entries == null) {
			entries = new ArrayList<Tweet>();
		}
		return entries;
	}

	public List<Tweet> getAllTweetsForAccountId(Long accountId) {
		final Query query = em
				.createQuery("SELECT b FROM Tweet b  WHERE b.accountId=:accountId");
		query.setParameter("accountId", accountId);
		List<Tweet> entries = query.getResultList();

		return entries;
	}

	public List<Tweet> getAllTweets() {
		final Query query = em
				.createQuery("SELECT b FROM Tweet b");
		List<Tweet> entries = query.getResultList();
		return entries;
	}

	public void deleteTweet(Tweet tweet) {
		tweet = em.merge(tweet);
		em.remove(tweet);
	}
}
