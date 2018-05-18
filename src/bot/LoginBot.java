package bot;

import static instagram.Const.INSTAGRAM;
import static instagram.Const.INSTAGRAM_LOGIN;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Handles naively logging into instagram:
 * 
 * Logs in
 * checks if login was successful
 * sets basic driver timers
 * 
 * TODO:
 * Improve login checks
 * 
 * @author aliu
 *
 */
class LoginBot {

	private WebDriver webDriver;
	private long explicitWait;
	private long pageWait;
	private String username;
	private String password;
	
	public LoginBot(String username, String password) {
		this(new SafariDriver(),username, password);
	}
	
	public LoginBot(WebDriver webDriver, String username, String password) {
		this.webDriver = webDriver;
		this.pageWait = 10;
		this.webDriver.manage().timeouts().pageLoadTimeout(pageWait, TimeUnit.SECONDS);
		this.webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		this.webDriver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		this.webDriver.manage().window().setSize(new Dimension(800,800));
		this.webDriver.manage().window().setPosition(new Point(900,100));
		this.explicitWait = 5;
		this.username = username;
		this.password = password;
	}

	public void login() {
		this.login(username, password);
		waitForLogin();
	}
	
	/**
	 * Logs in to Instagram using a specified username and password
	 * @param username username of user
	 * @param password password of user
	 */
	public void login(String username, String password) {
		webDriver.get(INSTAGRAM_LOGIN);
		boolean running = true;
		while (running) {
			try {//Wait for page to load, then type in the username field
				waitForElement(By.cssSelector("input[name='username']")).sendKeys(username);
				running = false;
			}catch (TimeoutException | NoSuchElementException e) {
				//If the login page is too slow
			}
		}
		//Type in password field
		findElement(By.cssSelector("input[name='password']")).sendKeys(password);
		
		//Click the Log in Button
		new Actions(webDriver).moveToElement(findElement(By.xpath("//button[.='Log in']"))).click().perform();
	}
	
	/**
	 * Waits for login. Try catch block checks for this one really annoying pop-up. God, bots can get broken by the smallest things.
	 */
	void waitForLogin() {
		loggedIn();
		try {
			this.waitForElement(1,By.className("_c92w7 _3kb1s")).click();
		} catch (NoSuchElementException | TimeoutException e) {}
	}
	
	/**
	 * Checks if the bot has successfully logged in after entering credentials.
	 * @return true if and only if successful in logging in.
	 */
	private boolean loggedIn() {
		try {
			driverWait(pageWait).until(ExpectedConditions.urlToBe(INSTAGRAM));//Wait to see if redirected to homepage
			driverWait(pageWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//nav[@class='_68u16 _gft4l  ']")));
			return true;
		} catch (TimeoutException t) {
			try {//Check if the username and password information were incorrect
				if (webDriver.getCurrentUrl().equals(INSTAGRAM_LOGIN)) {//Still at login page
					if (findElement(By.id("slfErrorAlert")).getText().contains("username"))//there's an error message
						throw new BotConfigurationException("Username isn't associated with an account.");
					else//There's an error message
						throw new BotConfigurationException("Password was incorrect.");
				} else throw new BotConfigurationException("An unknown error occured. Have you turned off two-factor authentification?");
			} catch (NoSuchElementException e) {}//Couldn't find an error message
		} throw new BotConfigurationException("Instagram timed out during login attempt.");
	}
	
	/** 
	 * Getter for WebDriver
	 * @return the WebDriver
	 */
	WebDriver getWebDriver() {
		return webDriver;
	}

	/**
	 * Quits and throws an exception
	 * @param cause an exception that warrants quitting the program. 
	 */
	public void quitException(Throwable cause) {
		quit();
		throw new BotConfigurationException(cause);
	}
	
	/**
	 * Quits and throws an exception
	 * @param message the message the exception should have
	 */
	public void quitException(String message) {
		quit();
		throw new BotConfigurationException(message);
	}
	
	/**
	 * Find an element using the webDriver
	 * @param identifier how to find the element
	 * @return the element
	 */
	WebElement findElement(By identifier) {
		return webDriver.findElement(identifier);
	}
	
	/**
	 * Find a list of elements using the webDriver
	 * @param identifier how to find the element
	 * @return the element
	 */
	List<WebElement> findElements(By identifier) {
		return webDriver.findElements(identifier);
	}
	
	/**
	 * Waits for an element to be clickable for as long as the explicit wait variable is set for
	 * @param identifier what to look for
	 * @return the element
	 */
	WebElement waitForElement(By identifier) {
		return waitForElement(explicitWait, identifier);
	}
	
	/**
	 * Waits for an element to be clickable for a specified amount of time
	 * @param time how long to wait for
	 * @param identifier what to look for
	 * @return the element
	 */
	WebElement waitForElement(long time,By identifier) {
		return driverWait(time).until(ExpectedConditions.elementToBeClickable(identifier));
	}
	
	/**
	 * returns a new WebDriverWait Object for however much time the explicit wait variable is set for
	 * @return a WebDriverWait Object
	 */
	WebDriverWait driverWait() {
		return driverWait(explicitWait);
	}
	
	/**
	 * returns a new WebDriverWait Object for a specified amount of time
	 * @param time how long to wait
	 * @return a WebDriverWait Object
	 */
	WebDriverWait driverWait(long time) {
		return new WebDriverWait(webDriver,time);
	}
	
	public String getUserInfo() {
		return String.format("User %s", username);
	}
	
	/**
	 * @return the username
	 */
	String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * quits the webDriver
	 */
	public void quit() {
		webDriver.quit();
	}
}
