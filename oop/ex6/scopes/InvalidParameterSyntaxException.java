
package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

/** This exception is thrown when the parameter declaration of a method is invalid.  
 * @author Shana M
 *
 */
public class InvalidParameterSyntaxException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Invalid method parameter syntax.";
	
	/**
	 * 
	 */
	public InvalidParameterSyntaxException() {
		super(defaultMsg);
	}

	/**
	 * @param msg
	 */
	public InvalidParameterSyntaxException(String msg) {
		super(msg);
	}

}
