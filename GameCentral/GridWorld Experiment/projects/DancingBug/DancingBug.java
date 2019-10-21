/*
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * @author Cay Horstmann
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 */

import info.gridworld.actor.Bug;

/**
 * A <code>DancingBug</code> traces out a square "Dancing" of a given siDancinge. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class DancingBug extends Bug
{
    int counter;
    int length;
    int[] anArr;



    /**
     * Constructs a Dancing bug that traces a square of a given side length
     * @param length the side length
     */
    public DancingBug(int[]arr)
    {
      //line
      anArr = arr;
      counter = 0;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        length = anArr.length-1;
        move();
        move();
        move();
        move();
        curent++;
          for(int a = 0;a<=current; a++){
            turn();
          }
          if(current >=arr.length)
            current = 0;
    }
  }
