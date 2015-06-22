package scopes;

import java.util.regex.Pattern;

import types.Type;
import types.TypeFactory;

public class Method extends Scope{
	private Type[] argArray;
	private String name;
	
	// The regex for a comma seperator.
	// TODO prob to delete, dealt with elsewhere.
	private String commaSep = "\\s*[,]*\\s*";
	
	// The regex for the method parameters.
	// TODO prob to delete, dealt with elsewhere.
	public static final String methParam = "(\\s*+(final{1}+\\s{1})?+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*+([,]{1}+\\s*+(final{1}+\\s{1})?+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*)*)*";


	public Method(String line, int depth) {
		super(line, depth);
		checkSyntax(line);
		
		updateLogic();
		
	}
	
	public void updateLogic() {
		
	}




	private void checkSyntax(String line) {
		// TODO Auto-generated method stub
		
	}



	// Separates a string of the arguments into an array of types.
	public Type[] getArgs() {
		if(checkParamSyntax(this.params)){
			String[] sepParams = this.params.split(commaSep);
			Type[] argArray = new Type[sepParams.length];
			for(int i = 0; i < sepParams.length; i++){
				TypeFactory typeFactory = new TypeFactory();
				Type paramType = typeFactory.generateType(sepParams[i], this.depth);
				argArray[i] = paramType;
				}
			return argArray;
		}else{
			// Throw syntax error
			return null;
		}
				
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public boolean checkParamSyntax(String params) {
		return Pattern.matches(methParam, params);
	}

}
