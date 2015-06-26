package oop.ex6.main;

/** This exception is thrown when the parser encounters an if or while block in the global scope.
 * @author Joshua Voss, shanam
 *
 */
public class GlobalIfWhileException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "An if/ while block was in the global scope, "
			+ "this is not allowed.";
	
	/** Identical to parent's constructor.
	 * 
	 */
	public GlobalIfWhileException() {
		super(defaultMsg);
	}
	
	/** This method passes a message up the stack.
	 * @param msg the message to be passed.  
	 */
	public GlobalIfWhileException(String msg) {
		super(msg);
	}
	
	

}
