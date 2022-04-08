package com.nct.android.myapplication;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Background {

    private Bitmap image;
    private int x=0, y=0;

    public Background(Bitmap res) {

        System.out.println("Debug:BackGround:Constructor");
        image = res;

    }

    public void moveRight() {
        System.out.println("Debug:BackGround:moveRight");
        x +=GamePanel.MOVESPEED;
        if (x < -GamePanel.BACKGROUND_WIDTH) {
            x = 0;
        }
    }

    public void moveLeft(){
        System.out.println("Debug:BackGround:moveLeft");
        x-=GamePanel.MOVESPEED;
        if(x>GamePanel.BACKGROUND_WIDTH){
            x=0;
        }
    }

    public void draw(Canvas canvas) {
        System.out.println("BackGround:draw");
        canvas.drawBitmap(image, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GamePanel.BACKGROUND_WIDTH, y, null);
        }
        else if(x>0){
            canvas.drawBitmap(image,x-GamePanel.BACKGROUND_WIDTH,y,null);
        }
        /*Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,Character.baseGround,Levels.WIDTH,Levels.HEIGHT,paint);  */  }
}