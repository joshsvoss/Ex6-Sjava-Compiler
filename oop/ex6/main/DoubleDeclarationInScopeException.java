package oop.ex6.main;

/** This Exception is to be thrown when two variable of the same name are declared in the same scope.
 * @author Joshua Voss, shanam
 * 
 *
 */
public class DoubleDeclarationInScopeException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private final static String defaultMessage = "You tried to declare a variable or method in a scope "
			+ "where it was already declared.";
	
	/** Identical to the parent's constructor.
	 * 
	 */
	public DoubleDeclarationInScopeException() {
		super(defaultMessage);
	}
	
	
	/** Passes the message string up the stack.  
	 * @param msg the message to be passed up.  
	 */
	public DoubleDeclarationInScopeException(String msg) {
		super(msg);
	}
	
}
