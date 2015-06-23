package main;

public class DoubleDeclarationInScopeException extends SJavacException {
	
	private final static String defaultMessage = "You tried to declare a variable in a scope where it "
			+ "was already declared.";
	
	public DoubleDeclarationInScopeException() {
		super(defaultMessage);
	}
	
	public DoubleDeclarationInScopeException(String msg) {
		super(msg);
	}
	
}
