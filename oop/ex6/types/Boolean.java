package oop.ex6.types;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Boolean extends Type {
	
	private static final Pattern booleanRegex = Pattern.compile("(true|false|(\\-?[0-9]+(\\.{1}+[0-9]+)?))");
	
	/** This constructor is for making Type instances out of method declaration's paramaters.
	 * @param isFinal this is passed as true
	 * @param name
	 */
	public Boolean(boolean isFinal, String name){
		super(isFinal, name); 
	}

	public Boolean(String name, String value, int depth, boolean isFinal) throws InvalidValueException,
	UninitializedFinalVariableException, AssignmentFromUninitializedVarException {
		super(name, value, depth, isFinal);
	}

	@Override
	public boolean doesValueMatchType(String value) throws InvalidValueException {
		Matcher booleanMatch = booleanRegex.matcher(value);
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
