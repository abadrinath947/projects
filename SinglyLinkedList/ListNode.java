/**
 *	A List Node for a singly-linked list.
 *
 *	@author	Mr Greenstein
 *	@since	April 1, 2018
 */
public class ListNode<E extends Comparable<E>>
{
	private E value;  // the element stored in this node
	private ListNode<E> next; // reference to next node in List

	/**
	 *	post: constructs a new element with object initValue,
	 *	followed by next element
	 *	@param initValue	the Object to store in this node
	 *	@param initNext		the link to the next ListNode
	 */
	public ListNode(E initValue, ListNode<E> initNext) 
	{
		value = initValue;
		next = initNext;
	}
	
	/**
	 *	post: constructs a new element with object initValue,
	 *	followed by a reference to null
	 *	@param initValue	the Object to store in this node
	 */
	public ListNode(E initValue)
	{
		this(initValue, null);
	}
	
	/**
	 *	post: returns value associated with this element
	 *	@return  Object in this node
	 */
	public E getValue()
	{
		return value;
	}
	
	/**
	 *	post: returns reference to next value in list
	 *	@return  the next ListNode in the list
	 */
	public ListNode<E> getNext()
	{
		return next;
	}
	
	/**
	 *	@param theNewValue  the new Object value to store in this node
	 */
	public void setValue(E theNewValue) 
	{
		value = theNewValue;
	}
	
	/**
	 *	post: sets reference to new next value
	 *	@param theNewNext  the new next ListNode
	 */
	public void setNext(ListNode<E> theNewNext) 
	{
		next = theNewNext;
	}
}
