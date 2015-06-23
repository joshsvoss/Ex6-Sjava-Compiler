package main;

public class NestedMethodDeclarationException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private final static String defaultMsg = "In Sjava, you can't define a method inside another. ";
	
	
	public NestedMethodDeclarationException() {
		super(defaultMsg);
	}

	public NestedMethodDeclarationException(String msg) {
		super(msg);
	}

}
