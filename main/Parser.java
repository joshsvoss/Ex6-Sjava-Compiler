package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scopes.IfWhile;
import scopes.Method;
import scopes.Scope;
import types.Type;
import types.TypeFactory;

public class Parser {
	
	// Indices for the different capture groups.
	private static final int FIRST_GROUP_INDEX = 1;
	private static final int SECOND_GROUPD_INDEX = 2;
	private static final int THIRD_GROUP_INDEX = 3;
	private static final int FOURTH_GROUP_INDEX = 4;
	private static final int FIFTH_GROUP_INDEX = 5;
	private static final int SIXTH_GROUP_INDEX = 6;	
	private static final int SEVENTH_GROUP_INDEX = 7;
	private static final int EIGHTH_GROUP_INDEX = 8;
	private static final int NINTH_GROUP_INDEX = 9;
	private static final int TENTH_GROUP_INDEX = 10;
	
	private static final int NUM_READ_ITERATIONS = 2;
	private static final int GLOBAL_ITERATION = 0;
	private static final int SECOND_ITERATION = 1;
	private static final int METHOD_DEPTH = 1;
	private static final int GLOBAL_DEPTH = 0;
	
	
	// The possible line types.
	private static final int DOC_OR_BLANK = 0;
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
	private int lineCtr = 0;
	
	//TODO Should we make all of these regex fields private?
	//TODO should we anchor front and back with whitespace in between and how does this help?
	// The regex for the a variable declaration. (Deals with cases with/out final, and with/out assignment.)
	public static Pattern varDec = Pattern.compile("^\\s*+((final{1})+\\s{1})?+\\s*+((int|boolean|char|"
			+ "double|String){1})+\\s*+([a-zA-Z_]{1}+\\w*+)\\s*+(={1}+\\s*+(\\S+(\\s*+\\S+)*))?\\s*+;{1}+"
			+ "\\s*$");
	
	// The regex for variable (re)assignment.
	public static Pattern varAss = Pattern.compile("^\\s*+([a-zA-Z_]{1}+\\w*)+\\s*+={1}+\\s*+"
			+ "(\\S+(\\s*+\\S+)*)\\s*+;{1}+\\s*$");
	
	// The regex for method declaration.
	// TODO probably to delete, doesn't deal with method parameters, just that they may or may not exist.
//	public static Pattern methDecA = Pattern.compile("void{1}+\\s[a-zA-Z]{1}+[\\w]*+\\s*[(]{1}.*[)]{1}+"
//			+ "\\s*+[{]{1}+\\s*");
	
	// The regex for method declaration, with parameters.
	public static Pattern methDec = Pattern.compile("^\\s*void{1}+\\s*+([a-zA-Z]{1}+[\\w]*)+\\s*\\({1}"
			+ "(\\s*+(final{1}+\\s{1})?+\\s*+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*+"
			+ "(,{1}+\\s*+(final{1}+\\s{1})?+\\s*+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+"
			+ "\\s*)*)*\\){1}+\\s*+\\{{1}+\\s*$");
	
	// The regex for a method call.
	// TODO prob delete this doesn't deal with method parameters, just that they may or may not exist.
//	public static Pattern methCallA = Pattern.compile("^\\s*+[a-zA-Z]{1}+\\w*+\\s*\\({1}.*\\){1}+\\s*+"
//			+ "\\{{1}+\\s*$");
	

	// The regex for the loop parameters.
	// TODO prob to delete since dealt with in loop regex.
//	public static Pattern loopParam = Pattern.compile("^\\s*+(true|false|([a-zA-Z_]{1}+\\w*)|(-?+[0-9]++"
//			+ "(\\.{1}+[0-9]+)?)){1}+\\s*+(([|][|]|[&][&]){1}+\\s*+(true|false|([a-zA-Z_]{1}+\\w*)|(-?"
//			+ "+[0-9]++(\\.{1}+[0-9]+)?)){1}+\\s*)*\\s*$");
	
	// The regex for a method call, with params.
	public static Pattern methCall = Pattern.compile("^\\s*+([a-zA-Z]{1}+\\w*)+\\s*\\({1}(\\s*\\S*\\s*+"
			+ "(,{1}+\\s*\\S*\\s*)*)\\){1}+\\s*+;{1}+\\s*$");

	
	// The regex for if/while loop.
	// TODO prob to delete, doesn't deal with loop parameters, just that they exist.
//	public static Pattern loopA = Pattern.compile("\\s*+(if|while){1}+\\s*+[(]{1}\\s*\\S+.*[)]{1}+\\s*+"
//			+ "[{]{1}+\\s*");
	
