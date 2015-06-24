package main;

/**
 * @author Joshua Voss
 *
 */
public class WrongMethodNameException extends SJavacException {

	private final static String defaultMsg = "A method name was called that doesn't exist.";

	private static final long serialVersionUID = 1L;

	/**Identical behavior to super's methods.
	 * 
	 */
	public WrongMethodNameException() {
		super(defaultMsg);
	}

	/** Identical behavior to super's methods.
	 * @param msg
	 */
	public WrongMethodNameException(String msg) {
		super(msg);

	}

}
