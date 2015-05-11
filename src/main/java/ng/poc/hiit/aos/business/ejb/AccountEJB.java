package ng.poc.hiit.aos.business.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import ng.poc.hiit.aos.entity.Account;

@Stateless
public class AccountEJB {
	@PersistenceContext(unitName = "defaultPersistenceUnit")
	private EntityManager em;

	// default
	public AccountEJB() {

	}

	/**
	 * Persists a new account
	 * 
	 * @param account
	 * @return persisted {@link Account} object
	 */
	public Account saveAccount(Account account) {
		// debug error in testsuit
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Account>> constraintViolations = validator
				.validate(account);

		if (constraintViolations.size() > 0) {
			Iterator<ConstraintViolation<Account>> iterator = constraintViolations
					.iterator();
			while (iterator.hasNext()) {
				ConstraintViolation<Account> cv = iterator.next();
				System.err.println(cv.getRootBeanClass().getName() + "."
						+ cv.getPropertyPath() + " " + cv.getMessage());
			}
		} else {
			em.persist(account);
		}

		return account;
	}

	/**
	 * @return returns a {@link List} of all accounts
	 */
	public List<Account> findAllUserAccount() {
		final Query query = em
				.createQuery("SELECT b FROM Account b ORDER BY b.username DESC");
		List<Account> entries = query.getResultList();
		if (entries == null) {
			entries = new ArrayList<Account>();
		}
		return entries;
	}

	/**
	 * Retrieve account details for username
	 * 
	 * @param name
	 * @return {@link Account}
	 */
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

	/**
	 * delete the supplied user account
	 * 
	 * @param account
	 */
	public void deleteAccount(Account account) {
		account = em.merge(account);
		em.remove(account);
	}

	/**
	 * modify user account
	 * 
	 * @param account
	 */
	public void updateAccount(Account account) {
		account = em.merge(account);
		em.persist(account);
	}
}
