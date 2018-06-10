package instagram;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Profile Object, meant to represent all the data that can be gotten from viewing a profile, public or private.
 * 
 * TODO: Getting posts
 * TODO: blocking profiles
 * 
 * @author aliu
 *
 */
public class ProfilePage extends User {//Concrete class

	private String bio;
	private int postCount;
	private int followerCount;
	private int followingCount;
	private WebElement followerList;
	private WebElement followingList;
	private WebElement button;
	private boolean isPublic;
	private boolean openList;
	private WebElement postContainer;
	
	public final static String XPATH = "//div[@class='_mesn5']";
	public final static String CLASS = "_mesn5";
	public final static String USERNAME_FIELD_XPATH = "//h1[@class='_rf3jb notranslate']";
	public final static String NAME_FIELD_XPATH = "//h1[@class='_kc4z2']";
	public final static String BIO_XPATH = "//div[@class='_tb97a']/span[1]/span[1]";
	public final static String FIELD_XPATH = "/*/*";
	public final static String REL_FIELD_XPATH = "./*/*";
	public final static String POST_XPATH = "//ul[@class='_h9luf ']/li[1]";
	public final static String FOLLOWERS_XPATH = "//ul[@class='_h9luf ']/li[2]";
	public final static String FOLLOWING_XPATH = "//ul[@class='_h9luf ']/li[3]";
	public final static String BUTTON_XPATH = "//div[@class='_ienqf']/*[2]/span[1]";
	public final static String FOLLOW_LIST_XPATH = "//div[@class='_pfyik']/div[2]/div/div[2]/ul/div";
	public final static String FOLLOW_LIST_CLOSE_BUTTON_XPATH = "//div[@class='_pfyik']/button";
	public final static String POST_CONTAINER_XPATH = "//article";
	
	public ProfilePage(WebElement element) {super(element);}

//	@Override
//	public boolean checkClass(String cssClass) {
//		return cssClass.equals(CLASS);
//	}

	@Override
	void setup() {
		this.setUsername(getTextByXpath(USERNAME_FIELD_XPATH));
		this.setName(getTextByXpath(NAME_FIELD_XPATH));
		followerList = findElement(By.xpath(FOLLOWERS_XPATH));
		followingList = findElement(By.xpath(FOLLOWING_XPATH));
		postCount = Integer.parseInt(getTextByXpath(POST_XPATH+FIELD_XPATH)  );
		followerCount = Integer.parseInt( followerList.findElement(By.xpath(REL_FIELD_XPATH)).getText() );
		followingCount = Integer.parseInt( followingList.findElement(By.xpath(REL_FIELD_XPATH)).getText() );
		button = findElement(By.xpath(BUTTON_XPATH));
		isPublic = followerList.findElement(By.xpath("/*")).getTagName().equals("a");
		bio = getTextByXpath(BIO_XPATH);
		postContainer = findElement(By.xpath(POST_CONTAINER_XPATH));
		openList = false;
	}
	
	@Override
	void toggleFollow() {
		button.click();
	}
	
	public int getPostCount() {
		return postCount;
	}
	void setPostCount(int postCount) {
		this.postCount = postCount;
	}
	public int getFollowerCount() {
		return followerCount;
	}
	void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	public int getFollowingCount() {
		return followingCount;
	}
	void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}
	WebElement getButton() {
		return button;
	}
	void setButton(WebElement button) {
		this.button = button;
	}

	public WebElement getFollowerList() {
		followerList.click();
		openList = true;
		try {Thread.sleep(2000);} catch (Exception e) {}
		return findElement(By.xpath(FOLLOW_LIST_XPATH));
	}

	void setFollowerList(WebElement followerList) {
		this.followerList = followerList;
	}

	public WebElement getFollowingList() {
		followingList.click();
		openList = true;
		try {Thread.sleep(500);} catch (Exception e) {}
		return findElement(By.xpath(FOLLOW_LIST_XPATH));
	}

	void setFollowingList(WebElement followingList) {
		this.followingList = followingList;
	}

	public boolean isPublic() {
		return isPublic;
	}

	void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	public boolean listIsOpen() {
		return openList;
	}
	
	void openList() {
		openList = true;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public String toString() {
		return String.format("%s (%s)'s Page, with %d followers, %d following.%nBio: %s",getUsername(),getName(),followerCount,followingCount,bio);
	}

	@Override
	void follow() {
		
	}

	@Override
	void unFollow() {
		
		
	}

	public WebElement getPostContainer() {
		return postContainer;
	}

	void setPostContainer(WebElement postContainer) {
		this.postContainer = postContainer;
	}
	
}
