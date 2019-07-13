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

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains Dancing bugs. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class DancingBugRunner
{
    public static void main(String[] args)
    {
        int[] myArr = {1,5,2,6,3};

        ActorWorld world = new ActorWorld();
        //DancingBug alice = new DancingBug(3);
        DancingBug bob = new DancingBug(myArr);

      //  alice.setColor(Color.BLUE);
      //  alice.setDirection(90);
        bob.setColor(Color.ORANGE);
        bob.setDirection(0);
    //    world.add(new Location(1, 1), alice);
        world.add(new Location(4, 4), bob);

        world.show();
    }
}
