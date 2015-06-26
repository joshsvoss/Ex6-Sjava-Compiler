
package oop.ex6.main;

/** This exception is thrown when a variable is declared with incorrect syntax.  
 * @author Shana M
 *
 */
public class IncorrectVariableSyntaxException extends SJavacException {

	private static final long serialVersionUID = 1L;

	private static final String defaultMsg = "A variable was declared with an invalid syntax.";

	/** Identical to Exception's constructor.
	 * 
	 */
	public IncorrectVariableSyntaxException() {
		super(defaultMsg);
	}

	/** This passes the message up the stack.
	 * @param msg message to be passed up.
	 */
	public IncorrectVariableSyntaxException(String msg) {
		super(msg);
	}

}
