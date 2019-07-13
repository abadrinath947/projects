/**
 *	BubbleSort - Sorts an array of int's in ascending order
 *	using BubbleSort.
 *
 *	@author	
 *	@since	
 */
public class BubbleSort {
	
	private final int DEFAULT_SIZE = 100;	// default size of the array
	private final int MAX_SIZE = 1000;		// maximum size of the array
	
	/**
	 *	Bubble Sort algorithm - in ascending order
	 *	@param arr		array of int's to sort
	 */
	public void sort(int [] arr) {
		int compares = 0;
		for (int last = arr.length - 1; last > 0; last--) 
			for (int index = 0; index < last; index++) {
				compares++;
				if (arr[index] > arr[index + 1])
					swap (arr, index, index + 1);
			}
		System.out.println("\nNumber of elements = " + arr.length + 
							" Number of compares = " + compares);
	}
	
	/**
	 *	Swaps two int's in array arr
	 *	@param arr		array of int's
	 *	@param x		index of first int to swap
	 *	@param y		index of second int to swap
	 */
	private void swap(int[] arr, int x, int y) {
		int temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}
	
	/*********************************************************/
	
	public static void main(String[] args) {
		BubbleSort bs = new BubbleSort();
		bs.run(args);
	}
	
	public void run(String[] args) {
		int size = DEFAULT_SIZE;
		
		// if present, get size of array from command line
		if (args.length > 0) {
			try {
				size = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e) {
				System.err.println("Usage: java BubbleSort [arraySize]");
				System.exit(-1);
			}
			if (size > MAX_SIZE) {
				System.out.println("\nERROR: Maximum size of " + MAX_SIZE +
							" exceeded");
				System.out.println("Array size set to " + MAX_SIZE);
				size = MAX_SIZE;
			}
		}
		
		System.out.println("\nBUBBLE SORT\n");
		// Create array to sort
		int[] array = new int[size];
		
		// Store random numbers in array
		for (int a = 0; a < array.length; a++)
			array[a] = (int)(Math.random() * size * 5) + 1;
			
		// print the original array
		System.out.println("Orignal array:");
		printArray(array);
		sort(array);
		
		// print the sorted array
		System.out.println("\nSorted array:");
		printArray(array);
		System.out.println();
	}

	/**
	 *	Print an array of int's to the screen
	 *	@param arr		the array of int's
	 */
	public void printArray(int[] arr) {
		if (arr.length == 0) System.out.print("(");
		else System.out.printf("( %4d", arr[0]);
		for (int a = 1; a < arr.length; a++) {
			if (a % 10 == 0) System.out.printf(",\n  %4d", arr[a]);
			else System.out.printf(", %4d", arr[a]);
		}
		System.out.println(" )");
	}
	
}
