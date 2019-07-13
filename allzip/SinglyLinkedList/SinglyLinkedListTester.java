/**
 *	Tester for SinglyLinkedList using Coordinate class as values
 *
 *	@author	Mr Greenstein
 *	@since	April 1, 2018
 */
public class SinglyLinkedListTester
{
	private SinglyLinkedList<Coordinate> sll;
	
	private final int NUM_COORDS = 3;
	
	public SinglyLinkedListTester() { sll = new SinglyLinkedList<Coordinate>(); }
	
	public static void main(String[] args)
	{
		SinglyLinkedListTester sllt = new SinglyLinkedListTester();
		sllt.run();
	}
	
	public void run()
	{
		System.out.println("\nSingly Linked List Tester\n-------------------------");
		testAddFirst();				// Type-along 
		testGetFirst();				// Type-along 
		
		testClear();				// Assignment 2
		testSize();					// Assignment 3
		testGetLast();				// Assignment 4
		testAddLast();				// Assignment 5
		testRemoveFirst();			// Assignment 6 and 7 (removeLast)
		testRemoveFirstRepeating();	// Assignment 6
		testGet();					// Assignment 8
		testRemove();				// Assignment 9
		testAddInOrder();			// Assignment 10
	}
	
	public void testAddFirst()
	{
		System.out.println("1. Testing addFirst()");
		addCoordinates(sll);
		sll.printList();
		System.out.println();
	}
	
	public void testGetFirst()
	{
		System.out.println("\n2. Testing getFirst()");
		System.out.println("first = " + sll.getFirst().getValue());
	}

	public void testClear()
	{
		System.out.println("\n3. Testing clear()");
		sll.clear();
		System.out.println("List after clear():");
		sll.printList();
		System.out.println();
	}
	
	public void testSize()
	{
		System.out.println("\n4. Testing size()");
		addCoordinates(sll);
		sll.printList();
		System.out.println("size = " + sll.size());
	}
	
	public void testGetLast()
	{
		System.out.println("\n5. Testing getLast()");
		if (sll.getLast() != null) System.out.println("last = " + sll.getLast().getValue());
		else System.out.println("last = " + null);
	}
	
	public void testAddLast()
	{
		System.out.println("\n6. Testing addLast()");
		for (int row = 0; row < NUM_COORDS; row++)
			for (int col = 0; col < NUM_COORDS; col++)
				sll.addLast(new Coordinate(row, col));
		sll.printList();
		System.out.println();
	}
	
	public void testRemoveFirst()
	{
		System.out.println("\n7. Testing removeFirst() and removeLast()");
		System.out.println("oldFirst = " + sll.removeFirst().getValue()
						+ "  oldLast = " + sll.removeLast().getValue());
		sll.printList();
		System.out.println();
	}
	
	public void testRemoveFirstRepeating()
	{
		System.out.println("\n8. Testing removeFirst until the list is empty");
		SinglyLinkedList<Coordinate> sll2 = new SinglyLinkedList<Coordinate>(sll);
		addCoordinates(sll2);
		System.out.println("Before:");
		sll2.printList();
		System.out.println();
		while(sll2.size() > 0) sll2.removeFirst();
		System.out.println("After:");
		sll2.printList();
	}
	
	public void testRemoveLastRepeating()
	{
		System.out.println("\n9. Testing removeLast until the list is empty");
		SinglyLinkedList<Coordinate> sll2 = new SinglyLinkedList<Coordinate>(sll);
		addCoordinates(sll2);
		System.out.println("Before:");
		sll2.printList();
		System.out.println();
		while(sll2.size() > 0) sll2.removeLast();
		System.out.println("After:");
		sll2.printList();
	}
	
	public void testGet()
	{
		System.out.println("\n10. Testing get()");
		ListNode<Coordinate> n = sll.getFirst().getNext().getNext();
		System.out.println("Same addresses:      n = " + n + "  sll.get(n) = " + sll.get(n));
		ListNode<Coordinate> k = new ListNode<Coordinate>(new Coordinate(2, 3));
		System.out.println("Different addresses: k = " + k + "  sll.get(k) = " + sll.get(k));
	}
	
	public void testRemove()
	{
		System.out.println("\n11. Testing remove()");
		Coordinate t = new Coordinate(11, 3);
		System.out.println("Returns false: t = " + t + "  sll.remove(t) = " + sll.remove(t));
		sll.printList();
		System.out.println();
		Coordinate x = new Coordinate(0, 1);
		System.out.println("Returns true:  x = " + x + "  sll.remove(x) = " + sll.remove(x));
		sll.printList();
		System.out.println();
	}
	
	public void testAddInOrder()
	{	
		System.out.println("\n12. Testing addInOrder()");
		sll.clear();
		sll.addInOrder(new Coordinate(5, 4));
		sll.addInOrder(new Coordinate(1, 1));
		sll.addInOrder(new Coordinate(2, 3));
		sll.addInOrder(new Coordinate(2, 1));
		sll.addInOrder(new Coordinate(5, 2));
		sll.addInOrder(new Coordinate(0, 4));
		System.out.println("List after addInOrder():");
		sll.printList();
		System.out.println("\n");
	}
	
	/**********************************************************************/
	/******************* Helper methods for testing ***********************/
	/**********************************************************************/
	public void addCoordinates(SinglyLinkedList<Coordinate> sll)
	{		
		for (int row = 0; row < NUM_COORDS; row++)
			for (int col = 0; col < NUM_COORDS; col++)
				sll.addFirst(new Coordinate(row, col));
	}
}
