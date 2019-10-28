import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 * 		   *modified by Anirudhan Badrinath
 * @since February 4, 2018
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 **/
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		// set blue color to zero
        pixelObj.setBlue(0);
      }
    }
  }
  /** Method to negate the picture **/
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		// set colors to opposite by subtracting from 255
        pixelObj.setBlue(255 - pixelObj.getBlue());
        pixelObj.setRed(255 - pixelObj.getRed());
        pixelObj.setGreen(255 - pixelObj.getGreen());
      }
    }
  }
  /** Method to do grayscale **/
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		// find average of colors
		int avg = (pixelObj.getBlue() + pixelObj.getRed() + pixelObj.getGreen()) / 3;
        // set pixel to average
        pixelObj.setBlue(avg);
        pixelObj.setRed(avg);
        pixelObj.setGreen(avg);
      }
    }
  }
  /** Method to set everything except for blue to 0**/
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		// set red and green to zero
        pixelObj.setRed(0); pixelObj.setGreen(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the i
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
   /**
     * Adds average color to the pixels
     *
     * @param arr       2D array of pixels
     * @param avg  		Color from getAverageColor
     * @param rs        Starting row
     * @param ri        Ending row
     * @param cs        Starting column
     * @param ci        Ending column
     */
    public void setColor(Pixel[][] arr, Color avg, int rs, int ri, int cs, int ci)
    {
        for(int x = rs; x < arr.length && x < ri; x++) 
            for(int y = cs; y < arr[0].length && y < ci; y++)
                // sets each pixel to the avg color
                arr[x][y].setColor(avg);
    }
    /**
     * Swap the left and right sides of the picture.
     * @return 	modified picture
     */
    public Picture swapLeftRight() {
		// create new obj
        Pixel[][] pixels = this.getPixels2D();
        Picture newPic = new Picture(pixels.length, pixels[0].length);
        Pixel[][] resultPixels = newPic.getPixels2D();
        int width = pixels[0].length;
        // nested for loops
        for (int i = 0; i < pixels.length; i++) 
            for (int j = 0; j < pixels[0].length; j++) {
				// find color and set it
                Color origColor = pixels[i][j].getColor();
                resultPixels[i][(j + width/2) % width].setColor(origColor);
            }
        return newPic;
    }
    /**
     * Shifts pictures using stair steps, wrapping around when need be.,
     * @param shiftCount         The number of pixels to shift right
     * @param steps                The number of steps
     * @return                     The picture with pixels shifted in stair steps
     */
    public Picture stairStep(int shiftCount, int steps) {
		// create new object
        Pixel[][] pixels = this.getPixels2D();
        Picture newPic = new Picture(pixels.length, pixels[0].length);
        Pixel[][] resultPixels = newPic.getPixels2D();
        // set width and height
        int height = pixels.length, width = pixels[0].length;
        // two nested for loops
        for (int i = 0; i < pixels.length; i++) 
            for (int j = 0; j < pixels[0].length; j++) {
				//  create stepcount
                int stepCount = i / (height / steps);
                Color origColor = pixels[i][j].getColor();
                // set color
                resultPixels[i][(j + shiftCount * stepCount) % width].setColor(origColor);
            }
        return newPic;
    }
    /**
     * Shifts and messes up a picture based on a Gaussian curve
     * @param maxFactor            Larger means more pixel shift
     * @return                     liquifies picture
     */
     public Picture liquify(int maxFactor) {
		// create new objects
        Pixel[][] pixels = this.getPixels2D();
        Picture newPic = new Picture(pixels.length, pixels[0].length);
        Pixel[][] resultPixels = newPic.getPixels2D();
        int height = pixels.length, width = pixels[0].length, bellWidth = height / 12,
                                                              maxHeight = maxFactor;       
        for (int i = 0; i < pixels.length;i++) {
            for (int j = 0; j < pixels[0].length; j++) {
				// find exponent
                double exponent = Math.pow(i - height / 2.0, 2) /
                                        (2.0 * Math.pow(bellWidth,2));
                // find shift
                int shift = (int)(maxHeight * Math.exp(- exponent));
                // find the old color
                Color origColor = pixels[i][j].getColor();
                // set shift
                resultPixels[i][(j + shift) % width].setColor(origColor);
            }
        }
        return newPic;
     }
     /**
      * Renders the picture wavy using a sine curve
      * @param amplitude        maximum shift of pixels
      * @return                 Wavy picture
      */
     public Picture wavy(int amplitude) {
        Pixel[][] pixels = this.getPixels2D();
        Picture newPic = new Picture (pixels.length, pixels[0].length);
        Pixel[][] resultPixels = newPic.getPixels2D();
        // find height and width
        int height = pixels.length, width = pixels[0].length;
        
        for (int i = 0; i < pixels.length;i++) 
            for (int j = 0; j < pixels[0].length; j++) {
				// calculates the shift
                int shift = (int)(amplitude * Math.sin(2 * Math.PI * (1.0/100) * i)); 
                Color origColor = pixels[i][j].getColor();
                // sets old color and checks if it wraps around
                if (j + shift < 0)
                    resultPixels[i][width + (j + shift)].setColor(origColor);
                else
                    resultPixels[i][(j + shift) % width].setColor(origColor);
            }
        
        return newPic;
     }
  /**
   * Returns the average color of the pixels passed in as a parameter in
   * a Color object
   * @param x1 	initial x coordinate
   * @param x2 	final x coordinate
   * @param y1 	initial y coordinate
   * @param y2 	final y coordinate
   * @param arr	Pixel array
   * @return	average Color
   * 
   */
  public Color getAvg(int x1, int y1, int x2, int y2, Pixel[][] arr ) {
        // if the given coordinates are off the screen, reset them
        if (x1 < 0)
            x1 = 0;
        if (y1 < 0)
            y1 = 0;
        int avgGreen = 0, avgBlue = 0, avgRed = 0, counter = 0;
        for (int i = x1; i < arr.length && i < x2; i++)
            for (int j = y1; j < arr[0].length && j < y2; j++)
            {
                // add all the red, blue and green values of the square
                // area encompassed by the parameters
                avgRed += arr[i][j].getRed();
                avgGreen += arr[i][j].getGreen();
                avgBlue += arr[i][j].getBlue();
                counter++;
            }
        // create new Color object with total RGB values divided by number
        // of tiles
        return new Color(avgRed/counter, avgGreen/counter, avgBlue/counter);
    }
   
  /** To pixelate by dividing area into size x size.
   * @param size Side length of square area to pixelate.
   */
   public void pixelate(int size) {
	   // create new object
       Pixel[][] pixels = this.getPixels2D();
       int currentY = 0;
       // cycle through the array of pixels
       while (currentY < pixels.length) {
           int currentX = 0;
           while (currentX < pixels[0].length)  {
			   // go to specific square specified by params
               int avgBlue = 0, avgGreen = 0, avgRed = 0, counter = 0;
               for (int i = currentY; i < pixels.length && i < currentY + size; i++) {
                   for (int j = currentX; j < pixels[0].length && j < currentX + size; j++) {
					   // add all the blue, green and red values
                       avgBlue += pixels[i][j].getBlue();
                       avgRed += pixels[i][j].getRed();
                       avgGreen += pixels[i][j].getGreen();
                       counter++;
                   }
               }
               // set the three colors to their averages
               avgBlue /= counter; avgRed /= counter; avgGreen /= counter;
               for (int i = currentY; i < pixels.length && i < currentY + size; i++) {
                   for (int j = currentX; j < pixels[0].length && j < currentX + size; j++) {
					   // for loop to set all the values
                       pixels[i][j].setRed(avgRed);
                       pixels[i][j].setBlue(avgBlue);
                       pixels[i][j].setGreen(avgGreen);
                   }
               }
               currentX += size;
           }
           currentY += size;
       }
   }
   /** Method that blurs the picture
    * @param size Blur size, greater is more blur
    * @return Blurred picture
    */
    public Picture blur(int size) {
		// create new Pixel object to keep the temporary picture
        Pixel[][] pixels = this.getPixels2D();
        Picture result = new Picture(pixels.length, pixels[0].length);
        Pixel[][] resultPixels = result.getPixels2D(); 
		// get average colors and set the pixel to it
        for (int i = 0; i < resultPixels.length; i++) 
            for (int j = 0; j < resultPixels[0].length; j++)
				// set that pixel to the avg colors
                resultPixels[i][j].setColor(getAvg(i - size / 2, j - size / 2, 
											i + size / 2, j + size / 2, pixels));

        return result;
        
    }
    /** Method that enhances a picture by getting average Color around
     * a pixel then applies the following formula:
     *
     * pixelColor <- 2 * currentValue - averageValue
     *
     * size is the area to sample for blur.
     *
     * @param size Larger means more area to average around pixel
     * and longer compute time.
     * @return enhanced picture
     */
     public Picture enhance(int size)
     {
		 // create new temp object
         Pixel[][] pixels = this.getPixels2D();
         Picture result = new Picture(pixels.length, pixels[0].length);
         Pixel[][] resultPixels = result.getPixels2D(); 
	     // set pixel to 2*old pixel color - avg pixel color by doing
	     // separation into ints
         for (int i = 0; i < resultPixels.length; i++) 
             for (int j = 0; j < resultPixels[0].length; j++) {
				 //  find average color of surrounding pixel
                 Color avg = getAvg(i - size / 2, j - size / 2, 
									i + size / 2, j + size / 2, pixels);
                 // applies given formula
                 resultPixels[i][j].setRed(2 * pixels[i][j].getRed() - avg.getRed());
                 resultPixels[i][j].setGreen(2 * pixels[i][j].getGreen() - avg.getGreen());
                 resultPixels[i][j].setBlue(2 * pixels[i][j].getBlue() - avg.getBlue());
             }
		 // return result pic
         return result;
         
     }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
