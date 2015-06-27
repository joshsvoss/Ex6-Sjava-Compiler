package oop.ex6.types;

import oop.ex6.main.SJavacException;

/**This exception is thrown when an illegal type is declared.
 * 
 * @author Shana M, Joshsvoss
 *
 */
public class InvalidTypeException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Invalid type for variable declaration.";

	/** 
	 * Creates an exception with a default message.
	 */
	public InvalidTypeException() {
		super(defaultMsg);
	}

	
	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up with exception.
	 */
	public InvalidTypeException(String msg) {
		super(msg);
	}

}
