package bot;

/**
 * TestHarness for bots. 
 * @author aliu
 *
 */
public class Tester {
	
	public static void main(String... args) {
//Instagram blocks users if they keep doing the same thing over and over again. I've gotten 10-15 different kinds of warnings from Instagram by now, none of them made this process easier.
		
		InstaBot bot = new InstaBot();
		
	try {

		Thread.sleep(1000);
		bot.quit();	
	} catch (Exception e) {e.printStackTrace();bot.quit();}
	}
	
	
	static void print(Object o) {
		System.out.println(o.toString());
	}
}
