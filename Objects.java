package com.nct.android.myapplication;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by prashant on 12/21/15.
 * This should be extended by every class
 */
public abstract class Objects {
  protected int x;
  protected int y;
  protected int width;
  protected int height;
  public boolean ground=false;

  public int getX() {

    return x;
  }
  public int getY(){
    return  y;

  }
  public int getWidth(){
    return width;
  }
  public int getHeight(){
    return height;
  }
  //For collision Detection
  public Rect getRect() {

    return new Rect(x, y, x + width, y + height);

  }
  public void printCoordinates(){
    System.out.printf("Debug:Object:Cooridnates:x:from %d to %d,y from %d to %d\n",x,x+width,y,y+height);
  }

  public abstract void draw(Canvas canvas);

}