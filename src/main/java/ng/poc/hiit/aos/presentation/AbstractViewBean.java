package ng.poc.hiit.aos.presentation;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractViewBean implements Serializable {

	/**
	 * default
	 */
	private static final long serialVersionUID = -480066712148273502L;

	/**
	 * Helper method to return faces instance
	 * 
	 * @return
	 */
	protected FacesContext getCurrentFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Helper method to add messages to faces context
	 * 
	 * @param summary
	 * @param info
	 */
	public void addMessage(String summary, Severity info) {
		FacesMessage message = new FacesMessage(info, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Helper method to navigate to home page after correct login
	 */
	protected void navigateToLoginOk() {
		HttpServletRequest request = (HttpServletRequest) getCurrentFacesContext()
				.getExternalContext().getRequest();
		String navigateString = "/user/login-ok.xhtml";
		try {
			getCurrentFacesContext().getExternalContext().redirect(
					request.getContextPath() + navigateString);
		} catch (IOException ex) {
			getCurrentFacesContext().addMessage(null,
					new FacesMessage("Error!", "Exception occured"));
		}
	}

	/**
	 * Helper method to return current faces request
	 * 
	 * @return {@link HttpServletRequest}ß
	 */
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	/**
	 * Helper method to navigate to loginpage
	 */
	protected void navigateToLoginPage() {
		HttpServletRequest request = (HttpServletRequest) getCurrentFacesContext()
				.getExternalContext().getRequest();
		String navigateString = "/login.xhtml";
		try {
			getCurrentFacesContext().getExternalContext().redirect(
					request.getContextPath() + navigateString);
		} catch (IOException ex) {
			getCurrentFacesContext().addMessage(null,
					new FacesMessage("Error!", "Exception occured"));
		}
	}

	/**
	 * Removes the logged-in session attribute
	 */
	protected void removeSessionAttribute() {
		HttpSession session = (HttpSession) getCurrentFacesContext()
				.getExternalContext().getSession(true);
		session.removeAttribute("accountId");
	}

	/**
	 * Return the logged-in session attribute
	 */
	protected Long getUserIdFromSessionAttribute() {
		HttpSession session = (HttpSession) getCurrentFacesContext()
				.getExternalContext().getSession(true);
		return (Long) session.getAttribute("accountId");
	}
}
