package com.nct.android.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;

/*
 * Created by prashant on 12/28/15.
 */
public class Character extends Objects {

    private Bitmap spritesheet;
    private Bitmap reverseSprite;
    private int bufferDistance;
    private int numFrames;
    private Animation animation;
    private Animation reverseanimation;
    private long startTime;
    public static int onScreenHeight;
    private boolean jump=false;
    private int onScreenWidth;
    private int ground;
    private final int finalJumpSpeed=GamePanel.FINALJUMPSEED;
    private int jumpSpeed=finalJumpSpeed;
    private final int acclerationGravity=1;
    public static int baseGround;
    private boolean onGround=false;
    private int score;
    private int life;
    private int lifeBarLength=40;
    private int totalLife=3;
    private int scoreBarPosition=10;
    private boolean prevOnGround;
    private boolean dizzy=false;
    private int highscore;
    private MediaPlayer hurt;
    private boolean isFalling=false;


    //TODO CREATR MECHANISM TO UPDATE GROND BY ITSELF..DOne
    //TODO FIX THE PROBLEM WITH TABLES AND OTHER GROUND OBJECTS..Done

    public Character(Bitmap res,Context context) {
        score=0;
        height=962;
        width=489;
        bufferDistance=0;
        numFrames=5;
        highscore=GamePanel.returnHS();
        spritesheet=res;
        baseGround=GamePanel.BASEGROUND;
        ground = baseGround;
        onScreenHeight=GamePanel.HEIGHT/4;
        onScreenWidth=onScreenHeight/2;
        x=2*GamePanel.WIDTH/20;
        y=baseGround-onScreenHeight;
        hurt=MediaPlayer.create(context,R.raw.wound);
        this.isFalling=false;
        Bitmap[] image=new Bitmap[numFrames];
        Bitmap[] reverseImage=new Bitmap[numFrames];
        Matrix m=new Matrix();
        reverseSprite=Bitmap.createBitmap(spritesheet,0,0,spritesheet.getWidth(),spritesheet.getHeight(),m,false);

        for(int i=0;i<image.length;i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width+bufferDistance/2, 0, width-bufferDistance/2, height);
        }

        animation=new Animation();
        reverseanimation=new Animation();

        //Hit And Trial
        animation.setDelay(75);

        animation.setFrames(image);

        startTime=System.nanoTime();

        life=totalLife;
    }
    public boolean isJumping(){
        return jump;
    }
    public void jump(){
        hurt.start();
        System.out.println("Debug:Jump");
        if(jump!=true) {
            jump = true;
        }
    }
    public void update(){
        System.out.println("Debug:Character.update");
        if(jump==true){
            if(jumpSpeed>=0) {
                System.out.println("Debug:Going Up");
                y=y-jumpSpeed;
                jumpSpeed-=acclerationGravity;
            }
            else if(y<(ground-onScreenHeight)) {
                System.out.println("Debug:Going Down");
                //Parabolic path
                y = y - jumpSpeed;
                if(y>(ground-onScreenHeight)){
                    y=ground-onScreenHeight;
                }
                jumpSpeed-=acclerationGravity;
            }else{
                jump=false;
                jumpSpeed=finalJumpSpeed;
            }
        }
        if(onGround==false&&prevOnGround==true&&jump!=true) {
            jumpSpeed=0;
            jump=true;
        }
    }
    public void setGround(Objects g){
        System.out.println("Character:setGround");
        onGround=true;
        y=g.y-onScreenHeight;
        ground=g.y;
    }
    public void resetGround(){
        ground=baseGround;
    }
    public  void  moveLeft(){
        animation.forward();
    }
    public void moveRight() {
        animation.backward();
    }
     public void dontMove(){
         animation.resetAnimation();
     }
    public void draw(Canvas canvas) {
        Bitmap b = Bitmap.createScaledBitmap(animation.getImage(), onScreenWidth, onScreenHeight, false);
        if (dizzy != true) {
            canvas.drawBitmap(b, x, y, null);
        }
        canvas.drawBitmap(b, x, y, null);
        drawText(canvas);
        drawLife(canvas);
        //Things to be reset after draw
        prevOnGround = onGround;
        onGround = false;
        resetGround();
       /* Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y + onScreenHeight - 5, x + onScreenWidth + 5, y + onScreenHeight + 5, paint);
        canvas.drawRect(x,y,x+5,y+onScreenHeight, paint);
        canvas.drawRect(x,y,x+5,y+onScreenHeight, paint);
        canvas.drawRect(x+onScreenWidth,y,x+onScreenWidth+5,y+onScreenHeight, paint);*/
        if (dizzy == true) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(x, y, x + onScreenWidth, y + onScreenHeight, paint);
            dizzy=false;

        }
    }
    public int getScore(){
        System.out.println("Debug:Character:getScore");
        return score;
    }
    public void scoreUp(){
        System.out.println("Debug:Character:scoreUp");
        score++;

    }
    public boolean isAlive(){
        life--;
        if(life==0){
            return false;
        }
        return true;
    }
    public void drawText(Canvas canvas){

        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(GamePanel.WIDTH / 30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SCORE:" + this.getScore(), GamePanel.WIDTH / 30, GamePanel.HEIGHT / 15, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("HIGH SCORE:"+this.highscore, GamePanel.WIDTH / 2-GamePanel.WIDTH/8,GamePanel.HEIGHT / 15, paint);


    }
    public  void drawLife(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        int lifeBarPosition=GamePanel.WIDTH-GamePanel.WIDTH/10;
        int life=this.life;
        while(life>0) {
            canvas.drawRect(lifeBarPosition,scoreBarPosition,lifeBarPosition+lifeBarLength,scoreBarPosition+20,paint);
            lifeBarPosition-=3/2.0*lifeBarLength;
            life--;
        }
        life=this.totalLife-this.life;
        paint.setStyle(Paint.Style.STROKE);
        while(life>0){
            canvas.drawRect(lifeBarPosition,scoreBarPosition,lifeBarPosition+lifeBarLength,scoreBarPosition+20,paint);
            lifeBarPosition-=3/2.0*lifeBarLength;
            life--;
        }

    }
    public void isDizzy(){
        dizzy=true;
    }
    public Rect getButtonRect(){
        return new Rect(x,y+onScreenHeight-5,x+onScreenWidth,y+onScreenHeight+5);
    }
    public int getLife(){
        return life;
    }
    public void setScore(int i){
        this.score=i;
    }
    public void setLife(int l){
        this.life=l;
    }
    public Rect getLeftRect(){
        return new Rect(x,y+onScreenHeight-5,x+onScreenWidth+5,y+onScreenHeight+5);
    }
    public Rect getRightRect(){
        return new Rect(x,y-5-5,x+onScreenWidth+5,y+5);
    }

    public Rect getRect(){
        return new Rect(x,y+10,x+onScreenWidth-10,y+onScreenHeight-10);
    }

}