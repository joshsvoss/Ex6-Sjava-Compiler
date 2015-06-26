package oop.ex6.main;

public class MissingMethodReturnException extends SJavacException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Missing the method return statement.";

	public MissingMethodReturnException() {
		super(defaultMsg);
	}

	public MissingMethodReturnException(String msg) {
		super(msg);
	}

}
