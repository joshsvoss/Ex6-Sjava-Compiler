package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

/** This exception is thrown when a method is called with passing arguments that haven't yet been 
 * initialized.
 * @author Joshua Voss
 *
 */
public class ParameterNotInitializedException extends SJavacException {

	private static final long serialVersionUID = 1L;

	private static final String defaultMsg = "One of the parameters passed through the method is "
			+ "not initialized.";
	
	/** Creates an exception with the default message.  
	 * 
	 */
	public ParameterNotInitializedException() {
		super(defaultMsg);
	}

	/** Creates an exception with a custom message. 
	 * @param msg the custom message.  
	 */
	public ParameterNotInitializedException(String msg) {
		super(msg);
	}

}
