/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("Images/redMotorcycle.jpg");
	beach.swapLeftRight().explore();
	beach.stairStep(10,10).explore();
	beach.liquify(200).explore();
	beach.wavy(50).explore();
  }
  
  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("Images/caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  /** Method to test testKeepOnlyBlue */
  public static void testKeepOnlyBlue()
  {
	  Picture caterpillar = new Picture("Images/caterpillar.jpg");
	  caterpillar.explore();
	  caterpillar.keepOnlyBlue();
	  caterpillar.explore();
  }
  /** Method to test negate */
  public static void testNegate()
  {
	  Picture caterpillar = new Picture("Images/caterpillar.jpg");
	  caterpillar.explore();
	  caterpillar.negate();
	  caterpillar.explore();
  }
  /** Method to test grayscale */
  public static void testGrayscale()
  {
	  Picture caterpillar = new Picture("Images/swan.jpg");
	  caterpillar.explore();
	  while (true) {
		caterpillar.createCollage();
		caterpillar.explore(); }
  }
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.edgeDetection(10);
    canvas.explore();
  }
  
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  
  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    //testZeroBlue();
    //testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
    testGrayscale();
    //testFixUnderwater();
    //testMirrorVertical();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    ///testCopy();
    //testEdgeDetection();
    //testEdgeDetection2();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
  }
}
