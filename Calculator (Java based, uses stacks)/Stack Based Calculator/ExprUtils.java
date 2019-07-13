import java.util.ArrayList;
import java.util.List;

/**
 *	Arithmetic expression utilities
 *	o Methods to tokenize an arithmetic expression.
 *	o Methods to validate the List of expression tokens
 *
 *	@author	Mr Greenstein
 *	@since	February 8, 2018
 *
 *	Feb 14, 2018
 *		o Fixed bug in tokenizeExpression that erroneously tokenized ") - 18" as [")", "-18"]
 *		o Created isBinaryOperator() method that finds operators excluding parentheses
 */
public class ExprUtils {
	
	private ArrayStack<String> operatorStack;	// stack for operators
	private ArrayStack<Double> valueStack;		// stack for values
	
	public ExprUtils() {
		operatorStack = new ArrayStack<String>();
		valueStack = new ArrayStack<Double>();
	}
	/*************************************************************************/
	/****************** Expression Tokenizer methods *************************/
	/*************************************************************************/
	/**
	 *	Tokenize a mathematical expression. This can handle the following
	 *	situations:
	 *	1. The unary operator "-" or "+".
	 *	2. An integer or decimal number. The decimal number can start with
	 *		a digit or a decimal (".").
	 *	3. An identifier for a variable which contains only letters.
	 *	4. An operator, like "(", ")", "+", "-", etc.
	 *	
	 *	@param expression	the expression to tokenize
	 *	@return				a List of tokens, each token has a length of one or greater
	 */
	public List<String> tokenizeExpression(String expression) {
		// remove extraneous characters from expression
		expression = cleanExpr(expression);
		
		// the expression tokens
		List<String> result = new ArrayList<String>();
		
		// Keep track of last token processed
		String lastToken = "";
		
		int ind = 0;	// index into String expression
		
		// while there are characters in the expression
		while (ind < expression.length()) {
			char c = expression.charAt(ind++);
			String token = "";
			
			// if character is "-" or "+"
			if (c == '-' || c == '+') {
				// check if there is a "-" or "+" operator preceding this character.
				// This means:
				// 	Expression starts with unary operator like "-4".
				// 	Expression contains two consecutive unary operators "3+-2".
				if (lastToken.length() == 0 || lastToken.equals("=") ||
							lastToken.equals("+") || lastToken.equals("-")) {
					// precede number or variable with unary operator
					token += c;
					// If what follows is a digit, then input number
					if (Character.isDigit(expression.charAt(ind))) {
						// Add number to unary operator, e.g. "-" + "4.3" => "-4.3"
						while (ind < expression.length() && 
							( Character.isDigit(expression.charAt(ind)) ||
								expression.charAt(ind) == '.') )
							token += expression.charAt(ind++);
					}
					// else what follows is a variable, input variable
					else {
						while (ind < expression.length() && 
								Character.isLetter(expression.charAt(ind)))
							token += expression.charAt(ind++);
					}
					result.add(token);
				}
				else {
					token = "" + c;
					result.add(token);
				}
			}
			// if character is digit or decimal, read in number
			else if (Character.isDigit(c) || c == '.') {
				token += c;
				while (ind < expression.length() && 
					( Character.isDigit(expression.charAt(ind)) ||
						expression.charAt(ind) == '.') )
					token += expression.charAt(ind++);
				result.add(token);
			}
			// if character is a letter, read in alpha identifier
			else if (Character.isLetter(c)) {
				token += c;
				while (ind < expression.length() && Character.isLetter(expression.charAt(ind)))
					token += expression.charAt(ind++);
				result.add(token);
			}
			// if character is operator or parentheses (, ), +, -, *, /, or '='
			else if (isOperator(c)) {
				token = "" + c;
				result.add(token);
			}
			
			// if anything else, do nothing
			
			lastToken = token;
		}
		
		return result;
	}
	
	/**
	 *	Remove extraneous characters (like spaces)
	 *	@param expr		the expression String
	 *	@return			the expression with extraneous characters removed
	 */
	private String cleanExpr(String expr) {
		String result = "";
		for (int a = 0; a < expr.length(); a++)
			if (validChar(expr.charAt(a))) result += expr.charAt(a);
		return result;
	}
	
	/**
	 *	Test if the character is valid:
	 *		letter, digit, arithmetic operator, or decimal point
	 *	@param c	character to check
	 *	@return		true if character is valid; false otherwise
	 */
	private boolean validChar(char c) {
		if (Character.isLetterOrDigit(c) || isOperator(c) || c == '.')
			return true;
		return false;
	}
	
	
	/**	Determine if character is valid arithmetic operator including parentheses
	 *	@param c	the character to check
	 *	@return		true if the character is '+', '-', '*', '/', '^', '=','(', or ')'
	 */
	public boolean isOperator(char c) {
		return isBinaryOperator(c) || c == '(' || c == ')';
	}
	
