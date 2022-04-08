package com.nct.android.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Uttam on 1/26/2016.
 */
public class ScoreManager {
    private String preferenceFile = "ScorePrefs";
    private SharedPreferences prefs;
    private Context context;
    private final String SCORE_KEY = "highscore";

    public ScoreManager(Context context){
        this.context = context;
        prefs = this.context.getSharedPreferences(preferenceFile,Context.MODE_PRIVATE);
        System.out.println("ScoreManager:Construtor");
    }

    public void saveHighScore(int score){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SCORE_KEY,score);
        editor.commit();
    }

    public int getHighScore(){
        System.out.println("Debug: Highscore " + Integer.toString(prefs.getInt(SCORE_KEY,0)));
        return prefs.getInt(SCORE_KEY,0);
    }
}

/*

ScoreManager manager = new ScoreManager(this);

int currentHighScore = manager.getHighScore();

if(currentScore > currentHighScore){
    manager.saveHighScore(currentScore);
}
 */
