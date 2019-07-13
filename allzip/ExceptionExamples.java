/**
 * The APCS exam tests for the following exceptions.
 *	- ArithmeticException
 *	- NullPointerException
 *	- IndexOutOfBoundsException
 *	- ArrayIndexOutOfBoundsException
 *	- IllegalArgumentException
 */

public class ExceptionExamples {
	public static void main(String[] args) {
		ExceptionExamples ee = new ExceptionExamples();
		ee.run();
	}
	
	public void run() {
		arithmetic();
		nullPointer();
		indexBounds();
		arrayIndexBounds();
		illegalArgument();
	}
	
	public void arithmetic() {
		int x = 1;
		int y = 0;
		try {
			int z = x / y;
		} catch (ArithmeticException ex) {
			System.err.println("ERROR: Arithmetic Exception");
		}
	}
	
	public void nullPointer() {
		Integer[] arr = new Integer[10];
		try {
			int a = arr[0];
		} catch (NullPointerException ex) {
			System.err.println("ERROR: NullPointerException");
		}		
	}
	
	public void indexBounds() {
		String hello = "Hello";
		try {
			char a = hello.charAt(hello.length());
		} catch (IndexOutOfBoundsException ex) {
			System.err.println("ERROR: IndexOutOfBoundsException");
		}
	}
		
	public void arrayIndexBounds() {
		int[] arr = new int[10];
		try {
			int k = arr[10];
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.err.println("ERROR: ArrayIndexOutOfBoundsException");
		}
	}
	
	public void illegalArgument() {
		String str = "hello";
		try {
			System.out.printf("%d\n", str);
		}  catch (IllegalArgumentException ex) {
			System.err.println("ERROR: IllegalArgumentException");
		}
	}
	
}
