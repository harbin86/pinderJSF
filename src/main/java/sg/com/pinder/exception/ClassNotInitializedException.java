package sg.com.pinder.exception;

/**
 * Every Class should be properly initialized before use
 * @author Han Liang Wee, Eric
 * @version 04/08/2013
 */
public class ClassNotInitializedException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception, caused by a missing database
	 */
	public ClassNotInitializedException()
	{
		super();
	}

	/**
	 * Constructs a new exception with the specified message
	 * @param message The message that describes this exception
	 */
	public ClassNotInitializedException(String message)
	{
		super(message); 
	}
	
	/**
	 * Constructs a new exception with the specified message and cause
	 * @param message The message that describes this exception
	 * @param cause The cause of this exception
	 */
	public ClassNotInitializedException(String message, Throwable cause) 
	{
		super(message, cause);
	}
	
	/**
	 * Constructs a new exception with the specified cause
	 * @param cause The cause of this exception
	 */
	public ClassNotInitializedException(Throwable cause) 
	{ 
		super(cause); 
	}
}
