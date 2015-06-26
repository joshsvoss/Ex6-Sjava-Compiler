package oop.ex6.scopes;

import oop.ex6.main.SJavacException;

public abstract class Scope {
	
	// The scope name (a method name, if, or while).
	protected String name;
	
	// The depth of the current scope.
	protected int depth; //TODO we might not need this now that depth is handled in parser
	
	// The current scope's params.
	protected String params;
	
	public Scope(String name, String params, int depth){
		this.name = name;
		this.depth = depth;
		this.params = params;
		
	}
	
	// TODO may want to try catch in method..
	public abstract boolean checkParamLogic(String params, int depth) throws SJavacException;
	
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