	// The regex for if/while scope, with params.
	public static Pattern ifWhile = Pattern.compile("^\\s*+((if|while){1})+\\s*+\\({1}\\s*+((true|false|"
			+ "([a-zA-Z_]{1}+\\w*)|(-?+[0-9]++(\\.{1}+[0-9]+)?)){1}+\\s*+(([|][|]|[&][&]){1}+\\s*+(true|"
			+ "false|([a-zA-Z_]{1}+\\w*)|(-?+[0-9]++(\\.{1}+[0-9]+)?)){1}+\\s*)*)\\s*\\){1}+\\s*+\\{{1}+"
			+ "\\s*$");
	
	// The regex for a comment.
	public static Pattern doc = Pattern.compile("^/s*//{1}+.*$");
	
	// The regex for white space.
	public static Pattern whiteSpace = Pattern.compile("^\\s*$");
	
	// The regex for a method return.
	public static Pattern methEnd = Pattern.compile("^\\s*+return{1}+\\s*+;{1}+\\s*$");
	
	// The regex for closing a scope.
	public static Pattern scopeClose = Pattern.compile("^\\s*+\\}{1}+\\s*$");
	
	// Scope depth counter.
	private int depth = 0;
	
	// A type factory.
	private TypeFactory typeFactory = new TypeFactory();
	
	// A loop factory.
	// TODO delete.
//	private LoopFactory lFactory = new LoopFactory();
	
	// A list of symbol tables:
	private Vector<HashMap<String, Type>> symbolTableList;
	
	// List of methods:
	private HashMap<String, Method> methodMap;
	
