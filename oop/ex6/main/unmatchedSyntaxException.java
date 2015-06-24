package oop.ex6.main;

/** This exception is thrown when the line parsed doesn't 
 * match any of our regex formulas, and therefore must be incorrect.
 * @author Joshua Voss
 *
 */
public class unmatchedSyntaxException extends SJavacException {
	
	private static final long serialVersionUID = 1L;

	public unmatchedSyntaxException() {
		super();
	}
	
	public unmatchedSyntaxException(String msg) {
		super(msg);
	}
}
