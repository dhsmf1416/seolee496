package com.example.q.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class newActivity extends AppCompatActivity {
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
        scoreview2[1] = findViewById(R.id.image5);
        scoreview2[2]  = findViewById(R.id.image3);
        scoreview2[3]  = findViewById(R.id.image2);
        scoreview2[4]  = findViewById(R.id.image4);
        scoreview2[5]  = findViewById(R.id.image1);


        for (int i=1 ; i < 6 ; i++){
            System.out.println("!!!!");
            String stringNumber = getIntent().getStringExtra("score"+ String.valueOf(i));
            score[i] = Integer.parseInt(stringNumber);
            System.out.println(score[i]);
            scoreview[i].setText(" " + score[i]);
        }
        // 1. 규한이형
        // 2. 나
        // 3. 호성이
        // 4. 류석영님
        // 5. 장병규님
        Btn2 = (Button) findViewById(R.id.Btn2);
        this.Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 1 ; i < 6 ; i++) {
                    scoreview2[i].setVisibility(View.INVISIBLE);
                    scoreview[i].setVisibility(View.INVISIBLE);
                }
                Btn2.setVisibility(View.INVISIBLE);
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.INVISIBLE);
                int allMoney = score[1] * 10 + score[2] * (-10) + score[3] * (-10) + score[4] * (100) + score[5] * 1000;
                String myMoney = String.valueOf(allMoney)+"억원";
                int j = getIndexOfLargest(score);
                float myScore = 1f;
                String who = "";
                if(j==1) {
                    myScore = 3.52f;
                    who = "조교님";
                }
                if(j==2) {
                    myScore = 1.54f;
                    who = "이윤택";
                }
                if(j==3) {
                    myScore = 2.27f;
                    who = "서호성";
                }
                if(j==4) {
                    myScore = 4.27f;
                    who = "류석영교수님";
                }
                if(j==5) {
                    myScore = 3.01f;
                    who = "장병규대표님";
                }
                String scoreScore = Float.toString(myScore)+ " / 4.3";
                long myPartner = Math.round(Math.random() * 100);
                scoreview[3].setText(Long.toString(myPartner) + "점");
                scoreview[4].setText(who);
                scoreview[5].setVisibility(View.INVISIBLE);
                String text = "당신의 미래 자산 : " + myMoney + "\n";
                text += "다음 학기 학점 : " + scoreScore + "\n";
                text += who +"께 질문 기회를 드립니다. 축하드립니다";
                findViewById(R.id.future1).setVisibility(View.VISIBLE);
                TextView myView = findViewById(R.id.future1);
                myView.setText(text);


            }
        });
    }
    public int getIndexOfLargest( int[] array )
    {
        if ( array == null || array.length == 0 ) return -1; // null or empty

        int largest = 1;
        for ( int i = 2; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return largest; // position of the first largest found
    }
}
