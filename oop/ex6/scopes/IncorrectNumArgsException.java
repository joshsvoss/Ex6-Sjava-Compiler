package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

public class IncorrectNumArgsException extends SJavacException {


	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Method call was made with a correct method name but incorrect"
			+ " number of arguments.";
	
	public IncorrectNumArgsException() {
		super(defaultMsg);
	}

	public IncorrectNumArgsException(String msg) {
		super(msg);
	}

}
