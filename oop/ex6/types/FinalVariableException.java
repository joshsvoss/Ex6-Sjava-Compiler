package oop.ex6.types;

import oop.ex6.main.SJavacException;

public class FinalVariableException extends SJavacException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "A final variable cannot be reassigned.";

	public FinalVariableException() {
		super(defaultMsg);
	}

	public FinalVariableException(String msg) {
		super(msg);
	}

}
