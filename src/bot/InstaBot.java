package bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import bot.constraints.Constraint;
import instagram.Post;
import instagram.ProfilePage;

import static bot.Util.getNextLoop;
import static instagram.Const.INSTAGRAM_BLANK;
import static instagram.Const.INSTAGRAM;

/**
 * Bot that uses the selenium API to use Instagram's website. Stores cookies in a directory.
 * 
 * TODO:
 * get full support for premade constraints
 * get full support for properties file
 * make the class look prettier
 * Add default scripts, and a 'on start up' property.
 * 
 * @author aliu
 *
 */
public class InstaBot extends VengefulBot {
	
	private BotProperties properties;
	private final static String DEFAULT_DIRECTORY = "properties/";
	private final static String PROPERTIES_FILE = "bot.properties";
	private final static String COOKIES_FILE = "cookies.txt";
	
	public InstaBot(Constraint... constraints) {
		this(DEFAULT_DIRECTORY,constraints);
	}
	
	public InstaBot(String directory, Constraint...constraints) {//Add more intuitive support for using more than one user
		super(null, null);
		properties = new BotProperties(directory + PROPERTIES_FILE);
		properties.setProperty("directory",directory);
		this.setUsername(properties.getProperty("username"));
		this.setPassword(properties.getProperty("password"));
	}
	
	public InstaBot(WebDriver webDriver,Constraint... constraints) {
		this(webDriver, DEFAULT_DIRECTORY);
	}
	
	public InstaBot(WebDriver webDriver, String directory,Constraint... constraints) {
		super(webDriver, null, null);
		properties = new BotProperties(directory+PROPERTIES_FILE);
		properties.setProperty("directory",directory);
		this.setUsername(properties.getProperty("username"));
		this.setPassword(properties.getProperty("password"));
	}
	
	/**
	 * Log in, and check for cookies before starting the login process
	 */
	@Override
	public void login() {
		if(!(properties.getProperty("cookies") == null || properties.getProperty("cookies").trim().equals(""))) {
			try {
				getWebDriver().get(INSTAGRAM_BLANK);
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(properties.getProperty("directory")+properties.getProperty("cookies"))));
				Set<?> set = (Set<?>) input.readObject();
				if (set == null) {input.close();throw new ClassNotFoundException("File was empty!");}
				for (Object item : set) {
					Cookie cookie = (Cookie) item;
					if (cookie.getDomain().equals("www.instagram.com")) {
						getWebDriver().manage().addCookie(cookie);
					}
				}
				input.close();
				getWebDriver().get(INSTAGRAM);
				waitForLogin();
				return;
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Cookies not found! Logging in normally...");
			} catch (BotConfigurationException e) {
				System.out.println("Cookies expired! Logging in normally...");
			}
		} else {
			properties.setProperty("cookies", COOKIES_FILE);
		}
		properties.remove("cookies");
		super.login();
	}
	
	/**
	 * Likes until it finds a post that is already liked.
	 * Follows soft and hard requirements for liking.
	 */
	@Override
	public void likeAllRecent() {
		WebElement elem = getFirstPost();
		boolean running = true;
		while (running) {
			Post post = new Post(elem);
			if (post.isLiked()) running = false;
			else if (shouldLike(post) && canLike(post)) {
				post.like();//In the implementation described by the constraint interface, if shouldLike is true canLike must also be true.
				System.out.println(post.toString());
			} else post.view();
			elem = getNextLoop(elem);
			sleep();
		}
	}
	
	ProfilePage getHomePage() {
		ProfilePage page = super.getHomePage();
		properties.setProperty("followerCount", Integer.toString(this.getFollowerCount()));
		properties.setProperty("followingCount",Integer.toString(this.getFollowingCount()));
		return page;
	}
	
	/**
	 * Stores the cookies that have been collected this session in a file
	 */
	void storeCookies() {
		try {
			File file;
			if (properties.getProperty("cookies") == null || properties.getProperty("cookies").trim().equals("")) {
				file = new File(properties.getProperty("directory")+COOKIES_FILE  );
				properties.setProperty("cookies",COOKIES_FILE );
			} else
				file = new File(properties.getProperty("directory")+properties.getProperty("cookies")  );
			Set<Cookie> cookies = getWebDriver().manage().getCookies();
			file.delete();
		
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file)  );
			output.writeObject(cookies);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves data, then quits.
	 */
	public void quit() {
		try {
			this.storeCookies();
			properties.storeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.quit();
	}
}
