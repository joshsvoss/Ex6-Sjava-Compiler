package types;

public abstract class Type {
	
	// Common fields to all of the types
	private int declarationDepth;  //TODO need this with the vector of symbol tables?
	private boolean isFinal;
	private String name;
	private String value;
	private int currInitializationDepth;
	//TODO delete below variables if don't use.
	//private boolean isInitialized; //TODO change this to method?
	//private boolean isLocallyInitialized; //TODO remember what this is for?
	
	public Type(String name, String value, int depth, boolean isFinal) throws InvalidValueException { //TODO should isLocallyInitialized be a method too?
		
		this.declarationDepth = depth;
		this.isFinal = isFinal;
		this.name = name;
		if(doesValueMatchType(value)){
			this.value = value;
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
		if(isFinal){
			if(doesValueMatchType(value)){
				this.value = value;
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
		return !this.name.equals(null);
	}
	public boolean isLocallyInitialized(){
		// TODO write this method. Do we need this?
		return false;
	}

}
