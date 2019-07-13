/**
 *	Calculating Fibonacci numbers using iteration and recursion.
 *	Recursion is O(2^n) while iteration is O(n).
 *
 *	@author	Mr Greenstein
 *	@since	December 11, 2016
 */
public class Fibonacci {
	
	public static void main(String[] args) {
		Fibonacci f = new Fibonacci();
		f.run(args);
	}
	
	/*
	 *	Run both the iterative and recursive versions of the algorithm
	 *	to compute the Fibonacci numbers. Report both the number result
	 *	and the time to compute the number in seconds.
	 */
	public void run(String[] args) {
		int n = 0;
		if (args.length < 1) {
			System.err.println("Usage: java Fibonacci <integer>");
			System.exit(0);
		}
		
		try {
			n = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e) {
			System.err.println("Usage: java Fibonacci <integer>");
			System.exit(-1);
		}
		long startTime = System.currentTimeMillis();
		long result = fibIterative(n);
		long stopTime = System.currentTimeMillis();
		System.out.println("fibIterative(" + n + ") = " + result + "  seconds = "
						+ ((stopTime - startTime) / 1000.0));
		startTime = System.currentTimeMillis();
		result = fibRecursive(n);
		stopTime = System.currentTimeMillis();
		System.out.println("fibRecursive(" + n + ") = " + result + "  seconds = "
						+ ((stopTime - startTime) / 1000.0));
	}
	
	/**
	 *	Calculate the nth Fibonacci number using iteration.
	 *	@param n	the Fibonacci index
	 *	@return		the nth Fibonacci number
	 */
	public long fibIterative(int n) {
		long f1 = 1, f2 = 1;
		
		while (n > 2) {
			long temp = f1 + f2;
			f1 = f2;
			f2 = temp;
			n--;
		}
		return f2;
	}
	
	/**
	 *	Calculate the nth Fibonacci number using recursion.
	 *	@param n	the Fibonacci index
	 *	@return		the nth Fibonacci number
	 */
	public long fibRecursive(int n) {
		if (n < 3)
			return 1;
		else
			return fibRecursive(n - 1) + fibRecursive(n - 2);
	}
}
