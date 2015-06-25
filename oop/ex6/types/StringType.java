package oop.ex6.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class implements the object StringType.  This class is used to create object that represent
 * variable of the type String.   The name "StringType" was used to avoid namespace collision with
 * the Java class String.
 * @author Joshua Voss
 *
 */
public class StringType extends Type {

	private static final Pattern stringRegex = Pattern.compile("\"{1}.*\"{1}");
	
	public StringType(boolean isFinal, String name){
		super(isFinal, name);
	}
	
	public StringType(String name, String value, int depth, boolean isFinal) throws InvalidValueException,
	UninitializedFinalVariableException, AssignmentFromUninitializedVarException {
		super(name, value, depth, isFinal);
	}

	@Override
	public boolean doesValueMatchType(String value)
			throws InvalidValueException {
		Matcher stringMatch = stringRegex.matcher(value);
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
