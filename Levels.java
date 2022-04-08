package com.nct.android.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by prashant on 1/29/16.
 */
public abstract class Levels extends Activity{
    protected GamePanel panel;
    private boolean quit;
    public void QuitDailog(){
        final Activity context=this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                panel.getThread().pauseGame();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Wicked Willio");
                alertDialogBuilder
                        .setMessage("Do You Want to quit the game and go back to main menu?")
                        .setCancelable(false)
                        .setPositiveButton("Quit game", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                quit = true;
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("Play on", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                                panel.getThread().resumeGame();

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(quit==true){
            super.onBackPressed();
        }else {
            QuitDailog();
        }
    }
    @Override
    protected void onStop() {
        System.out.println("Level1/2:CLosed");
        super.onStop();
        super.onDestroy();
        this.finish();
    }

}
