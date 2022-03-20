package com.example.group2_decisionbasedgame;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Credits extends AppCompatActivity {

    private Animation animation;
    private TextView creditText;
    private MediaPlayer credits;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_credits);
        Objects.requireNonNull(getSupportActionBar()).hide();

        animation = AnimationUtils.loadAnimation(this,R.anim.credits);
        credits = new MediaPlayer();
        credits = MediaPlayer.create(this, R.raw.credits);
        credits.start();

        creditText = findViewById(R.id.creditsTextView);
        creditText.setText("CREDITS \n" + "\n " + "GROUP 2 \n" + "\n " +"MEMBERS \n" + "Edzyl Philip Boniza\n" + "\n" +
                "Marc Loen De Jesus\n" + "\n" +
                "Marienel Namion \n" + "\n" +
                "Nathaniel Rijndorp \n" + "\n" +
                "Renzi Albastro \n");

        creditText.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override public void onAnimationEnd(Animation animation){
                credits.release();
                creditText.setVisibility(View.GONE);
                Intent mainActivity = new Intent(Credits.this,SplashScreen.class);
                startActivity(mainActivity);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void onBackPressed() {

    }
}