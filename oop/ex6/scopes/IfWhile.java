package oop.ex6.scopes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.Parser;
import oop.ex6.types.Int;
import oop.ex6.types.InvalidValueException;
import oop.ex6.types.Type;
import oop.ex6.types.Double;
import oop.ex6.types.Boolean;

public class IfWhile extends Scope{
	
	//TODO We don't need to have an ifWhile object do we, so maybe we should just have a static 
	// TODO "checkIFWhile()" method
	
	private Pattern booleanIntOrDouble = Pattern.compile("true|false|(-?+[0-9]++(\\.{1}+[0-9]+)?)");
	private Pattern legalVarName = Pattern.compile("([a-zA-Z_]{1}+\\w*)");
	private String booleanOperatorSeparator = "\\s*([|][|]){1}\\s*|\\s*([&][&]){1}\\s*";
	private String[] conditions;

	public IfWhile(String name, String conditions, int depth) throws InvalidValueException,
	UninitializedVariableUsedException {
		super(name, conditions, depth);
		this.conditions = separateConditions(conditions);
		for(int i = 0; i < this.conditions.length; i++){
			checkParamLogic(this.conditions[i]);
		}
		
	}
	
	private String[] separateConditions(String conditions){
		return conditions.split(booleanOperatorSeparator);
	}

	// TODO is this necessary?
	public void updateLogic() {
		// TODO Auto-generated method stub
		
	}
	
	public int getDepth(){
		return this.depth;
	}

	@Override
	public boolean checkParamLogic(String condition) throws InvalidValueException,
	UninitializedVariableUsedException {
		Matcher boolenaIntOrDoubleMatch = booleanIntOrDouble.matcher(condition);
		Matcher legalVarNameMatch = legalVarName.matcher(condition);
		if(boolenaIntOrDoubleMatch.matches()){
			return true;
		}else if(legalVarNameMatch.matches()){
			for (int i = this.depth; i >= 0; i--) {
				Type conditionVar = Parser.getSymbolTableList().elementAt(i).get(condition);
				if(conditionVar != null){
					if((conditionVar instanceof Int) || (conditionVar instanceof Double) || 
							(conditionVar instanceof Boolean)){
						if(!conditionVar.isInitialized()){
							throw new UninitializedVariableUsedException();
						}
						return true;
						
					}
					break;
				}
			}
		}
		throw new InvalidValueException("Invalid condition for an if/while block.");
	}

}
