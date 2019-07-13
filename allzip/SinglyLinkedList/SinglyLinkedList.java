/**
 *	Sets up a singly linked list of ListNode's.
 *	
 *	@author	Anirudhan Badrinath
 *	@since	4/8/18
 */
public class SinglyLinkedList<E extends Comparable<E>>
{
	private ListNode<E> head, tail;		// the first & last ListNode in the list

	/**
	 *	Constructor creates a list with no nodes
	 */
	public SinglyLinkedList()
	{
		head = null;
		tail = null;
	}
	
	/** Copy constructor 
	 *  @param list		original list to copy
	 */
	public SinglyLinkedList(SinglyLinkedList<E> list)
	{
		// calls original constructor
		this();
		// copies the old list into new one
		ListNode<E> ptr = list.getFirst();
		while (ptr != null) {
			addLast(ptr.getValue());
			ptr = ptr.getNext();
		}
	}
	
	/**
	 *	Add an Object to the head of the list
	 *	@param value  the Object to add
	 */
	public void addFirst(E value) 
	{
		// find head and add new value
		head = new ListNode<E>(value, head);
		// add a tail if not present already
		if (tail == null) tail = head;
	}
	
	/**
	 *	Get the first ListNode of the list
	 *	@return  the first ListNode in the list
	 */
	public ListNode<E> getFirst()
	{
		// return first value
		return head;
	}
	
	/** Clears the list of nodes by setting the value of head 
	 * and tail to null respectively.
	 */
	public void clear() {
		// get rid of head and tail, other objs will get cleared by
		// garbage collection
		head = null;
		tail = null;
	}
	/**
	 * Returns the number of nodes in the SinglyLinkedList.
	 * @return 	the size of the SinglyLinkedList
	 */
	public int size() {
		// establish size
		int size = 0;
		ListNode<E> cur = head;
		// increment size for each traversal of the list
		while (cur != null) {
			size++;
			cur = cur.getNext();
		}
		return size;
	}
	/**
	 * Returns the ListNode at the end of the list. Throw 
	 * NoSuchElementException if the list is empty.
	 * @return 	the last node in the list
	 */
	public ListNode<E> getLast() {
		// throw an exception if empty
		if (head == null) throw new java.util.NoSuchElementException();
		// traverse list and find last value
		ListNode<E> cur = head;
		while (cur.getNext() != null) 
			cur = cur.getNext();
		return cur;
	}
	/** Adds a ListNode with E value to the end of the list.
	 * 	@param value	the value to add to the end of the LinkedList
	 * 
	 */
	public void addLast(E value) {
		// find last value
		tail = new ListNode<E>(value);
		if (head != null)
		// add to the end
			getLast().setNext(tail);
		else {
			// if only one element
			head = tail;
		}
	}
	/**
	 *  Removes the first node from the list and returns the
	 *	ListNode it removed. Throw NoSuchElementException if the list is empty.
	 * @return 		the first node or null if not removed
	 */
	public ListNode<E> removeFirst() {
		// if nothing in the list
		if (head == null) throw new java.util.NoSuchElementException();
		// find head and next element
		ListNode<E> h = head;
		head = head.getNext();
		// if there's one node
		if (head == null)
			tail = null;
		return h;
	}
	/**
	 * Removes the last node from the list and returns the
	 * ListNode it removed. Throw NoSuchElementException if the list is empty.
	 * @return 		the last node or null if not removed
	 */
	public ListNode<E> removeLast() {
		// if 0 elements (empty list)
		if (head == null) throw new java.util.NoSuchElementException();
		ListNode<E> previous = null;
		ListNode<E> current = head;
		// cycle through the list and find the ptr before tail
		while (current.getNext() != null) {
	         previous = current; 
	         current = current.getNext();
	    }

	    ListNode<E> result = tail; 
	    tail = previous;
	    // if one element
	    if (tail == null)
	    	head = null;
	    // otherwise if not one element
	    else
	      	tail.setNext(null);
		return result;
	}
	/**
	 *  Searches for a node in the SinglyLinkedList by checking if the
	 * 	objects are equal and if they are, return the object.
	 *  @param node		the node to seach for
	 * 	@return 		the object that is equal to the user inputted node
	 */
	public ListNode<E> get(ListNode<E> node) {
		ListNode<E> cur = head;
		// check if objects are equal w/ the head
		if (head.getValue().equals(node.getValue())) {
			return head;
		}
		// cycle through and try to find the obj
		while (cur.getNext() != null) {
			if (cur.getNext().getValue().equals(node.getValue())) {
				// if its equal, return the value
				return cur.getNext();
			}
			// keep going in the list
			cur = cur.getNext();
		}
		// if not found, return null
		return null;
			
	}
	/**
	 * Removes the first ListNode in the list that has the 
	 * equivalent value Object. It returns true if the value is found and
	 * the node removed; false otherwise. 
	 * @param value		the value to remove from the list
	 * @return			whether the value was removed
	 */
	public boolean remove(E value) {
		
		ListNode<E> cur = head;
		if (head.getValue().equals(value)) {
			// if head is equal to the value
			removeFirst();
			return true;
		}
		while (cur.getNext() != null) {
			// otherwise, cycle through the list and check each value
			if (cur.getNext().getValue().equals(value)) {
				// value was found in the list
				cur.setNext(cur.getNext().getNext());
				return true;
			}
			cur = cur.getNext();
		}
		// if not found, return false;
		return false;
	}
	/**
	 * Adds the value to the list keeping the list in ascending 
	 * order. (Data type E must be a Comparable)
	 * @param value		value of the object to add in orderr
	 */
	public void addInOrder(E value) {
		ListNode<E> cur = head;
		// if size is 0
		if (cur == null) {
			head = tail = new ListNode<E>(value);
			return;
		}
		// if less than head, add it first
		if (cur.getValue().compareTo(value) > 0) {
			addFirst(value);
			return;
		}
		// cycle through and stop where the next node is less than the 
		// user inputted value
		while (cur.getNext() != null && cur.getNext().getValue().compareTo(value) < 0)
			cur = cur.getNext();
		// place the value in the list, fixing nodes around it
		ListNode<E> enter = new ListNode<E>(value);
		enter.setNext(cur.getNext());
		cur.setNext(enter);
		
	}
	/**
	 *	Print out the singly linked list
	 */
	public void printList()
	{
		ListNode<E> ptr = head; // start at the first node
		while (ptr != null) 
		{
			System.out.print(ptr.getValue() + " ");
			ptr = ptr.getNext(); // go to next node
		}
	}
}
