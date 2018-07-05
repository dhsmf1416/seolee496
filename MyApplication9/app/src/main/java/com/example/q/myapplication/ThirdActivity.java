package com.example.q.myapplication;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ThirdActivity extends Fragment {
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
    final Handler textHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Timerwindow.setText(" "+ msg.arg1 + " ");
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.third_main,container,false);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Moles = new ArrayList<>();
        isImageOn = new ArrayList<>();
        Timerwindow = (TextView) getView().findViewById(R.id.textView);
        for(int i=0;i<15;i++)
            isImageOn.add(0);
        ImageView imageView1 = (ImageView) getView().findViewById(R.id.imageView1);
        Moles.add(imageView1);
        ImageView imageView2 = (ImageView) getView().findViewById(R.id.imageView2);
        Moles.add(imageView2);
        ImageView imageView3 = (ImageView) getView().findViewById(R.id.imageView3);
        Moles.add(imageView3);
        ImageView imageView4 = (ImageView) getView().findViewById(R.id.imageView4);
        Moles.add(imageView4);
        ImageView imageView5 = (ImageView) getView().findViewById(R.id.imageView5);
        Moles.add(imageView5);
        ImageView imageView6 = (ImageView) getView().findViewById(R.id.imageView6);
        Moles.add(imageView6);
        ImageView imageView7 = (ImageView) getView().findViewById(R.id.imageView7);
        Moles.add(imageView7);
        ImageView imageView8 = (ImageView) getView().findViewById(R.id.imageView8);
        Moles.add(imageView8);
        ImageView imageView9 = (ImageView) getView().findViewById(R.id.imageView9);
        Moles.add(imageView9);
        ImageView imageView10 = (ImageView) getView().findViewById(R.id.imageView10);
        Moles.add(imageView10);
        ImageView imageView11 = (ImageView) getView().findViewById(R.id.imageView11);
        Moles.add(imageView11);
        ImageView imageView12 = (ImageView) getView().findViewById(R.id.imageView12);
        Moles.add(imageView12);
        ImageView imageView13 = (ImageView) getView().findViewById(R.id.imageView13);
        Moles.add(imageView13);
        ImageView imageView14 = (ImageView) getView().findViewById(R.id.imageView14);
        Moles.add(imageView14);
        ImageView imageView15 = (ImageView) getView().findViewById(R.id.imageView15);
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
        Btn = (Button) getView().findViewById(R.id.Btn);
        textView2 = (TextView) getView().findViewById(R.id.future1);
        textView3 = (TextView) getView().findViewById(R.id.textView3);
        this.Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer = new Timer();
                timeRemained(5);
                timer.schedule(new TimerTask() {
                    public void run() {
                        Intent intent = new Intent(getActivity(), newActivity.class);
                        intent.putExtra("score1",String.valueOf(score[1]));
                        intent.putExtra("score2",String.valueOf(score[2]));
                        intent.putExtra("score3",String.valueOf(score[3]));
                        intent.putExtra("score4",String.valueOf(score[4]));
                        intent.putExtra("score5",String.valueOf(score[5]));
                        startActivity(intent);

                    }
                },20000);
                textView3.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                Btn.setVisibility(View.INVISIBLE);
            }
        });

    }


    public void timeRemained (final int time) {
        // time seconds 를 띄움

        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                int time2 =time;
                Message msgText = new Message();
                msgText.arg1 = time2;
                textHandler.sendMessage(msgText);
                if (time2 == 0)
                {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                    periodfunc();
                    return;
                }
                timeRemained(time2-1);

            }
        },1000);

    }
    public int getRandomIndex()
    {
        double randomNumber = Math.random();
        int x = 0;
        if(randomNumber <= 0.1)
        {
            x = 1;
        }
        if(0.1 < randomNumber && randomNumber <= 0.2)
        {
            x = 2;
        }
        if(0.2 < randomNumber && randomNumber <= 0.45)
        {
            x = 3;
        }
        if(0.45 < randomNumber && randomNumber <= 0.7)
        {
            x = 4;
        }
        if(0.7 < randomNumber && randomNumber <= 1.0)
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
