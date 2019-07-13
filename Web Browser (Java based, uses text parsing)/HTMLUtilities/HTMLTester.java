import java.util.Scanner;

/**
 *	Test class for HTMLUtilities
 *	This class can be run with the following command:
 *		java HTMLTester <htmlFileName>
 *
 *	Requires the OpenFile class
 *
 *	@author Mr Greenstein
 *	@version October 12, 2017
 */

public class HTMLTester {

	private HTMLUtilities util;
	
	public HTMLTester() {
		util = new HTMLUtilities();
	}
	
	public static void main(String[] args) {
		HTMLTester ht = new HTMLTester();
		ht.run(args);
	}
	
	/**
	 *	Opens the HTML file specified on the command line
	 *	then inputs each line and prints out the line and the
	 *	tokens produced by HTMLUtilities.
	 *	@param args		the String array holding the command line arguments
	 */
	public void run(String[] args) {
		Scanner input = null;
		String fileName = "";
		if (args.length > 0)
			fileName = args[0];
		else {
			System.out.println("Usage: java HTMLTester <htmlFileName>");
			System.exit(0);
		}
		
		input = OpenFile.openToRead(fileName);
		
		while (input.hasNext()) {
			String line = input.nextLine();
			System.out.println("\n" + line);
			String [] tokens = util.tokenizeHTMLString(line);
			util.printTokens(tokens);
		}
		input.close();
	}
}