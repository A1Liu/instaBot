package main;

import java.io.IOException;

import bot.InstaBot;

/**
 * Main runner class.
 * @author aliu
 *
 */
public class Main extends runner.Runner {
	
	private InstaBot bot;
	
	public static void main(String... args) throws IOException {
		launch(args);
	}

	@Override
	public void start(String... args) throws Exception {
		bot = new InstaBot();
		try {
			run();
			Thread.sleep(2000);
			bot.quit();
		}catch(Exception e) {e.printStackTrace();bot.quit();}
	}
	
	public void run() {
		bot.login();
		bot.likeAllRecent();
		System.out.println(bot.getNoFollowBack());
	}
}
