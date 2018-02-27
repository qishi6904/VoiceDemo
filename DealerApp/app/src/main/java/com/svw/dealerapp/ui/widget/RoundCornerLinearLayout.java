package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.svw.dealerapp.R;

/**
 * Created by qinshi on 10/16/2017.
 */

public class RoundCornerLinearLayout extends LinearLayout {

    private int roundColor = Color.RED;
    private int roundRadius = 40;
    private Paint paint;
    private Path path;
    private RectF rectF;


    public RoundCornerLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public RoundCornerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        path = new Path();
        rectF = new RectF();

        roundRadius = getResources().getDimensionPixelSize(R.dimen.list_view_round_corner) * 2;
        roundColor = getResources().getColor(R.color.page_bg);
        paint.setColor(roundColor);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        path.reset();
        path.moveTo(0, 0);
        path.lineTo(0, roundRadius);
        rectF.left = 0;
        rectF.right = roundRadius;
        rectF.top = 0;
        rectF.bottom = roundRadius;
        path.arcTo(rectF, 180, 90);
        path.close();
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(0, height);
        path.lineTo(roundRadius, height);
        rectF.left = 0;
        rectF.right = roundRadius;
        rectF.top = height - roundRadius;
        rectF.bottom = height;
        path.arcTo(rectF, 90, 90);
        path.close();
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(width, height);
        path.lineTo(width, height - roundRadius);
        rectF.left = width - roundRadius;
        rectF.right = width;
        rectF.top = height - roundRadius;
        rectF.bottom = height;
        path.arcTo(rectF, 0, 90);
        path.close();
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(width, 0);
        path.lineTo(width - roundRadius, 0);
        rectF.left = width - roundRadius;
        rectF.right = width;
        rectF.top = 0;
        rectF.bottom = roundRadius;
        path.arcTo(rectF, 270, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

}
