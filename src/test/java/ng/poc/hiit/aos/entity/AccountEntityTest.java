package ng.poc.hiit.aos.entity;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import ng.poc.hiit.aos.business.ejb.AccountEJB;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This is an example of a fully automated account entity bean testing. Beans
 * are randomly generated and written to an in memory database. Tested is mainly
 * an entity bean with property access WITHOUT relation.
 * 
 * @author aos
 * 
 */
// Switched test implementations trying out ejb3unit
public class AccountEntityTest {
	private AccountEJB accountEJB;
	private static EJBContainer container;
	private static Context ctx;

	@BeforeClass
	public static void setUp() throws Exception {
		Map properties = new HashMap();
		properties.put(EJBContainer.MODULES, new File("target/classes"));
		container = EJBContainer.createEJBContainer(properties);
		ctx = container.getContext();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		// Shutdown the embeddable container
		container.close();
	}

	@Test
	public void createNewAccount() throws NamingException {
		accountEJB = (AccountEJB) ctx
				.lookup("java:global/classes/AccountEJB!ng.poc.hiit.aos.business.ejb.AccountEJB");
		// Do your tests
		assertNotNull(accountEJB);
		Account sample = new Account();
		sample.setFirstName("ha");
		sample.setUsername("twittersampleuser");

		accountEJB.saveAccount(sample);
		Account persisted = accountEJB
				.getAccountDetailsByName("twittersampleuser");
		assertNotNull(persisted);
		assertTrue(persisted.getUsername().equals(sample.getUsername()));
	}
}
