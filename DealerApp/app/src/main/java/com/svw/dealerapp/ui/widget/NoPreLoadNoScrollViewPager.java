package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by qinshi on 6/20/2017.
 */

public class NoPreLoadNoScrollViewPager extends NoPreloadViewPager {
    public NoPreLoadNoScrollViewPager(Context context) {
        super(context);
    }

    public NoPreLoadNoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
