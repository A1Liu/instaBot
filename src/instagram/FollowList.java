package instagram;

import static instagram.Util.getNextLoop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FollowList extends InstagramObject {

	public final static String XPATH = "//div[@class='_pfyik']";
	public final static String CLASS = "_pfyik";
	public final static String LIST_XPATH = "./div[2]/div/div[2]/ul/div";
	public final static String FIRST_LIST_ELEMENT_XPATH = "./div[2]/div/div[2]/ul/div/li";
	public final static String CLOSE_BUTTON_XPATH = ".";
	
	public FollowList(WebElement element) {
		super(element);
	}

//	@Override
//	public boolean checkClass(String cssClass) {
//		return cssClass.equals(CLASS);
//	}

	@Override
	protected void setup() {
		
	}
	
	private WebElement getFirstItem() {
		return findElement(By.xpath(FIRST_LIST_ELEMENT_XPATH));
	}
	
	public List<String> getElements() {
		ArrayList<String> users = new ArrayList<String>();
		try {Thread.sleep(1000);}catch (Exception e) {}
		WebElement elem = getFirstItem();
		while(elem!=null) {
			System.out.println(elem.getText());
			users.add(new TableRow(elem).getUsername());
			elem = getNextLoop(elem);
		}
		return users;
	}
	
	public void unFollowAll(Collection<String> usernames) {
		WebElement elem = getFirstItem();
		while(elem!=null) {
			TableRow user = new TableRow(elem);
			user.view();
			if (usernames.contains(user.getUsername()))
				System.out.println("hello");
				//user.unFollow();
		}
	}
	
	public void unFollow(String username) {
		WebElement elem = getFirstItem();
		boolean sentinel = true;
		while(elem!=null && sentinel) {
			TableRow user = new TableRow(elem);
			user.view();
			if (username.equals(user.getUsername())) {
				System.out.println("hello");
				//user.unFollow();
				sentinel = false;
			}	
		}
	}

	public void close() {
		
	}

}
