package bot;

/**
 * This exception represents a problem at the startup of the bot -- i.e. login credentials were wrong, or login took to long, or 2 factor auth. was turned on.
 * @author aliu
 *
 */
public class BotConfigurationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a BotConfigurationException with the given detail message.
     * @param message The detail message of the BotConfigurationException.
     */
    public BotConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a BotConfigurationException with the given root cause.
     * @param cause The root cause of the BotConfigurationException.
     */
    public BotConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a BotConfigurationException with the given detail message and root cause.
     * @param message The detail message of the BotConfigurationException.
     * @param cause The root cause of the BotConfigurationException.
     */
    public BotConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
