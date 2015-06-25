package oop.ex6.types;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Int extends Type {
	
	private static final Pattern intRegex = Pattern.compile("-?[0-9]+"); //TODO Magic number?
	
	public Int(boolean isFinal, String name){
		super(isFinal, name);
	}
	
	public Int(String name, String value, int depth, boolean isFinal) throws InvalidValueException, 
	UninitializedFinalVariableException, AssignmentFromUninitializedVarException {
		super(name, value, depth, isFinal);
	}


	@Override
	public boolean doesValueMatchType(String value) throws InvalidValueException {
		
		Matcher intMatch = intRegex.matcher(value);
		if(intMatch.matches()){
			return true;
		}
		throw new InvalidValueException();
	}
	
	@Override
	protected boolean doesTargetTypeMatchSource(Type foundType) {
		return foundType instanceof Int; 
	}

}
