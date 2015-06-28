package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

/** This exception is thrown when two methods have the same name.  
 * @author Joshua Voss, shanam
 *
 */
public class MethodNamespaceCollision extends SJavacException {
	

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "ERROR: The names of two different methods were identical.";
	
	
	/** 
	 * Creates an exception with a default message.
	 */
	public MethodNamespaceCollision() {
		super(defaultMsg); 
	}
	
	/** Creates an exception with a custom message.
	 * 
	 * @param msg message to be passed up with exception.
	 */
	public MethodNamespaceCollision(String msg) {
		super(msg);
	}
	
	
	
}
