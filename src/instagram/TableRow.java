package instagram;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Represents a table row in a follow table (the 'following' and 'followers' lists on the instagram website)
 * 
 * @author aliu
 *
 */
public class TableRow extends User {
	
	public final static String CLASS = "_6e4x5";
	public final static String XPATH = "//div[@class='_pfyik']/div[2]/div/div[2]/ul/div/li";
	public final static String BUTTON_CONTAINER_XPATH = "./div/div[2]";
	public final static String USERNAME_XPATH = "./div/div[1]/div/div[1]";
	public final static String NAME_XPATH = "./div/div[1]/div/div[2]";
	
	private WebElement button;
	private boolean following;
	private boolean self;
	
	
	public TableRow(WebElement element) {
		super(element);
	}
	
	public void view() {
		getElement().click();
	}

	@Override
	public void follow() {
		if (!following) {
			toggleFollow();
		}
	}

	@Override
	public void unFollow() {
		if (following) {
			toggleFollow();
		}
	}

//	@Override
//	public boolean checkClass(String cssClass) {
//		return cssClass.contains(CLASS);
//	}

	@Override
	protected void setup() {
		this.setUsername(this.getTextByXpath(USERNAME_XPATH));
		this.setName(this.getTextByXpath(NAME_XPATH));
		button = findElement(By.xpath(BUTTON_CONTAINER_XPATH));
		if (button.getSize().getWidth() > 0) {
			self = false;
			following = button.getText().equals("Following");
		} else {
			self = true;
			following = true;
		}
	}

	@Override
	void toggleFollow() {
		if (!self) {
			button.click();
			following = following == false;
		}
	}

	

}
