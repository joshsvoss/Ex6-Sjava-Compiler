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
	
	// The regex for the a variable declaration. (Deals with cases with/out final, and with/out assignment.)
	public static final String varDec = "\\s*+(final{1}+\\s{1})?+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*+([=]{1}+\\s*+\\S+)?\\s*+[;]{1}+\\s*";
	
	// The regex for variable (re)assignment.
	public static final String varAss = "\\s*+[a-zA-Z_]{1}+\\w*+\\s*+([=]{1}+\\s*+\\S+)?\\s*+[;]{1}+\\s*";
	
	// The regex for method declaration.
	// TODO didn't deal with method parameters, just that they may or may not exist.
	public static final String methDec = "void{1}+\\s[a-zA-Z]{1}+[\\w]*+\\s*[(]{1}.*[)]{1}+\\s*+[{]{1}+\\s*";
	
	// The regex for a method call.
	// TODO didn't deal with method parameters, just that they may or may not exist.
	public static final String methCall = "\\s*+[a-zA-Z]{1}+[\\w]*+\\s*[(]{1}.*[)]{1}+\\s*+[{]{1}+\\s*";	
	
	// The regex for if/while loop.
	// TODO didn't deal with loop parameters, just that they exist.
	public static final String loop = "\\s*+(if|while){1}+\\s*+[(]{1}\\s*\\S+.*[)]{1}+\\s*+[{]{1}+\\s*";
	
	// The regex for a comment.
	public static final String doc = "//{1}+.*";
	
	// The regex for white space.
	public static final String whiteSpace = "\\s*";
	
	// The regex for a method return.
	public static final String methEnd = "\\s*+return{1}+\\s*+[;]{1}+\\s*";
	
	// The regex for closing a scope.
	public static final String scopeClose = "\\s*+[}]{1}+\\s*";
	
	// Scope depth counter.
	private int depth = 0;
	
	// A type factory.
	private TypeFactory tFactory = new TypeFactory();
	
	// A loop factory.
	private LoopFactory lFactory = new LoopFactory();
	
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
			if(Pattern.matches(doc, currLn)||Pattern.matches(whiteSpace, currLn)||Pattern.matches(methCall, currLn)||Pattern.matches(methEnd, currLn)){
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
			}else{
				// Throw syntax error.
			}
			
		}
	}

}
