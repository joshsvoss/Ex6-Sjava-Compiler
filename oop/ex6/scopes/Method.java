package oop.ex6.scopes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.SJavacException;
import oop.ex6.types.InvalidTypeException;
import oop.ex6.types.InvalidValueException;
import oop.ex6.types.Type;
import oop.ex6.types.TypeFactory;

public class Method extends Scope{
	private Type[] paramTypes;
	private String name;
	private String[] paramsList;
	private static final int SECOND_GROUPD_INDEX = 2;
	private static final int THIRD_GROUP_INDEX = 3;
	private boolean doesMethodClose;

	// The regex for a comma seperator.
	private String commaSeparater = "\\s*[,]{1}\\s*";
	
	// The regex for the method parameters.
	Pattern methParam = Pattern.compile("\\s*+((final{1})+\\s{1})?+((int|boolean|char|double|String){1})+"
			+ "\\s*+[a-zA-Z_]{1}+\\w*+\\s*");
	
	// A type factory.
	TypeFactory typeFactory = new TypeFactory();

	public Method(String name, String params, int depth) throws InvalidTypeException {
		super(name, params, depth);
		this.doesMethodClose = false;
		if (this.params != null) {
			this.paramsList = separateParams(this.params);
			this.paramTypes = new Type[this.paramsList.length];
			// Create the method param types;
			for (int i = 0; i < this.paramsList.length; i++) {
				Matcher methParamMatch = this.methParam
						.matcher(this.paramsList[i]);
				String finalStr = methParamMatch.group(SECOND_GROUPD_INDEX);
				String type = methParamMatch.group(THIRD_GROUP_INDEX);
				Type paramType = typeFactory.generateMethodParamType(finalStr,
						type);
				this.paramTypes[i] = paramType;
			}
		} else{
			this.paramTypes = null;
		}
	}
	
	//TODO is this necessary?
	public void updateLogic() {
		
	}

	private String[] separateParams(String params){
		return params.split(commaSeparater);
	}

	// TODO necessary?
	private void checkSyntax(String line) {
		// TODO Auto-generated method stub
		
	}

	// Separates a string of the arguments into an array of types.
	public Type[] getParamTypes() {
		return this.paramTypes;
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
		if(this.paramTypes == null || params == null) {
			if ( !( this.paramTypes == null && params == null )) {
				throw new IncorrectNumArgsException();
			}
		}
		
		String[] paramsPassedThrough = params.split(commaSeparater);
		
		// First check that the number of arguments matches the number of parameters
		if (paramsPassedThrough.length != this.paramTypes.length) {
			throw new IncorrectNumArgsException();
		}
		
		// Then make sure that their corresponding types match
		for(int i = 0; i < paramsPassedThrough.length; i++){
//			isParamLocallyInitialized(paramsPassedThrough[i]); //TODO according to our test, you can pass an uninitialized arg to a method! so no need!
			this.paramTypes[i].doesValueMatchType(paramsPassedThrough[i]);
		}
		return true;
	}
	
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
