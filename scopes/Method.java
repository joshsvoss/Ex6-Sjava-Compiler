package scopes;

import types.Type;

public class Method extends Scope{
	private Type[] argArray;
	private String name;


	public Method(String line) {
		checkSyntax(line);
		
		updateLogic();
		
		
	}
	
	
	
	
	private void updateLogic() {
		
		
	}




	private void checkSyntax(String line) {
		// TODO Auto-generated method stub
		
	}




	public Type[] getArgs() {
		
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
