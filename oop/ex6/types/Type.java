package oop.ex6.types;

import oop.ex6.main.Parser;
import oop.ex6.main.SJavacException;

/**The abstract parent class from which all variable type classes inherit.
 * 
 * @author Shana M, Joshsvoss
 *
 */
public abstract class Type {
	
	// Common fields to all of the types
	private int declarationDepth;
	private boolean isFinal;
	private String name;
	private String value;
	private boolean isInitialized = false;
	

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
	
	/**Constructs a regular variable type.
	 * 
	 * @param name the name of the variable.
	 * @param value the value of the variable.
	 * @param depth the depth at which the variable was declared.
	 * @param isFinal keeps track of whether the variable is final.
	 * @throws InvalidValueException thrown when an illegal value is assigned to the variable.
	 * @throws UninitializedFinalVariableException thrown when a final variable is declared without
	 * initialization.
	 * @throws AssignmentFromUninitializedVarException thrown when the an uninitialized variable is assigned 
	 * to a different variable.
	 */
	public Type(String name, String value, int depth, boolean isFinal) 
			throws InvalidValueException, UninitializedFinalVariableException, 
			AssignmentFromUninitializedVarException {
		
		this.declarationDepth = depth;
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
				// If it is initialized, check that the type matches
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
		}
		
	}
	
	/**Check if the variable type found matches the type to which it is being assigned.
	 * 
	 * @param foundType the Type found from the SymbolTableList.
	 * @return true or false depending on whether or not it is the correct type.
	 */
	protected abstract boolean doesTargetTypeMatchSource(Type foundType);

	/** This method takes the value in an assignment statement, and checks to see if it's a symbol
	 * instead of a literal. 
	 */
	private Type lookupPossibleSymbol(String value) {
		
		// Start from most recent scope and go down to global:		
		for (int i = this.declarationDepth; i >= Parser.GLOBAL_DEPTH; i--) {
			
			Type foundType = Parser.searchSymbolTableList(value, i);
			
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
	
	/**Reassign the variable with the given value.
	 * 
	 * @param value the value to be assigned.
	 * @throws SJavacException thrown when the variable to which the new value is being assigned is final.
	 */
	public void setValue(String value) throws SJavacException {
		if(!isFinal){
			//If the variable isn't final check if the value is the correct type.
			//make isInitialized true.
			if(doesValueMatchType(value)){
				this.value = value;
				this.isInitialized = true;
			}
		//If the variable is final, throw an exception, since final variables can't be reassigned.	
		}else{
			throw new FinalVariableException();
		}
	}
	
	/**
	 * @return the depth at which the variable was declared.
	 */
	public int getDeclarationDepth(){
		return this.declarationDepth;
	}
	
	/**Checks if the given value is legal for the type to which it is being assigned.
	 * @param value the value being checked.
	 * @return true if the given value is legal.
	 * @throws InvalidValueException thrown if the value is illegal.
	 */
	public abstract boolean doesValueMatchType(String value) throws InvalidValueException;
		
	
	/**
	 * @return true if the variable is initialized, otherwise false.
	 */
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	
	/**Make a copy of the variable Type.
	 * 
	 * @return a copy of the variable Type.
	 * @throws InvalidValueException thrown when an illegal value is assigned to the variable.
	 * @throws UninitializedFinalVariableException thrown thrown when a final variable is declared without 
	 * initialization.
	 * @throws AssignmentFromUninitializedVarException thrown when an uninitialized variable is assigned to 
	 * a different variable.
	 */
	public Type copyVar() throws InvalidValueException, UninitializedFinalVariableException,
	AssignmentFromUninitializedVarException{
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
