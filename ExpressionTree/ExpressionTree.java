import java.util.ArrayList;
import java.util.List;

/**
 *	ExpressionTree - Creates an expression tree from an expression given
 *				in infix notation.
 *
 *	@author	Anirudhan Badrinath
 *	@since	24 May 2018
 */
public class ExpressionTree {
		
	private List<String> tokens;		// the tokens of the expression
	private int tokenIndex;				// index into tokens
	
	private String expr;				// expression
	
	private TreeNode<String> root;		// the root node of the expression tree
	
	private ExprUtils utils;			// utilities to tokenize expression
	
	private final int PRINT_SPACES = 3;	// number of spaces between tree levels
										// used by printTree()
	
	// constructor
	public ExpressionTree() {
		// initialize utils to a new ExprUtils() object
		utils = new ExprUtils();
		tokenIndex = 0;
		// set expr to empty string
		expr = "";
		// initialize root and tokens to null
		root = null;
		tokens = null;
	}
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		ExpressionTree et = new ExpressionTree();
		et.run();
	}
	/**
	 * Run method
	 */
	public void run() {
		System.out.println();
		System.out.println("Welcome to ExpressionTree!!!");
		treeMakerInterface();
		System.out.println("\nThanks for using ExpressionTree! Goodbye.\n");
	}
	/**
	 *	The user interface for the Expression Tree
	 */
	public void treeMakerInterface() {	
		String user = "";
		
		do { 
			// print the menu first and ask user for input
			printMenu();
			user = Prompt.getString("");
			System.out.print("\n");
			// if user wants to enter an expression
			if (user.equals("i")){
				expr = Prompt.getString("expression");
				root = buildTree();
			}
			// print infix order
			else if (user.equals("in")){
				System.out.println("Infix order");
				utils.printInorder(root);
			}
			// print postfix order
			else if (user.equals("post")){
				System.out.println("Postfix order");
				utils.printPostorder(root);
			}
			// print evaluation (answer)
			else if (user.equals("e"))
				System.out.println("Answer: " + evaluateExpression());
			// print prefix order
			else if (user.equals("pre")){
				System.out.println("Prefix order");
				utils.printPreorder(root);
			}
			// print the tree created
			else if (user.equals("p")){
				System.out.println("Print tree");
				printTree();
			}
		// till user hits q and exits
		} while (!user.equals("q"));
	}
	
	/**	Print help */
	public void printMenu() {
		System.out.println("\nCurrent expression: " + expr);
		System.out.println("\nChoose:");
		System.out.println("  (i) input new expression");
		System.out.println("  (pre) print prefix notation");
		System.out.println("  (in) print infix notation");
		System.out.println("  (post) print postfix notation");
		System.out.println("  (e) evaluate expression");
		System.out.println("  (p) print tree");
		System.out.println("  (q) quit");
	}
	
	/**	Builds a Binary Expression Tree from tokens.
	 *	@return		root of expression tree
	 *
	 *	Algorithm: 
	 * 	1. Create a new ArrayStack, using toPostfix() to get a list of
	 * 	tokens. Traverse the ArrayList of tokens.
	 * 	2. If it is an operator, pop the last two operators and push it
	 * 	onto the tree stack (with a TreeNode containing both operators).
	 * 	3. If it is a number, push a Treenode with just the number.
	 * 	4. Return the last popped value.
	 */
	public TreeNode<String> buildTree() {
	 	// build the stack
	 	ArrayStack<TreeNode<String>> treeStack = new ArrayStack<TreeNode<String>>();
	 	// get tokens using toPostfix method and reset tokenIndex to 0
	 	tokenIndex = 0;
	 	tokens = utils.toPostfix(expr);
	 	// loop through the tokens gotten by the postfix
	 	while (tokenIndex < tokens.size()) {
			// if it is an operator
			if (utils.isOperator(tokens.get(tokenIndex))){
				// find first and second operator
				TreeNode<String> first = treeStack.pop();
				TreeNode<String> second = treeStack.pop();
				treeStack.push(new TreeNode<String>(tokens.get(tokenIndex),
													second, first));
			}
			// if it is a number
			else if (utils.isNumber(tokens.get(tokenIndex))) {
				treeStack.push(new TreeNode<String>(tokens.get(tokenIndex)));
			}
			// increment token index
			tokenIndex++;
		}
		// pop the last value
		return treeStack.pop();
	}

	/**
	 *	Evaluate expression in ExpressionTree using a recursive method
	 *	@return		the evaluated answer
	 */
	public double evaluateExpression() {
		// use recursive method
		return (evaluateTreeRecursive(root));
	}
	
	/** Utilize a recursive method to evaluate the expression by
	 * 	traversing the tree.
	 * 	@param TreeNode<String>	the current TreeNode
	 * 	@return 	the value of expression
	 */
	
	private double evaluateTreeRecursive(TreeNode<String> current){
		// return 0.0 if the value is null
		if (current == null)
			return 0.0;
		// get the val of node
		String ret = current.getValue();
		// if it is a node, apply it to left and right for the following
		// operations: 
		
		// addition
		if (ret.equals("+"))
			return evaluateTreeRecursive(current.getLeft()) +
					evaluateTreeRecursive(current.getRight());
		// exponentiation
		else if (ret.equals("^"))
			return Math.pow(evaluateTreeRecursive(current.getLeft()), 
						evaluateTreeRecursive(current.getRight()));
		// subtraction	
		else if (ret.equals("-"))
			return evaluateTreeRecursive(current.getLeft()) - 
				evaluateTreeRecursive(current.getRight());
		// modulus	
		else if (ret.equals("%"))
			return evaluateTreeRecursive(current.getLeft()) % 
				evaluateTreeRecursive(current.getRight());
		// product
		else if (ret.equals("*"))
			return evaluateTreeRecursive(current.getLeft()) *
				evaluateTreeRecursive(current.getRight());
		// divide
		else if (ret.equals("/"))
			return evaluateTreeRecursive(current.getLeft()) /
				evaluateTreeRecursive(current.getRight());	
			
		// parse the double and return it
		return Double.parseDouble(ret);
	}
	
	/**
	 *	Print expression tree
	 *	@param root		root node of binary tree
	 *
	 *	Prints in vertical order, top of output is right-side of tree,
	 *			bottom is left side of tree,
	 *			left side of output is root, right side is deepest leaf
	 *	Example tree (expression "5 + 2 * 3""):
	 *			  +
	 *			/	\
	 *		  /		  \
	 *		5			*
	 *				  /	  \
	 *				2		3
	 *
	 *	would be output as:
	 *
	 *				3
	 *			*
	 *				2
	 *		+
	 *			5
	 */
	public void printTree() {
		printLevel(root, 0);
	}
	
	/**
	 *	Recursive node printing method
	 *	Prints reverse order: right subtree, node, left subtree
	 *	Prints the node spaced to the right by level number
	 *	@param node		root of subtree
	 *	@param level	level down from root (root level = 0)
	 */
	public void printLevel(TreeNode<String> node, int level) {
		if (node == null) return;
		// print right subtree
		printLevel(node.getRight(), level + 1);
		// print node: print spaces for level, then print value in node
		for (int a = 0; a < PRINT_SPACES * level; a++) System.out.print(" ");
		System.out.println(node.getValue());
		// print left subtree
		printLevel(node.getLeft(), level + 1);
	}
	
}
