package com.example.group2_decisionbasedgame;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity implements View.OnClickListener {


    //TODO I need to add a way to like, check if you win or lose so idk
    Animation animation;
    Button choice1, choice2, choice3, choice4;
    TextView dialogueText;
    int choiceEnable; //for checking how many choices you will have
    int gameState;
    int gameTurn;
    int buttonNumber; //Checks which button is clicked
    int winCondition = 1;
    MediaPlayer bgm;
    ImageView character;

    ConstraintLayout background;
    String[] defText, game; //string arrays used to pull data
    Timer timer; //timer for text playing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();

        worldsEndTableBGM();

        character = findViewById(R.id.characterImage);

        animation = AnimationUtils.loadAnimation(this,R.anim.transitionin);

        //Set the default text
        defText = getResources().getStringArray(R.array.default_text);
        //Set the game text
        game = getResources().getStringArray(R.array.default_text);
        //Buttons for choices
        choice1 = findViewById(R.id.button1);
        choice2 = findViewById(R.id.button2);
        choice3 = findViewById(R.id.button3);
        choice4 = findViewById(R.id.button4);
        //Buttons for choices onClickListener
        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
        //Set dialogue text
        dialogueText = findViewById(R.id.gameText);
        //Set Game Background
        background = findViewById(R.id.backgroundGame);
        //Set the default image view.
        background.setBackgroundResource(R.drawable.worldsendtable);

        // Set text to default scenario.
        dialogueText.setText(" ");
        choice1.setText(defText[1]);
        choice2.setText(defText[2]);
        choice3.setText(defText[3]);
        choice4.setText(defText[4]);
        setText(defText[0]);
    }

    public void enableButton () {
        if (choiceEnable == 0) { // Default Four Choice
            choice1.setVisibility(View.VISIBLE);
            choice2.setVisibility(View.VISIBLE);
            choice3.setVisibility(View.VISIBLE);
            choice4.setVisibility(View.VISIBLE);
            choice1.startAnimation(animation);
            choice2.startAnimation(animation);
            choice3.startAnimation(animation);
            choice4.startAnimation(animation);
        }
        if (choiceEnable == 1) { // One Choice Only
            choice1.setVisibility(View.VISIBLE);
            choice1.startAnimation(animation);
            choice2.setVisibility(View.GONE);
            choice3.setVisibility(View.GONE);
            choice4.setVisibility(View.GONE);
        }
        if (choiceEnable == 2) { // Two Choice Only
            choice1.setVisibility(View.VISIBLE);
            choice2.setVisibility(View.VISIBLE);
            choice1.startAnimation(animation);
            choice2.startAnimation(animation);
            choice3.setVisibility(View.GONE);
            choice4.setVisibility(View.GONE);
        }
        if (choiceEnable == 3) { // Three Choice Only
            choice1.setVisibility(View.VISIBLE);
            choice2.setVisibility(View.VISIBLE);
            choice3.setVisibility(View.VISIBLE);
            choice1.startAnimation(animation);
            choice2.startAnimation(animation);
            choice3.startAnimation(animation);
            choice4.setVisibility(View.GONE);
        }
    }
    public void disableButton () {
        choice1.setVisibility(View.GONE);
        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);
        choice4.setVisibility(View.GONE);
    }
    public void onBackPressed() {

    }
    public void setText(final String s) {
        int[] i = new int[1];
        i[0] = 0;
        int length = s.length();
        Handler handler;
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                char c= s.charAt(i[0]);
                dialogueText.append(String.valueOf(c));
                i[0]++;

            }
        };
        timer = new Timer();
        disableButton();
        Handler handler2 = new Handler();
        Runnable enabledButtonRunnable = new Runnable() {
            @Override
            public void run() {
                enableButton();
            }
        };
        TimerTask taskEverySplitSecond = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
                if (i[0] == length - 1) {
                    timer.cancel();
                    timer.purge();
                }
            }
        };
        timer.schedule(taskEverySplitSecond, 1, 75);
        handler2.postDelayed(enabledButtonRunnable, s.length()*75);
    }
    public void youLose (){ // returns to splash screen on lose
        Intent loss = new Intent (this, YouLost.class);
        startActivity(loss);
        bgm.release();
    }
    @Override
    public void onClick(View view) {
        Log.d(TAG, ":newGameTurn " + gameTurn);
        Log.d(TAG, ":newWinState " + buttonNumber);
        dialogueText.setText(" ");
        timer.cancel();
        dialogueText = findViewById(R.id.gameText);
        switch (view.getId()) {
            case R.id.button1:
                if (gameTurn == 0) {
                    gameState = 1;
                }
                buttonNumber = 1;
                nextTurn(gameState, gameTurn);
                setText(game[0]);
                choice1.setText(game[1]);
                choice2.setText(game[2]);
                choice3.setText(game[3]);
                choice4.setText(game[4]);
                gameTurn++;
                break;
            case R.id.button2:
                if (gameTurn == 0) {
                    gameState = 2;
                }
                buttonNumber = 2;
                nextTurn(gameState, gameTurn);
                setText(game[5]);
                choice1.setText(game[6]);
                choice2.setText(game[7]);
                choice3.setText(game[8]);
                choice4.setText(game[9]);
                gameTurn++;
                break;
            case R.id.button3:
                if (gameTurn == 0) {
                    gameState = 3;
                }
                buttonNumber = 3;
                nextTurn(gameState, gameTurn);
                setText(game[10]);
                choice1.setText(game[11]);
                choice2.setText(game[12]);
                choice3.setText(game[13]);
                choice4.setText(game[14]);
                gameTurn++;
                break;
            case R.id.button4:
                if (gameTurn == 0) {
                    gameState = 4;
                }
                buttonNumber = 4;
                nextTurn(gameState, gameTurn);
                setText(game[15]);
                choice1.setText(game[16]);
                choice2.setText(game[17]);
                choice3.setText(game[18]);
                choice4.setText(game[19]);
                gameTurn++;
                break;
        }
        //Checks per turn
        if (gameTurn == 2) {
            //gameState checks class, winState checks which button click
            if (gameState == 1 && buttonNumber == 4) {

            }
            if (gameState == 2 && buttonNumber == 2) {
                youLose();
            }
            if (gameState == 3 && buttonNumber == 2) {
                youLose();
            }
            if (gameState == 4 && buttonNumber == 4) {
                youLose();
            }
        }
        if (gameTurn == 3) {
            if (gameState == 1 && buttonNumber == 1) {

            }
            if (gameState == 2 && buttonNumber == 2) {
                youLose();
            }
            if (gameState == 3 && buttonNumber == 3) {
                youLose();
            }
            if (gameState == 4 && buttonNumber == 4) {
                youLose();
            }
        }
        if (gameTurn == 7) {
                youLose();
        }
    }
    public void worldsEndTableBGM () {
        bgm = new MediaPlayer();
        bgm = MediaPlayer.create(this, R.raw.worldsendtablebgm);
        bgm.start();
        bgm.setLooping(true);
    }
    public void forestBGM () {
        bgm = new MediaPlayer();
        bgm = MediaPlayer.create(this, R.raw.forestbgm);
        bgm.start();
        bgm.setLooping(true);
    }
    public void nextTurn(int gameState, int gameTurn) {
        if (gameTurn == 0) { //Turn 1
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_1_1);
                    character.setImageResource(R.drawable.warrior);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_1_2);
                    character.setImageResource(R.drawable.mage);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_1_3);
                    character.setImageResource(R.drawable.archer);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_1_4);
                    character.setImageResource(R.drawable.thief);
                    break;
            }
        }
        else if (gameTurn == 1) { //Turn 2
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_2_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_2_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_2_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_2_4);
                    break;
            }
        }
        else if (gameTurn == 2) { //Turn 3
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_3_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_3_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_3_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_3_4);
                    break;
            }
        }
        else if (gameTurn == 3) { //Turn 4
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_4_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_4_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_4_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_4_4);
                    break;
            }
            choiceEnable = 0;
            bgm.release();
            forestBGM();
            background.setBackgroundResource(R.drawable.forestinside);
        }
        else if (gameTurn == 4) { //Turn 5
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_5_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_5_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_5_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_5_4);
                    break;
            }
        }
        else if (gameTurn == 5) {//Turn 6
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_5_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_5_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_5_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_5_4);
                    break;
            }
        }
        else if (gameTurn == 6) {//Turn 7
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_5_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_5_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_5_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_5_4);
                    break;
            }
        }
        else if (gameTurn == 7) {//Turn 8
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_5_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_5_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_5_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_5_4);
                    break;
            }
        }
        else if (gameTurn == 8) {//Turn 9
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_5_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_5_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_5_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_5_4);
                    break;
            }
        }
        else if (gameTurn == 9) {//Turn 10
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_5_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_5_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_5_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_5_4);
                    break;
            }
        }
        else if (gameTurn == 10) {//Turn 11
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_5_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_5_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_5_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_5_4);
                    break;
            }
        }
        else if (gameTurn == 11) {//Turn 12
            switch (gameState) {
                case 0:
                    game = getResources().getStringArray(R.array.scenario_5_1);
                    break;
                case 1:
                    game = getResources().getStringArray(R.array.scenario_5_2);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_5_3);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_5_4);
                    break;
            }
        }
        else if (gameTurn == 12) {//Turn 13
            game = getResources().getStringArray(R.array.scenario_1_1);
        }
        else if (gameTurn == 13) {//Turn 13
            game = getResources().getStringArray(R.array.scenario_1_1);
        }
        else if (gameTurn == 14) {//Turn 13
            game = getResources().getStringArray(R.array.scenario_1_1);
        }
        else if (gameTurn == 15) {//Turn 13
            game = getResources().getStringArray(R.array.scenario_1_1);
        }
        else if (gameTurn == 16) {//Turn 13
            game = getResources().getStringArray(R.array.scenario_1_1);
        }
        else if (gameTurn == 12) {//Turn 13
            game = getResources().getStringArray(R.array.scenario_1_1);
        }
        if (winCondition == 0) {
            youLose();
        }
    }
}