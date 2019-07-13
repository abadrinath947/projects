import java.util.Scanner;

/**
 *	Prompt.java
 *	Provide some utilities for user input.  We want to enhance the Scanner class,
 *	so that our programs can recover from "bad" input, and also provide a way to
 *	limit numerical input to a range of values.
 *
 *	@author Anirudhan Badrinath
 *	@version 08/29/17
 */

public class Prompt
{
	/**
	 *	Given an input String to display to the user, this method will
	 *  return another String which captures user input using a Scanner
	 *  object, asking continuously until success
	 */
	public static String getString (String ask)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print(ask + " -> ");
		String input = keyboard.nextLine();		
		return input;
	}
	
	/**
	 *	Given an input String to display to the user, this method will
	 *  return a char which captures user input using a Scanner class
	 *  object, asking continuously until success
	 */
	public static char getChar (String ask)
	{
		
		String input = new String("");
		char result = 0;
		do {
			input = getString(ask);
			result = input.charAt(0);
		} while (input.length() > 1);
		return result;
	}
	
	/**
	 *	Given an input String to display to the user, this method will
	 *  return an int which captures user input using a Scanner class
	 *  object, asking continuously until success
	 */
	public static int getInt (String ask)
	{
		boolean badInput = false;
		String input = new String("");
		int result = 0;
		do {
			badInput = false;
			input = getString(ask);
			try {
				result = Integer.parseInt(input);
			}
			catch (NumberFormatException e){
				badInput = true;
			}
		} while (badInput);
		return result;
	}
	
	/**
	 *	Given an input String to display to the user, this method will
	 *  return an int which captures user input using a Scanner class
	 *  object, but only if the input is within the specified numerical range,
	 *  asking continuously until success
	 */
	public static int getInt (String ask, int min, int max)
	{
		int value = 0;
		do{
		value = getInt(ask);
	}
		while (value < min || value > max);
		return value;
	}
	
	/**
	 *	Given an input String to display to the user, this method will
	 *  return a double which captures user input using a Scanner class
	 *  object, asking continuously until success
	 */
	public static double getDouble (String ask)
	{
		boolean badInput = false;
		String input = new String("");
		double result = 0.0;
		do {
			badInput = false;
			input = getString(ask);
			try {
				result = Double.parseDouble(input);
			}
			catch (NumberFormatException e){
				badInput = true;
			}
		} while (badInput);
		return result;
	}
	
	/**
	 *	Given an input String to display to the user, this method will
	 *  return an double which captures user input using a Scanner class
	 *  object, but only if the input is within the specified numerical range,
	 *  asking continuously until success
	 */
	public static double getDouble (String ask, double min, double max)
	{
		double value = 0;
		do {
			value = getDouble(ask);
		} while (value < min || value > max);
		return value;
	}
}
