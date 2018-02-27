package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.svw.dealerapp.util.DensityUtil;

/**
 * Created by qinshi on 5/2/2017.
 */

public class DragLayout extends LinearLayout {

    private View frontView;
    private View backView;

    private ViewDragHelper dragHelper;
    private int width;
    private int range;
    private float diffX;
    private float lastMoveX;
    private float downX;
    private int paddingLeft;
    private int delayX;
    private OnTouchDragListener onDragListener;
    private OnStatusChangeListener statusChangeListener;
    private boolean isOpen = false;
    private boolean isMove = false;
    private boolean isClose = true;

    public DragLayout(Context context) {
        super(context);
        init(context);
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        dragHelper = ViewDragHelper.create(this, new DragHelperCallBack());
        paddingLeft = this.getPaddingLeft();
        delayX = DensityUtil.dp2px(context, 6);
        this.setOrientation(HORIZONTAL);
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == frontView) {
                if (left > paddingLeft) {
                    return paddingLeft;
                } else if (left < -range + paddingLeft) {
                    return -range + paddingLeft;
                }
            } else if (child == backView) {
                if (left > width) {
                    return width;
                } else if (left < width - range) {
                    return width - range;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == frontView) {
                backView.offsetLeftAndRight(dx);
            } else if (changedView == backView) {
                frontView.offsetLeftAndRight(dx);
            }
            invalidate();
        }


        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount < 2) {
            throw new IllegalStateException("you need 2 children mView");
        }
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException("your children must be instance of ViewGroup");
        }

        frontView = getChildAt(0);
        backView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = frontView.getMeasuredWidth();
        range = backView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (frontView.getLeft() != 0 && frontView.getLeft() == range) {
            this.requestDisallowInterceptTouchEvent(true);
        }
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (frontView.getLeft() != paddingLeft) {
            this.requestDisallowInterceptTouchEvent(true);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastMoveX = event.getX();
                downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (diffX < 0) {
                    dragHelper.smoothSlideViewTo(frontView, -range + paddingLeft, 0);
                    ViewCompat.postInvalidateOnAnimation(this);
                } else {
                    dragHelper.smoothSlideViewTo(frontView, paddingLeft, 0);
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                diffX = 0;
                downX = 0;
                return true;
            case MotionEvent.ACTION_MOVE:
                if(downX == 0){
                    downX = event.getX();
                    return true;
                }
                float moveX = event.getX();
                if(downX - moveX > 0 && downX - moveX < delayX){
                    return true;
                }
                diffX = moveX - lastMoveX;
                lastMoveX = moveX;
                break;

        }
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if(null != onDragListener){
            onDragListener.onDrag(range, Math.abs(frontView.getLeft()));
        }
        if(!isOpen && range != 0 && frontView.getLeft() == -range + paddingLeft){
            isClose = false;
            isMove = false;
            isOpen = true;
            if(null != statusChangeListener){
                statusChangeListener.onOpen();
            }
        }else if(!isClose && frontView.getLeft() == paddingLeft) {
            isOpen = false;
            isMove = false;
            isClose = true;
            if(null != statusChangeListener){
                statusChangeListener.onClose();
            }
        }else if(!isMove && frontView.getLeft() != -range + paddingLeft && frontView.getLeft() != paddingLeft){
            isOpen = false;
            isClose = false;
            isMove = true;
            if(null != statusChangeListener) {
                statusChangeListener.isMoveStatus(isMove);
            }
        }
    }

    public void setRange(int range){
        this.range = range;
    }

    public interface OnTouchDragListener {
        void onDrag(int rangeDistance, int offset);
    }

    public void setOnTouchDragListener(OnTouchDragListener onDragListener){
        this.onDragListener = onDragListener;
    }

    /**
     * 状态变化回调接口
     */
    public interface OnStatusChangeListener{
        void onOpen();
        void onClose();
        void isMoveStatus(boolean isMoveStatus);
    }

    /**
     * 设置状态变化回调接口
     * @param listener
     */
    public void setOnStatusChangeListener(OnStatusChangeListener listener){
        this.statusChangeListener = listener;
    }

    public void close(){
        dragHelper.smoothSlideViewTo(frontView, paddingLeft, 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void closeDelay(int delay){
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                close();
            }
        }, delay);
    }
}
