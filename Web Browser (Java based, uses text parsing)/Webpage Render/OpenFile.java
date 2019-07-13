/** 
 *   files for reading and writing
 *
 * @author Anirudhan Badrinath
 * @since September 7, 2017
 */
 
 import java.util.Scanner;
 import java.io.PrintWriter;
 
 public class OpenFile {
	 
	 /**
	  * Opens a file to read using the scanner class
	  * @param fileName 	name of filename to open
	  * @return 			the Scanner object to the file
	  */
	  public static Scanner openToRead(String fileName){
		  Scanner input = null;
		  try {
			input = new Scanner(new java.io.File(fileName));
		  }
		  catch (java.io.FileNotFoundException ex){
			System.err.println("ERROR: Cannot open " + fileName + " for" +
			" reading");
			System.exit(404);
		  }
		  return input;	  
	  }
	  
	  /**
	  * Opens a file to write using the PrintWriter class
	  * @param fileName 	name of filename to open
	  * @return 			the PrintWriter object to the file
	  */
	  public static PrintWriter openToWrite(String fileName) {
		PrintWriter output = null;
		try {
			output = new PrintWriter(new java.io.File(fileName));
			}
		catch (java.io.FileNotFoundException ex){
			System.err.println("ERROR: Cannot open " + fileName + " for" +
			" writing");
			System.exit(502);
			}
		return output;
	 }
 }
