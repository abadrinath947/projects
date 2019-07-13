public class IntArrayWorker
{
  /** two dimensional matrix */
  private int[][] matrix = null;
  
  /** set the matrix to the passed one
    * @param theMatrix the one to use
    */
  public void setMatrix(int[][] theMatrix)
  {
    matrix = theMatrix;
  }
  
  /**
   * Method to return the total 
   * @return the total of the values in the array
   */
  public int getTotal()
  {
    int total = 0;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        total = total + matrix[row][col];
      }
    }
    return total;
  }
  
  /**
   * Method to return the total using a nested for-each loop
   * @return the total of the values in the array
   */
  public int getTotalNested()
  {
    int total = 0;
    for (int[] rowArray : matrix)
    {
      for (int item : rowArray)
      {
        total = total + item;
      }
    }
    return total;
  }
  
  /**
   * Method to fill with an increasing count
   */
  public void fillCount()
  {
    int numCols = matrix[0].length;
    int count = 1;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < numCols; col++)
      {
        matrix[row][col] = count;
        count++;
      }
    }
  }
  /**
   * computes number of times value is in matrix.
   * @param num			number or value defined in the matrix
   * @return			number of times num is found
   */
   public int getCount(int num) {
	   int count = 0;
	   for (int[] arr : matrix) 
			for (int val : arr)
				if (val == num)
					count++;
		return count;
	}
	/**
	 * return largest value in the matrix
	 * @return 			largest value in the matrix
	 */
	public int getLargest() {
		int max = matrix[0][0];
		for (int[] arr : matrix) 
			for (int val : arr)
				if (val > max)
					max = val;
		return max;
		
	}
	/**
	 * get total in a specific column
	 * @param col		the column
	 * @return			sum of elements in that column
	 */
	public int getColTotal(int col) {
		int result = 0;
		for (int i = 0; i < matrix.length; i++)
			result += matrix[i][col];
		return result;
	}
	/**
	 * reverse the rows in the matrix
	 */
	public void reverseRows() {
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < (matrix[i].length - 1) / 2; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[i][matrix[i].length - j - 1];
				matrix[i][matrix[i].length - j - 1] = temp;
			}
		}
	}
  /**
   * print the values in the array in rows and columns
   */
  public void print()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        System.out.print( matrix[row][col] + " " );
      }
      System.out.println();
    }
    System.out.println();
  }
  
  
  /** 
   * fill the array with a pattern
   */
  public void fillPattern1()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; 
           col++)
      {
        if (row < col)
          matrix[row][col] = 1;
        else if (row == col)
          matrix[row][col] = 2;
        else
          matrix[row][col] = 3;
      }
    }
  }
 
}
