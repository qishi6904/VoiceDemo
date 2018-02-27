package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by qinshi on 5/17/2017.
 */

public class DragFrontLinearLayout extends LinearLayout {

    private OnInterceptListener interceptListener;
    private boolean isInterceptTouchEvent;

    public DragFrontLinearLayout(Context context) {
        super(context);
    }

    public DragFrontLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if(isInterceptTouchEvent){
            if(null != interceptListener){
                interceptListener.onIntercept();
            }
            requestDisallowInterceptTouchEvent(true);
            return true;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isInterceptTouchEvent){
            return true;
        }
        return super.onTouchEvent(event);
    }

    public interface OnInterceptListener{
        void onIntercept();
    }

    public void setOnInterceptListener(OnInterceptListener listener){
        this.interceptListener = listener;
    }

    public void setIsInterceptTouchEvent(boolean isInterceptTouchEvent){
        this.isInterceptTouchEvent = isInterceptTouchEvent;
    }
}
