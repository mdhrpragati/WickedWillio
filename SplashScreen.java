package com.nct.android.myapplication;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

public class SplashScreen extends AppCompatActivity implements Runnable {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        RelativeLayout layout = new RelativeLayout(this);
        layout.setBackgroundResource(R.drawable.grouplogo);
        setContentView(layout);
        new android.os.Handler().postDelayed(this,SPLASH_DISPLAY_LENGTH);
    }
    @Override
    public void run() {
        SplashScreen.this.finish();
    }

}