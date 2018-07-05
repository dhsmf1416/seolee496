package com.example.q.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class TouchEventView extends android.support.v7.widget.AppCompatImageView {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    public GalleryActivity upper;
    public TouchEventView(Context context)
    {
        super(context);
    }

    public TouchEventView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }



    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = motionEvent.getX();
                    return true;
                case MotionEvent.ACTION_UP:
                    x2 = motionEvent.getX();
                    float deltaX = x2 - x1;
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        if(x2 < x1)
                            upper.changeExpanded(true);
                        else
                            upper.changeExpanded(false);



                    } else {
                        upper.closeWindow();
                    }

                default:
                    break;

            }
            return false;
        }
    }
}