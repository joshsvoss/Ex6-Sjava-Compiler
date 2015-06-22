package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scopes.LoopFactory;
import scopes.Method;
import scopes.Scope;
import types.Type;
import types.TypeFactory;

public class Parser {
	
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
	public static Pattern varDec = Pattern.compile("^\\s*+((final{1})+\\s{1})?+\\s*+((int|boolean|char|double|"
			+ "String){1})+\\s*+([a-zA-Z_]{1}+\\w*+)\\s*+(={1}+\\s*+(\\S+))?\\s*+;{1}+\\s*$");
	
	// The regex for variable (re)assignment.
	public static Pattern varAss = Pattern.compile("^\\s*+([a-zA-Z_]{1}+\\w*)+\\s*+(={1}+\\s*+(\\S+))?"
			+ "\\s*+;{1}+\\s*$");
	
	// The regex for method declaration.
	// TODO probably to delete, doesn't deal with method parameters, just that they may or may not exist.
//	public static Pattern methDecA = Pattern.compile("void{1}+\\s[a-zA-Z]{1}+[\\w]*+\\s*[(]{1}.*[)]{1}+"
//			+ "\\s*+[{]{1}+\\s*");
	
	// The regex for method declaration, with parameters.
	public static Pattern methDec = Pattern.compile("^\\s*void{1}+\\s*+[a-zA-Z]{1}+[\\w]*+\\s*\\({1}(\\s*+"
			+ "(final{1}+\\s{1})?+\\s*+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*+(,{1}+"
			+ "\\s*+(final{1}+\\s{1})?+\\s*+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*)*)"
			+ "*\\){1}+\\s*+\\{{1}+\\s*$");
	
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
	public static Pattern methCall = Pattern.compile("^\\s*+[a-zA-Z]{1}+\\w*+\\s*\\({1}\\s*\\S*\\s*+"
			+ "(,{1}+\\s*\\S*\\s*)*\\){1}+\\s*+;{1}+\\s*$");

	
	// The regex for if/while loop.
	// TODO prob to delete, doesn't deal with loop parameters, just that they exist.
//	public static Pattern loopA = Pattern.compile("\\s*+(if|while){1}+\\s*+[(]{1}\\s*\\S+.*[)]{1}+\\s*+"
//			+ "[{]{1}+\\s*");
	
	// The regex for if/while scope, with params.
	public static Pattern ifWhile = Pattern.compile("^\\s*+(if|while){1}+\\s*+\\({1}\\s*+(true|false|([a-zA-Z_]"
			+ "{1}+\\w*)|(-?+[0-9]++(\\.{1}+[0-9]+)?)){1}+\\s*+(([|][|]|[&][&]){1}+\\s*+(true|false|"
			+ "([a-zA-Z_]{1}+\\w*)|(-?+[0-9]++(\\.{1}+[0-9]+)?)){1}+\\s*)*\\s*\\){1}+\\s*+\\{{1}+\\s*$");
	
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
	private TypeFactory tFactory = new TypeFactory();
	
	// A loop factory.
	private LoopFactory lFactory = new LoopFactory();
	
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
	 * @throws unmatchedSyntaxException 
	 */
	public void readCode() throws SJavacException {
		while(scanner.hasNext()){
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
			if(docMatch.matches()||whiteSpaceMatch.matches()||methEndMatch.matches()){
				//continue;
				// TODO the above line shouldn't be a comment. Remove printLn.
				System.out.println("documentation, whitespace, or method end: " + currLn);
			}else if(varDecMatch.matches()){
				// Send currLn and depth to type factory.
				String finalStr = varDecMatch.group(2);
				String type = varDecMatch.group(3);
				String name = varAssignmentMatch.group(5);
				String value = varAssignmentMatch.group(7);
				//Type var = tFactory.generateType(currLn, this.depth);
				// TODO the above line shouldn't be a comment. Remove printLns.
				System.out.println("variable declaratio: " + currLn);
				if(finalStr != null){
					System.out.println("final: " + finalStr);
				}
				System.out.println("type: " + type);
				System.out.println("name: " + name);
				System.out.println("value: " + value);
			}else if(methDecMatch.matches()){
				this.depth++;
				// Create new method.
				//Method method = new Method(currLn, this.depth);
				// TODO the above line shouldn't be a comment. Remove printLn.
				System.out.println("method declaration: " + currLn);
			}else if(ifWhileMatch.matches()){
				this.depth++;
				// Send currLn and depth to loop Factory.
				//Scope loop = lFactory.generateLoop(currLn, this.depth);
				// TODO the above line shouldn't be a comment. Remove printLn.
				System.out.println("if/while: " + currLn);
			}else if(scopeCloseMatch.matches()){
				this.depth--;
				//continue;
				// TODO the above line shouldn't be a comment. Remove printLn.
				System.out.println("close scope: " + currLn);
			}else if(varAssignmentMatch.matches()){
				// Update the variable value. If variable doesn't exist throw error.
				String name = varAssignmentMatch.group(1);
				String value = varAssignmentMatch.group(3);
				// TODO Remove printLns.
				System.out.println("variable assignment: " + currLn);
				System.out.println("name: " + name);
				System.out.println("value: " + value);
			}else if(methCallMatch.matches()){
				//String methodParams = methCallMatch.group(1);
				// TODO the above line shouldn't be a comment. Remove printLn.
				System.out.println("method call: " + currLn);
				// Check the method params.
			}else{
				// TODO Remove printLn.
				System.out.println("ERROR");
				// Throw syntax error.
				// TODO how could we have a more specific exception
				throw new unmatchedSyntaxException("Current line doesn't match any possible correct"
						+ "syntax.");
				
			}
			
		}
	}

}