	/**	Determine if character is valid binary arithmetic operator excluding parentheses
	 *	@param c	the character to check
	 *	@return		true if the character is '+', '-', '*', '/', '^', or '='
	 */
	private boolean isBinaryOperator(char c) {
		switch (c) {
			case '+': case '-': case '*': case '/': 
			case '%': case '=': case '^':
				return true;
		}
		return false;
	}

	
	/*************************************************************************/
	/***************** Expression Validation methods *************************/
	/*************************************************************************/
	/**
	 *	Evaluate expression and determine if it is valid
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			true if expression is valid; false otherwise
	 *
	 *	Algorithm:
	 *	1. while there are tokens to be read in,
	 *		1.1 Get the next token
	 *		1.2 If the token is:
	 *			1.2.1 A number: push it onto the value stack
	 *			1.2.2 (extra credit) A variable: get its value, and push onto the value stack
	 *			1.2.3 A left parenthesis: push it onto the operator stack
	 *			1.2.4 A right parenthesis
	 *				A. While the thing on top of the operator stack is not
	 *					a left parenthesis
	 *					A.1 Pop the operator from the operator stack
	 *					A.2 Pop the value stack twice, getting two operands.
	 *					A.3 Apply the operator to the operands, in the correct order.
	 *					A.4 Push the result onto the value stack.
	 *				B. Pop the left parenthesis from the operator stack, and discard.
	 *			1.2.5 An operator
	 *				A. While the operator stack is not empty, and the top thing
	 *					on the operator stack has the same or greater precedence
	 *					as the token's operator
	 *					A.1 Pop the operator from the operator stack
	 *					A.2 Pop the value stack twice, getting two operands
	 *					A.3 Apply the operator to the operands, in the correct order.
	 *					A.4 Push the result onto the value stack.
	 *				B. Push token operator onto the operator stack.
	 *	2. While the operator stack is not empty,
	 *		2.1 Pop the operator from the operator stack.
	 *		2.2 Pop the value stack twice, getting two operands
	 *		2.3 Apply the operator to the operands, in the correct order
	 *		2.4 Push the result on the value stack.
	 *	3. At this point the operator stack should be empty, and value stack
	 *		should have only one value in it, which is the final result. 
	 */
	public boolean hasValidExpression(List<String> tokens) {
		int index = 0;
		double value = 0;
		String token;
		
		// Empty stacks
		while (! valueStack.isEmpty()) valueStack.pop();
		while (! operatorStack.isEmpty()) operatorStack.pop();
		
		// 1. while there are tokens to be read in
		while (index < tokens.size()) {
			token = tokens.get(index);
			//System.out.println("hasValidExpression: token = " + token);	// debug
			// 1.2.1 Token is a number
			if (Character.isDigit(token.charAt(0)) || 
					(token.length() > 1 && Character.isDigit(token.charAt(1))) )
				valueStack.push(Double.parseDouble(token));
			// 1.2.2 Token is variable (extra credit)
			else if (Character.isLetter(token.charAt(0)))
				valueStack.push(0.0);
			// 1.2.3 A left parenthesis
			else if (token.charAt(0) == '(')
				operatorStack.push(token);
			// 1.2.4 A right parenthesis
			else if (token.charAt(0) == ')') {
				// A. while the thing on top of the operator stack is not left paren
				while (! operatorStack.peek().equals("(")) {
					// pop operator from operatorStack, pop two values from valueStack,
					// compute result and push on valueStack
					popComputePush();
				}
				// B. pop the left parenthesis from the operatorStack
				if (operatorStack.isEmpty()) return false;
				operatorStack.pop();
			}
			// 1.2.5 An operator
			else if (isOperator(token.charAt(0))) {
				// A. while the operator stack is not empty, and the top operator on
				//	operatorStack has the same or greater precedence as token's operator
				while (! operatorStack.isEmpty() && 
							hasPrecedence(token, operatorStack.peek()) ) {
					// pop operator from operatorStack, pop two values from valueStack,
					// compute result and push on valueStack
					if (! popComputePush()) return false;
				}
				// Push token operator onto the operatorStack
				operatorStack.push(token);
			}
			// advance token index
			index++;
		}
		// 2. While the operatorStack is not empty
		while (! operatorStack.isEmpty()) {
			// pop operator from operatorStack, pop two values from valueStack,
			// compute result and push on valueStack
			if (! popComputePush()) return false;
		}
		if (valueStack.isEmpty()) return false;
		value = valueStack.pop();
		return true;
	}
	
	/**
	 *	Pop the operatorStack and pop two values from the valueStack,
	 *	then perform arithmetic operation and push result onto valueStack
	 *	@return		true if stacks can be popped; false otherwise
	 */
	private boolean popComputePush() {
		if (! operatorStack.isEmpty()) operatorStack.pop();
		else return false;
		
		if (! valueStack.isEmpty()) valueStack.pop();
		else return false;
		
		if (! valueStack.isEmpty()) valueStack.pop();
		else return false;
		
		// push a dummy value on stack
		valueStack.push(0.0);
		
		return true;
	}
	
	/**
	 *	Precedence of operators
	 *	@param op1		operator 1
	 *	@param op2		operator 2
	 *	@return			true if op2 has higher or same precedence as op1; false otherwise
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
	
	
	/******************************************************************/
	/************************** For Testing ***************************/
	/******************************************************************/
	public static void main(String[] args) {
		ExprUtils et = new ExprUtils();
		et.run();
	}
	
	public void run() {
		List<String> tokens;
		String expr;
		System.out.println();
		
		expr = "2 + 3 * 5";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "xa = 2.1 + 3 * (5 - 4)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "3.456 * 23 / (.5 - 23)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "- 54 + - .12";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "4 * (3 + 2) - 18 / (6 * 3)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
		
		expr = "- 1 + 1";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
	}
}
