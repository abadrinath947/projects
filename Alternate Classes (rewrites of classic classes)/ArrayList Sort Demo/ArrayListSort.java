import java.util.ArrayList;

/**
 *	ArrayListSort - Take a list of numbers and create an ArrayList
 *					that stores them in ascending order.
 *	@author	Mr Greenstein
 *	@author
 *	@since	
 */
public class ArrayListSort {
	
	private int[] nums;
	
	public ArrayListSort() {
		nums = new int[] { 98, 34, 23, 12, 82, 73, 4, 56, 17, 92 };
	}
	
	public static void main(String[] args) {
		ArrayListSort als = new ArrayListSort();
		als.run();
	}
	
	public void run() {
		System.out.print("Before: ");
		printArray(nums);
		System.out.println();
		ArrayList<Integer> sorted = createSorted(nums);
		System.out.print("After : ");
		System.out.println(sorted);
	}
	/**
	 * Creates an ArrayList of sorted integers
	 * @param arr		an array of random integers as input
	 * @return 			an ArrayList of the integers in ascending order
	 */
	 public ArrayList<Integer> createSorted(int[] arr) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < arr.length; i++) {
			int index = 0;
			boolean found = false;
			while ( index < result.size() && !found )
				if (arr[i] < result.get(index)) 
					found = true;
				else
					index++;
			result.add(index, arr[i]);
		}
		return result;
	 }
	 
	/**
	 *	Print an array of integers.
	 *	@param arr		the array of integers
	 */
	public void printArray(int[] arr) {
		System.out.print("(");
		for (int a = 0; a < arr.length - 1; a++)
			System.out.print(arr[a] + ", ");
		System.out.print(arr[arr.length - 1] + ")");
	}
}
