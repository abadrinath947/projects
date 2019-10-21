import java.util.ArrayList;
import java.util.List;

/**
 *	ArrayStack - A stack represented by a List
 *	
 *	@author	Anirudhan Badrinath
 *	@since	February 13, 2018
 */
public class ArrayStack<E> implements Stack<E> {
	
	private List<E> elements;		// the elements on the stack
	
	//	no-args constructor
	public ArrayStack() { elements = new ArrayList<E>(); }
	
	/**
	 * Check if the array is empty.	
	 * @return		true if the ArrayStack is empty; false otherwise */
	public boolean isEmpty() { return elements.isEmpty(); }
	
	/**
	 * Look and return the top of the stack.	
	 * @return		the object on top of the stack
	 */
	public E peek() { return elements.get(elements.size() - 1); }
	
	/**
	 * Push an object on the top of the stack.	
	 * @param obj	push the Object obj onto the top of the stack */
	public void push(E obj) { elements.add(obj); }
	
	/**	
	 * Pop the element at the top of the stack.
	 * @return		the object on top of the stack, and remove the object from the stack
	 */
	public E pop() { return elements.remove(elements.size() - 1); }
	
	public String toString() { return elements.toString();}

}
