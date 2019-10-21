import java.util.ArrayList;
import java.util.List;

/**
 *	ArrayStack - A stack represented by a List
 *	From Litvin Ch 21, P 529
 *	@author	Mr Greenstein
 *	@since	December 18, 2017
 */
public class ArrayStack<E> implements Stack<E> {
	
	private List<E> elements;		// the elements on the stack
	
	//	no-args constructor
	public ArrayStack() { elements = new ArrayList<E>(); }
	
	/**	@return		true if the ArrayStack is empty; false otherwise */
	public boolean isEmpty() { return elements.isEmpty(); }
	
	/**	@return		the object on top of the stack */
	public E peek() { return elements.get(elements.size() - 1); }
	
	/**	@param obj	push the Object obj onto the top of the stack */
	public void push(E obj) { elements.add(obj); }
	
	/**	@return		the object on top of the stack, and remove the object from the stack */
	public E pop() { return elements.remove(elements.size() - 1); }

}