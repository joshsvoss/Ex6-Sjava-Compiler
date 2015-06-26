package oop.ex6.main;

/** This exception is thrown when a method is declared inside another.  
 * @author Joshua Voss
 *
 */
public class NestedMethodDeclarationException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private final static String defaultMsg = "In Sjava, you can't define a method inside another. ";
	
	
	/** Creates exception with default message. 
	 * 
	 */
	public NestedMethodDeclarationException() {
		super(defaultMsg);
	}

	
	/** Creates an exception with a custom message.
	 * @param msg custom message. 
	 */
	public NestedMethodDeclarationException(String msg) {
		super(msg);
	}

}
