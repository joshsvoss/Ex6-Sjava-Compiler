package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

public class ParameterNotInitializedException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	// TODO we're not using this exception...to delete?

	private static final String defaultMsg = "One of the parameters passed through the method is "
			+ "not initialized.";
	
	public ParameterNotInitializedException() {
		super(defaultMsg);
	}

	public ParameterNotInitializedException(String msg) {
		super(msg);
	}

}
