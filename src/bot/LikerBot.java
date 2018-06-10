package bot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import instagram.Post;

import static instagram.Const.INSTAGRAM;
import static bot.Util.getNextLoop;

/**
 * Handles liking posts on the feed:
 * 
 * likes posts
 * optionally checks posts for information
 * 
 * TODO: Improve runtime, make more robust in cases of instagram being weirdw
 * TODO: Change view methods to scroll
 * 
 * @author aliu
 *
 */
abstract class LikerBot extends LoginBot {
	
	private int sleepTime;
	private boolean scrolled;
	public static final String NEXT_SIBLING_XPATH = "./following-sibling::article";
	
	public LikerBot(String username, String password) {
		super(username, password);
		init();
	}
	
	public LikerBot(WebDriver webDriver, String username, String password) {
		super(webDriver, username, password);
		init();
	}
	
	private void init() {
		scrolled = false;
		sleepTime = 20;
	}
	
	/**
	 * Likes until it finds a post that is already liked.
	 * Follows soft and hard requirements for liking.
	 */
	public void likeAllRecent() {
		WebElement elem = getFirstPost();
		boolean running = true;
		while (running) {
			JavascriptExecutor je = (JavascriptExecutor) getWebDriver();
			je.executeScript("arguments[0].scrollIntoView(true);",elem);
			Post post = new Post(elem);
			if (post.isLiked()) running = false;
			else if (shouldLike(post) && canLike(post)) {
				post.like();//In the implementation described by the constraint interface, if shouldLike is true canLike must also be true.
				System.out.println(post.toString());
			}
			elem = getNextLoop(elem);
//			sleep();
		}
	}
	
	/**
	 * Likes a specified amount of the most recent posts.
	 * Overrides soft requirements on liking and unliking.
	 * Ignores whether or not the post has already been liked.
	 * @param limit the amount of posts to like
	 */
	public void likeAll(int limit) {
		WebElement elem = getFirstPost();
		int count = 0;
		while (count < limit) {
			JavascriptExecutor je = (JavascriptExecutor) getWebDriver();
			je.executeScript("arguments[0].scrollIntoView(true);",elem);
			Post post = new Post(elem);
			if (canLike(post)) {
				post.like();
				System.out.println(post.toString());
			}
			count++;
			if (count < limit) elem = getNextLoop(elem);
//			sleep();
		}
	}
	
	/**
	 * Unlikes a specified amount of the most recent posts in the feed.
	 * Overrides soft requirements on liking and unliking.
	 * Ignores whether or not the post has already been unliked.
	 * @param limit the amount of posts to unlike
	 */
	public void unLikeAll(int limit) {
		WebElement elem = getFirstPost();
		int count = 0;
		while (count < limit) {
			JavascriptExecutor je = (JavascriptExecutor) getWebDriver();
			je.executeScript("arguments[0].scrollIntoView(true);",elem);
			Post post = new Post(elem);
			if (canUnlike(post)) {
				post.unLike();
				System.out.println(post.toString());
			}
			count++;
			if (count < limit) elem = getNextLoop(elem);
//			sleep();
		}
	}
	
	/**
	 * Gets the first post on the feed page, and also ensures the page has been correctly loaded.
	 * Doesn't reload/refresh the page unless absolutely necessary
	 * @return the first post.
	 */
	WebElement getFirstPost() {
		if (!getWebDriver().getCurrentUrl().equals(INSTAGRAM) || scrolled)
			getWebDriver().get(INSTAGRAM);
		scrolled = true;
		return waitForElement(By.xpath(Post.FEED_POST_XPATH));
	}
	
	/**
	 * Goes to the first post on the feed page and ensures that the page has been loaded.
	 * @return the first post
	 */
	public WebElement gotoFeed() {
		getWebDriver().get(INSTAGRAM);
		scrolled = false;
		return waitForElement(By.xpath(Post.FEED_POST_XPATH));
	}
	
	void setScrolled(boolean scrolled) {
		this.scrolled = scrolled;
	}
	
	/**
	 * default method for the bot sleep
	 */
	void sleep() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {}
	}
	
	public void setSleepTime(int time) {
		this.sleepTime = time;
	}
	
	int getSleepTime() {
		return sleepTime;
	}
	
	/**
	 * Contains logic for whether or not a post should be liked. Can be overriden in the future to affect behavior of bot
	 * 
	 * NOTE: This is a 'soft' requirement, and is ignored by some of the methods
	 * 
	 * @param post the post to evaluate
	 * @return true if the bot should like it, false if the bot shouldn't
	 */
	public boolean shouldLike(Post post) {
		return canLike(post);
	}
	
	/**
	 * Contains logic for whether or not a post can be liked by this bot. Can be overriden in the future to affect behavior of bot
	 * @param post the post to evaluate
	 * @return true if the bot is allowed to like it, false if the bot isn't
	 */
	public boolean canLike(Post post) {
		return true;
	}
	
	/**
	 * Contains logic for whether or not a post can be unliked. Can be overriden in the future to affect behavior of bot
	 * @param post the post to evaluate
	 * @return true if the bot is allowed to unlike it, false if the bot isn't
	 */
	public boolean canUnlike(Post post) {
		return true;
	}
}
