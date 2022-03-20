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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.navigation.NavigationView;

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
    int turnNumber = 1;
    int buttonNumber; //Checks which button is clicked
    int winCondition = 1;
    int route; // extra depth
    int healCD;
    MediaPlayer bgm, sfx;
    ImageView character;
    ImageButton attack, heal;


    ConstraintLayout background;
    String[] defText, game; //string arrays used to pull data
    Timer timer; //timer for text playing
    TextView txtHeroHP, txtMonsHP;

    int heroHP;
    int herodps;
    int monsterHP;
    int monsdps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();


        worldsEndTableBGM();



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

        //Start of the game only 1 button active
        choice1.setVisibility(View.VISIBLE);
        choice1.startAnimation(animation);
        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);
        choice4.setVisibility(View.GONE);


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
        timer.schedule(taskEverySplitSecond, 1, 40);
        handler2.postDelayed(enabledButtonRunnable, s.length()*42);
    }
    public void youLose (){ // returns to splash screen on lose
        bgm.release();
        Intent loss = new Intent (this, YouLost.class);
        startActivity(loss);
        bgm.release();
    }
    public void youWin (){ // goes to credits
        bgm.release();
        Intent win = new Intent (this, Credits.class);
        startActivity(win);
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
                if (gameTurn == 2) {
                    gameState = 1;
                }
                buttonNumber = 1;
                nextTurn(gameState, gameTurn);
                //Pulls choices from string array
                setText(game[0]);
                choice1.setText(game[1]);
                choice2.setText(game[2]);
                choice3.setText(game[3]);
                choice4.setText(game[4]);
                gameTurn++;
                break;
            case R.id.button2:
                if (gameTurn == 2) {
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
                if (gameTurn == 2) {
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
                if (gameTurn == 2) {
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
    }
    //BGMs
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
    public void crimsonGroveBGM () {
        bgm = new MediaPlayer();
        bgm = MediaPlayer.create(this, R.raw.crimsongrovebgm);
        bgm.start();
        bgm.setLooping(true);
    }
    public void bansheeBGM () {
        bgm = new MediaPlayer();
        bgm = MediaPlayer.create(this, R.raw.bansheebgm);
        bgm.start();
        bgm.setLooping(true);
    }
    public void necromancerTowerBGM () {
        bgm = new MediaPlayer();
        bgm = MediaPlayer.create(this, R.raw.necromancertowerbgm);
        bgm.start();
        bgm.setLooping(true);
    }
    public void youWinBGM () {
        bgm = new MediaPlayer();
        bgm = MediaPlayer.create(this, R.raw.youwinbgm);
        bgm.start();
        bgm.setLooping(true);
    }
    //SFXs
    public void bansheeScreechSFX() {
        sfx = new MediaPlayer();
        sfx = MediaPlayer.create(this, R.raw.bansheescream);
        sfx.start();
        sfx.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                sfx.release();
            }
        });
    }
    public void slashSFX() {
        sfx = new MediaPlayer();
        sfx = MediaPlayer.create(this, R.raw.slashsfx);
        sfx.start();
        sfx.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                sfx.release();
            }
        });
    }
    public void nextTurn(int gameState, int gameTurn) {
        if (gameTurn == 0) { //Turn 1
           game = getResources().getStringArray(R.array.dialogue1);
           choiceEnable = 0;
        }
        else if (gameTurn == 1) { //Turn 2
            game = getResources().getStringArray(R.array.dialogue2);
        }
        else if (gameTurn == 2) { //Turn 3
            game = getResources().getStringArray(R.array.dialogue3);
        }
        else if (gameTurn == 3) { //Turn 4
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_1_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_1_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_1_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_1_4);
                    break;
            }
            bgm.release();
            forestBGM();
            background.setBackgroundResource(R.drawable.darkforest);
        }
        else if (gameTurn == 4) { //Turn 5
            game = getResources().getStringArray(R.array.dialogue4);
            switch (buttonNumber) { // if button 3 click, lose
                case 1:
                case 2:
                case 4:
                    break;
                case 3:
                    youLose();
                    break;
            }
        }
        else if (gameTurn == 5) {//Turn 6
            game = getResources().getStringArray(R.array.dialogue5);
            switch (buttonNumber) { // if button 2,3,4 click, lose
                case 1:
                    break;
                case 2:
                case 3:
                case 4:
                    youLose();
                    break;
            }
            bgm.release();
            crimsonGroveBGM();
            background.setBackgroundResource(R.drawable.bloodgrove);
        }
        else if (gameTurn == 6) {//Turn 7
            game = getResources().getStringArray(R.array.dialogue6);
            switch (buttonNumber) { // if button 3,4 click, lose
                case 1:
                case 2:
                    break;
                case 3:
                case 4:
                    youLose();
                    break;
            }
        }
        else if (gameTurn == 7) {//Turn 8
            switch (buttonNumber) { // if button 1,4 click, lose
                case 1:
                case 4:
                    youLose();
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.leftRoute);
                    route = 0;
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.rightRoute);
                    route = 1;
                    break;
            }
        }
        else if (gameTurn == 8) {//Turn 9
            switch (route) { // determine which route, left or right
                case 0:
                    switch (buttonNumber) { // left route
                        case 1:
                            break;
                        case 2:
                        case 3:
                        case 4:
                            youLose();
                            break;
                    }
                    route = 0;
                    break;
                case 1:
                    switch (buttonNumber) { // right route
                        case 1:
                        case 2:
                        case 3:
                            youLose();
                            break;
                        case 4:
                            break;

                    }
                    route = 0;
                    break;

            }
            game = getResources().getStringArray(R.array.dialogue7);
            bansheeScreechSFX();
        }
        else if (gameTurn == 9) {//Turn 10
            switch (buttonNumber) {
                case 1:
                    game = getResources().getStringArray(R.array.dialogue8);
                    break;
                case 2:
                case 3:
                case 4:
                   youLose();
                   break;
            }
        }
        else if (gameTurn == 10) {//Turn 11
            switch (gameState) {
                case 1:
                    heroHP = 1000;
                    herodps = 100;
                    break;
                case 2:
                    heroHP = 500;
                    herodps = 200;
                    break;
                case 3:
                    heroHP = 750;
                    herodps = 125;
                    break;
                case 4:
                    heroHP = 350;
                    herodps = 250;
                    break;
            }
            monsterHP = 2000;
            monsdps = 50;
            bgm.release();
            bansheeBGM();
            setContentView(R.layout.activity_banshee_fight);
            character = findViewById(R.id.character);
            switch (gameState) {
                case 1:
                    character.setImageResource(R.drawable.warrior);
                    break;
                case 2:
                    character.setImageResource(R.drawable.mage);
                    break;
                case 3:
                    character.setImageResource(R.drawable.archer);
                    break;
                case 4:
                    character.setImageResource(R.drawable.thief);
                    break;
            }
            //txt view for hp
            txtHeroHP = findViewById(R.id.heroHp);
            txtMonsHP = findViewById(R.id.bansheeHp);
            //setting value hp
            txtHeroHP.setText(String.valueOf(heroHP));
            txtMonsHP.setText(String.valueOf(monsterHP));

            attack = findViewById(R.id.btnAttack);
            heal = findViewById(R.id.btnHeal);


        }
        else if (gameTurn == 11) {//Turn 12
            game = getResources().getStringArray(R.array.dialogue10);
            sfx.release();
            background.setBackgroundResource(R.drawable.bloodgrove);
        }
        else if (gameTurn == 12) {//Turn 13
            game = getResources().getStringArray(R.array.dialogue11);
            bgm.release();
            sfx.release();
            background.setBackgroundResource(R.drawable.darkbridge);
            necromancerTowerBGM();
        }
        else if (gameTurn == 13) {//Turn 13
            switch (buttonNumber) {
                case 1:
                case 2:
                case 3:
                    youLose();
                    break;
                case 4:
                    break;
            }
            background.setBackgroundResource(R.drawable.necromancertowermid);
            game = getResources().getStringArray(R.array.dialogue12);
        }
        else if (gameTurn == 14) {//Turn 13
            switch (buttonNumber) {
                case 1:
                    break;
                case 2:
                case 3:
                case 4:
                    youLose();
                    break;
            }
            background.setBackgroundResource(R.drawable.necromancerentrance);
            game = getResources().getStringArray(R.array.dialogue13);
        }
        else if (gameTurn == 15) {//Turn 13
            background.setBackgroundResource(R.drawable.necromancertowerthrone);
            game = getResources().getStringArray(R.array.dialogue14);
        }
        else if (gameTurn == 16) {//Turn 13
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
        else if (gameTurn == 17) {//Turn 13
            switch (buttonNumber) {
                case 1:
                case 4:
                case 2:
                    youLose();
                    break;
                case 3:
                    break;
            }
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
        else if (gameTurn == 18) {//Turn 13
            switch (buttonNumber) {
                case 1:
                case 4:
                case 2:
                    youLose();
                    break;
                case 3:
                    break;
            }
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
        }
        else if (gameTurn == 19) {//Turn 13
            switch (buttonNumber) {
                case 1:
                case 4:
                case 3:
                    youLose();
                    break;
                case 2:
                    break;
            }
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
        else if (gameTurn == 20) {//Turn 13
            switch (buttonNumber) {
                case 4:
                case 2:
                case 3:
                    youLose();
                    break;
                case 1:
                    break;
            }
            switch (gameState) {
                case 1:
                    game = getResources().getStringArray(R.array.scenario_6_1);
                    break;
                case 2:
                    game = getResources().getStringArray(R.array.scenario_6_2);
                    break;
                case 3:
                    game = getResources().getStringArray(R.array.scenario_6_3);
                    break;
                case 4:
                    game = getResources().getStringArray(R.array.scenario_6_4);
                    break;
            }
        }
        else if (gameTurn == 21) {//Turn 13
            game = getResources().getStringArray(R.array.dialogue15);
        }
        else if (gameTurn == 22) {//Turn 13
            sfx.release();
            bgm.release();
            youWinBGM();
            background.setBackgroundResource(R.drawable.youwinbg);
            game = getResources().getStringArray(R.array.dialogue16);
        }
        else if (gameTurn == 23) {//Turn 13
            youWin();
        }
        if (winCondition == 0) {
            youLose();
        }
    }

    public void attackCombat() {
        slashSFX();
        if(turnNumber % 2 == 1) { //oddturnNumber % 2 == 1
            {
                monsterHP -= herodps;
            }

            txtMonsHP.setText(String.valueOf(monsterHP));
        }
        else if(turnNumber %2 != 1){ //even
            heroHP -= monsdps;
        }
        if (monsterHP == 0 || monsterHP < 0) {
            bgm.release();
            setContentView(R.layout.activity_game_screen);
            crimsonGroveBGM();
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
            dialogueText.setText(" ");
            //Set Game Background
            background = findViewById(R.id.backgroundGame);
            //Set the default image view.
            background.setBackgroundResource(R.drawable.bloodgrove);
            game = getResources().getStringArray(R.array.dialogue9);
            gameTurn = 11;
            setText(game[10]);
            choice1.setText(game[11]);
            choice2.setText(game[12]);
            choice3.setText(game[13]);
            choice4.setText(game[14]);
            gameTurn = 11;
        }
        txtHeroHP.setText(String.valueOf(heroHP));
        turnNumber++;
    }

    public void attackButton(View view) {
        attackCombat();
        healCD--;
        if (healCD < -1) {
            healCD = 0;
        }
    }
    public void healButton(View view) {
        if(turnNumber % 2 == 1 && healCD < 0) { //oddturnNumber % 2 == 1
            healCD = 3;
            heroHP += 250;
            txtHeroHP.setText(String.valueOf(heroHP));
            turnNumber++;
            Toast.makeText(this, "Your HP increased by 250!\n Heal is on a three turn cooldown.", Toast.LENGTH_SHORT).show();
        }
        else if(turnNumber %2 != 1) { //even
            Toast.makeText(this, "Not your turn!", Toast.LENGTH_SHORT).show();
        }
        else if(healCD > 0) { //even
            Toast.makeText(this, "Heal on cooldown!", Toast.LENGTH_SHORT).show();
        }
    }

}