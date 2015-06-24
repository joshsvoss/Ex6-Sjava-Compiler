package oop.ex6.main;

public class DoubleDeclarationInScopeException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private final static String defaultMessage = "You tried to declare a variable or method in a scope "
			+ "where it was already declared.";
	
	public DoubleDeclarationInScopeException() {
		super(defaultMessage);
	}
	
	public DoubleDeclarationInScopeException(String msg) {
		super(msg);
	}
	
}
