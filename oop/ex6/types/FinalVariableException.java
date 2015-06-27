package oop.ex6.types;

import oop.ex6.main.SJavacException;

/**This exception is thrown when a final variable is reassigned.
 * 
 * @author Shana M, Joshsvoss
 *
 */
public class FinalVariableException extends SJavacException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "A final variable cannot be reassigned.";
	
	/** 
	 * Identical to parents constructor.
	 */
	public FinalVariableException() {
		super(defaultMsg);
	}
	
	/** Identical to that of super.  
	 * @param msg message to be passed up with exception.
	 */
	public FinalVariableException(String msg) {
		super(msg);
	}

}
