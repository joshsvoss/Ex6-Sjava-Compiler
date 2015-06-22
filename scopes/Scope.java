package scopes;

public abstract class Scope {
	
	// The data for the current scope.
	protected String scopeInit;
	
	// The depth of the current scope.
	protected int depth;
	
	// The current scope's params.
	protected String params;
	
	// The 
	
	// Regex to single out the scope params.
	protected static final String findParams = ".*[(]{1}\\s*|\\s*[)]{1}.*"; 
	
	public Scope(String currLn, int depth){
		this.scopeInit = currLn;
		this.depth = depth;
		String[] tempParams = currLn.split(findParams);
		this.params = tempParams[1];
		
	}
	
	public abstract void updateLogic();
	
	public abstract boolean checkParamSyntax(String params);
	
	public abstract String getName();
	
	public int getdepth(){
		return this.depth;
	}
}
