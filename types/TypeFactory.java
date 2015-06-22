package types;

public class TypeFactory {

	private boolean isFinal = false;
	
	public Type generateType(String finalStr, String type, String name, String value, int depth) 
			throws InvalidTypeException, InvalidValueException{
		if(finalStr != null){
			isFinal = true;
		}
		
		switch(type){
		
		case "boolean":
			return new Boolean(name, value, depth, isFinal);
		case "char":
			return new Char(name, value, depth, isFinal);
		case "int":
			return new Int(name, value, depth, isFinal);
		case "double":
			return new Double(name, value, depth, isFinal);
		case "String":
			return new StringType(name, value, depth, isFinal);
		default:
			break;
		}
		throw new InvalidTypeException("Invalid type for variable declaration.");
	}
	
	public Type generateMethodParamType(String finalStr, String type) throws InvalidTypeException{
		if(finalStr != null){
			isFinal = true;
		}
		
		switch(type){
		
		case "boolean":
			return new Boolean(isFinal);
		case "char":
			return new Char(isFinal);
		case "int":
			return new Int(isFinal);
		case "double":
			return new Double(isFinal);
		case "String":
			return new StringType(isFinal);
		default:
			break;
		}
		throw new InvalidTypeException("Invalid type for variable declaration.");
	}
}
