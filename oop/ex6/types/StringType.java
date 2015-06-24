package oop.ex6.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringType extends Type {

	private static final Pattern stringRegex = Pattern.compile("\"{1}.*\"{1}");
	
	public StringType(boolean isFinal){
		super(isFinal);
	}
	
	public StringType(String name, String value, int depth, boolean isFinal) throws InvalidValueException,
	UninitializedFinalVariableException {
		super(name, value, depth, isFinal);
		// TODO Auto-generated constructor stub
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



}
