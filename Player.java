/* This code was written by Joella Wu-Cardona for CSC 1054 in spring 2022. */

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Player extends GameObject {
   private int rows, columns;
   public Player(int x, int y) {
      super(x, y, Color.RED);
   }
   
   //checks if the player is on the ground (top of barrier block or bottom)
   public boolean isOnGround(ArrayList<ArrayList<GameObject>> list) {
      boolean crash = false;
      int indexY = (y-10)/25, indexX = (x-10)/25; //gets index of current block location for comparison in collision
      y++;
      
      if (collides(list.get(indexY+1).get(indexX)) || collides(list.get(indexY+1).get(indexX+1))) {
         y--;
         return true;
      }
      
      return crash;
   }
   
   //draw method for player
   public void draw(Graphics g) {
      g.setColor(getColor());
      g.fillRect(x,y,25,25);
   }
   
   //movement method of player - can move unless collision with object
   public boolean move(int moveX, int moveY, ArrayList<ArrayList<GameObject>> list) {
      boolean canMove = true;
      x+=moveX;
      y+=moveY;
      int indexY = (y-10)/25, indexX = (x-10)/25; //gets index of current block location for comparison in collision
      
      //left
      if (moveX<0) {
         if (collides(list.get(indexY).get(indexX)) || collides(list.get(indexY+1).get(indexX))) {
            x-=moveX;
            y-=moveY;
            canMove = false;
         }
      }
      //right
      if (moveX>0) {
         if (collides(list.get(indexY).get(indexX+1)) || collides(list.get(indexY+1).get(indexX+1))) {
            x-=moveX;
            y-=moveY;
            canMove = false;
         }
      }
      //up
      if (moveY<0) {
         if (collides(list.get(indexY).get(indexX)) || collides(list.get(indexY).get(indexX+1))) {
            x-=moveX;
            y-=moveY;
            canMove = false;
         }
      }
      //down
      if (moveY>0) {
         if (collides(list.get(indexY+1).get(indexX)) || collides(list.get(indexY+1).get(indexX+1))) {
            x-=moveX;
            y-=moveY;
            canMove = false;
         }
      }
      return canMove;
   }
}