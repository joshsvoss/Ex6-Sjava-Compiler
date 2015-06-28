package oop.ex6.main;

/** This exception is thrown when a return statement is encountered in the global scope.
 * @author Joshua Voss, shanam
 *
 */
public class GlobalReturnException extends SJavacException {

	private final static String defaultMsg = "A return statement can't appear in the global scope.";

	private static final long serialVersionUID = 1L;

	/** 
	 * Creates an exception with a default message.
	 */
	public GlobalReturnException() {
		super(defaultMsg);
	}

	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up.
	 */
	public GlobalReturnException(String msg) {
		super(msg);
	}

}
