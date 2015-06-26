package oop.ex6.main;

/** This exception is thrown when the line parsed doesn't 
 * match any of our regex formulas, and therefore must be incorrect.
 * @author Joshua Voss
 *
 */
public class UnmatchedSyntaxException extends SJavacException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Current line doesn't match any possible correct "
			+ "syntax regex patterns.";

	public UnmatchedSyntaxException() {
		super(defaultMsg);
	}
	
	public UnmatchedSyntaxException(String msg) {
		super(msg);
	}
}
