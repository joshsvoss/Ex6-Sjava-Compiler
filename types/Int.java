package types;

import java.util.regex.Pattern;


public class Int extends Type {
	// fields
	private int value;   
	private boolean isInitialized;
	
	public Int(String lineString, int depth) {
		//TODO should the constuctor be responsible for both checking the syntax AND creating the logic objects?
		// TODO any reason the syntax check shouldn't be done inside the constructor?
		
		//  Get the value of the assignment statement:
		Pattern equalsSign =  Pattern.compile("\\w*=\\w*"); //TODO magic string
		String[] splitArray = lineString.split("\\w*=\\w*");
		
		// TODO The value should either be in the second spot or the last spot depending on how much we've validated before
		try {
			this.value = Integer.parseInt(splitArray[splitArray.length - 1]);
		}
		catch (NumberFormatException e) {
			// This means the value to be assigned to the int wasn't numerical
			
		}
		
	}

}
