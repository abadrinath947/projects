import java.util.List;		// used by expression evaluator
import java.util.ArrayList; // used for extra credit
/**
 *	SimpleCalc is a simple calculator designed to use a user-created
 *	Stack class in conjunction with the Prompt and Identifier classes.
 *	Using precedence and different operators, SimpleCalc simplifies
 *	expressions and can hold values for variables to use in future 
 *	expressions as well. It works using the class ArrayStack to store
 *	values (numbers) entered and the operators entered. Using a clever
 *	algorithm which loops through, it returns a numeric double to the 
 *	user.
 *
 *	@author	Anirudhan Badrinath
 *	@since	1st March, 2018
 */
public class SimpleCalc {
	
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack
	private ArrayList<Identifier> variableStack; // extra credit

	// constructor	
	public SimpleCalc() {
		utils = new ExprUtils();
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
		variableStack = new ArrayList<Identifier>();
		variableStack.add(new Identifier("pi", Math.PI));
		variableStack.add(new Identifier("e", Math.E));
	}
	/**
	 * Main method to create and start the calculator
	 */
	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	/**
	 * Run method to run the calculator and display intro & end messages
	 */
	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() {
		String s = "";
		// while the string inputted is not "q"
		do {
			s = Prompt.getString(" ");
			// help message
			if (s.equals("h"))
				printHelp();
			// extra credit - list vars
			else if (s.equals("l"))
				printVars();
			// otherwise evaluate the expression
			else if (!s.equals("q"))
				System.out.println(evaluateExpression(utils.tokenizeExpression(s)));
		} while (!s.equals("q"));
	}
	
