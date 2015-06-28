package oop.ex6.main;

/** This exception is thrown when a method is called with a name that doesn't exist.  
 * @author Joshua Voss, shanam
 *
 */
public class WrongMethodNameException extends SJavacException {

	private final static String defaultMsg = "A method name was called that doesn't exist.";

	private static final long serialVersionUID = 1L;

	/** 
	 * Creates an exception with a default message.
	 */
	public WrongMethodNameException() {
		super(defaultMsg);
	}

	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up with exception.
	 */
	public WrongMethodNameException(String msg) {
		super(msg);

	}

}
