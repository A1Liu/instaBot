package runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * This class holds static utility methods for debugging. All dependencies are public api
 * Method names are shortened.
 * 
 * @author aliu
 *
 */
public final class Debug {

	private static String filePath = null;
	private static Timer sysTimer;
	private static Timer[] timers;
	
	private Debug() {}
	
	/*----Print Utilities---*/
	
	/**
	 * Prints an array object using {@link DebugMethods#print}
	 * @param array array to print
	 */
	public static <T> void pArr(T[] array) {
		p(arrayToList(array).toString());
	}

	/**
	 * Prints the toString of the object, or 'null' if the object is a null
	 * @param o object to print
	 */
	public static void sp(Object o) {
		if (o == null)
			System.out.println("null");
		else
			System.out.println(o.toString());
	}
	
	/**
	 * Prints a marker statement to the system output
	 */
	public static void p() {
		System.out.printf("Print Statement called at %s", Thread.currentThread().getStackTrace()[2].toString());
	}
	
	/**
	 * Prints an object, the object's class, and the location of the print statement
	 * @param o object to print
	 */
	public static void p(Object o) {
		String output;
		String outputClass;
		if (o == null) {
			output = null;
			
			outputClass = "unknown class: object is null";
		} else {
			output = o.toString();
			outputClass = output.getClass().toString();
		}
		System.out.printf("Object class: <%s>%nObject value: [%s]%nPrint Called at: %s%n",outputClass,output,Thread.currentThread().getStackTrace()[2].toString());//prints out the location of where this statement was called
	}
	
	/*----File Utilities---*/
	
	/**
	 * Sets the file path of the file to store information in
	 *@param file file path of file to store stuff in
	 */
	public static void f(String file) {
		filePath = file;
	}
	
	/**
	 * writes the tostring form of an object to the file specified by {@link Debug#file(String)}
	 * @param o object to write
	 */
	public static void ws(Object o) {
		if (o == null)
			w(null);
		else
			w(o.toString());
	}
	
	/**
	 * Writes information of an object to a file specified by {@link Debug#file(String)}
	 * @param obj object to store
	 */
	public static <T extends Serializable> void w(T obj) {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File(filePath) ) );
			stream.writeObject(obj);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads information of an object from a file specified by {@link DebugMethods#setFilePath(String)}
	 * @return object from file
	 */
	public static Object r() {
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File(filePath) ) );
			Object o = stream.readObject();
			stream.close();
			return o;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*----Function Utilities---*/
	
	/**
	 * Catches all exceptions for a function
	 * @param function function to catch exceptions for
	 * @param input input to give to function
	 * @return function's output, or null if an exception was thrown
	 */
	public static <I,O> O cAll(Function<I,O> function, I input) {
		try {
			return function.apply(input);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*----Input Utilities---*/
	
	/**
	 * Gets input from System.in and returns it
	 * @return String that was typed into command line, or null if there was an IOException
	 */
	public static String sysIn() {
		try {
			return new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*----Timer Utilities---*/
	
	/**
	 * Timer object
	 * @author aliu
	 *
	 */
	private static class Timer {//TODO: Timer name
		private final long startTime;private long stopTime;private StackTraceElement beginTimerLoc;private StackTraceElement endTimerLoc;
		Timer() {this(System.nanoTime());}
		Timer(long startTime) {this.startTime = startTime;this.beginTimerLoc = Thread.currentThread().getStackTrace()[4];this.endTimerLoc = null;}
		
		public long stopTimer() {//Add method for longer times
			if (endTimerLoc == null) {
				stopTime = System.nanoTime();
				endTimerLoc = Thread.currentThread().getStackTrace()[3];
			} else {
				throw new TimerException("Timer cannot be stopped twice!"); //TODO: figure out what to do here
			}
			System.out.printf("        Timer duration: %f s%n Timer start called at: %s%n  Timer stop called at: %s%n",
						(stopTime-startTime)/1000000000.0,
						beginTimerLoc.toString(),
						endTimerLoc.toString());//prints out the location of where this statement was called
			return stopTime;
		}
		
		public long timeSinceStart() {
			long time = System.nanoTime() - startTime;
			System.out.printf("Time since timer start: %f s%n Timer start called at: %s%n        Time called at: %s%n",
					time/1000000000.0,
					beginTimerLoc.toString(),
					Thread.currentThread().getStackTrace()[3].toString());
			return time;
		}
		
		public boolean isRunning() {
			return endTimerLoc == null;
		}
	}
	
	/**
	 * Exception for the timer
	 * @author aliu
	 *
	 */
	static class TimerException extends RuntimeException {private static final long serialVersionUID = 1L;
		/** Constructor for timer exception that adds message
		 * @param message the message for the exception
		 */
		public TimerException(String message) {super(message);}
	}
	
	/**
	 * Thread sleeps for a certain amount of time
	 * @param seconds seconds to sleep
	 */
	public static void slp(int seconds) {
		try {Thread.sleep(seconds*1000);} catch (InterruptedException i) {i.printStackTrace();}
	}
	
	/**
	 * Start a timer instance
	 */
	public static void t() {
		if (sysTimer == null || !sysTimer.isRunning()) {
			sysTimer = new Timer();
		} else
			throw new TimerException("Can't start a timer that's still running!");
	}
	
	/**
	 * Restart the timer instance
	 */
	public static void tr() {
		sysTimer = new Timer();
	}
	
	/**
	 * Stop the timer instance and print the time.
	 * @return the nanoseconds since the timer started
	 */
	public static long ts() {
		if (sysTimer == null)
			throw new TimerException("");
		else
			return sysTimer.stopTimer();
	}
	
	/**
	 * Time since the timer started
	 * @return nanoseconds since the timer started
	 */
	public static long tss() {
		return sysTimer.timeSinceStart();
	}
	
	/**
	 * starts a timer with the specified id
	 * @param timerID the id of the timer
	 */
	public static void t(int timerID) {
		if (timers == null) {
			if (timerID >= 10) {
				timers = new Timer[timerID+1];
			} else 
				timers = new Timer[10];
		} else if (timerID >= timers.length) {
			timers = Arrays.copyOf(timers, (int) (timerID*1.5));
		}
		if (timers[timerID] == null || !timers[timerID].isRunning()) {
			timers[timerID] = new Timer();
		} else
			throw new TimerException("Can't start a timer that's still running!");
	}
	
	/**
	 * Stops a timer with the specified id
	 * @param timerID id of the timer
	 * @return the time in nanoseconds since the timer started
	 */
	public static long ts(int timerID) {
		if (timerID >= timers.length || timers[timerID] == null)
			throw new TimerException("Invalid timer ID.");
		else return timers[timerID].stopTimer();
	}
	
	/**
	 * time since specified timer started
	 * @param timerID the id of the timer
	 * @return the time in nanoseconds since the specified timer started
	 */
	public static long tss(int timerID) {
		if (timerID >= timers.length || timers[timerID] == null)
			throw new TimerException("Invalid timer ID.");
		else return timers[timerID].timeSinceStart();
	}
	
	/*--------private utility methods----------*/
	
	/**
	 * turns an array to a list
	 * @param array array to return
	 * @return array as a list
	 */
	private static <T> List<T> arrayToList(T[] array) {
		List<T> list = new ArrayList<T>();
		for (T item : array) {
			list.add(item);
		}return list;
	}
}
