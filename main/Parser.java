package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser extends Sjavac {
	
	// The source file.
	private File srcFile;
	
	// The file scanner.
	private Scanner scanner;
	
	// The current line.
	private String currLn;
	
	// A line counter.
	private int lineCtr = 0;
	
	// The regex for the start of a variable declaration. (Deals with cases with/out final, and with/out assignment.)
	public static final String varDec = "\\s*+([f][i][n][a][l]++\\s+)?+[int|boolean|char|double|String]++\\s[a-zA-Z_]{1}+\\w*+\\s*+([=]++\\s*+\\S+)?\\s*+[;]++\\s*";
	
	// The regex for method declaration.
	// TODO didn't deal with method parameters, just that they may or may not exist.
	public static final String methDec = "[v][o][i][d]++\\s[a-zA-Z]{1}+[\\w]*+\\s[(]{1}+[\\w|\\s]*+([)][;]){1}+\\s*";
	
	// The regex for a method call.
	// TODO didn't deal with method parameters, just that they may or may not exist.
	public static final String methCall = "\\s*+[a-zA-Z]{1}+[\\w]*+\\s[(]{1}+[\\w|\\s]*+([)][;]){1}+\\s*";
	
	// The regex for if/while loop.
	// TODO didn't deal with loop parameters, just that they exist.
	public static final String loop = "\\s*+[if|while]++\\s*+[(]{1}+\\s*+[\\w|\\s]++\\s*+([)]+\\s*+[{]+){1}+\\s*";
	
	// The regex for a comment.
	public static final String doc = "\\++.*";
	
	// The regex for white space.
	public static final String whiteSpace = "\\s*";
	
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
			
			if(Pattern.matches(currLn, doc)){
				continue;
			}else if(Pattern.matches(currLn, varDec)){
				// Send currLn to type factory.	
			}else if(Pattern.matches(currLn, methDec)){
				// Send currLn to scope Factory.
			}
			
		}
	}

}
