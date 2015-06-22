package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scopes.LoopFactory;
import scopes.Method;
import scopes.Scope;
import types.Type;
import types.TypeFactory;

public class Parser {
	
	// The source file.
	private File srcFile;
	
	// The file scanner.
	private Scanner scanner;
	
	// The current line.
	private String currLn;
	
	// A line counter.
	private int lineCtr = 0;
	//TODO Should we make all of these regex fields private?
	// The regex for the a variable declaration. (Deals with cases with/out final, and with/out assignment.)
	
	public static Pattern varDec = Pattern.compile("\\s*+(final{1}+\\s{1})?+(int|boolean|char|double|"
			+ "String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*+([=]{1}+\\s*+\\S+)?\\s*+[;]{1}+\\s*");
	
	// The regex for variable (re)assignment.
	public static Pattern varAss = Pattern.compile("\\s*+[a-zA-Z_]{1}+\\w*+\\s*+([=]{1}+\\s*+\\S+)?"
			+ "\\s*+[;]{1}+\\s*");
	
	// The regex for method declaration.
	// TODO didn't deal with method parameters, just that they may or may not exist.

	public static Pattern methDec = Pattern.compile("void{1}+\\s[a-zA-Z]{1}+[\\w]*+\\s*[(]{1}.*[)]{1}+"
			+ "\\s*+[{]{1}+\\s*");

	public static Pattern methDecA = Pattern.compile("void{1}+\\s[a-zA-Z]{1}+[\\w]*+\\s*[(]{1}(\\s*+"
			+ "(final{1}+\\s{1})?+"
			+ "(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*+([,]{1}+\\s*+(final{1}+\\s{1})"
			+ "?+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*)*)*[)]{1}+\\s*+[{]{1}+\\s*");
	
	// The regex for a method call.
	// TODO didn't deal with method parameters, just that they may or may not exist.
	public static Pattern methCall = Pattern.compile("\\s*+[a-zA-Z]{1}+[\\w]*+\\s*[(]{1}.*[)]{1}+\\s*+"
			+ "[{]{1}+\\s*");
	

	// The regex for the loop parameters.
	public static Pattern loopParam = Pattern.compile("\\s*+(true|false|([a-zA-Z_]{1}+\\w*)|([-]?+[0-9]++"
			+ "([.]{1}+[0-9]+)?)){1}+\\s*+(([|][|]|[&][&]){1}+\\s*+(true|false|([a-zA-Z_]{1}+\\w*)|([-]?"
			+ "+[0-9]++([.]{1}+[0-9]+)?)){1}+\\s*)*\\s*");
	
	public static Pattern methCallA = Pattern.compile("\\s*+[a-zA-Z]{1}+[\\w]*+\\s*[(]{1}(\\s*+(final{1}+"
			+ "\\s{1})?+"
			+ "(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*+([,]{1}+\\s*+(final{1}+\\s{1})"
			+ "?+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*)*)*[)]{1}+\\s*+[{]{1}+\\s*");

	
	// The regex for if/while loop.
	// TODO didn't deal with loop parameters, just that they exist.
	public static Pattern loop = Pattern.compile("\\s*+(if|while){1}+\\s*+[(]{1}\\s*\\S+.*[)]{1}+\\s*+"
			+ "[{]{1}+\\s*");
	
	// The regex for a comment.
	public static Pattern doc = Pattern.compile("//{1}+.*");
	
	// The regex for white space.
	public static Pattern whiteSpace = Pattern.compile("\\s*");
	
	// The regex for a method return.
	public static Pattern methEnd = Pattern.compile("\\s*+return{1}+\\s*+[;]{1}+\\s*");
	
	// The regex for closing a scope.
	public static Pattern scopeClose = Pattern.compile("\\s*+[}]{1}+\\s*");
	
	// Scope depth counter.
	private int depth = 0;
	
	// A type factory.
	private TypeFactory tFactory = new TypeFactory();
	
	// A loop factory.
	private LoopFactory lFactory = new LoopFactory();
	
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
	}
	
	
	/**
	 * Read the file line by line, sending each line to the line factory.
	 */
	public void readCode() {
		while(scanner.hasNext()){
			currLn = scanner.nextLine();
			lineCtr++;
			
			// If the currLn is documentation, a blank line, method call, or method return, continue.
			if(Pattern.matches(doc, currLn)||Pattern.matches(whiteSpace, currLn)||Pattern.matches(methEnd, currLn)){
				continue;
			}else if(Pattern.matches(varDec, currLn)){
				// Send currLn and depth to type factory.
				Type var = tFactory.generateType(currLn, this.depth);
			}else if(Pattern.matches(methDec, currLn)){
				this.depth++;
				// Create new method.
				Method method = new Method(currLn, this.depth);
			}else if(Pattern.matches(loop, currLn)){
				this.depth++;
				// Send currLn and depth to loop Factory.
				Scope loop = lFactory.generateLoop(currLn, this.depth);
			}else if(Pattern.matches(scopeClose, currLn)){
				this.depth--;
				continue;
			}else if(Pattern.matches(varAss, currLn)){
				// Update the variable value. If variable doesn't exist throw error.
			}else if(Pattern.matches(methCallA, currLn)){
				
				//
			}else{
				// Throw syntax error.
			}
			
		}
	}

}
