package com.example.q.molegame;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    ArrayList<ImageView> Moles;
    private Button Btn;
    private TextView textView2;
    private TextView textView3;
    Timer timer;
    private TextView Timerwindow;
    ArrayList<Integer> isImageOn;
    int score[] = new int[6];


final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            for(int x = 0; x<=14 ; x++){
                Moles.get(x).setVisibility(View.VISIBLE);
            }
            Timerwindow.setVisibility(View.INVISIBLE);
        }
    };
    final Handler imageHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            ImageView currentView = (ImageView)msg.obj;
            if(msg.arg1 == 0)
                currentView.setImageResource(android.R.color.white); // image change
            if(msg.arg1 == 1)
                currentView.setImageResource(R.drawable.leepng); // image change
            if(msg.arg1 == 2)
                currentView.setImageResource(R.drawable.leey); // image change
            if(msg.arg1 == 3)
                currentView.setImageResource(R.drawable.seopng); // image change
            if(msg.arg1 == 4)
                currentView.setImageResource(R.drawable.ryupng); // image change
            if(msg.arg1 == 5)
                currentView.setImageResource(R.drawable.changpng); // image change

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Moles = new ArrayList<>();
        isImageOn = new ArrayList<>();
        for(int i=0;i<15;i++)
            isImageOn.add(0);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        Moles.add(imageView1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        Moles.add(imageView2);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
        Moles.add(imageView3);
        ImageView imageView4 = (ImageView) findViewById(R.id.imageView4);
        Moles.add(imageView4);
        ImageView imageView5 = (ImageView) findViewById(R.id.imageView5);
        Moles.add(imageView5);
        ImageView imageView6 = (ImageView) findViewById(R.id.imageView6);
        Moles.add(imageView6);
        ImageView imageView7 = (ImageView) findViewById(R.id.imageView7);
        Moles.add(imageView7);
        ImageView imageView8 = (ImageView) findViewById(R.id.imageView8);
        Moles.add(imageView8);
        ImageView imageView9 = (ImageView) findViewById(R.id.imageView9);
        Moles.add(imageView9);
        ImageView imageView10 = (ImageView) findViewById(R.id.imageView10);
        Moles.add(imageView10);
        ImageView imageView11 = (ImageView) findViewById(R.id.imageView11);
        Moles.add(imageView11);
        ImageView imageView12 = (ImageView) findViewById(R.id.imageView12);
        Moles.add(imageView12);
        ImageView imageView13 = (ImageView) findViewById(R.id.imageView13);
         Moles.add(imageView13);
        ImageView imageView14 = (ImageView) findViewById(R.id.imageView14);
        Moles.add(imageView14);
        ImageView imageView15 = (ImageView) findViewById(R.id.imageView15);
        Moles.add(imageView15);

        for(int i = 0;i<6;i++){
            score[i]=0;
        }

        for(int i=0;i<15;i++) {
            final int j = i;
            Moles.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(isImageOn.get(j));
                    if (isImageOn.get(j) == 0)
                        return;

                    int x = isImageOn.get(j);
                    score[x]++;
                    Message msg = new Message();
                    msg.arg1 = 0;
                    msg.obj = Moles.get(j);
                    imageHandler.sendMessage(msg);
                    isImageOn.set(j,0);
                }
            });
        }
        Btn = (Button) findViewById(R.id.Btn);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        this.Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer = new Timer();
                timeremained(5);
                timer.schedule(new TimerTask() {
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, NewActivity.class);
                        intent.putExtra("score1",String.valueOf(score[1]));
                        intent.putExtra("score2",String.valueOf(score[2]));
                        intent.putExtra("score3",String.valueOf(score[3]));
                        intent.putExtra("score4",String.valueOf(score[4]));
                        intent.putExtra("score5",String.valueOf(score[5]));
                        startActivity(intent);

                        finish();
                    }
                },20000);
                textView3.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                Btn.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void timeremained (final int time) {
        // time seconds 를 띄움
        this.Timerwindow = (TextView) findViewById(R.id.textView);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                int time2 =time;
                Timerwindow.setText(" "+ time2 + " ");
                if (time2 == 0)
                {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                    periodfunc();
                    return;
                }
                timeremained(time2-1);

            }
        },1000);

    }
    public int getRandomIndex()
    {
        double randomNumber = Math.random();
        int x = 0;
        if(randomNumber <= 0.2)
        {
            x = 1;
        }
        if(0.2 < randomNumber && randomNumber <= 0.4)
        {
            x = 2;
        }
        if(0.4 < randomNumber && randomNumber <= 0.6)
        {
            x = 3;
        }
        if(0.6 < randomNumber && randomNumber <= 0.8)
        {
            x = 4;
        }
        if(0.8 < randomNumber && randomNumber <= 1.0)
        {
            x = 5;
        }
        return x;
    }
    public void periodfunc()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               for(int i=0;i<=14;i++)
                               {
                                   if(Math.random() <= 0.02 && isImageOn.get(i) == 0)
                                   {
                                       ImageView currentView = Moles.get(i);
                                       Message msg = new Message();
                                       msg.obj = currentView;
                                       int a = getRandomIndex();
                                       msg.arg1 = a;
                                       imageHandler.sendMessage(msg);
                                       isImageOn.set(i,a); // 이미지에맞는 숫자로 맞춰준다.
                                       final int j = i;
                                       final ImageView originalView = currentView;
                                       Timer newTimer = new Timer();
                                       // 클릭하면 없어지고, 점수 추가
                                       newTimer.schedule(new TimerTask() {
                                           @Override
                                           public void run() {
                                               //
                                               if(isImageOn.get(j) == 0)
                                                   return;
                                               isImageOn.set(j,0);
                                               Message msg = new Message();
                                               msg.obj = originalView;
                                               msg.arg1 = 0;
                                               imageHandler.sendMessage(msg);
                                           }
                                       },2000);
                                   }
                               }
                               periodfunc();
                           }
                       },100);
    }












}
