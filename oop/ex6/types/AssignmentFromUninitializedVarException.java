
package oop.ex6.types;

import oop.ex6.main.SJavacException;

/** This exception is thrown when one variable was assigned the value of another which wasn't
 * initialized.  
 * @author Joshua Voss
 *
 */
public class AssignmentFromUninitializedVarException extends SJavacException {


	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "A variable was assigned a value from another variable, "
			+ "when the second variable was not yet initialized";

	/** 
	 * Creates an exception with a default message.
	 */
	public AssignmentFromUninitializedVarException() {
		super(defaultMsg);
	}
	
	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up with exception.
	 */
	public AssignmentFromUninitializedVarException(String msg) {
		super(msg);
	}

}
