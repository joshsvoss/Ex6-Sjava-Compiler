package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser extends Sjavac {
	
	private File srcFile;
	private Scanner scanner;
	
	public Parser(File file) throws FileNotFoundException{
		// We know the file has been verified in the main,
		// So we can assume it is readable and exists.  
		this.srcFile = file;
		this.scanner = new Scanner(this.srcFile);
	}
	
	
	public void readCode() {
		
	}

}
