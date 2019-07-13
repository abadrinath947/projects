/**
 *    SortMethods - Sorts an array of Integers in ascending order.
 *
 *    @author Anirudhan Badrinath
 *    @since 12/11/17
 */

public class SortMethods {
        
    private final int ARR_SIZE = 100;

    /**
     *    Bubble Sort algorithm - in ascending order
     *    @param arr        array of Integer objects to sort
     */
    public void bubbleSort(Integer [] arr) {
		// initialize compare numbers
        int compares = 0;
        // outer loop
        for (int last = arr.length - 1; last > 0; last--)
			// inner loop
            for (int index = 0; index < last; index++) {
                compares++;
                // compare the two adjacent indices
                if (arr[index].compareTo(arr[index + 1]) > 0)
                    swap (arr, index, index + 1);
            }
        System.out.println("\nNumber of elements = " + arr.length +
                            " Number of compares = " + compares);
    }    
    /**
     *    Swaps two Integer objects in array arr
     *    @param arr        array of Integer objects
     *    @param x        index of first object to swap
     *    @param y        index of second object to swap
     */
    private void swap(Integer[] arr, int x, int y) {
		// swap two indices
        Integer temp = arr[x];
        // store in temp
        arr[x] = arr[y];
        arr[y] = temp;
    }

