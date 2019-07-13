import java.util.ArrayList;
/**
 *	AnagramMaker - This utility finds anagrams of a given size in terms of 
 *	words when given a list of words and a target word. It will cycle through
 *	the text file and find a list of anagrams that match the user-inputted 
 *	word in length and letters (not the order). The user will also need to 
 *	input a maximum number of anagram phrases to output since there will be
 *	too many. In addition, they will input a target number of words per 
 *	anagram. Users can input 'q' to quit at any time.
 *
 *	Requires the WordUtilities, SortMethods, Prompt, and OpenFile classes
 *
 *	@author	Anirudhan Badrinath
 *	@since	Jan 22, 2018
 */
public class AnagramMaker {
								
	private final String FILE_NAME = "randomWords.txt";	// file containing all words
	
	private WordUtilities wu;	// the letters utilities for building the letters
								// database, sorting the database,
								// and finding all words that match
								// a string of characters
	
	// variables for constraining the print output of AnagramMaker
	private int numWords;		// the number of words in a phrase to print
	private int maxPhrases;		// the maximum number of phrases to print
	private int numPhrases;		// the number of phrases that have been printed

		
	/**	Initialize the database inside WordUtilities
	 *	The database of words does NOT have to be sorted for AnagramMaker to work,
	 *	but the output will appear in order if you DO sort.
	 **/
	public AnagramMaker() {
		// create new WordUtilities object and sort the file with words in it
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}
	/**
	 * Main method which creates an AnagramMaker object
	 * and runs the program.
	 */
	public static void main(String[] args) {
		// create obj and run the program
		AnagramMaker am = new AnagramMaker();
		am.run();
	}
	
	/**	The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run() {
		// prints introduction to the program
		printIntroduction();
		// runs the main program
		runAnagramMaker();
		// end message
		System.out.println("\nThanks for using AnagramMaker!\n");
	}
	
	/**
	 *	Print the introduction to AnagramMaker
	 */
	public void printIntroduction() {
		System.out.println("\nWelcome to ANAGRAM MAKER");
		System.out.println("\nProvide a letters, name, or phrase and out comes their anagrams.");
		System.out.println("You can choose the number of words in the anagram.");
		System.out.println("You can choose the number of anagrams shown.");
		System.out.println("\nLet's get started!");
	}
	
	/**
	 *	Prompt the user for a phrase of characters, then create anagrams from those
	 *	characters.
	 */
	public void runAnagramMaker() {
		// initial prompt to get the user input
		String phrase = Prompt.getString("\nWord(s), name, or phrase (q to quit)");
		// after initial prompt, keep repeating till user enters 'q'
		while (!phrase.toLowerCase().equals("q")) {
			// reset values of the 3 field variables
			maxPhrases = 0; numWords = 0; numPhrases = 0;
			String newPhrase = "";
			// get user input
			numWords = Prompt.getInt("Number of words in anagram");
			maxPhrases = Prompt.getInt("Maximum number of phrases to print");
			System.out.println();
			// remove all nonalphabetic characters
			for (int i = 0; i < phrase.length(); i++)
				if (phrase.charAt(i) >= 'a' && phrase.charAt(i) <= 'z' || phrase.charAt(i) >= 'A' && phrase.charAt(i) <= 'Z')
					newPhrase += phrase.charAt(i);
			// use recursive method to get all anagrams
			recurse(newPhrase, new ArrayList<String>());
			// if manually stopped due to entered max phrases, alert user
			if (numPhrases == maxPhrases) 
				System.out.println("\nStopped at " + numPhrases + " anagrams");
			// repeat the initial prompt
			phrase = Prompt.getString("\nWord(s), name, or phrase (q to quit)");
		}
	}
	/**
	 * Recursively parses a list of random words and uses given methods
	 * to check if there are anagrams to be formed using these words to
	 * form a user-inputted word. Requires the variables numPhrases,
	 * maxPhrases and numWords.
	 * 
	 * @param phrase	the phrase being broken into anagrams
	 * @param anagram	the ArrayList of anagrams
	 */
	public void recurse(String phrase, ArrayList<String> anagram) {
		// BASE CASE: if the phrase length is 0 and the size of
		// the anagram is the same as what is wanted by the user
		if (phrase.length() == 0 && anagram.size() == numWords){
			numPhrases++;
			// print out all from the ArrayList
			for (String letters : anagram)
				System.out.print(letters + " ");
			System.out.println();
		}
		// non-base case a.k.a recursive case
		else {	
			ArrayList<String> allWords = wu.allWords(phrase);
			// cycle through the words returned by the allWords method
			for (int i = 0; i < allWords.size(); i++) {
				// add to ArrayList
				anagram.add(allWords.get(i));
				if (anagram.size() > numWords) {
					anagram.remove(anagram.size()-1);
					return;
				}
				// recursive call
				recurse(removeWord(allWords.get(i),phrase), anagram);
				// if we exceed the number wanted by user, exit
				if (numPhrases >= maxPhrases)
					return;
				// remove last element
				anagram.remove(anagram.size() - 1);
			}
			return;
		}
	}
	/**
	 *	Remove letters from a phrase (helper utility). ONLY
	 *	removes the first instance of every character in the letters
	 *	however.
	 *	@param letters		the letters to remove from phrase
	 *	@param phrase		the phrase from which to remove
	 *	@return				the phrase after all given letters removed
	 */
	private String removeWord(String letters, String phrase) {
		// cycle through letters
		while (letters.length() > 0) {
			// get the index where the first letter is
			int i = phrase.toLowerCase().indexOf(Character.toLowerCase(letters.charAt(0))) + 1;
			// get rid of the letter
			letters = letters.substring(1);
			// change phrase using String methods
			phrase = phrase.substring(0, i - 1)
					+ phrase.substring(i);
		}
		// return final String
		return phrase;
	}
}
