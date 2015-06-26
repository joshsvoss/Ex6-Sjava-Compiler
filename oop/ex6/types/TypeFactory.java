package oop.ex6.types;

import oop.ex6.main.SJavacException;

/** This class has the job of received a string that defines what type to create,
 * and trying to create an instance of the corresponding type.
 * 
 * If the String passed in does not match anything, an InvalidTypeException is thrown.
 * @author Joshua Voss
 *
 */
public class TypeFactory {

	private boolean isFinal = false;
	
	/** This  method returns the correct Type based on the type String.
	 * @param finalStr the part of the line where "final" would be
	 * @param type substring with the typename
	 * @param name substring with the name of the variable.
	 * @param value substring with the value assigned to the variable.
	 * @param depth the depth in the stack that this 
	 * @return a reference to an instance of the correct subtype of Type
	 * @throws SJavacException
	 */
	public Type generateType(String finalStr, String type, String name, String value, int depth) 
			throws SJavacException {
		if(finalStr != null){
			isFinal = true;
		}
		
		switch(type){
		
		case "boolean":
			return new Boolean(name, value, depth, isFinal);
		case "char":
			return new Char(name, value, depth, isFinal);
		case "int":
			return new Int(name, value, depth, isFinal);
		case "double":
			return new Double(name, value, depth, isFinal);
		case "String":
			return new StringType(name, value, depth, isFinal);
		default:
			break;
		}
		throw new InvalidTypeException("Invalid type for variable declaration.");
	}
	
	/** This method returns a the correct Type of a method argument, 
	 * for the purpose of validating whether the args are valid or not.
	 * @param finalStr substring where "final" would be
	 * @param type substring where the type is defined
	 * @return a reference to an instance of the correct subclass of Type.
	 * @throws InvalidTypeException is thrown when the String type doesn't match any
	 * of the accepted type patterns.
	 */
	public Type generateMethodParamType(String finalStr, String type, String name) throws InvalidTypeException{
		if(finalStr != null){
			isFinal = true;
		}
		
		switch(type){
		
		case "boolean":
			return new Boolean(isFinal, name);
		case "char":
			return new Char(isFinal, name);
		case "int":
			return new Int(isFinal, name);
		case "double":
			return new Double(isFinal, name);
		case "String":
			return new StringType(isFinal, name);
		default:
			break;
		}
		throw new InvalidTypeException();
	}
}
