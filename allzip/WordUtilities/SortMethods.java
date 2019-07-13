import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;		// for testing purposes

/**
 *	SortMethods - Sorts an ArrayList of Strings in ascending order.
 *
 *	Requires OpenFile class to compile.
 *	Requires file randomWords.txt to execute a test run.
 *
 *	@author	
 *	@since	
 */
public class SortMethods {
	
	/**
	 *	Merge Sort algorithm - in ascending order
	 *	@param arr		List of String objects to sort
	 */
	public void mergeSort(List<String> arr) {
		mergeSortRecurse(arr, 0, arr.size() - 1);
	}
	
	/**
	 *	Recursive mergeSort method.
	 *	@param arr		List of String objects to sort
	 *	@param first	first index of arr to sort
	 *	@param last		last index of arr to sort
	 */
	public void mergeSortRecurse(List<String> arr, int first, int last) {
		int middle = (last + first) / 2 + 1, length = last - first + 1;
        // if there are more than two elements in the split, split more
        if (length > 2) {
			// sort first part, store returned comapres in variable
            mergeSortRecurse(arr, first, middle - 1);
            // sort second part
            mergeSortRecurse(arr, middle, last);
            merge(arr, first, middle, last);              
        }
        // if there is 2 elements left, swap them if needed
        else if (length == 2) {
            if (arr.get(first).compareTo(arr.get(last)) > 0)
                swap(arr, first, last);
        }
	}
	 private void swap(List<String> arr, int x, int y) {
		// swap two indices
        String temp = arr.get(x);
        // store in temp
        arr.set(x, arr.get(y));
        arr.set(y, temp);
    }
	
	/**
	 *	Merge two lists that are consecutive elements in array.
	 *	@param arr		List of String objects to merge
	 *	@param first	first index of first list
	 *	@param mid		the last index of the first list;
	 *					mid + 1 is first index of second list
	 *	@param last		last index of second list
	 */
	public void merge(List<String> arr, int first, int mid, int last) {
		// initialize index in second part of array, first part of array,
		// and index in new array
        int arr2i = mid, arr1i = first;
        List<String> newArr = new ArrayList<String>();
        
        while (arr1i < mid && arr2i <= last) {
            // if second index is less than first index
            if (arr.get(arr2i).compareTo(arr.get(arr1i)) < 0) {
                // add to new array and increment new array index
                newArr.add(arr.get(arr2i));
                arr2i++;
            }
            // if second index is higher than first index
            else {
				// add to new array and increment new array index
                newArr.add(arr.get(arr1i));
                arr1i++;
            }
        }
        // dump the rest of the first part of the array
        while (arr1i < (first + last) / 2 + 1) {
				// add to new array and increment new array index
                newArr.add(arr.get(arr1i));
                arr1i++;
        }
        // dump the rest of the second part of the arry
        while (arr2i <= last) {
				// add to new array and increment new array index
                newArr.add(arr.get(arr2i));
                arr2i++;
        }
        // transfer data into old array
        for (int i = 0; i < newArr.size(); i++)
            arr.set(first + i, newArr.get(i));

	}

	
	/**
	 *	Print an List of Strings to the screen
	 *	@param arr		the List of Strings
	 */
	public void printArray(List<String> arr) {
		if (arr.size() == 0) System.out.print("(");
		else System.out.printf("( %-15s", arr.get(0));
		for (int a = 1; a < arr.size(); a++) {
			if (a % 5 == 0) System.out.printf(",\n  %-15s", arr.get(a));
			else System.out.printf(", %-15s", arr.get(a));
		}
		System.out.println(" )");
	}
	
	/*************************************************************/
	/********************** Test program *************************/
	/*************************************************************/
	private final String FILE_NAME = "randomWords.txt";
	
	public static void main(String[] args) {
		SortMethods se = new SortMethods();
		se.run();
	}
	
	public void run() {
		List<String> arr = new ArrayList<String>();
		// Fill List with random words from file		
		fillArray(arr);
		
		System.out.println("\nMerge Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		mergeSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
	}
	
	// Fill String array with words
	public void fillArray(List<String> arr) {
		Scanner inFile = OpenFile.openToRead(FILE_NAME);
		System.out.println("before");
		while (inFile.hasNext()) 
			arr.add(inFile.next());
		System.out.println("after");
		inFile.close();
		
	}
}
