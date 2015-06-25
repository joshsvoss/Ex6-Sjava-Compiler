package oop.ex6.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Char extends Type {

	private static final Pattern charRegex = Pattern.compile("\\'{1}+.{1}+\\'{1}");
	
	public Char(boolean isFinal, String name){
		super(isFinal, name);
	}
	
	public Char(String name, String value, int depth, boolean isFinal) throws InvalidValueException,
	UninitializedFinalVariableException, AssignmentFromUninitializedVarException {
		super(name, value, depth, isFinal);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean doesValueMatchType(String value)
			throws InvalidValueException {
		Matcher charMatch = charRegex.matcher(value);
		if(charMatch.matches()){
			return true;
		}
		throw new InvalidValueException();
	}
	
	@Override
	protected boolean doesTargetTypeMatchSource(Type foundType) {
		return foundType instanceof Char; 
	}

}
