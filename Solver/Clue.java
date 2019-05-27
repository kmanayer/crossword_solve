package Solver;


enum Type { ACROSS, DOWN; }

public class Clue {
  
  int num, x, y, len;
  Type type;

  Clue(int n, Type t, int l, int x_pos, int y_pos) {
    num = n;
    type = t;
    len = l;
    x = x_pos;
    y = y_pos;
  }
  
}