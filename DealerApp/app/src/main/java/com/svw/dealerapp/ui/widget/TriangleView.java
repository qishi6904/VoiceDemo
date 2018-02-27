package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.svw.dealerapp.R;

/**
 * Created by qinshi on 4/14/2017.
 * 三角形
 */

public class TriangleView extends View {

    private Paint paint;
    private RectF rectF;
    private Path path;
    private int color;
    private int radius;

    public TriangleView(Context context) {
        super(context);
        init();
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.triangleView);
        color = typedArray.getColor(R.styleable.triangleView_triangle_view_color, Color.RED);
        radius = typedArray.getDimensionPixelOffset(R.styleable.triangleView_triangle_view_radius, 10);
        typedArray.recycle();

        init();
    }

    private void init(){
        paint = new Paint();
        path = new Path();
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        path.reset();

        path.moveTo(0, height * 0.5f);
        path.lineTo(0, radius);
        rectF.left = 0;
        rectF.top = 0;
        rectF.right = radius * 2;
        rectF.bottom = radius * 2;
        path.arcTo(rectF, 180, 90);
        path.lineTo(width * 0.5f, 0);
        path.close();

        canvas.drawPath(path, paint);
    }
}
