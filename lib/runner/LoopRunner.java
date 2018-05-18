package runner;

/**
 * Implementation of Runner that executes 3 methods:
 * 
 * Start method at startup
 * loop method that is called over and over until one of either quit() or forceQuit() are called
 * end method when quit() is called
 * 
 * subclasses must have a public, implicit constructor
 * 
 * @author aliu
 *
 */
public abstract class LoopRunner extends Runner {

	private boolean run;
	
	public LoopRunner() {
		run = true;
	}
	
	/**
	 * Create an array of specified length, which will eventually become an Object array of string objects.
	 * @param arrayLength length of array
	 */
	public static void launch(int arrayLength) {
		Runner.launch(new String[arrayLength]);
	}
	
	@Override
	public final void start(String... args) throws Exception {
		Object argo = (Object[]) args;
		atStart(argo);
		while(run) {
			loop(argo);
		}
		atEnd(argo);
	}
	
	/**
	 * This method is executed before entering the loop
	 * @param args arguments to pass to the method at startup. castable to String.
	 * @throws Exception generalized exception
	 */
	public abstract void atStart(Object...args) throws Exception;
	
	/**
	 * This method is executed in the loop until the method quit() or forceQuit() is called.
	 * @param args arguments to pass to loop
	 * @throws Exception generalized exception
	 */
	public abstract void loop(Object ...args) throws Exception;
	
	/**
	 * This method is executed when the loop ends if quit() is called, but not if forceQuit() is called.
	 * @param args arguments to pass to this method at end.
	 * @throws Exception generalized exception
	 */
	public abstract void atEnd(Object...args) throws Exception;
	
	/**
	 * Quits the loop.
	 */
	protected final void quit() {
		run = false;
	}
	
	/**
	 * Quits the program. Skips over all methods.
	 * @throws InterruptedException the exception that causes a full application quit.
	 */
	protected final void forceQuit() throws InterruptedException {
		super.quit();
	}
}
