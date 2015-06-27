package oop.ex6.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import oop.ex6.scopes.IfWhile;
import oop.ex6.scopes.Method;
import oop.ex6.scopes.MethodNamespaceCollision;
import oop.ex6.types.Type;
import oop.ex6.types.TypeFactory;

/** This class handles reading in the Sjava file and parsing through it.
 * @author Joshua Voss
 *
 */
public class Parser {
	
	// Indices for the different capture groups.
	// These have been made public because they are useful to classes in the Method package.
	//TODO this is akward that we need to make these public instead of just package, because things outside
	// TODO the package use them.  Should all things using common resources be inside one package then?
	public static final int FIRST_GROUP_INDEX = 1;
	public static final int SECOND_GROUP_INDEX = 2;
	public static final int THIRD_GROUP_INDEX = 3;
	public static final int FOURTH_GROUP_INDEX = 4;
	public static final int FIFTH_GROUP_INDEX = 5;
	public static final int SIXTH_GROUP_INDEX = 6;	
	public static final int SEVENTH_GROUP_INDEX = 7;
	public static final int EIGHTH_GROUP_INDEX = 8;
	public static final int NINTH_GROUP_INDEX = 9;
	public static final int TENTH_GROUP_INDEX = 10;
	
	private static final int NUM_READ_ITERATIONS = 2;
	private static final int GLOBAL_ITERATION = 0;
	private static final int SECOND_ITERATION = 1;
	public static final int METHOD_DEPTH = 1;
	public static final int GLOBAL_DEPTH = 0;
	
	private static final int BEG_OF_FILE = 0;
	
	
	
	// The possible line types.
	private static final int DOC_OR_BLANK = 0; //TODO README use as example for enum
	private static final int VAR_DECLARATION = 1;
	private static final int VAR_ASSIGNMENT = 2;
	private static final int METHOD_DECLARATION = 3;
	private static final int METHOD_CALL = 4;
	private static final int METHOD_RETURN = 5;
	private static final int IF_WHILE = 6;
	private static final int SCOPE_CLOSE = 7;

	
	// The line type of the previous line.
	private int previousLnType;
	
	// The source file.
	private File srcFile;
	
	// The file scanner.
	private Scanner scanner;
	
	// The current line.
	private String currLn;
	
	// A line counter.
	// NOTE: for expandibility, to have multiple parser instances running simultaneously,
	// NOTE: make this field and its getter non-static.
	private static int lineCtr = 0;
	
	// A regex for finding commas.
	public static final String COMMA_SEPARATOR = "\\s*[,]{1}\\s*";
	
	private static final String POSSIBLE_VAR_VALUES = "(true|false|(-?[0-9]+(\\.{1}+[0-9]+)?)|"
			+ "(\'{1}+.{1}+\'{1})|(\"{1}.*\"{1})|([a-zA-Z_]{1}+\\w*+))";
	
	public static final String POSSIBLE_VAR_NAMES = "(([a-zA-Z]{1}+\\w*)|(_{1}\\w+))";
	
	//TODO Should we make all of these regex fields private?
	//TODO should we anchor front and back with whitespace in between and how does this help?
	// The regex for the a variable declaration. (Deals with cases with/out final, and with/out assignment.)
	private static final Pattern VAR_DEC = Pattern.compile("^\\s*+((final{1})+\\s{1})?+\\s*+((int|boolean|"
			+ "char|double|String){1})+\\s*+(("+POSSIBLE_VAR_NAMES+"+)\\s*+(={1}+\\s*+("+POSSIBLE_VAR_VALUES
			+"){1})?\\s*+(,{1}+\\s*+("+POSSIBLE_VAR_NAMES+"+)\\s*+(={1}+\\s*+("+POSSIBLE_VAR_VALUES+"){1})?"
			+ "\\s*)*)\\s*+;{1}\\s*$");
	
	// The regex for the variable name with/out the value, upon declaration.
	private static final Pattern VAR_NAME_OR_VALUE = Pattern.compile("\\s*+("+POSSIBLE_VAR_NAMES+"){1}+\\s*+"
			+ "(={1}+\\s*+"+POSSIBLE_VAR_VALUES+"{1}+\\s*)?+\\s*");
	
