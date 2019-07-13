/**
 *	TreeNode for a binary tree
 *
 *	@author	Mr Greenstein
 *	@since	April 24, 2018
 */
public class TreeNode<E extends Comparable<E>> {
	
	private E value;			// the value in this node
	private TreeNode<E> left;	// left branch of this node
	private TreeNode<E> right;	// right branch of this node
	
	/** constructor that passes both subtree nodes */
	public TreeNode(E myValue, TreeNode<E> l, TreeNode<E> r) {
		value = myValue;
		left = l;
		right = r;
	}
	
	/** constructor that makes both subtree nodes null */
	public TreeNode(E myValue) { this(myValue, null, null); }
	
	/* Accessors and modifiers */
	public TreeNode<E> getLeft() { return left; }
	public TreeNode<E> getRight() { return right; }
	public void setLeft(TreeNode<E> node) { left = node; }
	public void setRight(TreeNode<E> node) { right = node; }
	public E getValue() { return value; }
}