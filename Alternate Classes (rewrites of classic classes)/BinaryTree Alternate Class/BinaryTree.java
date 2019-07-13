/**
 *    Binary Tree of Comparable values.
 *    The tree only has unique values. It does not add duplicate values.
 *    
 *    @author    Anirudhan Badrinath
 *    @since    5/3/18
 */
import java.util.ArrayList;

public class BinaryTree<E extends Comparable<E>> {

    private TreeNode<E> root;        // the root of the tree
	private final int PRINT_SPACES = 3;    // print spaces between tree levels
                                        // used by printTree()
    
    /**    constructor for BinaryTree */
    public BinaryTree() { 
        root = null;
    }
    
    /**    Field accessors and modifiers */
    public TreeNode<E> getRoot() { return root; }
    
    /**    Add a node to the tree
     *    @param value        the value to put into the tree
     */
    /*
     public void add(E value) {
		// set a new variable current to the current root
        TreeNode<E> current = root;
        if (current == null) {
            root = new TreeNode<E>(value);
            return;
        }
        //  use a while loop to traverse and find spot to put value
        boolean done = false;
        while(!done) {
            //if value is less than current node value
            if (value.compareTo(current.getValue()) < 0)  {
                // set it to the left node
                if (current.getLeft() ==  null) {
                    current.setLeft(new TreeNode<E>(value));
                    done = true;
                }
                else {
                    current = current.getLeft();
                }
            }
            // if value is greater than
            else if (value.compareTo(current.getValue()) > 0) {
                // set it to the right node
                if (current.getRight() == null) {
                    current.setRight(new TreeNode<E>(value));
                    done = true;
                }
                else
                    current = current.getRight();
            }
            // if equal to
            else
                done = true;
        }
    }
    */
    /** Recursive way to add values to a binary tree.
     * 	Checks if there is no binary tree and sets 
     * 	root to value if so.
     * 	@param value	value to add to the binary tree
     */
    public void add(E value) {
        // check if there is a root, and add if not
        if (root == null) {
            root = new TreeNode<E>(value);
            return;
        }
        // call recursive method to add the node
        insertNode(root, value);
    }
    /**
     * Recursive method that inserts a node into a binary 
     * tree. Modifies the right and left nodes of the current
     * node accordingly.
     * @param cur	current node
     * @param value	value to be inserted
     * @return 		node that is returned
     */
    public TreeNode<E> insertNode(TreeNode<E> cur, E value) {
        // if the node passed is null (e.g, if a left node is null)
        if (cur == null) {
            cur = new TreeNode<E>(value);
            return cur;
        }
        // if less than the value
        if (value.compareTo(cur.getValue()) > 0) 
            cur.setRight(insertNode(cur.getRight(), value));
        // if greater than the value
        else
            cur.setLeft(insertNode(cur.getLeft(), value));
        return cur;
    }
    
    /**
     *    Print Binary Tree Inorder
     */
    public void printInorder() {
		// if root isn't initialized, return out
        TreeNode<E> current = root;
        if (current == null) 
			return;
		// use recursive traversal
        else 
            traverseInOrder(current);        
    }
    /**
     * Recursive method to traverse the binary tree 
     * in-order (gets left, then prints, gets right).
     * @param current	current node 
     */
    public void traverseInOrder(TreeNode<E> current) {
		// check if the current node is not null
        if (current != null) {
			// get left
            traverseInOrder(current.getLeft());
            // get value
            System.out.print(current.getValue() + " ");
            // get right
            traverseInOrder(current.getRight());
        }
    }
    /**
     * Recursive method to traverse the binary tree 
     * in-order (gets left, then prints, gets right).
     * Adds the values to an ArrayList.
     * @param balanceTreeHelper		ArrayList storing values
     * @param current				the current node
     * @return						ArrayList storing nodes
     */
    public ArrayList<E> balanceTraverse(ArrayList<E> balanceTreeHelper,
										TreeNode<E> current) {
		// check if the current node is not null								
        if (current != null) {
			// get left
            balanceTraverse(balanceTreeHelper, current.getLeft());
            // add value to the ArrayList
            balanceTreeHelper.add(current.getValue());
            // get right
            balanceTraverse(balanceTreeHelper, current.getRight());
        }
        // return the ArrayList
        return balanceTreeHelper;
    }
	/**
     * Print Binary Tree Preorder
     */     
     public void printPreorder() {
        // set current as root
        TreeNode<E> current = root;
        // check if null and return if so
        if (current == null)
			return;
        else 
            traversePreorder(current);        
    }
    /**
     * Recursive method to traverse the binary tree 
     * pre-order (prints, gets left, gets right).
     * @param current	current node 
     */
    public void traversePreorder(TreeNode<E> current) {
		// check if the current node is null
        if (current != null) {
			// print value
            System.out.print(current.getValue() + " ");
            // get left
            traversePreorder(current.getLeft());
            // get right
            traversePreorder(current.getRight());
        }
    }
    