	HashMap<String, String> map;
	
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
		this.symbolTableList = new Vector<HashMap<String, Type>>();
		this.methodMap = new HashMap<String, Method>();
	}
	
	
	/**
	 * Read the file line by line, sending each line to the line factory.
	 * 
	 * @throws unmatchedSyntaxException 
	 */
	public void readCode() throws SJavacException {
		
		// For keeping track of the current method that we're in:
		Method curMethod = null;
		
		for (int i = 0; i < NUM_READ_ITERATIONS; i++) {
			
			while (scanner.hasNext()) {
				currLn = scanner.nextLine();
				lineCtr++;

				// Documentation matcher against the current line.
				Matcher docMatch = doc.matcher(currLn);

				// White space matcher against the current line.
				Matcher whiteSpaceMatch = whiteSpace.matcher(currLn);

				// Method end matcher against the current line.
				Matcher methEndMatch = methEnd.matcher(currLn);

				// Variable declaration matcher against the current line.
				Matcher varDecMatch = varDec.matcher(currLn);

				// Method declaration matcher against the current line.
				Matcher methDecMatch = methDec.matcher(currLn);

				// Loop matcher against the current line.
				Matcher ifWhileMatch = ifWhile.matcher(currLn);

				//  Scope closer matcher against the current line.
				Matcher scopeCloseMatch = scopeClose.matcher(currLn);

				// Variable assignment matcher against the current line.
				Matcher varAssignmentMatch = varAss.matcher(currLn);

				// Method call matcher against the current line.
				Matcher methCallMatch = methCall.matcher(currLn);

				// If the currLn is documentation, a blank line, method call, or method return, continue.
				// TODO move methEndMatch to it's own else if block? - if depth is 1 and followed by close scope, this is the closing of a method.
				if (docMatch.matches() || whiteSpaceMatch.matches()) {
					this.previousLnType = DOC_OR_BLANK;
					continue;

				} 
				
				else if (varDecMatch.matches()) {
					this.previousLnType = VAR_DECLARATION;
					if (shouldCreateLine(i) ) {
						// Send currLn and depth to type factory.
						String finalStr = varDecMatch.group(FIRST_GROUP_INDEX);
						String type = varDecMatch.group(THIRD_GROUP_INDEX);
						String name = varDecMatch.group(FIFTH_GROUP_INDEX);
						String value = varDecMatch.group(SEVENTH_GROUP_INDEX);
						Type var = typeFactory.generateType(finalStr, type,
								name, value, this.depth);
						
						// Now put it into the correct symbol table
						Type previousType = symbolTableList.elementAt(this.depth).put(name, var);
						if (previousType != null) {
							// This means that we tried to declare something with the same name
							// Twice in same scope, throw exception
							throw new DoubleDeclarationInScopeException();
						}
						
					}
					// Otherwise, even though we're not building the variable we found a syntax match
					// So it's kosher, continue to next line.
					else {
						continue;
					}

				} 
				
				else if (methDecMatch.matches() && this.depth == GLOBAL_ITERATION) {
					
					this.previousLnType = METHOD_DECLARATION;
					
					if (i == GLOBAL_ITERATION) {
						// Create new method.
						String name = methDecMatch.group(FIRST_GROUP_INDEX);
						String params = methDecMatch.group(THIRD_GROUP_INDEX);
						curMethod = new Method(name, params, this.depth);
						// Add the created method to list:
						Method previousValue = methodMap.put(name, curMethod);
						if (previousValue != null) {
							// This means that we tried to declare something with the same name
							// Twice in same scope, throw exception
							throw new DoubleDeclarationInScopeException();
						}
						
					}
					this.depth++;
					
				} 
				
				else if ((ifWhileMatch.matches())) {
					
					this.previousLnType = IF_WHILE;
					
					if (this.depth == GLOBAL_DEPTH ) {
						throw new GlobalIfWhileException();
					}
					
					// Otherwise, we're at LEAST in the method depth
					this.depth++;
					// Only create the if/while block if we're on the second iteration:
					if (i == SECOND_ITERATION) {
						// Create new if/while block
						String name = ifWhileMatch.group(FIRST_GROUP_INDEX); 
						String conditions = ifWhileMatch
								.group(THIRD_GROUP_INDEX);
						IfWhile ifWhile = new IfWhile(name, conditions, depth); //TODO warning here for not doing anything with new object
					}

				}
				
				else if (this.depth > GLOBAL_DEPTH && methEndMatch.matches()) {
					if ((this.depth == METHOD_DEPTH)
							&& (scanner.hasNext("\\s*\\}{1}\\s*"))) {
						// The a method is being closed
						curMethod.closeMethod();
					}
					this.previousLnType = METHOD_RETURN;
					continue;
				} 
				
				else if ((scopeCloseMatch.matches())) {
					// A closing bracket can't exist in global scope, so if it does throw Excp.
					if (this.depth == GLOBAL_DEPTH) {
						throw new GlobalClosingBracketException();
					}
					if ((this.depth == METHOD_DEPTH)
							&& (this.previousLnType != METHOD_RETURN)) {
						throw new MissingMethodReturnException(
								"Missing the method return statement.");
					}
					this.depth--;
					//continue;
					// TODO the above line shouldn't be a comment.
				} else if (varAssignmentMatch.matches()) {
					// Update the variable value. If variable doesn't exist throw error.
					String name = varAssignmentMatch.group(FIRST_GROUP_INDEX);
					String value = varAssignmentMatch
							.group(SECOND_GROUPD_INDEX);
					this.previousLnType = VAR_ASSIGNMENT;
				} else if ((methCallMatch.matches())
						&& (this.depth >= METHOD_DEPTH)) {
					String name = methCallMatch.group(FIRST_GROUP_INDEX);
					String params = methCallMatch.group(SECOND_GROUPD_INDEX);
					// Check the method params.
					this.previousLnType = METHOD_CALL;
				} else {
					// Throw syntax error.
					// TODO how could we have a more specific exception
					throw new unmatchedSyntaxException(
							"Current line doesn't match any possible correct "
									+ "syntax.");
				}

			}
			
			// At the end of for loop, reset scanner to beginign
			try {
				scanner = new Scanner(this.srcFile);
			} catch (FileNotFoundException e) {
				// Do nothing, since we would have caught this exception the first time 
				// we create the scanner.
			}
		}
	}


	private boolean shouldCreateLine(int i) {
		return (i == GLOBAL_ITERATION && this.depth == GLOBAL_ITERATION) || 
				(i != GLOBAL_ITERATION  && this.depth != GLOBAL_ITERATION);
	}

}