	/**	Print help */
	public void printHelp() {
		// print help message
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}
	/**
	 * Print all user inputted variables as well as the two already inserted ones
	 * (pi and e).
	 */
	public void printVars() {
		System.out.println("\nVariables:");
		// cycle through variable ArrayList
		for (int i = 0; i < variableStack.size(); i++) 
			System.out.println("\t" + variableStack.get(i).getName() + "\t\t=\t\t" + variableStack.get(i).getValue());
		System.out.println();
	}
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 *	Algorithm:
	 *	1. while there are tokens to be read in,
	 *	  1.1 Get the next token
	 *	  1.2 If the token is:
	 *		1.2.1 A number: push it onto the value stack
	 *		1.2.2 (extra credit) A variable: get its value, and push onto the value stack
	 *		1.2.3 A left parenthesis: push it onto the operator stack
	 *		1.2.4 A right parenthesis
	 *		  A. While the thing on top of the operator stack is not
	 *			a left parenthesis
	 *			A.1 Pop the operator from the operator stack
	 *			A.2 Pop the value stack twice, getting two operands.
	 *			A.3 Apply the operator to the operands, in the correct order.
	 *			A.4 Push the result onto the value stack.
	 *		  B. Pop the left parenthesis from the operator stack, and discard.
	 *		1.2.5 An operator
	 *		  A. While the operator stack is not empty, and the top thing
	 *			on the operator stack has the same or greater precedence
	 *			as the token's operator (hasPrecedence below)
	 *			A.1 Pop the operator from the operator stack
	 *			A.2 Pop the value stack twice, getting two operands
	 *			A.3 Apply the operator to the operands, in the correct order.
	 *			A.4 Push the result onto the value stack.
	 *		  B. Push token operator onto the operator stack.
	 *	2. While the operator stack is not empty,
	 *		2.1 Pop the operator from the operator stack.
	 *		2.2 Pop the value stack twice, getting two operands
	 *		2.3 Apply the operator to the operands, in the correct order
	 *		2.4 Push the result on the value stack.
	 *	3. At this point the operator stack should be empty, and value stack
	 *		should have only one value in it, which is the final result. 
	 */
	public double evaluateExpression(List<String> tokens) {
		// initialize answer and the value for the variables
		double value = 0, answer = 0;
		int tokenIndex = 0;
		// extra credit - initialize a variable name
		String varname = "";
		// if variable declaration is detected
		if (tokens.size() >= 2 && !isNumeric(tokens.get(0)) && tokens.contains("=")) {
			varname = tokens.remove(0);
			for (int i = 0; i < variableStack.size(); i++) {
				// if it is a duplicate identifier
				if (tokens.get(0).equals("=") && 
				variableStack.get(i).getName().equals(varname)) {
					// skip the next step by setting finished to true
					tokens.remove(0);
					double valueOfVar = evaluateExpression(tokens);
					variableStack.get(i).setValue(evaluateExpression(tokens));
					System.out.print(varname + " = ");
					return valueOfVar;
				}
			}
				// if not duplicate identifer
				if (tokens.get(0).equals("=")) {
					tokens.remove(0);
					double valueOfVar = evaluateExpression(tokens);
					variableStack.add(new Identifier(varname, valueOfVar));
					System.out.print(varname + " = ");
					return valueOfVar;
				}
		}
		// while not at the end of the List
		while (tokenIndex < tokens.size()) {
			// get the token
			String token = tokens.get(tokenIndex);
			// if it is a variable
			if (!isNumeric(token) && !utils.isOperator(token.charAt(0))) {
				boolean complete = false;
				for (int i = 0; i < variableStack.size(); i++) 
					// if token is found
					if (variableStack.get(i).getName().equals(token)) {
						valueStack.push(variableStack.get(i).getValue());
						complete = true;
					}
				if (!complete) return 0;
			}
			// if it is a number
			else if (isNumeric(token)) 
				valueStack.push(Double.parseDouble(token));
			// if it is a left parenthesis
			else if (token.equals("("))
				operatorStack.push("(");
			// if it is a right parenthesis
			else if (token.equals(")")) {
				while (!operatorStack.peek().equals("("))
					valueStack.push(findAnswer());
				operatorStack.pop();
			}
			// if it is an operator
			else if (utils.isOperator(token.charAt(0))) {
				while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek()))
					valueStack.push(findAnswer());
				operatorStack.push(token);
			}
			// increment the index
			tokenIndex++;
		}
		// run through operator stack
		while (!operatorStack.isEmpty())
			valueStack.push(findAnswer());
		// pop final answer
		value = valueStack.pop();
		return value;
	}
	/**
	 * Check if the supplied string is all alphabetical
	 * characters.
	 * @param name		String supplied by user
	 * @return			whether it is alphabetical
	 */
	private boolean isAlpha(String name) {
		// convert string to array of chars
	    char[] chars = name.toCharArray();
	    // cycle through char array
	    for (char c : chars) 
	        if (!Character.isLetter(c)) 
	            return false;
	    return true;
	}
	/**
	 * Pops last two numbers in value stack and the operator stack to find 
	 * the answer to the operation that needs to be done.
	 * @return		the answer to the operation
	 */
	private double findAnswer(){
		String operator = operatorStack.pop();
		double op2 = valueStack.pop(), op1 = valueStack.pop(), answer = 0;
		// depending on the operator
		switch (operator) {
			// use Math.pow method for exponentiation
			case "^":  answer = Math.pow(op1, op2); break;
			case "*":  answer = op1 * op2; break;
			case "/":  answer = op1 / op2; break;
			case "%":  answer = op1 % op2; break;
			case "+":  answer = op1 + op2; break;
			case "-":  answer = op1 - op2; break;
		}
		return answer;
	}
	/**
	 *	Precedence of operators
	 *	@param op1		operator 1
	 *	@param op2		operator 2
	 *	@return			true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		// checks precedence based on PEMDAS
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
	/**
	 * Checks if the token is numeric (a number)
	 * @param token		the token to be read in
	 * @return			if it is numeric
	 */
	private boolean isNumeric(String token) {
		try {
			Double.parseDouble(token);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
