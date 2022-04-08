package com.nct.android.myapplication;

import android.media.MediaPlayer;

/**
 * Created by prashant on 12/26/15.
 */
public class BackgroundMusic {

    private int length=0;
    private MediaPlayer mediaPlayer;

    public BackgroundMusic(MediaPlayer mediaPlayer){
        this.mediaPlayer=mediaPlayer;
        System.out.println("Debug:BackgroundMusic:Constructor");

    }
    public void startMusic(){
        System.out.println("Debug:BackgroundMusic:startMusic");
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);


    }
    public void pauseMusic(){
        System.out.println("Debug:BackgroundMusic:pauseMusic");
        mediaPlayer.pause();
        length=mediaPlayer.getCurrentPosition();

    }
    public void resetMusic(){
        System.out.println("Debug:BackgroundMusic:resetMusic");
            length=0;
    }
    public void destroyMusic(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}
