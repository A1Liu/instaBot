package instagram;

import org.openqa.selenium.WebElement;

/**
 * A page that isn't the bot user's
 * @author aliu
 *
 */
public class OtherPage extends ProfilePage {

	private boolean following;
	
	public OtherPage(WebElement element) {
		super(element);
		// TODO Auto-generated constructor stub
	}

	@Override
	void setup() {
		super.setup();
		following = this.getButton().getText().equals("Following");
	}

	@Override
	void toggleFollow() {
		super.toggleFollow();
		following = following == false;
	}
	
	@Override
	public void follow() {
		if (!following)
			toggleFollow();
	}

	@Override
	public void unFollow() {
		if (following)
			toggleFollow();
	}

	public boolean isFollowing() {
		return following;
	}

	void setFollowing(boolean following) {
		this.following = following;
	}

}
