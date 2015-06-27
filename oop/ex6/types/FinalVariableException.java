package oop.ex6.types;

import oop.ex6.main.SJavacException;

/**This exception is thrown when a final variable is reassigned.
 * 
 * @author Shana M, Joshsvoss
 *
 */
public class FinalVariableException extends SJavacException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "A final variable cannot be reassigned.";
	
	/** 
	 * Creates an exception with a default message.
	 */
	public FinalVariableException() {
		super(defaultMsg);
	}
	
	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up with exception.
	 */
	public FinalVariableException(String msg) {
		super(msg);
	}

}
