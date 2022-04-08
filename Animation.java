package com.nct.android.myapplication;

import android.graphics.Bitmap;

/**
 * Created by prashant on 12/28/15.
 */

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;


    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();

    }

    public void setDelay(long d) {
        delay = d;
    }

    public void forward() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Debug:elapsed time %d", elapsed);
        System.out.printf("Debug:Forward");
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();

        }
        if (currentFrame == frames.length) {
            currentFrame = 0;
        }

    }

    public void backward() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > delay) {
            currentFrame--;
            startTime = System.nanoTime();

        }
        if (currentFrame == -1) {
            currentFrame = frames.length - 1;
        }
    }
    public void resetAnimation(){
        currentFrame=0;
    }
    public  Bitmap  getImage(){
        return frames[currentFrame];
    }

}