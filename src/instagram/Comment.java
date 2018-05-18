package instagram;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * Comment Object, meant to represent a comment on a post on your feed.
 * @author aliu
 *
 */
class Comment extends InstagramObject {//Make it a concrete object, not abstract
	
	public final static String CLASS = "_ezgzd";
	public static final String USERNAME_CLASS = "_2g7d5 notranslate _95hvo";

	
	private String username;
	private String content;
	
	public Comment(WebElement element) {//Eventually change constructor to package. Post objects should create these, not anything else.
		super(element);
	}
	
	@Override
	public boolean checkClass(String cssClass) {
		return cssClass.equals(CLASS);
	}

	@Override
	protected void setup() {
		username = this.getElement().findElement(By.className(USERNAME_CLASS)).getText();
		content = this.getElement().findElement(By.xpath("span")).getText();
	}
	
	public String getUsername() {
		return username;
	}
	protected void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	protected void setContent(String content) {
		this.content = content;
	}	
	
	public String toString() {
		return String.format("%-15s: %s%n", username, content);
	}
	
//	content = ""; //Someday I might use this... But for today, I found a simpler way.
//	for (WebElement elem : this.getElement().findElements(By.xpath("span/span/*")))
//		content += elem.getText();
}
