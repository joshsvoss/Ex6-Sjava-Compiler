package types;

import main.SJavacException;

public class AssignmentMismatchException extends SJavacException {
	
	
	private static final long serialVersionUID = 1L;

	public AssignmentMismatchException() {
		super();
	}
	
	public AssignmentMismatchException(String msg) {
		super(msg);
	}
	

}
