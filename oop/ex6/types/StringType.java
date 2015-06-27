package oop.ex6.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class implements the object StringType.  This class is used to create object that represent
 * variable of the type String.   The name "StringType" was used to avoid namespace collision with
 * the Java class String.
 * 
 * @author Joshua Voss, shanam
 *
 */
public class StringType extends Type {

	private static final String LEGAL_STRING_VALUE_STRING = "\"{1}.*\"{1}";
	
	private static final Pattern LEGAL_STRING_VALUE_REGEX = Pattern.compile(LEGAL_STRING_VALUE_STRING);
	
	/** This constructor is for making StringType Type instances out of method declaration's parameters.
	 * 
	 * @param isFinal this is passed as true
	 * @param name the name of the variable.
	 */
	public StringType(boolean isFinal, String name){
		super(isFinal, name);
	}
	
	/**The constructor for creating a StringType Type variable, upon declaration.
	 * 
	 * @param name the name of the variable.
	 * @param value the value of the variable.
	 * @param depth the depth at which the variable was declared.
	 * @param isFinal keeps track of whether the variable is final.
	 * @throws InvalidValueException thrown when an illegal value is assigned to the variable.
	 * @throws UninitializedFinalVariableException thrown when a final variable is declared without
	 * initialization.
	 * @throws AssignmentFromUninitializedVarException thrown when the an uninitialized variable is assigned 
	 * to a different variable.
	 */
	public StringType(String name, String value, int depth, boolean isFinal) throws InvalidValueException,
	UninitializedFinalVariableException, AssignmentFromUninitializedVarException {
		super(name, value, depth, isFinal);
	}

	@Override
	public boolean doesValueMatchType(String value)
			throws InvalidValueException {
		Matcher stringMatch = LEGAL_STRING_VALUE_REGEX.matcher(value);
		if(stringMatch.matches()){
			return true;
		}
		throw new InvalidValueException();
	}
	
	@Override
	protected boolean doesTargetTypeMatchSource(Type foundType) {
		return foundType instanceof StringType; 
	}



}
