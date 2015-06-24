package types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Boolean extends Type {
	
	Pattern booleanRegex = Pattern.compile("true|false|(\\-?[0-9]+(\\.{1}+[0-9]+)?)");
	
	public Boolean(boolean isFinal){
		super(isFinal);
	}

	public Boolean(String name, String value, int depth, boolean isFinal) throws InvalidValueException {
		super(name, value, depth, isFinal);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean doesValueMatchType(String value) throws InvalidValueException {
		Matcher booleanMatch = booleanRegex.matcher(value);
		if(booleanMatch.matches()){
			return true;
		}
		throw new InvalidValueException();
	}

}
