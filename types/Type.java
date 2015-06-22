package types;

public abstract class Type {
	
	// Common fields to all of the types
	private int depth;  //TODO need this with the vector of symbol tables?
	private boolean isFinal;
	private boolean isInitialized; //TODO change this to method?
	private boolean isLocallyInitialized; //TODO remember what this is for?
	
	public Type(int depth, boolean isFinal,
			boolean isLocallyInitialized) { //TODO should isLocallyInitialized be a method too?
		
		this.depth = depth;
		this.isFinal = isFinal;
		this.isInitialized = isInitialized;
		this.isLocallyInitialized = isLocallyInitialized;
		
	}

}
