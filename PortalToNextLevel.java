package com.nct.android.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by prashant on 1/29/16.
 */
public class PortalToNextLevel extends Objects {
    private Bitmap bitmap;
    public PortalToNextLevel(Bitmap bitmap) {
        x=-GamePanel.MOVESPEED*GamePanel.TOTAL_STEPS;
        height=366;
        width=138;
        y=GamePanel.BASEGROUND-height;
        this.bitmap=bitmap;
    }
    public void moveRight(){
        x+=GamePanel.MOVESPEED;
    }
    @Override
    public void draw(Canvas canvas) {
        if(x<=GamePanel.WIDTH){
            canvas.drawBitmap(bitmap,x,y,null);
        }
    }

    @Override
    public Rect getRect() {
        return new Rect(x+width/2,y,x+width,x+height);
    }
}
