package com.nct.android.myapplication;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OptionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Debug:OptionActivity");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int width=size.x;

        final TextView  text=new TextView(this);

        // Setting the size of Padding in
        int left_padding=width/5;
        int padding = height/20;
        RelativeLayout layout = new RelativeLayout(this);
        layout.setBackgroundResource(R.drawable.menubackground);


        //to on the music
        Button musicon=new Button(this);
        musicon.setText("Music On");
        musicon.setBackgroundColor(Color.CYAN);
        musicon.setId(1);

        musicon.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(), "Music is On", Toast.LENGTH_SHORT).show();
                        WickedWilio.MUSIC_ON=true;

                        WickedWilio.MUSIC_ON=true;

                    }
                }
        );
        //to pause the music
        Button musicoff = new Button(this);
        musicoff.setText("Music Off");
        musicoff.setBackgroundColor(Color.CYAN);
        musicoff.setId(2);

        musicoff.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Music is Off", Toast.LENGTH_SHORT).show();
                        WickedWilio.MUSIC_ON = false;



                    }
                }
        );

        RelativeLayout.LayoutParams musiconRule=
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        musiconRule.setMargins(left_padding, padding, 0, 0);
        musiconRule.addRule(RelativeLayout.BELOW, text.getId());


        RelativeLayout.LayoutParams musicoffRule=
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);


        musicoffRule.addRule(RelativeLayout.BELOW, musicon.getId());
        musicoffRule.setMargins(left_padding, padding, 0, 0);

        layout.addView(musicon, musiconRule);
        layout.addView(musicoff, musicoffRule);
        setContentView(layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Debug:MainActivity:onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Debug:MainActivity:onResume");

    }


}