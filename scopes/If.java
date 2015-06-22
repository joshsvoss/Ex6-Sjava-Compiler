package scopes;

import java.util.regex.Pattern;

public class If extends Scope{
	
	

	public If(String currLn, int depth) {
		super(currLn, depth);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateLogic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkParamSyntax(String params) {
		return Pattern.matches(loopParam, params);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
