/**
 *	Utilities for handling HTML
 *
 *	Tokenizes all HTML code including several tags such as <body>, <html>, 
 *	<br> and <p>, as well as punctuation and whitespace. Demands an unformatted
 *	String with the HTML code and returns a tokenized String array of the same size
 *	as the number of tokens.
 *
 *	@author Anirudhan Badrinath
 *	@since October 17, 2017
 */
public class HTMLUtilities {

	private boolean isInComment; // whether the formatter is inside a comment
	
	/**
	 * The main utility in this class. It takes a string as input, then
	 * tokenizes the string and passes back an array of string tokens.
	 * Precondition: str is not a null pointed
	 * @param str		the input string to tokenize
	 * @return			the array of string tokens 
	 */
	
	public String[] tokenizeHTMLString(String str) {
		String[] result = new String[10000];	// make the size of the array large!
		int resultIndex = -1, strIndex = 0;
		
		// string to hold the token
		String token = "";
		
		while (strIndex < str.length()) {
			char c = str.charAt(strIndex);
			
			// check if it's the end of a comment
			if (str.indexOf("-->", strIndex) == strIndex) {
				isInComment = false;
				strIndex += 2;
				// make sure no tokens are recorded
				token = "";
			}
			// check if it's the beginning of the comment
			else if (str.indexOf("<!--", strIndex) == strIndex) {
				// set isInComment to true so that no tokens are recorded
				isInComment = true;
				// make sure no tokens are recorded
				token = "";
			}
			// check if beginning of a non-comment token
			else if (c == '<' && !isInComment) {
				int temp = strIndex;
				// if there is already a token, end it as long as it is
				// length > 0
				if (token.length() > 0) {
					resultIndex++;
					result[resultIndex] = token;
					// reset token
					token = "";
				}
				// add every character of the token to the token String
				for (int i = strIndex; i <= str.indexOf('>', strIndex); i++) 
					token += str.charAt(i);
				strIndex = str.indexOf('>', temp);
				c = str.charAt(strIndex);
			}
			// check if it is non-comment punctuation (including ellipsis)
			else if (isPunctuation(c) && !isInComment) {
				// if there is already a token, end it as long as it is
				// length > 0
				if (token.length() > 0) {
					resultIndex++;
					result[resultIndex] = token;
					// reset token
					token = "";
				}
				// check for ellipsis and if not ellipsis, add that char
				if (str.indexOf("...", strIndex) == strIndex) {
					token += "...";
					strIndex += 2;
					c = str.charAt(strIndex);
				}
				else 
					token += c;
			}
			// add non-whitespace and non-comment characters if all fails
			else if (!Character.isWhitespace(c) && !isInComment ) {
				token += c;
			}
			if (token.length() > 0 && (Character.isWhitespace(c) || c == '>' ||
					c == '<' || isPunctuation(c))){
				resultIndex++;
				result[resultIndex] = token;
				// reset token
				token = "";
			}
			strIndex++;
		}
		// in case last token goes to end of string
		if (token.length() > 0) {
			resultIndex++;
			result[resultIndex] = token;
		}
		
		result = sizeArray(result, resultIndex + 1);
		
		return result;
	}
	
	/** Takes a large string array as input and outputs a
	 *  copy string array that is exactly the size of the 
	 *  number of valid elements.
	 * 	@param arr		the input String array
	 * 	@param num 		the number of valid elements in array
	 * 	@return a 		a copy of the String array with exactly the number
	 * 					of valid elements
	 */
	 public String[] sizeArray(String[] arr, int num) {
		// new array with size as the valid elements in array
		String[] result = new String[num];
		// loop through array and assign each value
		for (int a = 0; a < num; a++) 
			result[a] = arr[a];
		return result;
	 } 
	
	/**
	 *	Checks if the character given is punctuation (not
	 *	letters or numbers) and then returns whether a 
	 *	boolean value.
	 *	@param c		the character in the main String
	 *	@return 		whether it is punctuation
	 */
	private boolean isPunctuation(char c) {
		// punctuation if it is not whitespace, letter or digit
		if ( !Character.isWhitespace(c) && !Character.isLetter(c) && !Character.isDigit(c))
			return true;
		return false;
	}
	
	/**
	 *	Print the tokens in the array
	 *	Precondition: All elements in the array are valid String objects. (no nulls)
	 *	@param tokens	an array of String tokens
	 */
	public void printTokens(String[] tokens) {
		if (tokens == null) return;
		for (int a = 0; a < tokens.length; a++) {
			if (a % 5 == 0) System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}
}
