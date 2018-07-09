package main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import bot.InstaBot;

public class Main {//TODO: Use debug class to analyze runtime
	
	private static InstaBot bot;
	
	private static String MAIN = "properties/main/";
	//private static String SECOND = "properties/instabot/second/";
	
	public static void main(String[] args) {
		//WebDriver driver = new HtmlUnitDriver();
		test1();
		
		
	}
	
	public static void test1() {
		bot = new InstaBot(MAIN);
		bot.login();
		bot.likeAllRecent();
		//System.out.println(bot.unfollow());
		bot.quit();
	}

}
