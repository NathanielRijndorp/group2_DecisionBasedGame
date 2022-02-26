package com.example.group2_decisionbasedgame;


import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;

public class GameScreen extends AppCompatActivity implements View.OnClickListener {

    Button choice1, choice2, choice3, choice4;
    TextView dialogueText;
    int gameState;
    int gameTurn;

    String[] defText, game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_screen);
        getSupportActionBar().hide();


        defText = getResources().getStringArray(R.array.default_text);
        game = getResources().getStringArray(R.array.default_text);

        choice1 = findViewById(R.id.button1);
        choice2 = findViewById(R.id.button2);
        choice3 = findViewById(R.id.button3);
        choice4 = findViewById(R.id.button4);

        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
        dialogueText = findViewById(R.id.gameText);


        dialogueText.setText(defText[0]);
        choice1.setText(defText[1]);
        choice2.setText(defText[2]);
        choice3.setText(defText[3]);
        choice4.setText(defText[4]);

    }
    public void game() {
        if (gameTurn == 0) {
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 1) {
            game = getResources().getStringArray(R.array.scenario_2);
        } else if (gameTurn == 2) {
            game = getResources().getStringArray(R.array.scenario_3);
        } else if (gameTurn == 3) {
            game = getResources().getStringArray(R.array.scenario_4);
        } else if (gameTurn == 4) {
            game = getResources().getStringArray(R.array.scenario_5);
        } else if (gameTurn == 5) {
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 6) {
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 7) {
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 8) {
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 9) {
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 10) {
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 11) {
            game = getResources().getStringArray(R.array.scenario_1);
        } else if (gameTurn == 12) {
            game = getResources().getStringArray(R.array.scenario_1);
        }
    }
    @Override
    public void onClick(View view) {
        game();
        dialogueText = findViewById(R.id.gameText);
        switch (view.getId()) {
            case R.id.button1:
                dialogueText.setText(game[0]);
                choice1.setText(game[1]);
                choice2.setText(game[2]);
                choice3.setText(game[3]);
                choice4.setText(game[4]);
                gameState = 1;
                gameTurn++;
                break;
            case R.id.button2:
                dialogueText.setText(game[5]);
                choice1.setText(game[6]);
                choice2.setText(game[7]);
                choice3.setText(game[8]);
                choice4.setText(game[9]);
                gameState = 2;
                gameTurn++;
                break;
            case R.id.button3:
                dialogueText.setText(game[10]);
                choice1.setText(game[11]);
                choice2.setText(game[12]);
                choice3.setText(game[13]);
                choice4.setText(game[14]);
                gameState = 3;
                gameTurn++;
                break;
            case R.id.button4:
                dialogueText.setText(game[15]);
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