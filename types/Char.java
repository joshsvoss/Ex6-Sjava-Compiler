package types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Char extends Type {

	private Pattern charRegex = Pattern.compile("\\'{1}+.{1}+\\'{1}");
	
	public Char(boolean isFinal){
		super(isFinal);
	}
	
	public Char(String name, String value, int depth, boolean isFinal) throws InvalidValueException {
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
		throw new InvalidValueException("The given value does not fit the variable type to which it is "
				+ "being assigned.");
	}

}
