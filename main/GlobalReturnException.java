package main;

/** This exception is thrown when a return statement is encountered in the global scope.
 * @author Joshua Voss
 *
 */
public class GlobalReturnException extends SJavacException {

	private final static String defaultMsg = "A return statement can't appear in the global scope.";

	private static final long serialVersionUID = 1L;

	/** Identical to super's constructor.
	 * 
	 */
	public GlobalReturnException() {
		super(defaultMsg);
	}

	/** Identical to super's constructor.
	 * @param msg message to be passed up.
	 */
	public GlobalReturnException(String msg) {
		super(msg);
	}

}
