package com.nct.android.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class WickedWilio extends AppCompatActivity {

    public static boolean MUSIC_ON=true;
    private static int  levelToPlay=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int width = size.x;

        // Setting the size of Padding in
        int left_padding = width / 2;
        int padding = height / 20;
        final Context context =  this;
        final Intent splash=new Intent(this,SplashScreen.class);
        startActivity(splash);
        final Intent gameLoop = new Intent(this, Level1.class);
        final Intent optionActivity = new Intent(this, OptionActivity.class);
        final Intent helpActivity = new Intent(this, HelpActivity.class);


        RelativeLayout layout = new RelativeLayout(this);
        layout.setBackgroundResource(R.drawable.menubackground);


        Button startGame = new Button(this);
        startGame.setText("New game");
        startGame.setBackgroundColor(Color.YELLOW);
        startGame.setId(1);

        startGame.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(gameLoop);
                    }

                }
        );


        Button options = new Button(this);
        options.setText("Options");
        options.setBackgroundColor(Color.YELLOW);
        options.setId(2);

        options.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(optionActivity);
                    }
                }
        );


        Button help = new Button(this);
        help.setText("Help");
        help.setBackgroundColor(Color.YELLOW);
        help.setId(3);

        help.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(helpActivity);
                    }
                }
        );


        Button exit = new Button(this);
        exit.setText("Exit");
        exit.setBackgroundColor(Color.YELLOW);
        exit.setId(4);
        exit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Wicked Willio");

                alertDialogBuilder
                        .setMessage("Do You Want to quit the game?")
                        .setCancelable(false)
                        .setPositiveButton("Quit game", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                WickedWilio.this.finish();
                            }
                        })
                        .setNegativeButton("Play on", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        RelativeLayout.LayoutParams startGameRule =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams optionRule =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams helpRule =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams exitRule =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);


        //startGameRule.addRule(RelativeLayout.CENTER_VERTICAL);
        // startGameRule.addRule(RelativeLayout.CENTER_HORIZONTAL);
        startGameRule.setMargins(left_padding, padding, 0, 0);


        optionRule.addRule(RelativeLayout.BELOW, startGame.getId());
        // optionRule.addRule(RelativeLayout.CENTER_HORIZONTAL);
        optionRule.setMargins(left_padding, padding, 0, 0);

        helpRule.addRule(RelativeLayout.BELOW, options.getId());
        // helpRule.addRule(RelativeLayout.CENTER_HORIZONTAL);
        helpRule.setMargins(left_padding, padding, 0, 0);

        exitRule.addRule(RelativeLayout.BELOW, help.getId());
        // exitRule.addRule(RelativeLayout.CENTER_HORIZONTAL);
        exitRule.setMargins(left_padding, padding, 0, 0);


        layout.addView(startGame, startGameRule);
        layout.addView(options, optionRule);
        layout.addView(help, helpRule);
        layout.addView(exit, exitRule);
        setContentView(layout);
    }
    protected void onResume(){
        super.onResume();
        if(levelToPlay==2)
        {
            Intent i=new Intent(this,Level2.class);
            startActivity(i);
        }
    }
    public static void setLevelToPlay(int i){
        levelToPlay=i;
    }

}
