package types;

public abstract class Type {
	
	// Common fields to all of the types
	private int depth;  //TODO need this with the vector of symbol tables?
	private boolean isFinal;
	private boolean isInitialized;
	private boolean isLocallyInitialized; //TODO remember what this is for?
	
	public Type(String line, int depth) {
		
	}

}
