
package oop.ex6.main;

/** This exception is thrown when a variable is declared with incorrect syntax.  
 * @author Shana M, Joshsvoss
 *
 */
public class IncorrectVariableSyntaxException extends SJavacException {

	private static final long serialVersionUID = 1L;

	private static final String defaultMsg = "A variable was declared with an invalid syntax.";

	/** 
	 * Creates an exception with a default message.
	 */
	public IncorrectVariableSyntaxException() {
		super(defaultMsg);
	}

	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up.
	 */
	public IncorrectVariableSyntaxException(String msg) {
		super(msg);
	}

}
