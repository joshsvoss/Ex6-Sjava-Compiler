package oop.ex6.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Double extends Type{
	
	private static final Pattern doubleRegex = Pattern.compile("-?[0-9]+(\\.{1}+[0-9]+)?");
	
	public Double(boolean isFinal){
		super(isFinal);
	}

	public Double(String name, String value, int depth, boolean isFinal) throws InvalidValueException, 
	UninitializedFinalVariableException, AssignmentFromUninitializedVarException {
		super(name, value, depth, isFinal);
	}

	@Override
	public boolean doesValueMatchType(String value)
			throws InvalidValueException {
		Matcher doubleMatch = doubleRegex.matcher(value);
		if(doubleMatch.matches()){
			return true;
		}
		throw new InvalidValueException();
	}
	
	@Override
	protected boolean doesTargetTypeMatchSource(Type foundType) {
		return (foundType instanceof Double) || (foundType instanceof Int); 
	}

}
