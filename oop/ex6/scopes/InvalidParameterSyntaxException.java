
package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

/** This exception is thrown when the parameter declaration of a method is invalid.  
 * @author Shana M, Joshsvoss
 *
 */
public class InvalidParameterSyntaxException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Invalid method parameter syntax.";
	
	/** 
	 * Creates an exception with a default message.
	 */
	public InvalidParameterSyntaxException() {
		super(defaultMsg);
	}

	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up with exception.
	 */
	public InvalidParameterSyntaxException(String msg) {
		super(msg);
	}

}
