/**
 * 
 */
package oop.ex6.types;

import oop.ex6.main.SJavacException;

/**
 * @author Shana M
 *
 */
public class UninitializedFinalVariableException extends SJavacException {

	private static final long serialVersionUID = 1L;
	
	private static final String defaultMsg = "Final variables must be initialized at declaration.";

	/**
	 * 
	 */
	public UninitializedFinalVariableException() {
		super(defaultMsg);
	}

	/**
	 * @param msg
	 */
	public UninitializedFinalVariableException(String msg) {
		super(msg);
	}

}
