package oop.ex6.main;

import java.io.File;
import java.io.FileNotFoundException;

/** This class is the driver class of the Sjava code legality verifier. 
 *  
 * @author Joshua Voss, shanam
 *
 */
public class Sjavac {
	
	// Output fields:
	private static final int IO_FAILURE_NUM = 2;
	private static final int ILLEGAL_CODE_NUM = 1;
	private static final int KOSHER_CODE_NUM = 0;
	
	// The source file idx in the args array.
	private static final int SRC_FILE_INDEX = 0;

	/** The main method.  The program expects one command line argument, the path to the Sjava file 
	 * we want to validate.  
	 * @param args command line arguments.
	 * 
	 * USAGE: java Sjavac <filepath>
	 */
	public static void main(String[] args) {
		
		// Make sure we received the necessary arguments:
		if (args.length < SRC_FILE_INDEX) {
			System.out.println(IO_FAILURE_NUM);
			System.err.println("ERROR: no command line arg provided.");
			System.err.println("USAGE: please provide source file path as cmd line argument.");
			return;
		}
		
		// Verify that the file exists and can be read:
		File srcFile = new File(args[SRC_FILE_INDEX]);
		if (!srcFile.exists() || !srcFile.canRead()) {
			System.out.println(IO_FAILURE_NUM);
			System.err.println("ERROR: file doesn't exist or can't be read.");
			return;
		}
		
		
		// Now we want to pass on the file to the parser
		try {
			Parser parser = new Parser(srcFile);
			parser.readCode();
		}
		catch (FileNotFoundException e) {
			System.out.println(IO_FAILURE_NUM);
			System.err.println("ERROR: file not found.");
			return;
		} catch (SJavacException e){
			System.out.println(ILLEGAL_CODE_NUM);
			System.err.println("Syntax error in line:  " + Parser.getLineCounter());
			System.err.println(e.getMessage());
			return;
		}
		System.out.println(KOSHER_CODE_NUM);
	}

}
