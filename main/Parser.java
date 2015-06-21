package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static final String methDec = "void{1}+\\s[a-zA-Z]{1}+[\\w]*+\\s[(]{1}+(\\w|\\s)*+([)][;]){1}+\\s*";
	
	// The regex for a method call.
	// TODO didn't deal with method parameters, just that they may or may not exist.
	public static final String methCall = "\\s*+[a-zA-Z]{1}+[\\w]*+\\s[(]{1}+(\\w|\\s)*+([)][;]){1}+\\s*";
	
	// The regex for the method parameters.
	public static final String methParam = "(\\s*+(final{1}+\\s{1})?+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*+([,]{1}+\\s*+(final{1}+\\s{1})?+(int|boolean|char|double|String){1}+\\s[a-zA-Z_]{1}+\\w*+\\s*)*)*";
	
	// The regex for if/while loop.
	// TODO didn't deal with loop parameters, just that they exist.
	public static final String loop = "\\s*+(if|while){1}+\\s*+[(]{1}+\\s*+(\\w|\\s)++\\s*+([)]+\\s*+[{]+){1}+\\s*";
	
	// The regex for the loop parameters.
	public static final String loopParam = "\\s*+(true|false|([a-zA-Z_]{1}+\\w*)|([-]?+[0-9]++([.]{1}+[0-9]+)?)){1}+\\s*+(([|][|]|[&][&]){1}+\\s*+(true|false|([a-zA-Z_]{1}+\\w*)|([-]?+[0-9]++([.]{1}+[0-9]+)?)){1}+\\s*)*\\s*";
	
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
			if(Pattern.matches(currLn, doc)||Pattern.matches(currLn, whiteSpace)||Pattern.matches(currLn, methCall)||Pattern.matches(currLn, methEnd)){
				continue;
			}else if(Pattern.matches(currLn, varDec)){
				// Send currLn to type factory.	
			}else if(Pattern.matches(currLn, methDec)){
				this.depth++;
				// Send currLn and depth to scope Factory.
			}else if(Pattern.matches(currLn, loop)){
				this.depth++;
				// Send currLn and depth to scope Factory.
			}else if(Pattern.matches(currLn, scopeClose)){
				this.depth--;
				continue;
			}else if(Pattern.matches(currLn, varAss)){
				// Update the variable value.
			}else{
				// Throw syntax error.
			}
			
		}
	}

}
