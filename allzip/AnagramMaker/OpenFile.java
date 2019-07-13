import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *  Open files for reading and writing.
 *
 *  @author Mr Greenstein
 *  @version September 7, 2014
 */

public class OpenFile {
	
	/**
	 *	Opens a file to read using the Scanner class.
	 *	@param fileName		name of the file to open
	 *	@return				the Scanner object to the file
	 */
    public static Scanner openToRead(String fileName) {
		Scanner input = null;
		try {
			input = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Cannot open " + fileName + " for reading.");
			System.exit(-1);
		}
		return input;
	}
	
	/**
	 *	Opens a file to write using the PrintWriter class
	 *	@param fileName		name of the file to open
	 *	@return				the PrintWriter object to the file
	 */
	public static PrintWriter openToWrite(String fileName) {
		PrintWriter output = null;
		try {
			output = new PrintWriter(new File(fileName));
		} catch (IOException e) {
			System.err.println("ERROR: Cannot open " + fileName + " for writing.");
			System.exit(-1);
		}
		return output;
	}
}
