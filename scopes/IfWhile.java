package scopes;

import types.InvalidValueException;

public class IfWhile extends Scope{

	public IfWhile(String name, String params, int depth) {
		super(name, params, depth);
		// TODO Auto-generated constructor stub
	}

	// TODO is this necessary?
	public void updateLogic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkParamLogic(String params) throws InvalidValueException {
		// TODO Auto-generated method stub
		return false;
	}

}
