package com.nct.android.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;

import java.util.Random;

/**
 * Created by prashant on 1/11/16.
 */
public class Points extends  Objects {
    private int radius=GamePanel.WIDTH/50;
    private boolean valid=true;
    private MediaPlayer hait;
    public Points(Context context) {
        this.x = GamePanel.WIDTH;
        Random rand = new Random();
        hait=MediaPlayer.create(context,R.raw.hait);
        this.y =GamePanel.HEIGHT-(int)(GamePanel.HEIGHT / 2*rand.nextDouble());
        y-=(GamePanel.HEIGHT-Character.baseGround);
        x=x-radius;
        y=y-radius;
        System.out.println("Debug:Points:ConstrutorCalled");
    }

    public void draw(Canvas canvas){
        if(valid==true) {
            System.out.println("Debug:Points:Draw");
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y , radius, paint);
            System.out.print("Debug:x,y=");
            System.out.print(x);
            System.out.print(y);
            System.out.println();

        }
    }
    public void dontDraw(){
        hait.start();
        valid=false;
        x=0;
        y=0;
        width=0;
        height=0;
    }
    public void moveLeft(){
        x-=GamePanel.MOVESPEED;
    }
    public void moveRight(){
        x += GamePanel.MOVESPEED;
    }
    @Override
    public Rect getRect(){
        return new Rect(x-radius,y-radius,x+radius,y+radius);
    }
}
