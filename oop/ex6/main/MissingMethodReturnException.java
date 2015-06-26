package oop.ex6.main;

/** This exception is thrown when a method is missing its return statement.  
 * @author Joshua Voss, shanam
 *
 */
public class MissingMethodReturnException extends SJavacException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Missing the method return statement.";

	/** Identical to that of Exception.
	 * 
	 */
	public MissingMethodReturnException() {
		super(defaultMsg);
	}

	/** This method passes the message up the stack along with the thrown exception.
	 * @param msg the message to be passed up.
	 */
	public MissingMethodReturnException(String msg) {
		super(msg);
	}

}
