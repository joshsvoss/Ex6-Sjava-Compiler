package types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Double extends Type{
	
	//TODO is -0 an issue?
	Pattern doubleRegex = Pattern.compile("-?[0-9]+(\\.{1}+[0-9]+)?");
	
	public Double(boolean isFinal){
		super(isFinal);
	}

	public Double(String name, String value, int depth, boolean isFinal) throws InvalidValueException {
		super(name, value, depth, isFinal);
		// TODO Auto-generated constructor stub
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

}
