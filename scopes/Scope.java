package scopes;

import types.InvalidValueException;

public abstract class Scope {
	
	// The scope name (a method name, if, or while).
	protected String name;
	
	// The depth of the current scope.
	protected int depth;
	
	// The current scope's params.
	protected String params;
	
//	// Regex to single out the scope params.
//	//TODO remove dealt with elsewhere.
//	protected static final String findParams = ".*[(]{1}\\s*|\\s*[)]{1}.*"; 
	
	public Scope(String name, String params, int depth){
		this.name = name;
		this.depth = depth;
		this.params = params;
		
	}
	
	// TODO may want to try catch in method..
	public abstract boolean checkParamLogic(String params) throws InvalidValueException;
	
	/**
	 * @return the scope name.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * @return the scope depth.
	 */
	public int getdepth(){
		return this.depth;
	}
}
