package com.nct.android.myapplication;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Table extends Objects {

    private Bitmap image;
    private int[] cordinates={-450,-400,-350,-300,-250,-200};
    public int index;
    public Table(Bitmap res) {
        height = 105;
        width = 206;
        x = GamePanel.WIDTH + 10;
        y = Character.baseGround - height;
        System.out.println("Debug:Table:Constructor");
        image = res;
    }

    public void moveRight() {
        System.out.println("Debug:BackGround:moveRight");
        x += GamePanel.MOVESPEED;
        if(x<=cordinates[index]){
            x=GamePanel.BACKGROUND_WIDTH;
            index++;
            index%=cordinates.length;
        }
    }

    public void moveLeft() {
        System.out.println("Debug:BackGround:moveLeft");
        x -= GamePanel.MOVESPEED;


    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);


    }
    public boolean isBlokcingLeft(Character c){
        int dx=GamePanel.MOVESPEED;
        Rect R=new Rect(x-dx,y-dx,x+dx,y+height);
        if(Rect.intersects(c.getLeftRect(),R)==true)
            return true;
        return false;
    }
    public boolean isBlokcingRight(Character c){
        Rect R=new Rect(x+width-1,y,x+width-1,y+height/2);
        if(Rect.intersects(c.getRightRect(),R)==true)
            return true;
        return false;
    }
    public boolean isOnTop(Character c){
        Rect R1=new Rect(x+10,y-1,x+width-10,y+height/4);
        if(Rect.intersects(c.getButtonRect(),R1)==true)
            return true;
        return false;

    }
}