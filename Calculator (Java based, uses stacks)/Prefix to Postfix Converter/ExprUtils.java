import java.util.ArrayList;	// tokenizeExpression uses this
import java.util.List;		// tokenizeExpression uses this

/**
 *	Arithmetic expression utilities
 *	o Methods to convert a String infix expression to list of tokens
 *	  in postfix order. (you write)
 *	o Methods to tokenize an arithmetic expression. (Already written)
 *	o Methods to validate the List of expression tokens (Extra credit)
 *
 *	@author	Mr Greenstein - tokenizeExpression()
 *	@author	Anirudhan Badrinath
 *	@since	
 *
 */
public class ExprUtils {
	
	private ArrayStack<String> operatorStack;	// stack for operators (toPostfix)
	
	public ExprUtils() {
		operatorStack = new ArrayStack<String>();
	}

	/*************************************************************************/
	/******************** Infix to Postfix methods ***************************/
	/*************************************************************************/
	/**	Convert a String from infix notation to a token list in postfix order
	 *	@param expr		the String expression in infix notation
	 *	@return			an ArrayList of the expression tokens in postfix order
	 *
	 *	Algorithm: (describe here)
	 */
	public List<String> toPostfix(String expr) {
		// Create list to hold the postfix notation
		List<String> result = new ArrayList<String>();
		// Create operator stack
		ArrayStack<String> operatorStack = new ArrayStack<String>();
		
		List<String> expression = tokenizeExpression(expr);
		String token = "";
		int c = 0;
		
		while (c < expression.size()){
			token = expression.get(c);
			
			if (isNumber(token))
				result.add(token);
				
			else if (token.equals(")")){
				while (!operatorStack.peek().equals("("))
					result.add(operatorStack.pop());
				operatorStack.pop();	
			}
			
			else if (token.equals("("))
				operatorStack.push(token);
				
			else{
				while (!operatorStack.isEmpty() && !(token.equals("(")) &&
						hasPrecedence(token, operatorStack.peek()))
					result.add(operatorStack.pop());
					
				operatorStack.push(token);
			}
			c++;
		}
		
		while (!operatorStack.isEmpty()){
			result.add(operatorStack.pop());
		}
				
		
		return result;
	}
	
	public void printInorder(TreeNode<String> root) {
		printInorderRecursive(root);
	}

	/** Traverses the tree and prints value
	 *	@param the current node
	 */
	private void printInorderRecursive(TreeNode<String> current){
		if (current == null)
			return;
			
		printInorderRecursive(current.getLeft());
		System.out.print(current.getValue() + " "); // print val
		printInorderRecursive(current.getRight());
	}
	
	/**
	 *	Print Binary Tree Preorder
	 */
	public void printPreorder(TreeNode<String> root) {
		printPreorderRecursive(root);
	}

	/** Traverses the tree and prints value
	 *	@param the current node
	 */
	private void printPreorderRecursive(TreeNode<String> current){
		if (current == null)
			return;
			
		System.out.print(current.getValue() + " "); // print val
		printPreorderRecursive(current.getLeft());
		printPreorderRecursive(current.getRight());
	}
	
	/**
	 *	Print Binary Tree Postorder
	 */
	public void printPostorder(TreeNode<String> root) {
		printPostorderRecursive(root);
	}
	
	/** Traverses the tree and prints value
	 *	@param the current node
	 */
	private void printPostorderRecursive(TreeNode<String> current){
		if (current == null)
			return;
			
		printPostorderRecursive(current.getLeft());
		printPostorderRecursive(current.getRight());
		System.out.print(current.getValue() + " "); // print val
	}
	
	/**	Helper method for toPostfix()
	 *	Precedence of operators
	 *	@param op1		operator 1
	 *	@param op2		operator 2
	 *	@return			true if op2 has higher precedence as op1; false otherwise
	 *
	 *	Algorithm:
	 *		if op2 is exponent or left parenthesis, then true
	 *		if op2 is multiplication or division or modulus and 
	 *				op1 is addition or subtraction, then true
	 *		otherwise false
	 */
	private boolean hasPrecedenceHigher(String op1, String op2) {
		if (op2.equals("^") || op2.equals("(")) return true;
		if ((op2.equals("*") || op2.equals("/") || op2.equals("%")) 
				&& (op1.equals("+") || op1.equals("-")))
			return true;
		return false;
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
	 *	4. Parentheses "(" and ")".
	 *	5. Assignment operator "=".
	 *	6. A binary operator, like "+", "-", "*", "/", "%", or "^".
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
				// Check for:
				// 	Expression starts with unary operator like "-4".
				//	Expression preceded by an assignment operator (=).
				// 	Expression contains consecutive binary and unary operators "3/-2".
				if (lastToken.length() == 0 || lastToken.equals("=") ||
						lastToken.length() == 1 && isBinaryOperator(lastToken.charAt(0))) {
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
				while (ind < expression.length() && 
							Character.isLetter(expression.charAt(ind)))
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
	
	/**	Determine if character is valid binary arithmetic operator excluding
	 *	parentheses.
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
	
	/**
	 *	Determine if string is a number
	 *	@param str		the String to check
	 *	@return			true if a number; false otherwise
	 */
	public boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 *	Tests if String is operator
	 *	@param str		the String to test
	 *	@return			true if "+", "-", "*", "/", "%", or "^"
	 */
	public boolean isOperator(String str) {
		return isOperator(str.charAt(0));
	}

	
	/*************************************************************************/
	/************ Expression Validation methods (Extra Credit) ***************/
	/*************************************************************************/
	/**
	 *	Evaluate expression and determine if it is valid (extra credit)
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			true if expression is valid; false otherwise
	 *
	 *	Algorithm: (describe here)
	 */
	public boolean hasValidExpression(List<String> tokens) {
		
		return false;
	}
	
	/**
	 *	Precedence of operators
	 *	@param op1		operator 1
	 *	@param op2		operator 2
	 *	@return			true if op2 has higher or same precedence as op1; 
	 *					false otherwise
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	public boolean hasPrecedence(String op1, String op2) {
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
		System.out.println("\nTesting tokenizeExpression method\n");
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
