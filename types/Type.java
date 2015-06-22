package types;

public abstract class Type {
	
	// Common fields to all of the types
	private int depth;  //TODO need this with the vector of symbol tables?
	private boolean isFinal;
	private String name;
	private String value;
	//TODO delete below variables if don't use.
	//private boolean isInitialized; //TODO change this to method?
	//private boolean isLocallyInitialized; //TODO remember what this is for?
	
	public Type(String name, String value, int depth, boolean isFinal) { //TODO should isLocallyInitialized be a method too?
		
		this.depth = depth;
		this.isFinal = isFinal;
		this.name = name;
		this.value = value;
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
	
	public void setValue(String value) throws FinalVariableException{
		if(isFinal){
			if(doesValueMatchType(value)){
				this.value = value;
			}
		}else{
			throw new FinalVariableException("A final variable cannot be reassigned.");
		}
	}
	
	public abstract boolean doesValueMatchType(String value);
	
	public boolean isInitialized(){
		return !this.name.equals(null);
	}
	public boolean isLocallyInitialized(){
		// TODO write this method.
		return false;
	}

}
