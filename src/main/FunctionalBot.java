package main;

import org.openqa.selenium.WebDriver;

/**
 * Blank slate with only the public methods that the bot uses, and no more.
 * @author aliu
 *
 */
public class FunctionalBot extends bot.FunctionalBot {

	public FunctionalBot(String username, String password) {
		super(username, password);
	}
	
	public FunctionalBot(WebDriver webDriver, String username, String password) {
		super(webDriver,username, password);
	}

	@Override
	public void startUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


}
