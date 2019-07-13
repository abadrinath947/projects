import java.io.PrintWriter;
import java.util.Scanner;

/**
 *	MVCipher - Provides a special cipher algorithm.
 *	Prompts user for a keyword, then applies special cipher 
 *  algorithm using the keyword to a particular input file
 *	to generate a specified output file.
 *  
 *	Requires Prompt and OpenFile classes.
 *	
 *	@author	Anirudhan Badrinath
 *	@since	12 September 2017
 */
public class MVCipher {
	
	private String key;				// uppercase version of key; must be one word
	private String inFileName, outFileName;
	private boolean encrypt;		// encrypt if true; otherwise decrypt
	private Scanner input;			// Input text file
	private PrintWriter output;		// Output text file
	
	/**
	 *  Initializes the value for whether to encrypt or decrypt,
	 *  serves as a constructor for the MVCipher class
	 */
	public MVCipher() {
		encrypt = true;
	}
	
	/**
	 * 	Creates a new MVCipher object using the above constructor and uses
	 *  run() method to start the encryption/decryption process.
	 *  @param args 	Array of the arguments for the main method
	 */
	public static void main(String[] args) {
		MVCipher mvc = new MVCipher();
		mvc.run();
	}
	
	/**
	 *	Displays welcome message to screen and prompts for keyword
	 *  and whether to encrypt or decrypt. Runs encryption or decryption
	 *  based on user choice on a select input file and prints out 
	 *  a "finished" message when process completes.
	 */
	public void run() {
		System.out.println("\n Welcome to the MV Cipher machine!\n");
		
		// Prompt for a key and change to uppercase
		// Do not let the key contain anything but alpha
		// Use the Prompt class to get user input
		
		key = getKeyWord("Please input a word to use as key (letters only)");

		// Prompt for encrypt or decrypt
		if (Prompt.getInt("Encrypt or decrypt (1 - 2)?", 1, 2) == 1)
			encrypt = true;
		else
			encrypt = false;
			
			
		// Prompt for an input file name
		if (encrypt)
			inFileName = Prompt.getString("Name of file to encrypt");
		else
			inFileName = Prompt.getString("Name of file to decrypt");
		input = OpenFile.openToRead(inFileName);
		
		
		// Prompt for an output file name
		outFileName = Prompt.getString("Name of output file");
		output = OpenFile.openToWrite(outFileName);
		
		// Read input file, encrypt or decrypt, and print to output file
		encryptDecryptFile();
		
		
		// Don't forget to close your output file
		if (encrypt)
			System.out.println("\nThe encrypted file " + outFileName + " has been created"
							+ " using the keyword -> " + key + "\n");
		else
			System.out.println("\nThe decrypted file " + outFileName + " has been created"
							+ " using the keyword -> " + key + "\n");
		output.close();
	}
	
	/**
	 *	Reads the input file, encrypts or decrypts the contents,
	 *	then writes the results to an output file.
	 */
	public void encryptDecryptFile() {
		
		String unformattedInput = ""; // Raw String for one line of the file
		char unformattedChar = 0, keywordChar = 0, formattedChar = 0; 
		int increaseUnformattedChar = 0, countOfChar = 0; 
		
		do {
			unformattedInput = input.nextLine();
			// Loop through each character of each line of the input file
			for (int i = 0; i < unformattedInput.length(); i++) {
				unformattedChar = unformattedInput.charAt(i);
				
				// If the specified chararacter is a letter, use the cipher
				if ((unformattedChar >= 'a' && unformattedChar <= 'z') ||
					(unformattedChar >= 'A' && unformattedChar <= 'Z'))
				{
					keywordChar = key.charAt(countOfChar % key.length());
					// If encrypted, use the encrypt reset letters method
					// and otherwise use the decrypt one
					if (encrypt) {
						increaseUnformattedChar = keywordChar - 'A' + 1;
						formattedChar = (char)(unformattedChar + increaseUnformattedChar); 
						output.write(encryptResetToLetters(formattedChar, unformattedChar));
					}
					else {
						increaseUnformattedChar = keywordChar - 'A';
						formattedChar = (char)(unformattedChar - increaseUnformattedChar); 
						output.write(decryptResetToLetters(formattedChar, unformattedChar));
					}
					
					countOfChar++;
				}
				// If the specified character is not a letter, leave as is
				else 
					output.write(unformattedChar);	
			}
			output.println();
		} while (input.hasNextLine());		
	}
	
	/**
	 *	Checks if the now-encrypted char is greater than 'Z' or 'z'
	 *	and then returns the value if the char looped from Z back to A
	 *  
	 *  @param formattedChar	The post-cipher formatted character
	 *  @param unformattedChar 	The pre-cipher unformatted character
	 *  @return 				The char if it stayed within letter boundaries
	 */
	public char encryptResetToLetters(char formattedChar, char unformattedChar) {
		// if it is a lowercase letter	
		while (formattedChar > 'z' && (unformattedChar >= 'a' && unformattedChar <= 'z')) 
			formattedChar = (char)((formattedChar - 'z') + 'a' - 1);
		// if it is a capital letter
		while (formattedChar > 'Z' && (unformattedChar >= 'A' && unformattedChar <= 'Z')) 
			formattedChar = (char)((formattedChar - 'Z') + 'A' - 1);
			
		return formattedChar;
	}
	
	/**
	 *	Checks if the now-decrypted char is less than 'A' or 'a'
	 *	and then returns the value if the char looped from A back to Z
	 *  
	 *  @param formattedChar	The post-cipher formatted character
	 *  @param unformattedChar 	The pre-cipher unformatted character
	 *  @return 				The char if it stayed within letter boundaries
	 */
	public char decryptResetToLetters(char formattedChar, char unformattedChar) {
		
		// Use boolean usedLoop to check if the formatted String has been looped
		// from Z back to A.
		boolean usedLoop = false;
		// If character is lowercase	
		while (formattedChar < 'a' && (unformattedChar >= 'a' && unformattedChar <= 'z')) {
			formattedChar = (char)('z' - ('a' - formattedChar));
			usedLoop = true;
		}
		// If character is capitalized
		while (formattedChar < 'A' && (unformattedChar >= 'A' && unformattedChar <= 'Z')) {
			formattedChar = (char)('Z' - ('A' - formattedChar));
			usedLoop = true;
		}
		
		if (!usedLoop)
			formattedChar--;
		
		return formattedChar;
	}
	/**
	 *	Prompts for a String and converts to uppercase, while
	 * 	continuously asking if keyword not composed fully of letters.
	 *  
	 *  @param ask 				The prompt line
	 *  @return 				User input in uppercase
	 */
	public String getKeyWord(String ask){
		String value = "";
		boolean isInvalid = false;
		do {
			isInvalid = false;
			
			// Use Prompt class and toUpperCase() method to be able to input keyword
			// and make uppercase
			value = Prompt.getString(ask).toUpperCase();
			
			// Cycle through each character to make sure they're letters
			for (int i = 0; i < value.length(); i++){
				if (value.charAt(i) < 'A' || value.charAt(i) > 'Z')
						isInvalid = true;
			}
			
		} while (isInvalid);
		System.out.println();
		return value;
	}
}
