package com.nct.android.myapplication;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.Random;


public class Level2Panel extends GamePanel
{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static final int BACKGROUND_WIDTH=800;//A bit less to remove somethings
    public static final int MOVESPEED = -10;
    public static final int BASEGROUND=390;
    public static final int FINALJUMPSEED=20;

    private boolean firstStart;
    private int stepsCount=0;
    private Background bg;

    private BackgroundMusic backgroundMusic;
    private Activity context;
    private boolean playing;
    private  long spelltimer;
    private long gentime=5000;
    private Random rand;
    private ArrayList<Points> points;
    private AgainstSpells spell;
    private int prevStepCount;

    public Level2Panel(Activity context)
    {
        super(context);

        this.context=context;
        System.out.println("Debug:GamePanel:Constructor");
        callOnClick();
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(),this);
        this.firstStart=true;//The construtor is called only the first Time
        setValues();
        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }
    public void setValues(){
        super.WIDTH=WIDTH;
        super.MOVESPEED=MOVESPEED;
        super.BACKGROUND_WIDTH=BACKGROUND_WIDTH;
        super.HEIGHT=HEIGHT;
        super.BASEGROUND=BASEGROUND;
        super.FINALJUMPSEED=FINALJUMPSEED;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        System.out.println("Debug:GamePanel:SurfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        System.out.println("Debug:GamePanel:SurfaceDestroyed");
        int count=0;
        while(count<1000)//Only try for 1000 times...if fail let system handle it
        {
            try{thread.setRunning(false);
                thread.join();
                thread=null;//Garbage Collecter will be called
                break;




            }catch(InterruptedException e){e.printStackTrace();}
        }
        if(WickedWilio.MUSIC_ON==true) {
            backgroundMusic.pauseMusic();
        }
        spelltimer=System.nanoTime();
        prevStepCount=0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        System.out.println("Debug:GamePanel:SurfaceCreated");
        if(WickedWilio.MUSIC_ON==true) {
            backgroundMusic = new BackgroundMusic(MediaPlayer.create(context, R.raw.backgroundmusic));
            backgroundMusic.startMusic();
        }
        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.wall));
        character=new Character(BitmapFactory.decodeResource(getResources(),R.drawable.charactersprite),context);
        character.setScore(super.Score);
        character.setLife(super.Life);

        //we can safely start the game loop

        if (thread.getState()==Thread.State.TERMINATED){
            thread=new MainThread(getHolder(),this);
        }
        thread.setRunning(true);
        thread.start();

        System.out.println("Debug:Thread Started");
        spelltimer=System.nanoTime();
        rand=new Random();
        spell=new AgainstSpells();
        playing=true;//Start Game
        points=new ArrayList<>();
        points.add(new Points(context));
    }

    public void update(){
        if(stepsCount-prevStepCount>10){
            points.add(new Points(context));
            prevStepCount=stepsCount;

        }
        if(playing==true) {

            boolean jump = character.isJumping();
            if (jump == false) {
                prevMoveDirn = 0;
            }
            if (moveleft == true && moveright == false && this.stepsCount > 0 && jump != true) {
                bg.moveLeft();
                character.moveLeft();
                for (Points p:points){
                    p.moveLeft();
                }
                //character.moveLeft();
                stepsCount--;
                System.out.printf("Debug:StepsCount=%d\n", stepsCount);

            } else if (moveright == true && moveleft == false&& jump != true) {
                bg.moveRight();
                character.moveRight();
                for (Points p:points){
                    p.moveRight();
                }
                //character.moveRight();
                //LaterOnAssociate This number with the Walk Aniamtion
                stepsCount++;
                System.out.printf("Debug:StepsCount=%d\n", stepsCount);

            } else if (jump == true) {
                if (prevMoveDirn == 0) {
                    if (moveleft == true && moveright == false) {
                        prevMoveDirn = 1;
                    } else if (moveleft == false && moveright == true) {
                        prevMoveDirn = 2;
                    }
                } else {
                    if (prevMoveDirn == 1) {
                        bg.moveLeft();
                        character.moveLeft();
                        for (Points p:points){
                            p.moveLeft();
                        }

                    } else if (prevMoveDirn == 2) {
                        bg.moveRight();
                        character.moveRight();
                        for (Points p:points){
                            p.moveRight();
                        }

                    } else {
                        character.dontMove();
                    }
                }
            } else {
                character.dontMove();
            }
            character.update();
            if (collision(spell, character) == true) {
                System.out.println("Debug:GameOver");
                character.printCoordinates();
                spell.printCoordinates();
                spell.reset();
                playing=character.isAlive();
                if(playing==false) {
                    thread.setRunning(false);
                    WickedWilio.setLevelToPlay(0);
                    context.finish();

                }
            }
            for(Points p:points){
                System.out.println("Debug:PointUpdate");
                if (collision(character,p)==true) {
                    //points.remove(p);
                    p.dontDraw();
                    character.scoreUp();
                }
            }

            spell.update();
        }
    }



    @Override
    public void draw(Canvas canvas)
    {
        System.out.println("Debug:GamePanel:draw");
        //To get Decimal Value
        final float scaleFactorX = (float)getWidth()/WIDTH;
        final float scaleFactorY = (float)getHeight()/HEIGHT;
        if(canvas!=null) {
            final int savedState = canvas.save();
            //Make Canvas the size of the Background
            canvas.scale(scaleFactorX, scaleFactorY);
            System.out.println(scaleFactorX);
            System.out.println(scaleFactorY);
            //Put the Background in the Canvas
            bg.draw(canvas);
            spell.draw(canvas);
            character.draw(canvas);
            spell.draw(canvas);
            for (Points p:points){
                System.out.println("Debug:PointDraw");
                p.draw(canvas);
            }
            //Restore Canvas Size
            canvas.restoreToCount(savedState);
        }
    }
    public  boolean collision(Objects a,Objects b){
        if(Rect.intersects(a.getRect(), b.getRect())==true){
            return  true;
        }
        return false;
    }

}