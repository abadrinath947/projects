/**
 *	Infix to Postfix Tester
 *	Tests the toPostfix method
 *
 *	@author	Mr Greenstein
 *	@since	May 10, 2018
 */
public class ToPostfixTester {
		
	private ExprUtils utils;			// tokenizer and toPostfix methods
	
	private boolean DEBUG = false;		// Mr Greenstein uses
	
	public ToPostfixTester() {
		utils = new ExprUtils();
	}
	
	public static void main(String[] args) {
		ToPostfixTester itpt = new ToPostfixTester();
		itpt.run();
	}
	
	public void run() {
		System.out.println("\nWelcome to Infix to Postfix Converter\n");
		String expr;
		// Expression:  2 * 3 + 4
		expr = "2 * 3 + 4";
		System.out.println("Expression:\t" + expr);
		System.out.println("Postfix:\t" + utils.toPostfix(expr) + "\n");
		// Expression:	2 + 3 * 4
		expr = "2 + 3 * 4";
		System.out.println("Expression:\t" + expr);
		System.out.println("Postfix:\t" + utils.toPostfix(expr) + "\n");
		// Expression:	7.3 / 4.9 + 6.2 * 3
		expr = "7.3 / 4.9 + 6.2 * 3";
		System.out.println("Expression:\t" + expr);
		System.out.println("Postfix:\t" + utils.toPostfix(expr) + "\n");
		// Expression:	96 + 2.8 * 61.1 - 45.2
		expr = "96 + 2.8 * 61.1 - 45.2";
		System.out.println("Expression:\t" + expr);
		System.out.println("Postfix:\t" + utils.toPostfix(expr) + "\n");
		// Expression:	5 * ( 6 + 7 ) / ( 9 % 2 ) - 1
		expr = "5 * ( 6 + 7 ) / ( 9 % 2 ) - 1";
		System.out.println("Expression:\t" + expr);
		System.out.println("Postfix:\t" + utils.toPostfix(expr) + "\n");
		// Expression:	8 / 4 + (2.1 * (5 + 3.3) % (6 - 1))
		expr = "8 / 4 + (2.1 * (5 + 3.3) % (6 - 1))";
		System.out.println("Expression:\t" + expr);
		System.out.println("Postfix:\t" + utils.toPostfix(expr) + "\n");

		System.out.println("\nThanks for using the converter. Good bye!\n");
	}

}