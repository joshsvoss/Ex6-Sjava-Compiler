package scopes;

import java.util.regex.Pattern;

public class If extends Scope{
	
	// The regex for the loop parameters.
	public static final String loopParam = "\\s*+(true|false|([a-zA-Z_]{1}+\\w*)|([-]?+[0-9]++([.]{1}+[0-9]+)?)){1}+\\s*+(([|][|]|[&][&]){1}+\\s*+(true|false|([a-zA-Z_]{1}+\\w*)|([-]?+[0-9]++([.]{1}+[0-9]+)?)){1}+\\s*)*\\s*";

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
