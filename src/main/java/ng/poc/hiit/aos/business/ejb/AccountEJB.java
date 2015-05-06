package ng.poc.hiit.aos.business.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ng.poc.hiit.aos.entity.Account;

@Stateless
public class AccountEJB {
	@PersistenceContext(unitName = "defaultPersistenceUnit")
	private EntityManager em;

	// default
	public AccountEJB() {

	}

	public Account saveAccount(Account account) {
		em.persist(account);
		return account;
	}

	public List<Account> findAllUserAccount() {
		final Query query = em
				.createQuery("SELECT b FROM Account b ORDER BY b.username DESC");
		List<Account> entries = query.getResultList();
		if (entries == null) {
			entries = new ArrayList<Account>();
		}
		return entries;
	}

	public Account getAccountDetailsByName(String name) {
		final Query query = em
				.createQuery("SELECT b FROM Account b  WHERE b.username=:loginname");
		query.setParameter("loginname", name);
		List<Account> entries = query.getResultList();
		if (entries.size() == 1) {
			return (Account) query.getSingleResult();
		}
		return null;
	}

	/**
	 * Find account by id
	 * 
	 * @param id
	 * @return {@link Account}
	 */
	public Account getAccountById(Long id) {
		final Query query = em
				.createQuery("SELECT b FROM Account b  WHERE b.id=:accountid");
		query.setParameter("accountid", id);
		List<Account> entries = query.getResultList();
		if (entries.size() == 1) {
			return (Account) query.getSingleResult();
		}
		return null;
	}

	public void deleteAccount(Account account) {
		account = em.merge(account);
		em.remove(account);
	}

	public void updateAccount(Account account) {
		account = em.merge(account);
		em.persist(account);
	}
}
