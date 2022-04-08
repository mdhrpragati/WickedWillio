package com.nct.android.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.Random;


public class Level1Panel extends GamePanel
{
    private final int WIDTH = 1250;
    private final int HEIGHT = 647;
    private final int BACKGROUND_WIDTH=1250;//A bit less to remove somethings
    private final int MOVESPEED =-10;
    private final int TOTAL_STEPS=1000;//This variable will decide the lenghtOfGame
    private final int BASEGROUND=575;
    public static int FINALJUMPSEED=20;

    private boolean firstStart;
    private int stepsCount=0;
    private Background bg;
    private BackgroundMusic backgroundMusic;
    private Table table;
    private Activity context;
    private boolean playing;
    private  long spelltimer;
    private long gentime=5000;
    private Random rand;
    private ArrayList<Points> points;
    // private AgainstSpells spell;
    private Knife knife;
    private int prevStepCount;
    private int changeMoveSpeed=-4;
    private double accleration;
    private PortalToNextLevel door;
    private boolean transition=false;
    public Level1Panel(Activity context)
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
        super.TOTAL_STEPS=TOTAL_STEPS;
        super.BASEGROUND=BASEGROUND;
        super.FINALJUMPSEED=FINALJUMPSEED;
        accleration=-2*MOVESPEED/TOTAL_STEPS;

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
        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.background));
        character=new Character(BitmapFactory.decodeResource(getResources(),R.drawable.charactersprite),context);
        table=new Table(BitmapFactory.decodeResource(getResources(),R.drawable.table));
        knife=new Knife(BitmapFactory.decodeResource(getResources(),R.drawable.knife));
        door=new PortalToNextLevel(BitmapFactory.decodeResource(getResources(),R.drawable.door));
        //we can safely start the game loop

        if (thread.getState()==Thread.State.TERMINATED){
            thread=new MainThread(getHolder(),this);
        }
        thread.setRunning(true);
        thread.start();

        System.out.println("Debug:Thread Started");
        //spelltimer=System.nanoTime();
        rand=new Random();
        //spell=new AgainstSpells();
        playing=true;//Start Game
        points=new ArrayList<>();
        points.add(new Points(context));



    }

    int i=1;//I am feeling sleepy..Its 2am...So please ignore this shitty code..Will fix it later....
    public void update(){
        if(stepsCount-prevStepCount>20){
            Points p;
            do {
                    p=new Points(context);
            }while(collision(p,table)==true);
            points.add(p);
            prevStepCount=stepsCount;
        }
        GamePanel.MOVESPEED+=Math.round(accleration);
        if(playing==true) {

            if(table.isBlokcingLeft(character)==true){
                moveright=false;
            }
            /*if(table.isBlokcingRight(character)==true){
                moveleft=false;
            }*/
            if(table.isOnTop(character)==true){
                character.setGround(table);
            }
            boolean jump = character.isJumping();

            if (jump == false) {
                prevMoveDirn = 0;
            }

            if (moveright == true && moveleft == false && this.stepsCount <= this.TOTAL_STEPS & jump != true) {
                door.moveRight();
                bg.moveRight();
                character.moveRight();
                table.moveRight();
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
                        table.moveLeft();
                        for (Points p:points){
                            p.moveLeft();
                        }

                    } else if (prevMoveDirn == 2) {
                        bg.moveRight();
                        table.moveRight();
                        door.moveRight();
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
            /*if (collision(spell, character) == true) {
                System.out.println("Debug:GameOver");
                character.printCoordinates();
                spell.printCoordinates();
                spell.reset();
                playing=character.isAlive();
                if(playing==false) {
                    context.finish();
                }
            }*/
            if(collision(knife,character)==true){
                System.out.println("Debug:GameOver");
                character.printCoordinates();
                knife.printCoordinates();
                knife.reset();
                character.isDizzy();
                playing=character.isAlive();
                if(playing==false) {
                    thread.setRunning(false);
                    context.finish();
                    super.updateHighScore(character);
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

        }
            //
            // spell.update();
            knife.update();
            if(collision(character,door)==true){
                playing=false;
                transition=true;
                super.Life=character.getLife();
                super.Score=character.getScore();
                thread.setRunning(false);
                WickedWilio.setLevelToPlay(2);
                context.finish();

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
            //spell.draw(canvas);
            character.draw(canvas);
            //spell.draw(canvas);
            table.draw(canvas);
            door.draw(canvas);
            knife.draw(canvas);
            for (Points p:points){
                System.out.println("Debug:PointDraw");
                p.draw(canvas);
            }
            if(transition==true){//I have no idea why this is working
                Paint paint=new Paint();
                paint.setTextSize(GamePanel.WIDTH / 30);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                paint.setColor(Color.GREEN);
                canvas.drawText("LOADING NEXT LEVEL", GamePanel.WIDTH / 3,GamePanel.HEIGHT / 2, paint);
            }
            //Restore Canvas Size
            canvas.restoreToCount(savedState);
        }
    }



}