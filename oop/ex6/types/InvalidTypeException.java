package oop.ex6.types;

import oop.ex6.main.SJavacException;

public class InvalidTypeException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Invalid type for variable declaration.";

	public InvalidTypeException() {
		super(defaultMsg);
	}

	public InvalidTypeException(String msg) {
		super(msg);
	}

}
