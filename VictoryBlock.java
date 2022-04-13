/* This code was written by Joella Wu-Cardona for CSC 1054 in spring 2022. */

import java.awt.*;

public class VictoryBlock extends GameObject {
   //extends gameobject constructor with location and color GREEN
   public VictoryBlock(int x, int y) {
      super(x, y, Color.GREEN);
   }
   //draw victory block
   public void draw(Graphics g) {
      g.setColor(getColor());
      g.fillRect(x,y,25,25);
   }
}