	// The regex for variable (re)assignment.
	private static final Pattern VAR_ASS = Pattern.compile("^\\s*+("+POSSIBLE_VAR_NAMES+")+\\s*+={1}+\\s*+"
			+ "("+POSSIBLE_VAR_VALUES+")\\s*+;{1}+\\s*$");
	
	// The regex for method declaration, with parameters.
	private static final Pattern METH_DEC = Pattern.compile("^\\s*+void{1}+\\s*+([a-zA-Z]{1}+[\\w]*)+\\s*+"
			+ "\\({1}(\\s*+((final{1}+\\s{1})?+\\s*+(int|boolean|char|double|String){1}+\\s*+"+
			POSSIBLE_VAR_NAMES+"+\\s*+(,{1}+\\s*+(final{1}+\\s{1})?+\\s*+(int|boolean|char|double|String){1}"
			+ "+\\s*+"+POSSIBLE_VAR_NAMES+"+\\s*)*))*\\){1}+\\s*+\\{{1}+\\s*$");
	
	
	// The regex for a method call, with params.
	private static final Pattern METH_CALL = Pattern.compile("^\\s*+([a-zA-Z]{1}+\\w*)+\\s*\\({1}\\s*(("+
	POSSIBLE_VAR_VALUES+"|("+POSSIBLE_VAR_NAMES+")){1}+\\s*+(\\s*,{1}+\\s*+("+POSSIBLE_VAR_VALUES+"|"
			+ "("+POSSIBLE_VAR_NAMES+")){1})*)*\\s*\\){1}+\\s*+;{1}+\\s*$");
	
	// The regex for the opening of an if/while scope, with conditions.
	private static final Pattern IF_WHILE_OPEN = Pattern.compile("^\\s*+((if|while){1})+\\s*+\\({1}\\s*+"
			+ "((true|false|("+POSSIBLE_VAR_NAMES+")|(-?+[0-9]++(\\.{1}+[0-9]+)?)){1}+\\s*+"
			+ "(([|][|]|[&][&]){1}+\\s*+(true|false|("+POSSIBLE_VAR_NAMES+")|"
			+ "(-?+[0-9]++(\\.{1}+[0-9]+)?)){1}+\\s*)*)\\s*\\){1}+\\s*+\\{{1}+\\s*$");
	
	// The regex for a comment.
	private static final Pattern DOCUMENTATION = Pattern.compile("^\\s*//{1}+.*$");
	
	// The regex for white space.
	private static final Pattern WHITE_SPACE = Pattern.compile("^\\s*$");
	
	// The regex for a method return.
	private static final Pattern METH_END = Pattern.compile("^\\s*+return{1}+\\s*+;{1}+\\s*$");
	
	// The regex for closing a scope.
	private static final Pattern SCOPE_CLOSER = Pattern.compile("^\\s*+\\}{1}+\\s*$");
	
	// Scope depth counter.
	private int depth = 0;
	
	// A type factory.
	private TypeFactory typeFactory = new TypeFactory();
	
	// A list of symbol tables:
	// NOTE: for expandibility, if you want to run multiple parsers instances at once, 
	// NOTE: You'll need to make this and the getter non-static, as well as other places 
	// NOTE: where the NOTE comment appears.
//	private static Vector<HashMap<String, Type>> symbolTableListA; //TODO remove and all associated with it.
	
	public static SymbolTableList symbolTableList;
	
	// List of methods:
	private HashMap<String, Method> methodMap;
	
	HashMap<String, String> map;// TODO do we need this?
	
	// Scope list 
	
