/* This code was written by Joella Wu-Cardona for CSC 1054 in spring 2022. */

import java.awt.*;
import javax.swing.*;

public class GameObject {
   protected Color color;
   protected int x, y;
   
   public GameObject(int x1, int y1, Color col) {
   //constructor initializes location and color
      x = x1;
      y = y1;
      color = col;
   }
   
   //variable accessors/setters
   public Color getColor() {
      return color;
   }
   
   public int getX() {
      return x;
   }
   
   public void setX(int x1) {
      x = x1;
   }
   
   public int getY() {
      return y;
   }
   
   public void setY(int y1) {
      y = y1;
   }
   
   //determines if two gameobjects collide
   public boolean collides(GameObject obj) {
      if (obj == this || obj == null) {
         return false;
      }
      int topThis = y, topOther = obj.getY(), bottomThis = y+24, bottomOther = obj.getY()+24;
      int leftThis = x, leftOther = obj.getX(), rightThis = x+24, rightOther = obj.getX()+24;
      //check if object collided is the victory, if so run win message
      if (obj instanceof VictoryBlock) {
         winMessage();
         System.exit(1);
      }
      return !((bottomThis<topOther) || (topThis>bottomOther) || (leftThis>rightOther) || (rightThis<leftOther));
   }
   
   //victory message pop up
   public void winMessage() {
      JFrame f = new JFrame();  
      JOptionPane.showMessageDialog(f,"You win!");
   }
   
   public void draw(Graphics g) {}
}