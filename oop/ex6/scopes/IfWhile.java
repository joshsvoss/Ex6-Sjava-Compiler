package oop.ex6.scopes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.Parser;
import oop.ex6.types.Int;
import oop.ex6.types.InvalidValueException;
import oop.ex6.types.Type;
import oop.ex6.types.Double;
import oop.ex6.types.Boolean;

public class IfWhile {
	
	//TODO We don't need to have an ifWhile object do we, so maybe we should just have a static 
	// TODO "checkIFWhile()" method
	
	private static final Pattern BOOLEAN_INT_DOUBLE = Pattern.compile("\\s*(true|false|(-?+[0-9]++(\\.{1}+"
			+ "[0-9]+)?))\\s*");
	private static final Pattern LEGAL_VAR_NAMES = Pattern.compile("\\s*("+Parser.POSSIBLE_VAR_NAMES+")"
			+ "\\s*");
	private static final String BOOLEAN_OPERATOR_SEPARATOR = "\\s*([|][|]){1}\\s*|\\s*([&][&]){1}\\s*";
	private String[] conditions;

	public IfWhile(String name, String conditions, int depth) throws InvalidValueException,
	ParameterNotInitializedException {
		
		this.conditions = separateConditions(conditions);
		for(int i = 0; i < this.conditions.length; i++){
			checkParamLogic(this.conditions[i], depth);
		}
		
	}
	
	private String[] separateConditions(String conditions){
		return conditions.split(BOOLEAN_OPERATOR_SEPARATOR);
	}

	

	public boolean checkParamLogic(String condition, int depth) throws InvalidValueException,
	ParameterNotInitializedException {
		Matcher boolenaIntOrDoubleMatch = BOOLEAN_INT_DOUBLE.matcher(condition);
		Matcher legalVarNameMatch = LEGAL_VAR_NAMES.matcher(condition);
		if(boolenaIntOrDoubleMatch.matches()){
			return true;
		}else if(legalVarNameMatch.matches()){
			for (int i = depth; i >= 0; i--) {
//				Type conditionVar = Parser.getSymbolTableList().elementAt(i).get(condition); //TODO switch to symbollist object
				Type conditionVar = Parser.searchSymbolTableList(condition, i);
				if(conditionVar != null){
					if((conditionVar instanceof Int) || (conditionVar instanceof Double) || 
							(conditionVar instanceof Boolean)){
						if(!conditionVar.isInitialized()){
							throw new ParameterNotInitializedException();
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
