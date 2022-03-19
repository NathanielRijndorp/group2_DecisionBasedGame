package com.example.group2_decisionbasedgame;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;



public class SplashScreen extends AppCompatActivity {

    public MediaPlayer splashScreen;
    //int time in ms.
    public static int time=2750;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide action bar and title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        splashScreen = new MediaPlayer();
        splashScreen = MediaPlayer.create(this, R.raw.group2studio);
        splashScreen.start();

        //after int is finished playing a new handler will run which does this stuff basically plays an animation xml to fade in and fade out to the next scene
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.release();
                Intent mainActivity = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(mainActivity);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                finish();
            }
        },time);
    }
}