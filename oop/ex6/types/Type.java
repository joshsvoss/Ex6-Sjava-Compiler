package oop.ex6.types;

import java.util.HashMap;
import java.util.Vector;

import oop.ex6.main.Parser;
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
	public Type(boolean isFinal, String name){
		this.isFinal = isFinal;
		this.name = name;
		this.value = null;
		
		//Because only method parameters are being constructed, and they are treated as if they are
		// already initialized, set initialized to true.
		this.isInitialized = true;
	}
	
	public Type(String name, String value, int depth, boolean isFinal) 
			throws InvalidValueException, UninitializedFinalVariableException, AssignmentFromUninitializedVarException { //TODO should isLocallyInitialized be a method too?
		
		this.declarationDepth = depth; //TODO are we using depth or does the vector of hashmaps make this superflouous?
		this.isFinal = isFinal;
		this.name = name;
		
		// If a final variable is not initialized at declaration throw an exception.
		if(this.isFinal && value == null){
			throw new UninitializedFinalVariableException();
		}
		
		if ((value != null)) {
			// first check if "value" isn't a literal, but rather is a reference to a symbol (variable)
			Type foundType = lookupPossibleSymbol(value);
			if (foundType != null) {
				// That means we found a symbol in our lookup, but we have to make sure
				// It's initialized and is Matim.
				if(!foundType.isInitialized) {
					throw new AssignmentFromUninitializedVarException();
				}
				// If it is initialized, check that the type matchs
				boolean isMatch = doesTargetTypeMatchSource(foundType);
				if (!isMatch) {
					throw new InvalidValueException();
				}

				// Otherwise, we can assign the value from the source to the target variable:
				// If found type value is null, because created in the method declaration.
				if(foundType.getValue() == null){
					this.value = null;
			}
				else{
				this.value = new String(foundType.getValue());
				this.isInitialized = foundType.isInitialized();
				}
			}
			
			// Only try to match if value isn't null:
			else if (this.doesValueMatchType(value)) {
				this.value = value;
				this.isInitialized = true;
			}
			// TODO delet below vars if don't end up using.
			//this.isInitialized = isInitialized;
			//this.isLocallyInitialized = isLocallyInitialized;
		}
		
	}
	
	protected abstract boolean doesTargetTypeMatchSource(Type foundType);

	/** This method takes the value in an assignment statement, and checks to see if it's a symbol
	 * instead of a literal.  
	 * 
	 */
	private Type lookupPossibleSymbol(String value) {
		// Start from most recent scope and go down to global:
		
		Vector<HashMap<String, Type>> list = Parser.getSymbolTableList();
		
		for (int i = this.declarationDepth; i >= Parser.GLOBAL_DEPTH; i--) {
			Type foundType = list.elementAt(i).get(value);
			
			if (foundType != null) {
				 // That means we found a symbol of that name. All that remains is to make sure
				// it's initialized and the source type and be assigned to the target type.
				return foundType;
			}
		}
		
		return null;
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
		return this.isInitialized;
	}
	public boolean isLocallyInitialized(){
		// TODO write this method. Do we need this?
		return false;
	}
	
	
	public Type copyVar() throws InvalidValueException, UninitializedFinalVariableException, AssignmentFromUninitializedVarException{
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
