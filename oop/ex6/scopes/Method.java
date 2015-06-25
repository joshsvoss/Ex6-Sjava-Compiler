package oop.ex6.scopes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.Parser;
import oop.ex6.main.SJavacException;
import oop.ex6.types.InvalidTypeException;
import oop.ex6.types.Type;
import oop.ex6.types.TypeFactory;

public class Method extends Scope{
	private Type[] paramTypesList;
	private String name;
	private String[] paramsList;
	private static final int SECOND_GROUP_INDEX = 2;
	private static final int THIRD_GROUP_INDEX = 3;
	private boolean doesMethodClose;

	// The regex for a comma seperator.
	private static final String commaSeparater = "\\s*[,]{1}\\s*";
	
	// The regex for the method parameters.
	private static final Pattern methParam = Pattern.compile("\\s*+((final{1})+\\s+)?+"
			+ "((int|boolean|char|double|String){1})+"
			+ "\\s*+([a-zA-Z_]{1}+\\w*+)\\s*");
	
	// A type factory.
	TypeFactory typeFactory = new TypeFactory();

	public Method(String name, String params, int depth) throws InvalidTypeException,
	InvalidParameterSyntaxException {
		super(name, params, depth); //TODO Scope is only Method's parent.  Dissolve it and move functionality here?
		this.doesMethodClose = false;
		if (this.params != null) {
			this.paramsList = separateParams(this.params);
			this.paramTypesList = new Type[this.paramsList.length];
			// Create the method param types;
			for (int i = 0; i < this.paramsList.length; i++) {
				Matcher methParamMatch = methParam.matcher(this.paramsList[i]);
				
				if (methParamMatch.matches()) {
					String finalStr = methParamMatch.group(Parser.SECOND_GROUP_INDEX);
					String type = methParamMatch.group(Parser.THIRD_GROUP_INDEX);
					String paramName = methParamMatch.group(Parser.FIFTH_GROUP_INDEX);
					// create a type object of this argument, and add it to the the paramTypes list
					Type paramType = typeFactory.generateMethodParamType(finalStr, type, paramName);
					this.paramTypesList[i] = paramType;
				} else {
					throw new InvalidParameterSyntaxException();
				}
			}
		} else{
			this.paramTypesList = null;
		}
	}
	
	//TODO is this necessary?
	public void updateLogic() {
		
	}
	
	

	private String[] separateParams(String params){
		return params.split(commaSeparater);
	}

	// TODO we're not using this in the end right?
	private void checkSyntax(String line) {
		// TODO Auto-generated method stub
		
	}

	/** Returns the list of parameters this method declares
	 * @return an array of Type objects
	 */
	public Type[] getParamTypesList() {
		return this.paramTypesList;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public boolean checkParamLogic(String params) throws SJavacException {
	
		// If one of the two is null, they both have to be null (0 args) to match
		if(this.paramTypesList == null || params == null) {
			if ( !( this.paramTypesList == null && params == null )) {
				throw new IncorrectNumArgsException();
			}
		}
		
		String[] paramsPassedThrough = params.split(commaSeparater);
		
		// First check that the number of arguments matches the number of parameters
		if (paramsPassedThrough.length != this.paramTypesList.length) {
			throw new IncorrectNumArgsException();
		}
		
		// Then make sure that their corresponding types match
		for(int i = 0; i < paramsPassedThrough.length; i++){
//			isParamLocallyInitialized(paramsPassedThrough[i]); //TODO according to our test, you can pass an uninitialized arg to a method! so no need!
			this.paramTypesList[i].doesValueMatchType(paramsPassedThrough[i]);
		}
		return true;
	}
	
	//TODO why is this never used?
	private boolean isParamLocallyInitialized(String paramName) throws ParameterNotInitializedException{
		// TODO search for variable, if it isn't locally initialized (or doesn't exist) throw the exception below, else return true.
		throw new ParameterNotInitializedException();
	}
	
	public boolean doesMethodClose(){
		return this.doesMethodClose;
	}
	
	public void closeMethod(){
		this.doesMethodClose = true;
	}

}
