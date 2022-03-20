package com.example.group2_decisionbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class YouLost extends AppCompatActivity {

    MediaPlayer youLost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_you_lost);

        youLost = new MediaPlayer();
        youLost = MediaPlayer.create(this, R.raw.youlost);
        youLost.start();
    }
    public void onBackPressed() {

    }
    public void dead(View view) {
        youLost.release();
        Intent intent = new Intent(this, SplashScreen.class);
        startActivity(intent);
        youLost.release();
    }

}