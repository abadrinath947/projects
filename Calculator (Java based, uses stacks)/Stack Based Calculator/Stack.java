/**
 *	A homebrew Stack interface
 *	From Litvin Ch 21, P 529
 *	@author	Mr Greenstein
 *	@since	December 18, 2017
 */
public interface Stack<E> {
	
	/**	@return		true if the stack is empty; false otherwise */
	boolean isEmpty();
	/**	@return		the object on top of the stack */
	E peek();
	/**	@param obj	put the Object obj on top of the stack */
	void push(E obj);
	/**	@return		the object on top of the stack, and remove the object from the stack */
	E pop();
}