package instagram;

import static instagram.Comments.COMMENT_CSS;

import java.time.Duration;
import java.time.Instant;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.WebElement;

/**
 * Post Object, meant to represent a post on the main page's feed.
 * 
 * TODO:
 * Re-make this entire thing using By.xpath instead of By.className
 * 
 * @author aliu
 *
 */
public class Post extends InstagramObject {//This needs to also have support for video posts.
	
	public static final String ARTICLE_CLASS = "_s5vjd _622au  _5lms4 _8n9ix  ";
	public static final String DIV_CLASS = "_mesn5";
	public static final String USERNAME_CLASS = "_2g7d5 notranslate _iadoq";
	public static final String LOCATION_CLASS = "_6y8ij";
	public static final String LIKES_CONTAINER_CLASS = "_3gwk6 _nt9ow";
	public static final String LIKE_BUTTON_CLASS = "_eszkz _l9yih";

	public static final String ARTICLE_CLASS2 = "_s5vjd _622au  _5lms4 _8n9ix  _9445e";
	public static final String COMMENTS_CLASS = Comments.CLASS;
	
	public static final String LIKES_FROM_FEED_CLASS = "_nzn1h";
	public static final String LIKES_FROM_PROFILE_CLASS = "_nzn1h _gu6vm";
	public static final String LIST_LIKES_FROM_FEED_CLASS = "_2g7d5 notranslate _de460";
	public static final String LIST_LIKES_FROM_PROFILE_CLASS = "_2g7d5 notranslate _de460";
	
	private String user,location,caption;
	private boolean liked;
	private int likes;
	private Instant datePosted;
	private WebElement comments;
	private boolean feed;
	private WebElement likeButton;
	
	public Post(WebElement element) {
		super(element);
	}
	
	@Override
	public boolean checkClass(String cssClass) {
		if (cssClass.equals(ARTICLE_CLASS) || cssClass.equals(ARTICLE_CLASS2)) {
			feed = true;
		} else if (cssClass.equals(DIV_CLASS)) {
			feed = false;
		} else {
			return false;
		} return true;
	}

	@Override
	protected void setup() {
		user = this.getElement().findElement(By.className(USERNAME_CLASS)).getText();
		try {
			location = this.getElement().findElement(By.className("_60iqg")).getText();
			if (location.equals("")) location = "[Unknown Location]";
		} catch (NoSuchElementException | ScriptTimeoutException e) {
			location = "[unknown location]";
		}
		try {
			WebElement likesContainer = this.getElement().findElement(By.className(LIKES_CONTAINER_CLASS)).findElement(By.cssSelector("a"));
		
			if (likesContainer.getAttribute("class").equals(LIST_LIKES_FROM_PROFILE_CLASS)) {//Eventually need to revisit this, make it more reliable and flexible
				likes = likesContainer.getText().split(",|\\sand\\s").length;
			} else {
				likes = Integer.parseInt(likesContainer.findElement(By.cssSelector("span")).getText().replaceAll(",", ""));
			}
			try {
				caption = this.getElement().findElement(By.cssSelector(COMMENT_CSS)).findElement(By.xpath("span")).getText();
			} catch (NoSuchElementException | ScriptTimeoutException e) {
				caption = "[no caption]";
			}
		} catch (NoSuchElementException e) {
			likes = 0;
		}
		
		likeButton = this.getElement().findElement(By.className(LIKE_BUTTON_CLASS));
		if (likeButton.getText().equals("Unlike"))
			liked = true;
		else
			liked = false;
		
		datePosted = Instant.parse(this.getElement().findElement(By.cssSelector("time[class='_p29ma _6g6t5']")).getAttribute("datetime"));
	}
	
	
	
	/*
	 * Getter and Setter Block for all values.
	 */
	public String getUser() {return user;}
	void setUser(String user) {this.user = user;}
	public String getLocation() {return location;}
	void setLocation(String location) {this.location = location;}
	public boolean isLiked() {return liked;}
	void setLiked(boolean liked) {this.liked = liked;}
	public String getCaption() {return caption;}
	void setCaption(String caption) {this.caption = caption;}
	public int getLikes() {return likes;}
	void setLikes(int likes) {this.likes = likes;}
	public Comments getComments() {return new Comments(comments);}
	public Instant getDatePosted() {return datePosted;}
	void setDatePosted(Instant datePosted) {this.datePosted = datePosted;}
	public final Duration getTimeSincePost() {return Duration.between(datePosted, Instant.now());}

	public void toggleLike() {
		try {
		this.likeButton.click();
		likes+=liked ? -1 : 1;
		liked = liked == false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void like() {
		if (!liked) {
			this.toggleLike();
		}
	}
	
	public void unLike() {
		if (liked) {
			this.toggleLike();
		}
	}
	
	public void view() {
		this.getElement().click();
	}
	
	public boolean isFromFeed() {
		return feed;
	}

	public void setFeed(boolean feed) {
		this.feed = feed;
	}

	public String toString() {
		if (liked) {
			return String.format("Post by '%s' at %s with %d likes (incl. you).%n    Caption: %s", user, location, likes, caption);
		} else {
			return String.format("Post by %s at %s with %d likes (not incl. you).%n    Caption: %s", user, location, likes, caption);
		}
	}	
	
}
/*
 * 
 * 
 * Other Profile Post:
 * Article class:_622au  _5lms4  _4kplh _9445e
 * Username class:_2g7d5 notranslate _iadoq
 * Location class:_6y8ij
 * likes container class:_nzn1h _gu6vm
 * Like button Classes:
 * 	Container:_eszkz _l9yih
 * 	Full:_8scx2  coreSpriteHeartFull
 * 	Empty:_8scx2 coreSpriteHeartOpen 
 * Comment container Class:_b0tqa
 * Date Posted:_p29ma _6g6t5
 * 
 * Feed Post:
 * Article class:_s5vjd _622au  _5lms4 _8n9ix
 * Username class:_2g7d5 notranslate _iadoq
 * Location class:_6y8ij
 * likes container class:_nzn1h
 * Like button Classes:
 * 	Button: _eszkz _l9yih
 * 	Full:_8scx2  coreSpriteHeartFull
 * 	Empty:_8scx2 coreSpriteHeartOpen 
 * Comment container Class:_b0tqa
 * Date Posted:_p29ma _6g6t5
 * 
 * Own Profile Post://same as other profile post
 * Article class:_622au  _5lms4  _4kplh _9445e
 * Username class: 
 * Location class:
 * likes class:
 * Like button Classes:
 * 	Container:
 * 	Full:
 * 	Empty:
 * Comment Class:
 * Date Posted:
 * 
 * 
 * 
 * 
 * 
 */
