package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

/**  This exception is thrown when a method is called with the incorrect number of args.  
 * @author Joshua Voss shanam 
 *
 */
public class IncorrectNumArgsException extends SJavacException {


	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Method call was made with a correct method name but incorrect"
			+ " number of arguments.";
	
	/** Creates an exception with the default message. 
	 * 
	 */
	public IncorrectNumArgsException() {
		super(defaultMsg);
	}

	/** Creates an exception with a custom message.  
	 * @param msg custom message.  
	 */
	public IncorrectNumArgsException(String msg) {
		super(msg);
	}

}
