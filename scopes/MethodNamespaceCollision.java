package scopes;

import main.SJavacException;

public class MethodNamespaceCollision extends SJavacException {
	

	private static final long serialVersionUID = 1L;
	
	
	public MethodNamespaceCollision() {
		super("ERROR: The names of two different methods were identical.");
	}
	
	public MethodNamespaceCollision(String msg) {
		super(msg);
	}
	
	
	
}
