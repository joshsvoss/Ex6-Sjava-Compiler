package scopes;

public abstract class Scope {
	
	// The data for the current scope.
	protected String scopeInit;
	
	// The depth of the current scope.
	protected int depth;
	
	// The current scope's params.
	protected String[] params;
	
	// Regex to single out the scope params.
	protected static final String findParams = "(\\s*.*[(]{1}\\s*)|(\\s*[)]{1}.*\\s*)"; 
	
	public Scope(String currLn, int depth){
		this.scopeInit = currLn;
		this.depth = depth;
		this.params = currLn.split(findParams);
	}
	
	public abstract void updateLogic();
	
	public abstract boolean checkParamLogic(String params);
	
	public abstract String getName();
	
	public int getdepth(){
		return this.depth;
	}
}
