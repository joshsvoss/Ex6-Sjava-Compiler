package oop.ex6.types;

import oop.ex6.main.SJavacException;

/** This class has the job of received a string that defines what type to create,
 * and trying to create an instance of the corresponding type.
 * 
 * If the String passed in does not match anything, an InvalidTypeException is thrown.
 * 
 * @author Joshua Voss, shanam
 *
 */
public class TypeFactory {

	private static boolean isFinal;
	
	/** This  method returns the correct Type based on the type String.
	 * 
	 * @param finalStr the part of the line where "final" would be
	 * @param type substring with the typename
	 * @param name substring with the name of the variable.
	 * @param value substring with the value assigned to the variable.
	 * @param depth the depth in the stack that this 
	 * @return a reference to an instance of the correct subtype of Type
	 * @throws SJavacException
	 */
	public static Type generateType(String finalStr, String type, String name, String value, int depth)
			throws SJavacException {
		isFinal = false;
				
		if(finalStr != null){
			isFinal = true;
		}
		
		Type toReturn;
		switch(type){
			case "boolean":
				toReturn = new Boolean(name, value, depth, isFinal);
				break;
			case "char":
				toReturn = new Char(name, value, depth, isFinal);
				break;
			case "int":
				toReturn = new Int(name, value, depth, isFinal);
				break;
			case "double":
				toReturn = new Double(name, value, depth, isFinal);
				break;
			case "String":
				toReturn = new StringType(name, value, depth, isFinal);
				break;
			default:
				throw new InvalidTypeException("Invalid type for variable declaration.");
		}
		
		isFinal = false;
		return toReturn;
		
		
		
	}
	
	/** This method returns a the correct Type of a method argument, 
	 * for the purpose of validating whether the args are valid or not.
	 * @param finalStr substring where "final" would be
	 * @param type substring where the type is defined
	 * @return a reference to an instance of the correct subclass of Type.
	 * @throws InvalidTypeException is thrown when the String type doesn't match any
	 * of the accepted type patterns.
	 */
	public static Type generateMethodParamType(String finalStr, String type, String name) throws InvalidTypeException{
		
		isFinal = false;
		
		if(finalStr != null){
			isFinal = true;
		}
		
		Type toReturn;
		switch(type){
		
			case "boolean":
				toReturn = new Boolean(isFinal, name);
				break;
			case "char":
				toReturn = new Char(isFinal, name);
				break;
			case "int":
				toReturn = new Int(isFinal, name);
				break;
			case "double":
				toReturn = new Double(isFinal, name);
				break;
			case "String":
				toReturn = new StringType(isFinal, name);
				break;
			default:
				throw new InvalidTypeException();
		}
		isFinal = false;
		return toReturn;
	}
	
		
}
