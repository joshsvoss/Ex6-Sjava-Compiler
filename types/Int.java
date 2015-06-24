package types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Int extends Type {
	
	private static final Pattern intRegex = Pattern.compile("-?[0-9]+");
	
	public Int(boolean isFinal){
		super(isFinal);
	}
	
	public Int(String name, String value, int depth, boolean isFinal) throws InvalidValueException {
		super(name, value, depth, isFinal);
		// TODO Auto-generated constructor stub
	}

// TODO remove the below comment.	
//	// fields
//	private int value;   
//	private boolean isInitialized;

//	public Int(String lineString, int depth) {
//		//TODO should the constuctor be responsible for both checking the syntax AND creating the logic objects?
//		// TODO any reason the syntax check shouldn't be done inside the constructor?
//		
//		//  Get the value of the assignment statement:
//		Pattern equalsSign =  Pattern.compile("\\w*=\\w*"); //TODO magic string
//		String[] splitArray = lineString.split("\\w*=\\w*");
//		
//		// TODO The value should either be in the second spot or the last spot depending on how much we've validated before
//		try {
//			this.value = Integer.parseInt(splitArray[splitArray.length - 1]);
//		}
//		catch (NumberFormatException e) {
//			// This means the value to be assigned to the int wasn't numerical
//			
//		}
//		
//	}

	@Override
	public boolean doesValueMatchType(String value)
			throws InvalidValueException {
		Matcher intMatch = intRegex.matcher(value);
		if(intMatch.matches()){
			return true;
		}
		throw new InvalidValueException();
	}

}
