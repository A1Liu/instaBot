package bot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import instagram.OtherPage;
import instagram.ProfilePage;

import static instagram.Const.INSTAGRAM;

/**
 * Handles basic interactions with a profile:
 * 
 * Going to the profile of a specific user
 * getting information about the bot user
 * 
 * TODO:
 * Getting posts on a profile
 * blocking user
 * 
 * @author aliu
 *
 */
class ProfileBot extends LikerBot {

	private String viewing;
	private int following;
	private int followers;
	
	public ProfileBot(WebDriver webDriver, String username, String password) {super(webDriver, username, password);init();}
	
	public ProfileBot(String username, String password) {super(username, password);init();}
	
	private void init() {
		viewing = null;
		setFollowing(-1);
		setFollowers(-1);
	}
	
	public void gotoHomePage() {
		this.getHomePage();
	}
	
	/**
	 * Go to the page of the specified user and get the page object of that page
	 * @param username username of the user we want to look at
	 * @return the page object of that user
	 */
	OtherPage getPage(String username) {
		if (!getWebDriver().getCurrentUrl().equals(INSTAGRAM + username + "/"))
			getWebDriver().get(INSTAGRAM + username);
		OtherPage page = new OtherPage(waitForElement(By.xpath(OtherPage.XPATH)));
		viewing = username;
		followers = page.getFollowerCount();
		following = page.getFollowingCount();
		return page;
	}
	
	/**
	 * Go to the bot user's home page and get the page object of that page
	 * @return the page object of the bot user
	 */
	ProfilePage getHomePage() {
		if (!getWebDriver().getCurrentUrl().equals(INSTAGRAM + getUsername() + "/"))
			getWebDriver().get(INSTAGRAM + getUsername());
		ProfilePage page = new ProfilePage(waitForElement(By.xpath(ProfilePage.XPATH)));
		viewing = getUsername();
		followers = page.getFollowerCount();
		following = page.getFollowingCount();
		return page;
	}
	
	/**
	 * get User information about this user
	 */
	public String getUserInfo() {
		if (following != -1)
			return super.getUserInfo() + String.format("[followers=%d,following=%d]", followers, following);
		else return super.getUserInfo();
	}
	
	/**
	 * go to the feed
	 */
	public WebElement gotoFeed() {
		viewing = null;
		return super.gotoFeed();
		
	}
	
	/**
	 * What username are we viewing?
	 * @return viewing
	 */
	public String getViewing() {
		return viewing;
	}

	void setViewing(String viewing) {
		this.viewing = viewing;
	}

	/**
	 * Gets the amount of users this bot user is following
	 * @return following count
	 */
	public int getFollowingCount() {
		return following;
	}

	
	void setFollowing(int following) {
		this.following = following;
	}

	/**
	 * Gets the amount of users that follow this bot user
	 * @return follower count
	 */
	public int getFollowerCount() {
		return followers;
	}

	void setFollowers(int followers) {
		this.followers = followers;
	}
	
	
}
