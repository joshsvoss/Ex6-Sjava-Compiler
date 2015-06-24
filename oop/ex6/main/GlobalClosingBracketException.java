package oop.ex6.main;

public class GlobalClosingBracketException extends SJavacException {
	

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "There was a closing bracket in the global scope."
			+ "This cannot happen since Sjava doesn't support classes.  All closing brackets"
			+" must either be closing a method or closing something inside a method.";

	public GlobalClosingBracketException() {
		super(defaultMsg);
	}

	public GlobalClosingBracketException(String message) {
		super(message);
	}


}
