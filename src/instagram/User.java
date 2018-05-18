package instagram;

import org.openqa.selenium.WebElement;

import static instagram.Const.*;
/**
 * User object, meant to represent the minimum amount of information that can identify a user.
 * This object doesn't refer to a specific WebElement; instead it holds useful methods to be used in subclasses.
 * 
 * 
 * @author aliu
 *
 */
abstract class User extends InstagramObject {
	
	private String username;
	private String name;
	
	public User(WebElement element) {
		super(element);
	}
	
	/**
	 * Press the follow/unfollow button
	 */
	abstract void toggleFollow();
	
	/**
	 * follow this user
	 */
	abstract void follow();
	
	/**
	 * unfollow this user
	 */
	abstract void unFollow();
	
	/**
	 * Get the url associated with this user
	 * @return user's profile url
	 */
	public final String getURL() {
		return INSTAGRAM + username + "/";
	}
	
	public String getUsername() {
		return username;
	}
	
	void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
	
}
