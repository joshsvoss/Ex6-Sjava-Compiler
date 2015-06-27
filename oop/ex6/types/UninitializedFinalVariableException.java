/**
 * 
 */
package oop.ex6.types;

import oop.ex6.main.SJavacException;

/**This exception is thrown when a final variable is declared without initialization.
 * @author Shana M, Joshsvoss
 *
 */
public class UninitializedFinalVariableException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Final variables must be initialized at declaration.";

	/** 
	 * Creates an exception with a default message.
	 */
	public UninitializedFinalVariableException() {
		super(defaultMsg);
	}

	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up with exception.
	 */
	public UninitializedFinalVariableException(String msg) {
		super(msg);
	}

}