	/**
	 * Construct a parser for the given file.
	 * 
	 * @param file, the file to be parsed.
	 * @throws FileNotFoundException
	 */
	public Parser(File file) throws FileNotFoundException{
		// We know the file has been verified in the main,
		// So we can assume it is readable and exists.  
		this.srcFile = file;
		this.scanner = new Scanner(this.srcFile);
		
		// Initilize the data structures to hold the symbol and method tables.
//		symbolTableList = new Vector<HashMap<String, Type>>();
		// And add the global table in the first spot:
//		symbolTableList.add(new HashMap<String, Type>());
		//symbolTableListA = new Vector<HashMap<String, Type>>(); //TODO remove?
		symbolTableList = new SymbolTableList();
		// And add a table in the first spot:
//		symbolTableListA.add(new HashMap<String, Type>());
		this.methodMap = new HashMap<String, Method>();
	}
	
	
	/**
	 * Read the file line by line, matching each line with it's corresponding regex, 
	 * and creating the logical structure through Type objects and inserting them into
	 * different levels of the symbolTableList.  
	 * 
	 * @throws SJavacException this is thrown upon encountering an invalid syntax.  The subclasses
	 * of SJavacException are the actual excpetions that will be thrown.  And they also
	 * carry default messages which will make the cause of the error more clear.   
	 */
	public void readCode() throws SJavacException {
		
		// For keeping track of the current method that we're in:
		Method curMethod = null;
		
		try {
			for (int i = 0; i < NUM_READ_ITERATIONS; i++) {
				
				while (scanner.hasNext()) {
					currLn = scanner.nextLine();
					lineCtr++;

					// Documentation matcher against the current line.
					Matcher docMatch = DOCUMENTATION.matcher(currLn);

					// White space matcher against the current line.
					Matcher whiteSpaceMatch = WHITE_SPACE.matcher(currLn);

					// Method end matcher against the current line.
					Matcher methEndMatch = METH_END.matcher(currLn);

					// Variable declaration matcher against the current line.
					Matcher varDecMatch = VAR_DEC.matcher(currLn);

					// Method declaration matcher against the current line.
					Matcher methDecMatch = METH_DEC.matcher(currLn);

					// Loop matcher against the current line.
					Matcher ifWhileMatch = IF_WHILE_OPEN.matcher(currLn);

					//  Scope closer matcher against the current line.
					Matcher scopeCloseMatch = SCOPE_CLOSER.matcher(currLn);

					// Variable assignment matcher against the current line.
					Matcher varAssignmentMatch = VAR_ASS.matcher(currLn);

					// Method call matcher against the current line.
					Matcher methCallMatch = METH_CALL.matcher(currLn);

					// If the currLn is documentation, a blank line, method call, or method return, continue.
					// TODO move methEndMatch to it's own else if block? - if depth is 1 and followed by close scope, this is the closing of a method.
					if (docMatch.matches() || whiteSpaceMatch.matches()) {
						this.previousLnType = DOC_OR_BLANK;
						// This will continue automatically because every other if clause is else if.
					} 
					
					else if (varDecMatch.matches()) {
						this.previousLnType = VAR_DECLARATION;
						if (shouldDeclareVar(i) ) {
							// Send currLn and depth to type factory.
							String finalStr = varDecMatch.group(FIRST_GROUP_INDEX);
							String type = varDecMatch.group(THIRD_GROUP_INDEX);
							String vars = varDecMatch.group(FIFTH_GROUP_INDEX);
							String[] varArray = vars.split(COMMA_SEPARATOR);
							
							for (int j = 0; j < varArray.length; j++) {
								Matcher nameAndValueMatch = VAR_NAME_OR_VALUE.matcher(varArray[j]);
								if (nameAndValueMatch.matches()) {
									String name = nameAndValueMatch
											.group(FIRST_GROUP_INDEX);
									String value = nameAndValueMatch
											.group(SIXTH_GROUP_INDEX);
									Type variable = typeFactory.generateType(
											finalStr, type, name, value,
											this.depth);
									// Now put it into the correct symbol table
//									Type previousType = symbolTableListA
//											.elementAt(this.depth).put(name,
//													variable);
									Type previousType = symbolTableList.addTypeToALevel(name,
													variable, this.depth);
									if (previousType != null) {
										// This means that we tried to declare something with the same name
										// Twice in same scope, throw exception
										throw new DoubleDeclarationInScopeException();
									}
									
									}else{
										throw new IncorrectVariableSyntaxException();
								}
							}
							
						}
						// Otherwise, even though we're not building the variable we found a syntax match
						// So it's kosher, continue to next line. (no need for conintue because they're all
						// "else if"

					} 
					
					else if (methDecMatch.matches()) {
						
						// If the method declaration comes inside another method, TREIF! (aka "not kosher")
						if (this.depth > GLOBAL_DEPTH) {
							throw new NestedMethodDeclarationException();
						}
						
						this.incrementDepth();
						
						this.previousLnType = METHOD_DECLARATION;
						
						// We want the name to be accessable to the else if as well.
						String methodName = methDecMatch.group(FIRST_GROUP_INDEX);
						if (i == GLOBAL_ITERATION) {
							// Create new method.
							String params = methDecMatch.group(THIRD_GROUP_INDEX);
							curMethod = new Method(methodName, params);
							// Add the created method to list:
							Method previousValue = methodMap.put(methodName, curMethod);
							if (previousValue != null) {
								// This means that we tried to declare something with the same name
								// Twice in same scope, throw exception
								throw new MethodNamespaceCollision();
							}
						}
						// But if we're in the second run through, we want to create all of the 
						// methods params as Type objects in our symbol tables, so they can 
						// be referenced (and checked) just like regular local variables.
						else if (i == SECOND_ITERATION) {
							// Get the method from our method list that matches the name
							Method runningMethod = this.methodMap.get(methodName);
							Type[] paramList = runningMethod.getParamTypesList();
							// Only iterate through the list and add them IF there are ARGS! 
							if (paramList != null) {
								for (Type paramType : paramList) {
									Type addReturn = symbolTableList.addTypeToALevel(paramType.getName(), 
											paramType, this.depth);
									// If our insertion replaced another Type, then their names overlap:
									if (addReturn != null) {
										// TODO delte message below, debug
										throw new DoubleDeclarationInScopeException( 
												"ThiS error shouldn't be happenign here!Cuz this scope symbol table should be empty since we just entered method.");
									}
								}
							}
							
						}

						
					} 
					
					else if ((ifWhileMatch.matches())) {
						
						this.previousLnType = IF_WHILE;
						
						if (this.depth == GLOBAL_DEPTH ) {
							throw new GlobalIfWhileException();
						}
						
						incrementDepth();
						// Only create the if/while block if we're on the second iteration:
						if (i == SECOND_ITERATION) {
							// Create new if/while block
							String name = ifWhileMatch.group(FIRST_GROUP_INDEX); 
							String conditions = ifWhileMatch
									.group(THIRD_GROUP_INDEX);
							IfWhile.checkLogic(name, conditions, depth); //TODO warning here for not doing anything with new object
						}

					}
					
					else if (methEndMatch.matches()) {
						// If this return statement is in global scope, that's a problem:
						if (this.depth == GLOBAL_DEPTH) {
							throw new GlobalReturnException();
						}
						
						if ((this.depth == METHOD_DEPTH)
								&& (scanner.hasNext("\\s*\\}{1}\\s*"))) {
							// The a method is being closed
							curMethod.closeMethod();
						}
						this.previousLnType = METHOD_RETURN;
					} 
					
					else if ((scopeCloseMatch.matches())) {
						// A closing bracket can't exist in global scope, so if it does throw Exception
						if (this.depth == GLOBAL_DEPTH) {
							throw new GlobalClosingBracketException();
						}
						else if ((this.depth == METHOD_DEPTH)
								&& (this.previousLnType != METHOD_RETURN)) {
							throw new MissingMethodReturnException();
						}
						// Delete the table for the scope that is closing, and decrement depth
//						symbolTableListA.remove(this.depth);
						symbolTableList.decreaseLevel(this.depth);
						this.depth--;
						
						this.previousLnType = SCOPE_CLOSE;
					} 
					
					else if (varAssignmentMatch.matches()) {
						
						// Only assign the value if on global scope XOR in second iteration.
						if (this.depth == GLOBAL_DEPTH ^ i == SECOND_ITERATION) {
							// Update the variable value. If variable doesn't exist throw error.
							String name = varAssignmentMatch.group(FIRST_GROUP_INDEX);
							String value = varAssignmentMatch
									.group(FIFTH_GROUP_INDEX);
							this.previousLnType = VAR_ASSIGNMENT;
							
							// Now try to change the value in our symbol table
							tryChangeVarValue(name, value);
						}
					} 
					
					else if (methCallMatch.matches()) {
						
						this.previousLnType = METHOD_CALL;
						
						if (i == SECOND_ITERATION){
							if (this.depth == GLOBAL_DEPTH) {
								throw new GlobalMethodCallException();
							}
							String name = methCallMatch.group(FIRST_GROUP_INDEX);
							String params = methCallMatch.group(SECOND_GROUP_INDEX);
							// Check the method params.
							
							verifyMethodCall(name, params);
						}
	
					} 
					
					else {
						// Throw syntax error.
						throw new UnmatchedSyntaxException();
					}

				}
				
				// At the end of for loop, reset scanner to beginign
				try {
					scanner = new Scanner(this.srcFile);
					lineCtr = BEG_OF_FILE;
				} catch (FileNotFoundException e) {
					// Do nothing, since we would have caught this exception the first time 
					// we create the scanner.
				}
			}
		} catch (SJavacException exceptionToRethrowAfterClosing) {
			// This catch exists to make sure you close the scanner (in the finally block) no matter
			// What exception is thrown and no matter where it is thrown.
			// After closing the scanner, rethrow the exception up to the main undisturbed
			// So it can be printed:
			throw exceptionToRethrowAfterClosing;
		}
		finally {
			this.scanner.close();
		}
	}


