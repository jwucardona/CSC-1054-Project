/* This code was written by Joella Wu-Cardona for CSC 1054 in spring 2022. */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Project extends JPanel {
   private String fileName = "ProjectText.txt";
   private ArrayList<ArrayList<GameObject>> blocks = new ArrayList<ArrayList<GameObject>>();
   private int [][] nums;
   private int gravity = 1, x = 0, y = 0, rows, columns, jump = 1, count10 = 0, count20 = 0;
   private Player player = new Player(x, y);

   public Project() {
      //call gameobject class
      try {
      //gather and sort data from file
         Scanner readFile = new Scanner(new File(fileName));
         player.setX((readFile.nextInt()*25) + 10);
         player.setY((readFile.nextInt()*25) + 10);
         rows = readFile.nextInt();
         columns = readFile.nextInt();
         //2d array holds map
         nums = new int[rows][columns];
         for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
               nums[i][j] = readFile.nextInt();
            }
         }
      }
      catch (FileNotFoundException fnfe) {}
      
      //create nested arraylist of gameobjects using data
      for (int i=0; i<rows; i++) {
         ArrayList<GameObject> innerlist = new ArrayList<GameObject>();
         int tempY = (10+(25*i));
         for (int j=0; j<columns; j++) {
            int tempX = (10+(25*j));
            if (nums[i][j]==1) {
            //normal barrier blocks
               innerlist.add(new Block(tempX, tempY));
            }
            else if (nums[i][j]==2) {
            //victory block
               innerlist.add(new VictoryBlock(tempX, tempY));
            }
            else {
            //all blanks null
               innerlist.add(null);
            }
         }
         blocks.add(innerlist);
      }
      
      //access keylistener
      addKeyListener(new ProjectKeyEvent());
      setFocusable(true);
      
      //timer makes animation smooth
      Timer t = new Timer(10, new TimeListener());
      t.start();
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      //border
      g.fillRect(0,0,820,620);
      g.setColor(Color.GRAY);
      g.fillRect(10, 10, 800, 600);
      //color background
      for (int i=0; i<blocks.size(); i++) {
         for (int j=0; j<blocks.get(i).size(); j++) {
            if (blocks.get(i).get(j)!=null) {
               blocks.get(i).get(j).draw(g);
            }
         }
      }
      //player
      player.draw(g);
   }
   
   //direction booleans change with keystrokes
   boolean left, right;
   public class ProjectKeyEvent implements KeyListener {
      public void keyTyped(KeyEvent e) {}
      public void keyReleased(KeyEvent e) {
         if(e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
         }
      }
      public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_W) {
         //makes sure player only jumps if on ground
            if (player.isOnGround(blocks)) {
               jump = 7;
            }
         }
         if (e.getKeyCode() == KeyEvent.VK_D) {
            right=true;
         }
         if (e.getKeyCode() == KeyEvent.VK_A) {
            left=true;
         }
      }
   }
   
   public class TimeListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
      //player moves left/right with keystrokes AD
         if(left) {
            player.move(-1, 0, blocks);
         }
         if(right) {
            player.move(1, 0, blocks);
         }
         //falls down until hit ground again
         for (int i=0; i<jump; i++) {
            if (jump>0) {
               gravity = 0;
               if (!player.move(0, -1, blocks)) {
                  jump = 0;
               }
               repaint();
            }
         }
         //speed of jumping up
         if (jump>0) {
            if (count10>10) {
               jump--;
               count10=0;
            }
         }
         count10++;
         //checks for barrier block above to fall back down
         if (player.collides(blocks.get((y-10)/25).get((x-10)/25)) || player.collides(blocks.get(((y-10)/25)+1).get(((x-10)/25)+1))) {
            jump = 0;
         }
         
         //gravity brings player down
         for (int i=0; i<gravity; i++) {
            player.move(0, 1, blocks);
            repaint();
         }
         
         //gravity increases speed every 20 ticks
         if (gravity<7) {
            if (count20>20) {
               gravity++;
               count20=0;
            }
         }
         count20++;
         if (player.isOnGround(blocks)) {
         //reset gravity when hit ground
            gravity=1;
         }
         repaint();
                  
         checkRange();
         repaint();
      }
      
      //makes sure player sprite does not leave the space assigned
      public void checkRange() {
         if (player.getX()<10) {
            player.move(1, 0, blocks);
         }
         if (player.getX()>785) {
            player.move(-1, 0, blocks);
         }
         if (player.getY()<10) {
            player.move(0, 1, blocks);
         }
         if (player.getY()>585) {
            player.move(0, -1, blocks);
         }
      }
   }
   
   public static void main(String[] args) {
      //create frame to display panel
      JFrame frame = new JFrame("Project");
      frame.add(new Project());
      frame.setSize(834, 657);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}