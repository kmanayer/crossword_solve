package Solver;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.ArrayList;

enum Type { ACROSS, DOWN; }	

public class Crossword {
   
  static BufferedImage img;
  static BufferedImage subimg;
  static int cell_w, cell_h, grid_w, grid_h;
  static int[][] grid;
  static int clue_count = 0;
  static ArrayList<Clue> clues = new ArrayList<Clue>();


  private static int img_avg(BufferedImage img) {
    int total = 0;
    int rgb, r, g, b;
    
    for (int i = 0 ; i < cell_h-1 ; i++) {
      for (int j = 0 ; j < cell_w-1 ; j++ ) {
        rgb = img.getRGB(i,j);
        r = (rgb >> 16) & 0xFF;
        g = (rgb >> 8) & 0xFF;
        b = (rgb & 0xFF);
        total += (r + g + b)/3;
      }
    }
    
    return total;
  }

  private static void print_grid(int[][] grid) {
    for (int i = 0 ; i < grid.length ; i++) {
      for (int j = 0 ; j < grid[0].length ; j++) {
        System.out.print(grid[i][j]);
      }
      System.out.println("");
    }
    return;
  }
  
  
  public static void main(String[] args) {
    int gray, clue_length;
    
    grid_w = 15;
    grid_h = 15;
    
    grid = new int[grid_h][grid_w];
    
    try {
      img = ImageIO.read(new File("puzzle.jpg"));
      cell_w = img.getWidth() / grid_w;
      cell_h = img.getHeight() / grid_h;
    } catch (Exception e) {
      System.out.println(e);
      return;
    }
    
    for (int i = 0 ; i < grid_h ; i++) {
      for (int j = 0 ; j < grid_w ; j++) {
        gray = img_avg(img.getSubimage(cell_w*j, cell_h*i, cell_w, cell_h));
        if ( gray < 100000 ) {
          grid[i][j] = 1;
        } else {
          grid[i][j] = 0;
        }
      }
    }
    
    for (int i = 0 ; i < grid_h ; i++) {
      for (int j = 0 ; j < grid_w ; j++) {
        if ( grid[i][j] == 1 ) {
          continue;
        } else if (( i==0 || grid[i-1][j]==1 ) && ( j==0 || grid[i][j-1]==1)) {

          clue_length = 1;
          while ( i+clue_length < grid_h && grid[i+clue_length][j]==0) {
            clue_length++;
          }
          clues.add(new Clue(++clue_count, Type.DOWN, clue_length, i, j));

          clue_length = 1;
          while ( j+clue_length < grid_w && grid[i][j+clue_length]==0 ) {
            clue_length++;
          }
          clues.add(new Clue(clue_count, Type.ACROSS, clue_length, i, j));
          

        } else if ( i==0 || grid[i-1][j]==1 ) {

          clue_length = 1;
          while ( i+clue_length < grid_h && grid[i+clue_length][j]==0) {
            clue_length++;
          }
          clues.add(new Clue(++clue_count, Type.DOWN, clue_length, i, j));

        } else if ( j==0 || grid[i][j-1]==1 ) {

          clue_length = 1;
          while ( j+clue_length < grid_w && grid[i][j+clue_length]==0 ) {
            clue_length++;
          }
          clues.add(new Clue(++clue_count, Type.ACROSS, clue_length, i, j));
          
        }
      }
    }
    System.out.println("Clues found: " + clue_count);
    
  }

}