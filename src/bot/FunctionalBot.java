package bot;

import org.openqa.selenium.WebDriver;

/**
 * This class represents the base version of the bot, without any bells and whistles. 
 * It has all the functionality of the bot, but is missing some extra infrastructure that is provided by other classes.
 * 
 * TODO:
 * Finalize the visibility of all methods so that FunctionalBot can be used as a going-off point by any class
 * 
 * @author aliu
 *
 */
public abstract class FunctionalBot extends VengefulBot {

	public FunctionalBot(String username, String password) {
		super(username, password);
		startUp();
	}
	
	public FunctionalBot(WebDriver webDriver, String username, String password) {
		super(webDriver,username, password);
		startUp();
	}
	
	/**
	 * Stuff that is done at instantiation. Called in constructor (don't call it again).
	 */
	public abstract void startUp();
	
	/**
	 * Actions the bot should be taking while running
	 */
	public abstract void run();

	

}
