package ng.poc.hiit.aos.presentation;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import ng.poc.hiit.aos.business.ejb.AccountEJB;
import ng.poc.hiit.aos.business.ejb.TweetEJB;
import ng.poc.hiit.aos.entity.Account;
import ng.poc.hiit.aos.entity.Tweet;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "menuBean")
@RequestScoped
public class MenuBean extends AbstractViewBean {
	/**
	 * default
	 */
	private static final long serialVersionUID = -4400450923129506224L;

	@EJB
	private TweetEJB tweetEJB;

	@EJB
	private AccountEJB accountEJB;

	private String newTweetMessage = "";
	// login user tweet list
	private List<Tweet> tweetList;
	// tweet list of all users including loggedin user
	private List<Tweet> tweetListAll;

	private UploadedFile file;
	private Tweet selectedTweet;

	@PostConstruct
	public void init() {
		// set loggedin user tweeter list
		HttpSession session = (HttpSession) getCurrentFacesContext()
				.getExternalContext().getSession(true);
		Object accountid = session.getAttribute("accountId");
		if (accountid != null) {
			tweetList = tweetEJB.getAllTweetsForAccountId(Long
					.valueOf(accountid.toString()));
		}
		// set twitter list for all users
		tweetListAll = tweetEJB.getAllTweets();
	}

	public TweetEJB getTweetEJB() {
		return tweetEJB;
	}

	public void setTweetEJB(TweetEJB tweetEJB) {
		this.tweetEJB = tweetEJB;
	}

	public List<Tweet> getTweetListAll() {
		return tweetListAll;
	}

	public String getNewTweetMessage() {
		return newTweetMessage;
	}

	public List<Tweet> getTweetList() {
		return tweetList;
	}

	public String deleteAction(Tweet tweet) {
		tweetEJB.deleteTweet(tweet);
		tweetList.remove(tweet);
		updateNumeberOfTweets(tweet.getAccountId(), 1);
		addMessage("Tweet entitled:" + tweet.getMessage() + "was deleted.",
				FacesMessage.SEVERITY_INFO);
		return null;
	}

	public void onRowSelect(RowEditEvent event) {
		String text = "Selected tweet: "
				+ ((Tweet) event.getObject()).getMessage();
		addMessage(text, FacesMessage.SEVERITY_INFO);
	}

	public Tweet getSelectedTweet() {
		return selectedTweet;
	}

	public void onRowUnselect(UnselectEvent event) {

	}

	public void setSelectedTweet(Tweet selectedTweet) {
		this.selectedTweet = selectedTweet;
	}

	public void setNewTweetMessage(String newTweetMessage) {
		this.newTweetMessage = newTweetMessage;
	}

	public void save() {
		addMessage("Data saved", FacesMessage.SEVERITY_INFO);
	}

	public void update() {
		addMessage("Data updated", FacesMessage.SEVERITY_INFO);
	}

	public void delete() {
		addMessage("Data deleted", FacesMessage.SEVERITY_INFO);
	}

	/**
	 * 
	 * @param actionEvent
	 */
	public void postTweet(ActionEvent actionEvent) {
		// Save tweet
		if (newTweetMessage != null) {
			Long accountid = Long.valueOf(FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("accountparam"));
			Tweet t = new Tweet();
			t.setMessage(newTweetMessage);
			t.setAccountId(Long.valueOf(accountid));
			tweetEJB.saveTweet(t);

			// update # of tweets details
			updateNumeberOfTweets(accountid, 0);
			addMessage("Your tweet has being posted!",
					FacesMessage.SEVERITY_INFO);
		} else {
			addMessage("Tweet can not be empty!", FacesMessage.SEVERITY_ERROR);
		}
		this.newTweetMessage = "";
	}

	/**
	 * Update number of tweets
	 * 
	 * @param accountId
	 * @param plusIsZeroMinusIsOne
	 */
	private void updateNumeberOfTweets(Long accountId, int plusIsZeroMinusIsOne) {
		Account tmpaccount = accountEJB.getAccountById(accountId);
		switch (plusIsZeroMinusIsOne) {
		case 0:
			tmpaccount.setTweetsCount(tmpaccount.getTweetsCount() + 1);
			break;
		default:
		case 1:
			tmpaccount.setTweetsCount(tmpaccount.getTweetsCount() - 1);
			break;
		}
		accountEJB.updateAccount(tmpaccount);
	}

	/**
	 * Logout from the application
	 * 
	 * @param actionEvent
	 */
	public void logout(ActionEvent actionEvent) {

		String navigateString = "/login.xhtml";
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(getRequest().getContextPath() + navigateString);
			removeSessionAttribute();
		} catch (IOException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Error!", "Exception occured"));
		}
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void handleFileUpload() throws IOException {
		String name = getFile().getFileName();
		byte[] fileContents = IOUtils.toByteArray(getFile().getInputstream());
		addMessage("file name is  " + name, FacesMessage.SEVERITY_INFO);
	}
}
