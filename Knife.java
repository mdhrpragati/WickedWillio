package com.nct.android.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by prashant on 1/14/16.
 */
public class Knife extends Objects{

    Random rand;
    Bitmap image;
    private int onScreenHeight;
    private int onScreenWidth;
    public Knife(Bitmap res){

        onScreenHeight=GamePanel.HEIGHT/16;
        onScreenWidth=GamePanel.WIDTH/8;
        this.image=res;
        System.out.println("Debug:AgainstSpells:Constructor");
        rand=new Random();
        this.y=(int)(rand.nextDouble()*Character.onScreenHeight);
        this.y=Character.baseGround-y;//So that the spells are fired exactly at character height
        this.y=y-(GamePanel.HEIGHT-Character.baseGround);

        x=GamePanel.WIDTH+10;
        this.width=1400;
        this.height=204;
    }


    public void update() {

        x+=2*GamePanel.MOVESPEED;
        if(x<0){
            x=GamePanel.WIDTH;
            y=(int)(rand.nextDouble()*Character.onScreenHeight);
            y=Character.baseGround-y;
            y=y-onScreenHeight;
        }
    }
    public void reset(){
        System.out.println("Debug:AgainstSpells:Reset");
        x=GamePanel.WIDTH;
        y=(int)(rand.nextDouble()*Character.onScreenHeight);
        y=Character.baseGround-y;
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap b=Bitmap.createScaledBitmap(image,onScreenWidth,onScreenHeight,false);
        canvas.drawBitmap(b, x, y, null);
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        //canvas.drawRect(x,y,x+onScreenWidth,y+onScreenHeight,paint);
    }
    @Override
    public Rect getRect(){
        return(new Rect(x,y,x+onScreenWidth,y+onScreenHeight));
    }

}
