package main;

public class GlobalIfWhileException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "An if/ while block was in the global scope, "
			+ "this is not allowed.";
	
	public GlobalIfWhileException() {
		super(defaultMsg);
	}
	
	public GlobalIfWhileException(String msg) {
		super(msg);
	}
	
	

}
