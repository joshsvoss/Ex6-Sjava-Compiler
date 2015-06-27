package oop.ex6.types;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**This is the class for the Boolean Type variable.
 * 
 * @author Shana M, joshsvoss
 *
 */
public class Boolean extends Type {
	
	// String used to create the legal boolean values regex.
	private static final String LEGAL_BOOLEAN_VALUES_STRING = "(true|false|(\\-?[0-9]+(\\.{1}+[0-9]+)?))";
	
	// The regex for the legal boolean values.
	private static final Pattern LEGAL_BOOLEAN_VALUES_REGEX = Pattern.compile(LEGAL_BOOLEAN_VALUES_STRING);
	
	/** This constructor is for making Boolean Type instances out of method declaration's parameters.
	 * 
	 * @param isFinal this is passed as true
	 * @param name the name of the variable.
	 */
	public Boolean(boolean isFinal, String name){
		super(isFinal, name); 
	}

	/**The constructor for creating a Boolean Type variable, upon declaration.
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
	public Boolean(String name, String value, int depth, boolean isFinal) throws InvalidValueException,
	UninitializedFinalVariableException, AssignmentFromUninitializedVarException {
		super(name, value, depth, isFinal);
	}

	@Override
	public boolean doesValueMatchType(String value) throws InvalidValueException {
		Matcher booleanMatch = LEGAL_BOOLEAN_VALUES_REGEX.matcher(value);
		if(booleanMatch.matches()){
			return true;
		}
		throw new InvalidValueException();
	}

	@Override
	protected boolean doesTargetTypeMatchSource(Type foundType) {
		return foundType instanceof Boolean; 
	}

}
