package com.example.group2_decisionbasedgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button startGame, credits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        // button when clicked goes to GameScreen activity
        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),GameScreen.class));
            }
        });
        // button when clicked goes to credits
        credits = (Button) findViewById(R.id.creditsRoll);
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Credits.class));
            }
        });
        //This is to make the startNigga text color rainbow
        TextView textview = findViewById(R.id.startGame);
        setTextView(textview,
                ResourcesCompat.getColor(getResources(), R.color.rainbow1, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow2, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow3, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow4, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow5, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow6, null)
        );
        //This is to make the creditsNigga text color rainbow
        TextView textview2 = findViewById(R.id.creditsRoll);
        setTextView(textview2,
                ResourcesCompat.getColor(getResources(), R.color.rainbow3, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow4, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow5, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow6, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow1, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow2, null)
        );
    }
    //This will make the display of the text into the given text view code
    public void setTextView(TextView textView, int... color){
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());

        Shader shader = new LinearGradient(0, 0, width, textView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(shader);
        textView.setTextColor(color[0]);
    }

}