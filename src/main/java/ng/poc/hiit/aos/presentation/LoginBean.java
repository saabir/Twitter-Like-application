package ng.poc.hiit.aos.presentation;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import ng.poc.hiit.aos.business.ejb.AccountEJB;
import ng.poc.hiit.aos.entity.Account;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean extends AbstractViewBean {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	// Bean Properties
	@EJB
	private AccountEJB accountEJB;
	private Long userId;
	private String userName;
	private String userLogin;
	private String userPassword;
	private int numberOfTweets;
	private int followersCount;
	private int followingCount;
	private String names;

	public AccountEJB getUser() {
		return accountEJB;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public int getNumberOfTweets() {
		return numberOfTweets;
	}

	public String getNames() {
		return names;
	}

	public void setNumberOfTweets(int numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * Listen for button clicks on the #{loginController.login} action,
	 * validates the username and password entered by the user and navigates to
	 * the appropriate page.
	 *
	 * @param actionEvent
	 */
	public void doLogin(ActionEvent actionEvent) {

		if (userLogin.isEmpty() || userPassword.isEmpty()) {
		} else {
			if (accountEJB == null) {
			} else {
				Account account = accountEJB.getAccountDetailsByName(userLogin);
				if (account != null) {
					String tmppwd = account.getPassword();
					if (userPassword.equals(tmppwd)) {
						userId = account.getId();
						userName = account.getFirstName();
						names = account.getFirstName() + " "
								+ account.getLastName();
						numberOfTweets = account.getTweetsCount();
						followersCount = account.getFollowerCount();
						followingCount = account.getFollowingCount();
						HttpSession session = (HttpSession) getCurrentFacesContext()
								.getExternalContext().getSession(true);
						session.setAttribute("accountId", userId);
						navigateToLoginOk();
					}
				}
			}
		}
	}
}
