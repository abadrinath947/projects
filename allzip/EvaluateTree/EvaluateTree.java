import java.util.ArrayList;
import java.util.List;

/**
 *	Evaluate Tree
 *
 *	@author	
 *	@since	
 */
public class EvaluateTree {
	
	private TreeNode<String> root;		// root of the expression tree
	
	private final int PRINT_SPACES = 3;	// print spaces between tree levels
	
	public EvaluateTree() {	}
	
	/**	Evaluate the expression tree and return a result.
	 *	@return		the value of the evaluation
	 */
	public double evaluateTree() {
		
		return 0.0;
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
	public void printTree(TreeNode<String> root) {
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
	
	/*********************************************************************/
	/*************************** For Testing *****************************/
	/*********************************************************************/
	
	public static void main(String[] args) {
		EvaluateTree et = new EvaluateTree();
		et.run();
	}
	
	public void run() {
		System.out.println("\nExpression Tree\n------------------------\n");
		// Expression 1
		System.out.println("Expression: ((2+3)*5/6)+7*8 \n");
		buildExpression1();
		printTree(root);
		System.out.println("\nAnswer: " + evaluateTree() + "\n");
		// Expression 2
		System.out.println("Expression: 1.2 * (( 3.4 - 2.1) / 1.3 * 8.) \n");
		buildExpression2();
		printTree(root);
		System.out.println("\nAnswer: " + evaluateTree() + "\n");
	}
		
	/**	Build the tree with this expression: ((2+3)*5/6)+7*8 */
	public void buildExpression1() {
		String[] arr = { "2", "3", "+", "5", "*", "6", "/", "7", "8", "*", "+" };
		List<TreeNode<String>> nodes = new ArrayList<TreeNode<String>>();
		for (int a = 0; a < arr.length; a++) nodes.add(new TreeNode<String>(arr[a]));
		nodes.get(2).setLeft(nodes.get(0)); nodes.get(2).setRight(nodes.get(1));
		nodes.get(4).setLeft(nodes.get(2)); nodes.get(4).setRight(nodes.get(3));
		nodes.get(6).setLeft(nodes.get(4)); nodes.get(6).setRight(nodes.get(5));
		nodes.get(10).setLeft(nodes.get(6)); nodes.get(10).setRight(nodes.get(9));
		nodes.get(9).setLeft(nodes.get(7)); nodes.get(9).setRight(nodes.get(8));
		root = nodes.get(10);
	}
		
	/**	Build the tree with this expression: 1.2 * (( 3.4 - 2.1) / 1.3 * 8.) */
	public void buildExpression2() {
		String[] arr = { "1.2", "*", "3.4", "-", "2.1", "/", "1.3", "*", "8." };
		List<TreeNode<String>> nodes = new ArrayList<TreeNode<String>>();
		for (int a = 0; a < arr.length; a++) nodes.add(new TreeNode<String>(arr[a]));
		nodes.get(1).setLeft(nodes.get(0)); nodes.get(1).setRight(nodes.get(7));
		nodes.get(7).setLeft(nodes.get(5)); nodes.get(7).setRight(nodes.get(8));
		nodes.get(5).setLeft(nodes.get(3)); nodes.get(5).setRight(nodes.get(6));
		nodes.get(3).setLeft(nodes.get(2)); nodes.get(3).setRight(nodes.get(4));
		root = nodes.get(1);
	}
	
}