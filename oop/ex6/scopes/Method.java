package oop.ex6.scopes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.Parser;
import oop.ex6.main.SJavacException;
import oop.ex6.types.InvalidTypeException;
import oop.ex6.types.Type;
import oop.ex6.types.TypeFactory;

/** This class implements the Method object, which houses all details of the method declared.
 * in the sjava file.
 * @author Joshua Voss, shanam
 *
 */
public class Method {
	


	private String params;
	
	// An array of the parameter types expected to be passed through the method.
	private Type[] paramTypesList;
	
	// The name of the method.
	private String name;
	
	// An array of the parameters in string form.
	private String[] paramsList;
	
	// Does the method have a closing braket?
	private boolean doesMethodClose;
	
	// The regex for the method parameters.
	private static final Pattern METH_PARAM = Pattern.compile("\\s*+((final{1})+\\s+)?+"
			+ "((int|boolean|char|double|String){1})+"
			+ "\\s*+("+Parser.POSSIBLE_VAR_NAMES+"+)\\s*");

	/** Constructor method.  
	 * @param name the name of the method.
	 * @param params a String that specifies the parameters the method expects.
	 * @param depth the depth in the stack of activation frames.  
	 * @throws InvalidTypeException - this exception is thrown when the type of one of the params is 
	 * invalid.
	 * @throws InvalidParameterSyntaxException this is thrown when the syntax of one of the params 
	 * is invalid. 
	 */
	public Method(String name, String params) throws InvalidTypeException,
																InvalidParameterSyntaxException {
		this.name = name;
		this.params = params;
		this.doesMethodClose = false;
		if (this.params != null) {
			this.paramsList = separateParams(this.params);
			this.paramTypesList = new Type[this.paramsList.length];
			// Create the method param types;
			for (int i = 0; i < this.paramsList.length; i++) {
				Matcher methParamMatch = METH_PARAM.matcher(this.paramsList[i]);
				
				if (methParamMatch.matches()) {
					String finalStr = methParamMatch.group(Parser.SECOND_GROUP_INDEX);
					String type = methParamMatch.group(Parser.THIRD_GROUP_INDEX);
					String paramName = methParamMatch.group(Parser.FIFTH_GROUP_INDEX);
					// create a type object of this argument, and add it to the the paramTypes list
					Type paramType = TypeFactory.generateMethodParamType(finalStr, type, paramName);
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
		return params.split(Parser.COMMA_SEPARATOR);
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


	public boolean checkParamLogic(String params, int depth) throws SJavacException {
		
		// If no params assign it as null so as to avoid differences in number of parameters.
		if(params == ""){
			params = null;
		}
	
		// If one of the two is null, they both have to be null (0 args) to match
		if(this.paramTypesList == null || params == null) {
			if (!(this.paramTypesList == null || params == null)) {
				throw new IncorrectNumArgsException();
			}
			// If both are null return true.
			else{
				return true;
			}
		}
		
		String[] paramsPassedThrough = params.split(Parser.COMMA_SEPARATOR);
		
		// First check that the number of arguments matches the number of parameters
		if (paramsPassedThrough.length != this.paramTypesList.length) {
			throw new IncorrectNumArgsException();
		}
		
		// Then make sure that their corresponding types match
		for(int i = 0; i < paramsPassedThrough.length; i++){
			boolean paramFound = false;
			for (int j = depth; j >= 0; j--) {
//				Type paramToCheck = Parser.getSymbolTableList().elementAt(j).get(paramsPassedThrough[i]); //Switch to symbollist object
				Type paramToCheck = Parser.searchSymbolTableList(paramsPassedThrough[i], j);
				if (paramToCheck != null) {
					this.paramTypesList[i].doesValueMatchType(paramToCheck.getValue());
					paramFound = true;
					break;
				}
					
				}
			if(!paramFound){
				this.paramTypesList[i].doesValueMatchType(paramsPassedThrough[i]);	
			}
		}
		return true;
	}
	
	public boolean doesMethodClose(){
		return this.doesMethodClose;
	}
	
	public void closeMethod(){
		this.doesMethodClose = true;
	}

}