    /**
     *    Selection Sort algorithm - in ascending order (you implement)
     *    @param arr        array of Integer objects to sort
     */
    public void selectionSort(Integer [] arr) {
		// choose max index and end index
        int maxIndex = 0, compares = 0;
        for (int outer = arr.length - 1; outer > 0; outer--) {
            for (int inner = 1; inner <= outer; inner++) {
                compares++;
                // if number is bigger than max index, set that to max
                if (arr[inner].compareTo(arr[maxIndex]) > 0) {
                    maxIndex = inner;
                }
            }
            // swap outer index and max index
            swap(arr, outer, maxIndex);
            // reset max index
            maxIndex = 0;
        }
		 // print number of compares
         System.out.println("\nNumber of elements = " + arr.length +
                 " Number of compares = " + compares);
    }
    /**
     *    Insertion Sort algorithm - in ascending order (you implement)
     *    @param arr        array of Integer objects to sort
     */
    public void insertionSort(Integer [] arr) {
			// initialize number of compares
            int compares = 0;
            // skip first index since we consider it sorted
            for (int i = 1; i < arr.length; i++){
				// store element at index i in temp var
                Integer temp = arr[i]; 
                int pointer = i;
                // insert
                while (pointer > 0 && temp < arr[pointer - 1]) {
					// increment compares    
                    compares++;
                    // shift values            
                    arr[pointer] = arr[pointer - 1];
                    // go back in the array
                    pointer--;
                }
                compares++;
                arr[pointer] = temp;
            }
            System.out.println("\nNumber of elements = " + arr.length +
                 " Number of compares = " + compares);
    }
    /**
     *    Merge Sort algorithm - in ascending order (you implement)
     *    @param arr        array of Integer objects to sort
     */
    public void mergeSort(Integer[] arr) {
		// call doMergeSort method using array name, initial index, 
		// final index and initial number of compares
        int compares = doMergeSort(arr, 0, arr.length - 1, 0);
        System.out.println("\nNumber of elements = " + arr.length +
                " Number of compares = " + compares);
    }
    /**
     * Method to recursively merge sort using the merge method as well    
     * @param arr                array of Integer objects to sort
     * @param indexA             start index
     * @param indexB            end index
     * @param compares            number of compares
     * @return                     final number of compares
     */
    private int doMergeSort(Integer [] arr, int indexA, int indexB, int compares) {
        int middle = (indexA + indexB) / 2 + 1, length = indexB - indexA + 1;
        // if there are more than two elements in the split, split more
        if (length > 2) {
			// sort first part, store returned comapres in variable
            compares = doMergeSort(arr, indexA, middle - 1, compares);
            // sort second part
            compares = doMergeSort(arr, middle, indexB, compares);
            compares = merge(arr, indexA, indexB, compares);              
        }
        // if there is 2 elements left, swap them if needed
        else if (length == 2) {
            compares++;
            if (arr[indexA] > arr[indexB])
                swap(arr, indexA, indexB);
        }
        // if there is 1 element left, don't do anything (assumed)
        return compares;
    }
    /**
     *  Method to merge two arrays in ascending order.
     *  @param arr               	 array of Integer objects to sort
     *  @param indexA            	 start index
     *  @param indexB            	end index
     *  @param compares           	 number of compares
     *  @return                     final number of compares
     */
    private int merge(Integer[] arr, int indexA, int indexB, int compares) {
		// initialize index in second part of array, first part of array,
		// and index in new array
        int arr2i = (indexA + indexB) / 2 + 1, arr1i = indexA, index = 0;
        Integer[] newArr = new Integer[indexB - indexA + 1];
        
        while (arr1i < ((indexA + indexB) / 2 + 1) && arr2i <= indexB) {
            compares++;
            // if second index is less than first index
            if (arr[arr2i].compareTo(arr[arr1i]) < 0) {
                // add to new array and increment new array index
                newArr[index] = arr[arr2i];
                arr2i++;
                index++;
            }
            // if second index is higher than first index
            else {
				// add to new array and increment new array index
                newArr[index] = arr[arr1i];
                arr1i++;
                index++;
            }
        }
        // dump the rest of the first part of the array
        while (arr1i < (indexA + indexB) / 2 + 1) {
				// add to new array and increment new array index
                newArr[index] = arr[arr1i];
                arr1i++;
                index++;
        }
        // dump the rest of the second part of the arry
        while (arr2i <= indexB) {
				// add to new array and increment new array index
                newArr[index] = arr[arr2i];
                arr2i++;
                index++;
        }
        // transfer data into old array
        for (int i = 0; i < newArr.length; i++)
            arr[indexA + i] = newArr[i];
        return compares;   
    }
    /**
     *    Print an array of Integers to the screen
     *    @param arr        the array of Integers
     */
    public void printArray(Integer[] arr) {
        if (arr.length == 0) System.out.print("(");
        else System.out.printf("( %4d", arr[0]);
        for (int a = 1; a < arr.length; a++) {
            if (a % 10 == 0) System.out.printf(",\n  %4d", arr[a]);
            else System.out.printf(", %4d", arr[a]);
        }
        System.out.println(" )");
    }
    /**
     * Create SortMethods object and run the 
     * sorting class. 
     * @param args		CLI args
     */ 
    public static void main(String[] args) {
        SortMethods se = new SortMethods();
        se.run();  
        
    }
    /**
     * Run method which tests the SortMethods class using an array with
     * random integers. 
     */
    public void run() {
        
        Integer[] arr = new Integer[ARR_SIZE];
        
        // Fill arr with random numbers
        for (int a = 0; a < ARR_SIZE; a++)
            arr[a] = (int)(Math.random() * ARR_SIZE * 2) + 1;
        System.out.println("\nBubble Sort");
        System.out.println("Array before sort:");
        printArray(arr);
        System.out.println();
        bubbleSort(arr);
        System.out.println("Array after sort:");
        printArray(arr);
        System.out.println();
        
        for (int a = 0; a < ARR_SIZE; a++)
            arr[a] = (int)(Math.random() * ARR_SIZE * 2) + 1;
        System.out.println("\nSelection Sort");
        System.out.println("Array before sort:");
        printArray(arr);
        System.out.println();
        selectionSort(arr);
        System.out.println("Array after sort:");
        printArray(arr);
        System.out.println();
   
        for (int a = 0; a < ARR_SIZE; a++)
            arr[a] = (int)(Math.random() * ARR_SIZE * 2) + 1;
        System.out.println("\nInsertion Sort");
        System.out.println("Array before sort:");
        printArray(arr);
        System.out.println();
        insertionSort(arr);
        System.out.println("Array after sort:");
        printArray(arr);
        System.out.println();
             
        for (int a = 0; a < ARR_SIZE; a++)
            arr[a] = (int)(Math.random() * ARR_SIZE * 2) + 1;
        System.out.println("\nMerge Sort");
        System.out.println("Array before sort:");
        printArray(arr);
        System.out.println();
        mergeSort(arr);
        System.out.println("Array after sort:");
        printArray(arr);
        System.out.println();
    }
}
