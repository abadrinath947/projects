/**
 * PromptTest.java
 * Tests the Prompt class
 * @author Anirudhan Badrinath
 * @version August 29, 2017
 */

public class PromptTest {
	public static void main(String args[]){
		// Create string and ask Prompt class for the getString method
		// Print out the final string 
		String str = Prompt.getString("Give me a string");
		System.out.println("Here it is -> " + str);
		
		int a = Prompt.getInt("Give me an integer");
		System.out.println("Here it is -> " + a);
		
		double b = Prompt.getDouble("Give me a double");
		System.out.println("Here it is -> " + b);
		
		
		int rangeInt = Prompt.getInt("Give me an integer in the range 2-5"
								,2,5);
		System.out.println("Here it is -> " + rangeInt);
		
		double rangeDouble = Prompt.getDouble("Give me a double in the range 1.0-10.0"
								,1.0,10.0);
		System.out.println("Here it is -> " + rangeDouble);
		
		
		char c = Prompt.getChar("Give me a char");
		System.out.println("Here it is -> " + c);
	}
}
