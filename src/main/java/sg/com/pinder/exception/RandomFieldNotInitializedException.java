package sg.com.pinder.exception;

/**
 * The Class NoDatabaseException should be caught by the calling environment 
 * during creation of an Database object
 * @author Han Liang Wee, Eric
 * @version 04/08/2013
 */
public class RandomFieldNotInitializedException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception, caused by a missing database
	 */
	public RandomFieldNotInitializedException()
	{
		super();
	}

	/**
	 * Constructs a new exception with the specified message
	 * @param message The message that describes this exception
	 */
	public RandomFieldNotInitializedException(String message)
	{
		super(message); 
	}
	
	/**
	 * Constructs a new exception with the specified message and cause
	 * @param message The message that describes this exception
	 * @param cause The cause of this exception
	 */
	public RandomFieldNotInitializedException(String message, Throwable cause) 
	{
		super(message, cause);
	}
	
	/**
	 * Constructs a new exception with the specified cause
	 * @param cause The cause of this exception
	 */
	public RandomFieldNotInitializedException(Throwable cause) 
	{ 
		super(cause); 
	}
}
