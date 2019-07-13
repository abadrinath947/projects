/**
 *	HTMLRender
 * 
 *	This program renders HTML code into a JFrame window. Uses
 * 	the HTMLUtilities class to tokenize and then prints out 
 * 	the tokens and plain text in a GUI window. It traverses
 * 	the String array created by the HTMLUtilities class and
 * 	modifies the given text based on the various tags, such
 * 	as bolding or italicizing. 
 * 
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline (break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hx>, </hx> - Start/end of heading of size x = 1, 2, 3, 4, 5, 6
 *	Optional tags supported:
 *		<pre>, </pre> - Preformatted text, optional
 *
 *	@author Anirudhan Badrinath
 *	@version 12 November 2017
 */

import java.util.Scanner;
 
public class HTMLRender {
	
	// the array holding all the tokens of the HTML file
	private String [] tokens;
	private final int TOKENS_SIZE = 100000;	// size of array
	private int index;

	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;

	/**
	 * Initializes 
	 */
	public HTMLRender() {
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
	}
	/**
	 * 
	 */
	public static void main(String[] args) {
		// create new HTMLRender object
		HTMLRender hf = new HTMLRender();
		// Use run method to execute program
		hf.run(args);
	}
	
	public void run(String[] args) {
		// find where array ends (e.g where its values exist)
		int length = openAndConvert(args);
		// parseArray and print results
		parseArray(tokens, length);	
	}
	/**
	 *  Parses the array created by HTMLUtilities and invokes
	 *  helper methods to print out text on screen. Wraps
	 *  around when 80 characters are reached per line. Supports
	 *  paragraph tags, horizontal rule, break, bold,
	 *  italics, quotes and 6 types of headers.
	 *  @param tokens		String array of tokenized HTML
	 *  @param length		effective length of array (non-null)
	 */
	public void parseArray(String[] tokens, int length) {
		int pixel = 0;
		while (index < length) {
			String current = tokens[index].toLowerCase();
			// If it represents a required tag, just skip it (since no action is required)
			if (current.equals("<html>") || current.equals("</html>") || current.equals("<body>")
					|| current.equals("</body>")) index++;
			// If a paragraph beginning tag is spotted
			else if (current.equals("<p>")) {
				index++;
				doParagraph();
				pixel = 0;
			}
			// If a horizontal rule is spotted
			else if (current.equals("<hr>")) {
				browser.printHorizontalRule();
				index++;
				pixel = 0;
			}
			// If a break is spotted
			else if (current.equals("<br>")) {
				browser.printBreak();
				index++;
				pixel = 0;
			}
			// If text needs to be bolded
			else if (current.equals("<b>")) {
				index++;
				pixel = doBoldAndItalic(pixel, index, true);
				
			}
			// If text needs to be italicized
			else if (current.equals("<i>")) {
				index++;
				pixel = doBoldAndItalic(pixel, index, false);
			}
			// If either beginning or end quotes are spotted
			else if (current.equals("<q>") || current.equals("</q>")) {
				if (current.equals("<q>")) { 
					browser.print(" \""); 
					pixel++;
				}
				else browser.print("\"");
				pixel++; index++;
			}
			// If a header is spotted
			else if (current.equals("<h1>") || current.equals("<h2>") || current.equals("<h3>")
					|| current.equals("<h4>") || current.equals("<h5>") || current.equals("<h6>")) {
				index++;
				doHeaders(current.charAt(2));
				pixel = 0;
			}
			// If just plain text
			else {
				pixel = printText(pixel, index);
				index++;
				
			}
		}
	}
	/**
	 *  Attempts to find ending header tag and print text
	 *  in between those two tags in header format given a 
	 *  beginning index and the type of header.
	 *  @param headerType		which HTML header type it is (1,2,3,4,5,6)
	 */
	public void doHeaders(char headerType) {
		int end = 0, pixel = 0; 
		// use for loop to find matching end header
		for (int i = tokens.length - 1; i > index; i--) 
			// Tries to find matching end header tag (</hN>)
			if (tokens[i] != null && tokens[i].toLowerCase().equals("</h" + headerType + ">")) 
				end = i;

		for (int i = index; i < end; i++) 
			pixel = printHeaders(pixel, i, headerType);
		// Sets index to one after the end header tag
		index = end + 1;
		// Print newline twice
		browser.println(); browser.println();
		// No need to return where on the screen we are since it is at 0

	}
	/**
	 *	Checks if the character given is punctuation (not
	 *	letters or numbers) and then returns whether a 
	 *	boolean value.
	 *	@param c		the character in the String
	 *	@return 		whether it is punctuation
	 */
	private boolean isPunctuation(char c) {
		// punctuation if it is not whitespace, letter or digit
		if ( !Character.isWhitespace(c) && !Character.isLetter(c) && !Character.isDigit(c))
			return true;
		return false;
	}
	/**
	 * 	Prints out HTML headers based on the type of header, the place
	 * 	on the screen and the current String array index. Prints new line 
	 * 	if 80 chars per line is exceeded and otherwise prints the various
	 * 	types of headers available.
	 * 	@param pixel 		the placement on the screen
	 * 	@param index		index of String array 
	 * 	@param headerType	which HTML header it is (1,2,3,4,5,6)
	 * 	@return 			the final placement on the screen
	 */
	public int printHeaders(int pixel, int index, char headerType) {
		// if we need to go to the next line since we're done with 80 characters
		if (pixel + tokens[index].length() > 80) {
			browser.println();
			pixel = 0;
		}
		// don't print space before if it's punctuation
		if (pixel == 0 || tokens[index].length() == 1 && 
				isPunctuation(tokens[index].charAt(0))) {
			
			pixel += tokens[index].length();
			switch (headerType) {
			// DON'T ADD SPACE
			case '1': browser.printHeading1(tokens[index]);
						break;
			case '2': browser.printHeading2(tokens[index]);
						break;
			case '3': browser.printHeading3(tokens[index]);
						break;
			case '4': browser.printHeading4(tokens[index]);
						break;
			case '5': browser.printHeading5(tokens[index]);
						break;
			case '6':	browser.printHeading6(tokens[index]);
						break;
			}
		}
	
		// if all else fails, assume we need space before
		else {
			switch (headerType) {
			// ADD SPACE
			case '1': browser.printHeading1(" " + tokens[index]);
						break;
			case '2': browser.printHeading2(" " + tokens[index]);
						break;
			case '3': browser.printHeading3(" " + tokens[index]);
						break;
			case '4': browser.printHeading4(" " + tokens[index]);
						break;
			case '5': browser.printHeading5(" " + tokens[index]);
						break;
			case '6':	browser.printHeading6(" " + tokens[index]);
						break;
			}
			// increment pixel and add 1 for the length of the space
			pixel += tokens[index].length() + 1;
		}
		// to keep track of where our pointer is 
		return pixel;

	}
	/**
	 *  Prints out HTML bold and italics based on if it is bold, the place
	 * 	on the screen and the current String array index. Prints new line 
	 * 	if 80 chars per line is exceeded and otherwise prints in either
	 * 	bold or italics.
	 * 	@param pixel 		the placement on the screen
	 * 	@param index		index of String array 
	 * 	@param bold			whether it is bold or not (italic)
	 * 	@return 			the final placement on the screen
	 */
	public int printBoldAndItalic(int pixel, int index, boolean bold) {
		// if we need to go to the next line since we're done with 80 characters
		if (pixel + tokens[index].length() > 80) {
			browser.println();
			pixel = 0;
		}
		// if we're at the beginning of the line or if it's punctuation, no space
		if (pixel == 0 || tokens[index].length() == 1 && isPunctuation(tokens[index].charAt(0))) {
			pixel += tokens[index].length();
			if (bold) browser.printBold(tokens[index]);
			else browser.printItalic(tokens[index]);
		}
		// otherwise, we need a space
		else {
			if (bold) browser.printBold(" " + tokens[index]);
			else browser.printItalic(" " + tokens[index]);
			pixel += tokens[index].length() + 1;
		}
		// keep track of where on the screen we're printing
		return pixel;

	}
	/**
	 * 	Prints normal text for paragraphs and other formats given 
	 * 	the place on the screen and current String array index. Prints
	 * 	new line if 80 chars per line is exceeded and otherwise prints 
	 * 	on the same line.
	 * 	@param pixel 		placement on the screen
	 * 	@param index		index of String array
	 * 	@return 			the final placement on the screen
	 */
	public int printText(int pixel, int index) {
		// if we're going to go above 80 chars, newline
		if (pixel + tokens[index].length() > 80) {
			browser.println();
			pixel = 0;
		}
		// if we're at the beginning of the line or if it's punctuation, no space
		if (pixel == 0 || tokens[index].length() == 1 && isPunctuation(tokens[index].charAt(0))) {
			pixel += tokens[index].length();
			browser.print(tokens[index]);
		}
		// otherwise, we need a space
		else {
			browser.print(" " + tokens[index]);
			pixel += tokens[index].length() + 1;
		}
		// keep track of where on the screen we're printing
		return pixel;
	}
	/**
	 *  Handles the printing of a paragraph given an initial paragraph
	 *  tag. Finds end tag and uses the current placement on the screen
	 *  to wraparound when 80 chars per line is exceeded. Finds nested 
	 *  italics and bold as well.
	 */
	public void doParagraph() {
		int end = 0, pixel = 0;
		// go to next line to print a paragraph
		browser.println();
		// use for loop to find matching paragraph tag
		for (int i = tokens.length - 1; i > index; i--) 
			if (tokens[i] != null && tokens[i].toLowerCase().equals("</p>")) 
				end = i;
		// if there is a nested italic/bold or just normal print
		for (int i = index; i < end; i++) {
			// if an italics is spotted (nested)
			if (tokens[i].toLowerCase().equals("<i>")) {
				i++;
				pixel = doBoldAndItalic(pixel,i,false);
				i = this.index;
			}
			// if a bold is spotted (nested)
			else if (tokens[i].toLowerCase().equals("<b>")) {
				i++;
				pixel = doBoldAndItalic(pixel,i,true);
				i = this.index;
			}
			// index will be automatically incremented inside if statement
			pixel = printText(pixel, i);

		}
		index = end + 1;
		// go to next line
		browser.println(); browser.println();

	}
	/**
	 * 	Handles non-nested bold and italics given the initial tag,
	 * 	the initial placement on the screen, index in the String
	 * 	array and whether it is bold or italics. Finds the matching
	 * 	end tag and prints out to screen.
	 * 	@param pixel		placement on screen
	 * 	@param index		index in String array
	 *  @param bold			whether it is bold or italics
	 *  @return 			final placement on screen
	 */
	public int doBoldAndItalic(int pixel, int index, boolean bold) {
		int end = 0;
		// Finds matching end tag (either italics or bold based on boolean)
		for (int i = tokens.length - 1; i > index; i--) 
			if (!bold && tokens[i] != null && tokens[i].toLowerCase().equals("</i>")) 
				end = i;
			else if (bold && tokens[i] != null && tokens[i].toLowerCase().equals("</b>")) 
				end = i;
		// Prints out from the beginning index to ending index
		for (int i = index; i < end; i++) 
			pixel = printBoldAndItalic(pixel, i, bold);
		// sets index to right after the end tag
		this.index = end + 1;
		return pixel;

	}
	/**
	 *  Opens a valid HTML file and uses the class HTMLUtilities to create
	 *  an array of Strings that comprise of tokenized HTML.
	 *  @param args 		CLI arguments that represent name of file
	 *  @return				the effective length of array (non-null)
	 */
	public int openAndConvert(String[] args) {
		Scanner input = null;
		String fileName = ""; // to store filename
		HTMLUtilities converter = new HTMLUtilities();
		int i = 0; // to store index in the array and final length
		
		if (args.length > 0)
			// make sure filename is not empty
			fileName = args[0];
		else {
			System.out.println("Usage: java HTMLRender <htmlFileName>");
			System.exit(0);
		}
		// Use OpenFile class to open the user inputted filename
		input = OpenFile.openToRead(fileName);
		
		while (input.hasNext()) {
			String line = input.nextLine();
			// create temporary array to hold one line of tokenized HTML
			String[] tempTokens = converter.tokenizeHTMLString(line);
			// transfer all tokens from temp array to final array
			for (int a = 0; a < tempTokens.length; a++) {
				tokens[i] = tempTokens[a];
				// increment place in final array
				i++;
			}
		}
		// close scanner
		input.close();
		// return length of final array
		return i;
	}
}
