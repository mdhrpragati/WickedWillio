package com.nct.android.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;
;
/**
 * Created by prashant on 1/6/16.
 */
public class AgainstSpells extends Objects {

    Random rand;
    public AgainstSpells(){
        System.out.println("Debug:AgainstSpells:Constructor");
        rand=new Random();
        this.y=(int)(rand.nextDouble()*Character.onScreenHeight);
        this.y=Character.baseGround-y;//So that the spells are fired exactly at character height
        x=GamePanel.WIDTH+10;
        this.width=40;
        this.height=10;
    }

    
    public void update() {

        x+=-20;
        if(x<0){
            x=GamePanel.WIDTH;
            y=(int)(rand.nextDouble()*Character.onScreenHeight);
            y=Character.baseGround-y;
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
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x + width, y + height, paint);
        System.out.println("Debug:AgainstSpells:draw");
    }

}
