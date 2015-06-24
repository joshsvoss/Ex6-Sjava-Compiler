package oop.ex6.types;

import oop.ex6.main.SJavacException;

public abstract class Type {
	
	// Common fields to all of the types
	private int declarationDepth;  //TODO need this with the vector of symbol tables?
	private boolean isFinal;
	private String name;
	private String value;
	private int currInitializationDepth; //TODO we dont' need this now that we have a symbol table to EACH depth
	private boolean isInitialized = false;
	//TODO delete below variables if don't use.
	//private boolean isLocallyInitialized; //TODO remember what this is for?
	
	
	/**
	 * Method parameter type initialization.
	 * 
	 * @param isFinal
	 */
	public Type(boolean isFinal){
		this.isFinal = isFinal;
		this.name = null;
		this.value = null;
	}
	
	public Type(String name, String value, int depth, boolean isFinal) throws InvalidValueException, 
	UninitializedFinalVariableException { //TODO should isLocallyInitialized be a method too?
		
		this.declarationDepth = depth;
		this.isFinal = isFinal;
		this.name = name;
		
		// If a final variable is not initialized at declaration throw an exception.
		if(this.isFinal && value == null){
			throw new UninitializedFinalVariableException();
		}
		
		// Only try to match if value isn't null:
		if ((value != null) && this.doesValueMatchType(value)) {
			this.value = value;
			this.isInitialized = true;
		}
		// TODO delet below vars if don't end up using.
		//this.isInitialized = isInitialized;
		//this.isLocallyInitialized = isLocallyInitialized;
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public void setValue(String value) throws SJavacException {
		if(!isFinal){
			if(doesValueMatchType(value)){
				this.value = value;
				this.isInitialized = true;
			}
		}else{
			throw new FinalVariableException();
		}
	}
	
	public int getDeclarationDepth(){
		return this.declarationDepth;
	}
	
	public int getcurrInitializationDepth(){
		return this.currInitializationDepth;
	}
	
	public abstract boolean doesValueMatchType(String value) throws InvalidValueException;
		
	
	public boolean isInitialized(){
		return this.isInitialized();
	}
	public boolean isLocallyInitialized(){
		// TODO write this method. Do we need this?
		return false;
	}
	
	
	public Type copyVar() throws InvalidValueException, UninitializedFinalVariableException{
		// Based on the type of the Type, call it's specifc constructor
		Type toReturn;

		toReturn = null;
		if (this instanceof Int) {
			toReturn = new Int(this.name, this.value, this.declarationDepth, this.isFinal);
		}
		else if (this instanceof Boolean) {
			toReturn = new Boolean(this.name, this.value, this.declarationDepth, this.isFinal);
		}
		else if (this instanceof Char) {
			toReturn = new Char(this.name, this.value, this.declarationDepth, this.isFinal);
		}
		else if (this instanceof Double) {
			toReturn = new Double(this.name, this.value, this.declarationDepth, this.isFinal);
		}
		else if (this instanceof StringType) {
			toReturn = new StringType(this.name, this.value, this.declarationDepth, this.isFinal);
		}
	
		
		return toReturn;
	}

}
