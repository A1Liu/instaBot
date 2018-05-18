package bot;

import static instagram.Const.INSTAGRAM;

import java.util.ArrayList;
import java.util.Collection;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import instagram.TableRow;

/**
 * Handles unfollowing people, and other petty things:
 * 
 * Checking for differences between followers and following
 * 
 * TODO:
 * rectify those differences!
 * Check engagement of posts and unfollow people that don't like posts (to be super petty)
 * 
 * @author aliu
 *
 */
class VengefulBot extends StalkerBot {

	public VengefulBot(String username, String password) {super(username, password);}
	public VengefulBot(WebDriver webDriver,String username, String password) {super(webDriver,username, password);}

	public Collection<String> getNoFollowBack() {
		gotoHomePage();
		return getFollowListDiff(this.getFollowers(),this.getHomePage().getFollowingList());
	}
	
	public Collection<String> getNotFollowingBack() {
		gotoHomePage();
		return getFollowListDiff(this.getFollowing(), this.getHomePage().getFollowerList());
	}
	
	private Collection<String> getFollowListDiff(Collection<String> followList1, WebElement followList2) {
		ArrayList<String> noFollowBacks = new ArrayList<String>();
		boolean running = true;
		WebElement elem = followList2.findElement(By.xpath("./li[@class='_6e4x5']"));
		while (running) {
			if (elem == null) {
				running = false;
			} else {
				JavascriptExecutor je = (JavascriptExecutor) getWebDriver();
				je.executeScript("arguments[0].scrollIntoView(true);",elem);
				String user = new TableRow(elem).getUsername();
				if (!followList1.contains(user)) {
					noFollowBacks.add(user);
				}
				elem = Util.getFollowingSibling(elem);
			}
		}
		getWebDriver().get(INSTAGRAM+getViewing());
		return noFollowBacks;
	}
	
	
}
