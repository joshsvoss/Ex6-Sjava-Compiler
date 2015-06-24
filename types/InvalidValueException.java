package types;

import main.SJavacException;

public class InvalidValueException extends SJavacException {

	private static final long serialVersionUID = 1L;

	private static final String defaultMsg = "The given value does not fit the variable type to which it is "
			+ "being assigned.";
	
	public InvalidValueException() {
		super(defaultMsg);
	}

	public InvalidValueException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
