/**
 * 
 */
package oop.ex6.main;

/**
 * @author Shana M
 *
 */
public class IncorrectVariableSyntaxException extends SJavacException {

	private static final long serialVersionUID = 1L;

	private static final String defaultMsg = "A variable was declared with an invalid syntax.";

	/**
	 * 
	 */
	public IncorrectVariableSyntaxException() {
		super(defaultMsg);
	}

	/**
	 * @param msg
	 */
	public IncorrectVariableSyntaxException(String msg) {
		super(msg);
	}

}
