package com.nct.android.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class Level2 extends Levels {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Debug:MainActivity:onCreate");
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        panel = new Level2Panel(this);
        setContentView(panel);
    }
    @Override
    public void onBackPressed(){
        WickedWilio.setLevelToPlay(0);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        WickedWilio.setLevelToPlay(0);
        super.onStop();
    }
}

