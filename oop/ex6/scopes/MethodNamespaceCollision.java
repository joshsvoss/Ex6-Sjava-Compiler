package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

public class MethodNamespaceCollision extends SJavacException {
	

	private static final long serialVersionUID = 1L;
	
	
	public MethodNamespaceCollision() {
		super("ERROR: The names of two different methods were identical."); //TODO doesn't look like this exception is in use, are we accounting for this?
	}
	
	public MethodNamespaceCollision(String msg) {
		super(msg);
	}
	
	
	
}
