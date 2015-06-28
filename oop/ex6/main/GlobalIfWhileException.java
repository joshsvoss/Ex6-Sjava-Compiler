package oop.ex6.main;

/** This exception is thrown when the parser encounters an if or while block in the global scope.
 * @author Joshua Voss, shanam
 *
 */
public class GlobalIfWhileException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "An if/ while block was in the global scope, "
			+ "this is not allowed.";
	
	/** 
	 * Creates an exception with a default message.
	 */
	public GlobalIfWhileException() {
		super(defaultMsg);
	}
	
	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up.
	 */
	public GlobalIfWhileException(String msg) {
		super(msg);
	}
	
	

}
