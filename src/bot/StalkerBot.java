package bot;

import static instagram.Const.INSTAGRAM;

import java.util.Collection;
import java.util.TreeSet;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import instagram.ProfilePage;
import instagram.TableRow;

/**
 * handles things typically associated with "stalking" a profile on instagram:
 * 
 * viewing posts on their feed
 * Checking their followers and followings
 * etc. etc.
 * 
 * TODO:
 * 
 * Liking all the posts on a profile
 * unliking all the posts on a profile
 * liking all recent posts on a profile
 * 
 * @author aliu
 *
 */
class StalkerBot extends ProfileBot {

	public StalkerBot(WebDriver webDriver, String username, String password) {super(webDriver, username, password);}
	
	public StalkerBot(String username, String password) {super(username, password);}
	
	/**
	 * Gets a list of people that a specific profile is following
	 * @return a TreeSet collection of following
	 */
	public Collection<String> getFollowing() {
		if(this.getViewing() != null) {
			ProfilePage page = new ProfilePage(waitForElement(By.xpath(ProfilePage.XPATH)));
			return getFollowTableNames(page.getFollowingList());
		} else {return null;}
	}
	
	/**
	 * Gets a list of followers of a specific profile
	 * @return a TreeSet collection of followers
	 */
	public Collection<String> getFollowers() {
		if(this.getViewing() != null) {
			ProfilePage page = new ProfilePage(waitForElement(By.xpath(ProfilePage.XPATH)));
			return getFollowTableNames(page.getFollowerList());
		} else return null;
	}
	
	/**
	 * Gets a list of follow entries given a webelement that contains them
	 * @param list list element
	 * @return follow elements
	 */
	private Collection<String> getFollowTableNames(WebElement list) {
		boolean running = true;
		WebElement elem = list.findElement(By.xpath("./li[@class='_6e4x5']"));
		TreeSet<String> treeList = new TreeSet<String>();
		
		while (running) {
			if (elem == null) {
				running = false;
			} else {
				JavascriptExecutor je = (JavascriptExecutor) getWebDriver();
				je.executeScript("arguments[0].scrollIntoView(true);",elem);
				treeList.add(new TableRow(elem).getUsername());
				elem = Util.getFollowingSibling(elem);
			}
		}
		getWebDriver().get(INSTAGRAM+getViewing());
		return treeList;
	}
}
