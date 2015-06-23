package scopes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.SJavacException;
import types.InvalidTypeException;
import types.InvalidValueException;
import types.Type;
import types.TypeFactory;

public class Method extends Scope{
	private Type[] paramTypes;
	private String name;
	private String[] params;
	private static final int SECOND_GROUPD_INDEX = 2;
	private static final int THIRD_GROUP_INDEX = 3;

	// The regex for a comma seperator.
	private String commaSeparater = "\\s*[,]{1}\\s*";
	
	// The regex for the method parameters.
	Pattern methParam = Pattern.compile("\\s*+((final{1})+\\s{1})?+((int|boolean|char|double|String){1})+"
			+ "\\s*+[a-zA-Z_]{1}+\\w*+\\s*");
	
	// A type factory.
	TypeFactory typeFactory = new TypeFactory();

	public Method(String name, String params, int depth) throws InvalidTypeException {
		super(name, params, depth);
		this.params = separateParams(params);
		this.paramTypes = new Type[this.params.length];
		// Create the method param types;
		for(int i = 0; i < this.params.length; i++){
			Matcher methParamMatch = this.methParam.matcher(this.params[i]);
			String finalStr = methParamMatch.group(SECOND_GROUPD_INDEX);
			String type = methParamMatch.group(THIRD_GROUP_INDEX);
			Type paramType = typeFactory.generateMethodParamType(finalStr, type);
			this.paramTypes[i] = paramType;
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
		String[] paramsPassedThrough = params.split(commaSeparater);
		for(int i = 0; i < paramsPassedThrough.length; i++){
			isParamLocallyInitialized(paramsPassedThrough[i]);
			this.paramTypes[i].doesValueMatchType(paramsPassedThrough[i]);
		}
		return true;
	}
	
	private boolean isParamLocallyInitialized(String paramName) throws ParameterNotInitializedException{
		// TODO search for variable, if it isn't locally initialized (or doesn't exist) throw the exception below, else return true.
		throw new ParameterNotInitializedException("One of the parameters passed through the method is "
				+ "not initialized.");
	}

}
