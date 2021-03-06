package com.example.q.myapplication;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    GalleryActivity gallery;
    public CustomViewPager(Context context) {
        super(context);
    }
    public CustomViewPager(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0)
    {
        return gallery.swipeok() ? super.onInterceptTouchEvent(arg0) : false;
    }
}
