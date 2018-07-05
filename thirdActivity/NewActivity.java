package com.example.q.molegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class NewActivity extends AppCompatActivity {
    int[] score = new int[6];
    TextView[] scoreview = new TextView[6];
    ImageView[] scoreview2 = new ImageView[6];
    TextView text1;
    TextView text2;
    private Button Btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        scoreview[1] = findViewById(R.id.score1);
        scoreview[2]  = findViewById(R.id.score2);
        scoreview[3]  = findViewById(R.id.score3);
        scoreview[4]  = findViewById(R.id.score4);
        scoreview[5]  = findViewById(R.id.score5);
        scoreview2[1] = findViewById(R.id.image1);
        scoreview2[2]  = findViewById(R.id.image2);
        scoreview2[3]  = findViewById(R.id.image3);
        scoreview2[4]  = findViewById(R.id.image4);
        scoreview2[5]  = findViewById(R.id.image5);


        for (int i=1 ; i < 6 ; i++){
            System.out.println("!!!!");
            String stringNumber = getIntent().getStringExtra("score"+ String.valueOf(i));
            score[i] = Integer.parseInt(stringNumber);
            System.out.println(score[i]);
            scoreview[i].setText(" " + score[i]);
        }

        Btn2 = (Button) findViewById(R.id.Btn2);
        this.Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 1 ; i < 6 ; i++) {
                    scoreview[i].setVisibility(View.INVISIBLE);
                    scoreview2[i].setVisibility(View.INVISIBLE);
                }
                Btn2.setVisibility(View.INVISIBLE);
            }
        });
    }
}
