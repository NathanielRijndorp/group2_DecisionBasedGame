package com.example.group2_decisionbasedgame;


import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity implements View.OnClickListener {

    Button choice1, choice2, choice3, choice4;
    TextView dialogueText;
    int gameState;
    int gameTurn;
    ConstraintLayout background;
    String[] defText, game;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();

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
        choice1.setVisibility(View.VISIBLE);
        choice2.setVisibility(View.VISIBLE);
        choice3.setVisibility(View.VISIBLE);
        choice4.setVisibility(View.VISIBLE);
    }
    public void disableButton () {
        choice1.setVisibility(View.GONE);
        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);
        choice4.setVisibility(View.GONE);

    }
    public void setText(final String s)
    {
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
    public void game() {
        if (gameTurn == 0) { //Turn 1
            game = getResources().getStringArray(R.array.dialogue1);
            dialogueText.setText(" ");
        } else if (gameTurn == 1) { //Turn 2
            game = getResources().getStringArray(R.array.scenario_1);
            dialogueText.setText(" ");
        } else if (gameTurn == 2) { //Turn 3
            game = getResources().getStringArray(R.array.scenario_3);
            dialogueText.setText(" ");
        } else if (gameTurn == 3) { //Turn 4
            game = getResources().getStringArray(R.array.scenario_4);
            background.setBackgroundResource(R.drawable.forestinside);
            dialogueText.setText(" ");
        } else if (gameTurn == 4) { //Turn 5
            game = getResources().getStringArray(R.array.scenario_5);
            dialogueText.setText(" ");
        } else if (gameTurn == 5) {//Turn 6
            game = getResources().getStringArray(R.array.scenario_1);
            dialogueText.setText(" ");
        } else if (gameTurn == 6) {//Turn 7
            game = getResources().getStringArray(R.array.scenario_1);
            dialogueText.setText(" ");
        } else if (gameTurn == 7) {//Turn 8
            game = getResources().getStringArray(R.array.scenario_1);
            dialogueText.setText(" ");
        } else if (gameTurn == 8) {//Turn 9
            game = getResources().getStringArray(R.array.scenario_1);
            dialogueText.setText(" ");
        } else if (gameTurn == 9) {//Turn 10
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 10) {//Turn 11
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 11) {//Turn 12
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 12) {//Turn 13
            game = getResources().getStringArray(R.array.scenario_1);
        }
    }
    @Override
    public void onClick(View view) {
        timer.cancel();
        game();
        dialogueText = findViewById(R.id.gameText);
        switch (view.getId()) {
            case R.id.button1:
                setText(game[0]);
                choice1.setText(game[1]);
                choice2.setText(game[2]);
                choice3.setText(game[3]);
                choice4.setText(game[4]);
                gameState = 1;
                gameTurn++;
                break;
            case R.id.button2:
                setText(game[5]);
                choice1.setText(game[6]);
                choice2.setText(game[7]);
                choice3.setText(game[8]);
                choice4.setText(game[9]);
                gameState = 2;
                gameTurn++;
                break;
            case R.id.button3:
                setText(game[10]);
                choice1.setText(game[11]);
                choice2.setText(game[12]);
                choice3.setText(game[13]);
                choice4.setText(game[14]);
                gameState = 3;
                gameTurn++;
                break;
            case R.id.button4:
                setText(game[15]);
                choice1.setText(game[16]);
                choice2.setText(game[17]);
                choice3.setText(game[18]);
                choice4.setText(game[19]);
                gameState = 4;
                gameTurn++;
                break;
        }
    }
}