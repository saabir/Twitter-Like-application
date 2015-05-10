package ng.poc.hiit.aos.entity;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import ng.poc.hiit.aos.business.ejb.AccountEJB;

import org.junit.Before;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		container = createContainer();
	}

	protected static EJBContainer createContainer() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(EJBContainer.MODULES, new File("target/classes"));
		properties.put(
				"org.glassfish.ejb.embedded.glassfish.installation.root",
				"/opt/glassfish");
		return EJBContainer.createEJBContainer(properties);
	}

	@Before
	public void setUp() throws Exception {
		// Retrieve a reference to the session bean using a portable global JNDI
		// name
		accountEJB = (AccountEJB) container
				.getContext()
				.lookup("java:global/classes/AccountEJB");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		// Shutdown the embeddable container
		container.close();
	}

	@Test
	public void createNewAccount() throws NamingException {
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
