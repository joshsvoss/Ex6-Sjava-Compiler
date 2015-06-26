package oop.ex6.main;

/** This exception is thrown when the parser encounters a closing bracket in the global scope.
 * @author Joshua Voss, shanam
 *
 */
public class GlobalClosingBracketException extends SJavacException {
	

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "There was a closing bracket in the global scope."
			+ "This cannot happen since Sjava doesn't support classes.  All closing brackets"
			+" must either be closing a method or closing something inside a method.";

	/** Identical to parent's constructor.
	 * 
	 */
	public GlobalClosingBracketException() {
		super(defaultMsg);
	}

	/** This constructor passes the method up the stack.
	 * @param message the string to be passed up.
	 */
	public GlobalClosingBracketException(String message) {
		super(message);
	}


}