	/** This method  first tries to find an existing declared method with the same name
	 * as the method call, and then checks if the parameters are legal.
	 * @param methodName name of the method.
	 * @param params arguments passed to said method.
	 * @throws SJavacException if the method call was incorrect
	 */
	private void verifyMethodCall(String methodName, String params) throws SJavacException {
		Method invokedMethod = this.methodMap.get(methodName);
		
		if (invokedMethod == null) {
			// Then the method name being called doesn't exist!
			throw new WrongMethodNameException();
		}
		else {
			invokedMethod.checkParamLogic(params, this.depth);
		}

	}
	
	/** This method searched the symbolTableList for a certain variable (or Type object).
	 * It wraps the search() method of the SymbolTableList class.
	 * @param name the name of the variable to be searched. 
	 * @param depth the level of the list where the table we want to search is.  
	 * @return The Type that was being searched for if it was found, or null if it wasn't found.
	 */
	public static Type searchSymbolTableList(String name, int depth){
		return symbolTableList.search(name, depth);
	}


	/** This method both increments the depth and creates a new symbolTable 
	 * and the "depth" index of the vector IF one wasn't already created at that level.
	 * 
	 */
	private void incrementDepth() {
		this.depth++;
		
		if (symbolTableList.size()  < this.depth + 1) {
			// then we need to add a new spot AND table onto the end:
			symbolTableList.increaseLevel();
		}
		
		if (symbolTableList.findLevel(this.depth)  == null) {
			symbolTableList.increaseLevel(this.depth);
		}
	}
	


