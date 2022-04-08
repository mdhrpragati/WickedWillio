package com.nct.android.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.logging.Level;


public class Level1 extends Levels {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Debug:MainActivity:onCreate");
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        panel=new Level1Panel(this);
        setContentView(panel);
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

