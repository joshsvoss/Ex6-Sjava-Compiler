/**
 * 
 */
package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

/**
 * @author Shana M
 *
 */
public class UninitializedVariableUsedException extends SJavacException {

	private static final long serialVersionUID = 1L;

	private static final String defaultMsg = "An uninitialized variable was used in an if/while block.";

	/**
	 * 
	 */
	public UninitializedVariableUsedException() {
		super(defaultMsg);
	}

	/**
	 * @param msg
	 */
	public UninitializedVariableUsedException(String msg) {
		super(msg);
	}

}
