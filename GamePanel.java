package com.nct.android.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by prashant on 1/12/16.
 */
public abstract class GamePanel  extends SurfaceView implements  SurfaceHolder.Callback{

    protected boolean moveleft=false;
    protected boolean moveright=false;
    protected int prevMoveDirn;//0=still,1==left,2=right
    protected  Character character;
    public static int WIDTH ;
    public static int HEIGHT;
    public static int BACKGROUND_WIDTH;//A bit less to remove somethings
    public static int MOVESPEED ;
    public static int TOTAL_STEPS;//This variable will decide the lenghtOfGame
    public static int BASEGROUND;
    public static int FINALJUMPSEED;
    private static int HIGHSCORE;
    protected MainThread thread;
    protected static int Score;
    protected static int Life;

    private ScoreManager manager;
    public  boolean collision(Objects a,Objects b) {
        if (Rect.intersects(a.getRect(), b.getRect()) == true) {
            return true;
        }
        return false;
    }
    public GamePanel(Activity activity){
        super(activity);
        manager =new ScoreManager(activity);
        HIGHSCORE=manager.getHighScore();

    }
    //Hack Code
    private float[] first=new float[2];
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getPointerCount()>1){
            System.out.printf("Debug:GamePane:Multitouch %d\n",event.getPointerCount());
        }
        else{
            System.out.println("Debug:Only One Finger");
        }

        System.out.println("Debug:onTouch:onTouchEvent");
        moveright=false;
        switch(event.getActionMasked()) {
            /*case MotionEvent.ACTION_DOWN: {
                System.out.println("Debug:onTouch:Pointer Down! Main");

                float x = event.getX();
                float y = event.getY();
                System.out.printf("Debug:onTouch:x,y=%f,%f\n", x, y);
                if (x < getWidth() / 2) {
                    moveright = true;
                }else if (x > getWidth() / 2) {
                    character.jump();
                }
                //EventHandled
                return true;
            }
            case MotionEvent.ACTION_UP: {
                System.out.println("Debug:onTouch:Pointer Up!Second");
                moveright = false;
                //Event Handled i
                return true;
            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                float x = event.getX(1);
                float y = event.getY(1);
                System.out.printf("Debug:onTouch:x,y=%f,%f\n", x, y);
                if (x < getWidth() / 2) {
                    moveright = true;
                }else if (x > getWidth() / 2) {
                    character.jump();

                }
                System.out.println("Debug:onTouch:Pointer Down!Second ");
                return  true;
            }
            case MotionEvent.ACTION_POINTER_UP:{
                float x=event.getX(1);
                float y=event.getY(1);
                if(x<getWidth()/2){
                    moveright=false;
                }

            }*/
            case MotionEvent.ACTION_MOVE:{
                float y=Math.abs(event.getY()-first[1]);
                if(y>20){
                    character.jump();
                        System.out.println("Math:"+y/Math.abs(event.getX()-first[0]));
                        if(Math.atan(y/Math.abs(event.getX()-first[0]))<0.7){
                            moveright=true;
                        }   break;

                }



            }
            case MotionEvent.ACTION_DOWN:{
                moveright=true;
                first[0]=event.getX();
                first[1]=event.getY();
                break;

            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                character.jump();
                moveright=true;
            }


        }
        return true;
    }
    public static int returnHS(){
        return HIGHSCORE;
    }
    public void updateHighScore(Character character){

        if (character.getScore()>HIGHSCORE){
            manager.saveHighScore(character.getScore());
        }
    }
    public MainThread getThread(){
        return thread;
    }
    public abstract void draw(Canvas canvas);
    public abstract void update();
    public abstract void setValues();

}
