/**
 *	FirstAssignment.java
 *	Display a brief description of your summer vacation on the screen.
 *
 *	To compile:	javac -cp .:acm.jar FirstAssignment.java
 *	To execute:	java -cp .:acm.jar FirstAssignment
 *
 *	@author	Your name 
 *	@since	Today's date
 */
import java.awt.Font;

import acm.program.GraphicsProgram;
import acm.graphics.GLabel;

public class FirstAssignment extends GraphicsProgram {
    
    public void run() {
    	//	The font to be used
    	Font f = new Font("Serif", Font.BOLD, 18);
    	
    	//	Line 1
    	GLabel s1 = new GLabel("What I did on my summer vacation ..."
    							, 10, 20);
    	s1.setFont(f);
    	add(s1);
    	
    	//	Line 2
    	GLabel s2 = new GLabel("In June, I took a trip to Canada to meet" +
								" one of my cousins who lives in Alberta," 
								, 10, 40);
    	s2.setFont(f);
    	add(s2);
    	
    	//Line 3
    	GLabel s3 = new GLabel("Canada. There, I visited a bunch of the" +
								" monuments and tourist sites. It was much" 
								, 10, 60);
    	s3.setFont(f);
    	add(s3);
    	
    	//Line 4
    	GLabel s4 = new GLabel("colder than I had previously anticipated" +
								" too, but I was told that it was very warm" 
								, 10, 80);
    	s4.setFont(f);
    	add(s4);
    
    	//Line 5
    	GLabel s5 = new GLabel("compared to the summer. Since the place where" +
								" they lived was a fairly remote place," 
								, 10, 100);
    	s5.setFont(f);
    	add(s5);
    	
    	//Line 6
    	GLabel s6 = new GLabel("there weren't as many tourist locations," +
								" but we visited places like the local museum" 
								, 10, 120);
    	s6.setFont(f);
    	add(s6);
    	
    	//Line 7
    	GLabel s7 = new GLabel("named Royal Alberta Museum (which was a drive" +
								" away). Overall, it was pretty " 
								, 10, 140);
    	s7.setFont(f);
    	add(s7);
    	    	
    	//Line 8
    	GLabel s8 = new GLabel("enjoyable because I not only met family and family" +
								" friends again, I also got to" 
								, 10, 160);
    	s8.setFont(f);
    	add(s8);
    	
    	//Line 9
    	GLabel s9 = new GLabel("witness some Canadian history, which was" +
								" cool since that's where I was born." 
								, 10, 180);
    	s9.setFont(f);
    	add(s9);
    	
    	//Line 10
    	GLabel s10 = new GLabel("However, I had to come back and study for" +
								" a bunch of the subjects and tests I'd" 
								, 10, 200);
    	s10.setFont(f);
    	add(s10);
    	
    	//Line 11
    	GLabel s11 = new GLabel("do this year, like the ACT and SAT 2 Math" +
								" Test. I had to prepare for the AP" 
								, 10, 220);
    	s11.setFont(f);
    	add(s11);
    	
    	//Line 12
    	GLabel s12 = new GLabel("classes I would be taking this year as well." +
								" I spent most of the rest of the summer" 
								, 10, 240);
    	s12.setFont(f);
    	add(s12);
    	
    	//Line 13
    	GLabel s13 = new GLabel("studying. It wasn't fun per se, but I took the" +
								" classes I did because I liked them, so" 
								, 10, 260);
    	s13.setFont(f);
    	add(s13);
    	
    	//Line 14
    	GLabel s14 = new GLabel("it wasn't too painful." 
								, 10, 280);
    	s14.setFont(f);
    	add(s14);
    	
    	//Line 15
    	GLabel s15 = new GLabel("I hope to have a great time in CS this" + 
								" year!" 
								, 10, 300);
    	s15.setFont(f);
    	add(s15);
    	
    }
   /* ### COMPLETE STORY in raw text ### 
    * In June, I took a trip to Canada to meet one of my cousins who lives in Alberta,
    * Canada. There, I visited a bunch of the monuments and tourist sites. It was much
    * colder than I had previously anticipated too, but I was told that it was very warm 
    * compared to the summer. Since the place where they lived was a fairly remote place,
    * there weren't as many tourist locations, but we visited places like the local museum
    * named Royal Alberta Museum (which was a drive away). Overall, it was pretty enjoyable
    * because I not only met family and family friends again, I also got to witness some
    * Canadian history, which was cool since that's where I was born.
    * 
    * However, I had to come back and study for a bunch of the subjects and tests I would be
    * doing this year, like the ACT and SAT 2 Math Subject Test. I had to prepare for the AP
    * classes I would be taking this year as well. I spent most of the rest of the summer
    * studying. It wasn't fun per se, but I took the classes I did because I liked them, so
    * it wasn't too painful. 
    * 
    * I hope to have a great time in CS this year!
    * 
   */
}
