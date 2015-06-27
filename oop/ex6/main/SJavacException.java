package oop.ex6.main;

/** This is the parent exception for all exceptions of Sjava syntax nature.  All exceptions 
 * that are thrown because of a problem with the Sjava program specified in the cmd lin argument
 * extend this exception.  
 * @author Joshua Voss, shanam
 *
 */
public class SJavacException extends Exception {

	private static final long serialVersionUID = 1L;

	/** Creates an exception.  
	 * 
	 */
	public SJavacException() {
		super();
	}
	
	/** Creates an exception this the message msg.
	 * @param msg
	 */
	public SJavacException(String msg) {
		super(msg);
	}

}
