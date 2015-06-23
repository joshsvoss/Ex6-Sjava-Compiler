package types;

public abstract class Type {
	
	// Common fields to all of the types
	private int declarationDepth;  //TODO need this with the vector of symbol tables?
	private boolean isFinal;
	private String name;
	private String value;
	private int currInitializationDepth;
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
	
	public Type(String name, String value, int depth, boolean isFinal) throws InvalidValueException { //TODO should isLocallyInitialized be a method too?
		
		this.declarationDepth = depth;
		this.isFinal = isFinal;
		this.name = name;
		if(doesValueMatchType(value)){
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
	
	public void setValue(String value, int depth) throws FinalVariableException, InvalidValueException{
		if(!isFinal){
			if(doesValueMatchType(value)){
				this.value = value;
				this.isInitialized = true;
				this.currInitializationDepth = depth;
			}
		}else{
			throw new FinalVariableException("A final variable cannot be reassigned.");
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

}