	/** This method is called when the sjava file performs a variable assignment.  
	 * @param varName the name of the variable to be assigned with a new value.
	 * @param valueToUpdate the value to assign to variable varName.
	 * @throws SJavacException When the variable to be reassigned has not yet been declared!
	 */
	private void tryChangeVarValue(String varName, String valueToUpdate) throws SJavacException { 
		// Start at highest depth (where we are now) and go down to global
		boolean foundVar = false;
		for (int i = this.depth; i >= 0; i--) {
			Type varToSet = symbolTableList.search(varName, i);
			if (varToSet != null) {
				
				if (this.depth > GLOBAL_DEPTH && i == GLOBAL_DEPTH) {
					// Then copy the value from the global level to ONLY the method level 
					// (not deeper) so that we don't ruin any global initialization of the variable
					// for future methods
					Type clonedVar = varToSet.copyVar();
					symbolTableList.addTypeToALevel(clonedVar.getName(), clonedVar, METHOD_DEPTH);
					clonedVar.setValue(valueToUpdate);
				}else{
				varToSet.setValue(valueToUpdate);
				}
				foundVar = true;
				break;
			}
		}
		
		// Now check to make sure we actually found a declared var, if not throw exception:
		if (foundVar == false) {
			throw new UndeclaredAssignmentException();
		}
	}


	/** This boolean helper method returns true if the program should enter into the declaring
	 * variable block. 
	 * @param i is the iteration of reading the file.  There are two iterations, 0 and 1.
	 * @return true if the variable should be declared inside the program.
	 */
	private boolean shouldDeclareVar(int i) {
		return (i == GLOBAL_ITERATION && this.depth == GLOBAL_ITERATION) || 
				(i != GLOBAL_ITERATION  && this.depth != GLOBAL_ITERATION);
	}
	
	/** This method returns the current line of the parser.  This method is static so that
	 * the main can have access to the current line number to print it out with the exception
	 * for a more detailed error message. 
	 * 
	 * NOTE: for expandability, if you want to have more than one Parser instance at once,
	 * make this method non-static and call it on a reference to the Parser instance.  
	 * @return int current line in parser.
	 */
	public static int getLineCounter() { //TODO make this non static and instead return a reference to the parser object to the maiin.
		return Parser.lineCtr;
	}

}
