package oop.ex6.main;

/** This exception is thrown when the sjava program attempts to assign a value to a variable
 * that hasn't been declared.  
 * @author Joshua Voss
 *
 */
public class UndeclaredAssignmentException extends SJavacException {


	private static final long serialVersionUID = 1L;
	
	private final static String defaultMsg = "You tried to assign a value to a variable "
			+ "that wasn't declared.";
			
	
	/** Creates an exception with the default message.  
	 * 
	 */
	public UndeclaredAssignmentException() {
		super(defaultMsg);
	}

	/** Creates an exception with the custom message.
	 * @param msg
	 */
	public UndeclaredAssignmentException(String msg) {
		super(msg);
	}

}
