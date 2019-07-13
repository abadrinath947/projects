import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Test the OpenFile class.
 *
 *	@author Mr Greenstein
 *	@version  August 24, 2015
 */

public class OpenFileTester {
	
	public static void main(String[] args) {
		Scanner infile = OpenFile.openToRead("panama.txt");
		PrintWriter outfile = OpenFile.openToWrite("output.txt");
		while (infile.hasNext()) {
			outfile.println(infile.nextLine());
		}
		outfile.close();
	}
}
