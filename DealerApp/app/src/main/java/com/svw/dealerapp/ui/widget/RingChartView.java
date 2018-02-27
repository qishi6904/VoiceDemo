package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.svw.dealerapp.R;

/**
 * Created by qinshi on 8/25/2017.
 */

public class RingChartView extends View {

    private boolean canDraw = false;
    private int startColor = Color.RED;
    private int endColor = Color.BLACK;
    private int textColor = Color.BLACK;
    private float per = 0.36f;
    private Paint paint;
    private RectF rectF;
    private int lineWidth = 4;
    private int indicatorWidth = 10;
    private int textSize = 20;

    public RingChartView(Context context) {
        super(context);
        paint = new Paint();
        rectF = new RectF();
    }

    public RingChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ringChartView);
        startColor = typedArray.getColor(R.styleable.ringChartView_start_color, Color.RED);
        endColor = typedArray.getColor(R.styleable.ringChartView_end_color, Color.BLACK);
        textColor = typedArray.getColor(R.styleable.ringChartView_center_text_color, Color.BLACK);
        lineWidth = typedArray.getDimensionPixelOffset(R.styleable.ringChartView_line_width, 4);
        indicatorWidth = typedArray.getDimensionPixelOffset(R.styleable.ringChartView_indicator_width, 14);
        textSize = typedArray.getDimensionPixelOffset(R.styleable.ringChartView_center_text_size, 20);
        typedArray.recycle();
        paint = new Paint();
        rectF = new RectF();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(!canDraw){
            return;
        }

        paint.setAntiAlias(true);

        int width = getWidth();
        int height = getHeight();
        float radius = Math.min(width, height) * 0.4f;
        String text = String.valueOf(Math.round(per * 100)) + "%";
        int[] colors = new int[]{startColor, endColor, startColor};
        float[] positions = new float[]{0f, 0.5f, 1f};
        float cx = width * 0.5f;
        float cy = height * 0.5f;
        SweepGradient sweepGradient = new SweepGradient(cx, cy, colors, positions);

        rectF.left = cx - radius;
        rectF.right = cx + radius;
        rectF.top = cy - radius;
        rectF.bottom = cy + radius;

        paint.setShader(sweepGradient);
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);

        canvas.rotate(-90, cx, cy);

        canvas.drawArc(rectF, 270, 360, false, paint);

        float halfIndicatorWidth = indicatorWidth * 0.5f;
        rectF.left = cx - radius + halfIndicatorWidth;
        rectF.right = cx + radius - halfIndicatorWidth;
        rectF.top = cy - radius + halfIndicatorWidth;
        rectF.bottom = cy + radius - halfIndicatorWidth;

        paint.setStrokeWidth(indicatorWidth);

        canvas.drawArc(rectF, 0, per * 360, false, paint);

        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(null);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.rotate(90, cx, cy);

        canvas.drawText(text, cx, cy + (textSize - paint.descent()) / 2f, paint);

    }

    public void setData(float data){
        per = data;
        canDraw = true;
        this.invalidate();
    }
}
