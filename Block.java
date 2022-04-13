/* This code was written by Joella Wu-Cardona for CSC 1054 in spring 2022. */

import java.awt.*;

public class Block extends GameObject {
   //extends gameobject constructor with location and color BLUE
   public Block(int x, int y) {
      super(x, y, Color.BLUE);
   }
   //draw block
   public void draw(Graphics g) {
      g.setColor(getColor());
      g.fillRect(x,y,25,25);
   }
}