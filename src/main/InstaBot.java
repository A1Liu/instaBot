package main;

import org.openqa.selenium.WebDriver;

import bot.constraints.Constraint;

/**
 * InstaBot.
 * @author aliu
 *
 */
public final class InstaBot extends bot.InstaBot {

	public InstaBot(Constraint... constraints) {
		super(constraints);
	}
	
	public InstaBot(String directory, Constraint...constraints) {//Add more intuitive support for using more than one user
		super(directory, constraints);
	}
	
	public InstaBot(WebDriver webDriver,Constraint... constraints) {
		super(webDriver, constraints);
	}
	
	public InstaBot(WebDriver webDriver, String directory,Constraint... constraints) {
		super(webDriver, directory, constraints);
	}

}
