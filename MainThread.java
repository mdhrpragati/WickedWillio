package com.nct.android.myapplication;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;
    private boolean pause=false;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        System.out.println("Debug:MainThread:Constructor");
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    @Override
    public void run()
    {
        System.out.println("Debug:MainThread:run");
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                System.out.println("Debug:Before Locking Canvas");
                canvas = this.surfaceHolder.lockCanvas();
                System.out.println("Debug:After Locking Canvas");

                synchronized (surfaceHolder) {
                    if(pause==false) {
                        this.gamePanel.update();
                    }
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                System.out.print("Debug:FPS:");
                System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b) {
        System.out.println("Debug:MainThread:setRunning");
        running=b;
    }
    public void pauseGame(){
        pause=true;
    }
    public void resumeGame(){
        pause=false;
    }
}