    /**
     * Print Binary Tree Postorder
     */     
     public void printPostorder() {
		// set current to root
        TreeNode<E> current = root;
        // return out if current is null
        if (current == null)
			return;
		// use recursive method to traverse
        else 
            traversePostorder(current);       
    }
    /**
     * Recursive method to traverse the binary tree 
     * post-order (gets left, gets right, print value).
     * @param current	current node 
     */
    public void traversePostorder(TreeNode<E> current) { 
		// if current node is not null
        if (current != null) {
			// get left
            traversePostorder(current.getLeft());
            // get right
            traversePostorder(current.getRight());
            // print value
            System.out.print(current.getValue() + " ");
        }
    }
        
    /**    
     * Return a balanced version of this binary tree
     * @return        the balanced tree
     */
    public BinaryTree<E> makeBalancedTree() {
		// get sorted ArrayList of values
        ArrayList<E> balanceTreeHelper = new ArrayList<E>();
        balanceTreeHelper = balanceTraverse(balanceTreeHelper, root);
        // declare new balancedTree
        BinaryTree<E> balancedTree = new BinaryTree<E>();
        // call recursive method to build the tree
        buildTreeUtil(balanceTreeHelper, balancedTree, 0, balanceTreeHelper.size() - 1);
        // return the balancedTree
        return balancedTree;  
    }
    /**
     * Recursively choose a left node and right node for a specified node
     * using a sorted ArrayList (obtained using in-order). Sets value as
     * root if it is null in the binary tree.
     * @param balanceTreeHelper	ArrayList containing values
     * @param balancedTree		the new balanced tree created from old tree
     * @param start				start index of the ArrayList
     * @param end				end index
     * @return					node that is being modified
     */
    public TreeNode<E> buildTreeUtil(ArrayList<E> balanceTreeHelper, BinaryTree<E> balancedTree,
			int start, int end) {
        // recursive base case
        if (start > end)
            return null;
        // find middle of ArrayList and get that node
        int mid = (start + end) / 2;
        TreeNode<E> node = new TreeNode<E>(balanceTreeHelper.get(mid));
        // if there are no values in the balancedTree, set it to root
        if (balancedTree.root == null)
            balancedTree.root = node;
        // otherwise find the left and right values and set those
        node.setLeft(buildTreeUtil(balanceTreeHelper, balancedTree, start, mid - 1));
        node.setRight(buildTreeUtil(balanceTreeHelper, balancedTree, mid + 1, end));
		// return the node
        return node;
    }
    

    /*******************************************************************************/    
    /********************************* Utilities ***********************************/    
    /*******************************************************************************/    
    /**
     *    Print binary tree
     *    @param root        root node of binary tree
     *
     *    Prints in vertical order, top of output is right-side of tree,
     *            bottom is left side of tree,
     *            left side of output is root, right side is deepest leaf
     *    Example Integer tree:
     *              11
     *            /     \
     *          /           \
     *        5            20
     *                  /      \
     *                14       32
     *
     *    would be output as:
     *
     *                 32
     *            20
     *                 14
     *        11
     *            5
     ***********************************************************************/
    public void printTree() {
        printLevel(root, 0);
    }
    
    /**
     *    Recursive node printing method
     *    Prints reverse order: right subtree, node, left subtree
     *    Prints the node spaced to the right by level number
     *    @param node        root of subtree
     *    @param level    level down from root (root level = 0)
     */
    private void printLevel(TreeNode<E> node, int level) {
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
