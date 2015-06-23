package main;

public class UndeclaredAssignmentException extends SJavacException {


	private static final long serialVersionUID = 1L;
	
	private final static String defaultMsg = "You tried to assign a value to a variable "
			+ "that wasn't declared.";
			
	
	public UndeclaredAssignmentException() {
		super(defaultMsg);
	}

	public UndeclaredAssignmentException(String msg) {
		super(msg);
	}

}
