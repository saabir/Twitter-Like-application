package ng.poc.hiit.aos.presentation;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import ng.poc.hiit.aos.business.ejb.AccountEJB;
import ng.poc.hiit.aos.entity.Account;

@Named(value = "accountBean")
@RequestScoped
public class AccountBean extends AbstractViewBean {
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AccountEJB accountEJB;

	private Account account = new Account();
	private String tmppassword = "";

	/**
	 * @return the Accounts
	 */
	public List<Account> getAllUserAccount() {
		return accountEJB.findAllUserAccount();
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setBlogEntry(Account account) {
		this.account = account;
	}

	/**
	 * Save a new account
	 * 
	 * @return {@link String} as navigation
	 */
	public String saveAccount() {
		accountEJB.saveAccount(account);
		return "success";
	}

	public void delete(Account account) {
		accountEJB.deleteAccount(account);
	}

	public String getTmppassword() {
		return tmppassword;
	}

	public void setTmppassword(String tmppassword) {
		this.tmppassword = tmppassword;
	}

	public void doChangePassword(ActionEvent actionEvent) {
		if (isPasswordValid(account.getPassword(), tmppassword)) {
			Long id = getUserIdFromSessionAttribute();
			if (id != null) {
				Account account = accountEJB.getAccountById(id);
				account.setPassword(tmppassword);
				accountEJB.updateAccount(account);
				addMessage("Password was modified!", FacesMessage.SEVERITY_INFO);
			} else {
				addMessage("Password was NOT modified!",
						FacesMessage.SEVERITY_WARN);
			}
		}
	}

	/**
	 * creates and persists a new account
	 * 
	 * @param actionEvent
	 */
	public void doNewAccount(ActionEvent actionEvent) {

		if (account.getUsername().isEmpty() || account.getFirstName().isEmpty()
				|| account.getLastName().isEmpty()) {
		} else {
			if (accountEJB == null) {
			} else {
				if (!isPasswordValid(account.getPassword(), tmppassword)) {
				} else {
					accountEJB.saveAccount(account);
					navigateToLoginPage();
				}
			}
		}
	}

	/**
	 * Helper method to check if password is correct
	 * 
	 * @param paswd1
	 * @param passwd2
	 * @return {@link Boolean}
	 */
	private boolean isPasswordValid(String paswd1, String passwd2) {
		boolean isValid = (account.getPassword().isEmpty() || tmppassword
				.isEmpty());
		if (Boolean.FALSE.equals(isValid)) {
			if (tmppassword.equals(account.getPassword())) {
				return true;
			}
		}
		return isValid;
	}
}
