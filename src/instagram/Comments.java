package instagram;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.WebElement;

class Comments extends InstagramObject {
	
	
	public final static String CLASS = "_b0tqa";
	public final static String COMMENT_CSS = "li[class='_ezgzd']";

	
	private ArrayList<Comment> comments;
	private boolean hasCaption;

	public Comments(WebElement element) {
		super(element);
	}

	@Override
	public boolean checkClass(String cssClass) {
		return (cssClass.equals(CLASS));
	}

	@Override
	protected void setup() {
		comments = new ArrayList<Comment>();
		try {
			int counter = 0;
			while (counter < 5) {
				WebElement element = this.getElement().findElement(By.cssSelector("li[class='_56pd5']"));//Helo
				WebElement btn = element.findElement(By.xpath("*"));
				btn.click();
				Thread.sleep(100);
				counter++;	
			}
		} catch (NoSuchElementException | ScriptTimeoutException n) {
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<WebElement> commentList = this.getElement().findElements(By.cssSelector(COMMENT_CSS));
		for (WebElement elem : commentList) {
			comments.add(new Comment(elem));
		}
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public boolean hasCaption() {
		return hasCaption;
	}

	public void setCaption(boolean hasCaption) {
		this.hasCaption = hasCaption;
	}
	
	


}
