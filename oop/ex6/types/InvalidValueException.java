package oop.ex6.types;

import oop.ex6.main.SJavacException;

/**This exception is thrown when an illegal value is assigned to a variable.
 * 
 * @author Shana M, Joshsvoss
 *
 */
public class InvalidValueException extends SJavacException {

	private static final long serialVersionUID = 1L;

	private static final String defaultMsg = "The given value does not fit the variable type to which it is "
			+ "being assigned.";
	
	/** 
	 * Creates an exception with a default message.
	 */
	public InvalidValueException() {
		super(defaultMsg);
	}

	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up with exception.
	 */
	public InvalidValueException(String msg) {
		super(msg);
	}

}
