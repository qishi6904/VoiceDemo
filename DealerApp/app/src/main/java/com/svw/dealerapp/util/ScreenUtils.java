package com.svw.dealerapp.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by qinshi on 4/27/2017.
 */

public class ScreenUtils {

    private static int screenWidth;
    private static int screenHeight;
    private static int statusBarHeight;

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        if(screenWidth <= 0){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);
            screenWidth = metrics.widthPixels;
        }
        return screenWidth;
    }

    public static int getScreenHeight(Context context){
        if(screenHeight <= 0){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);
            screenHeight = metrics.heightPixels;
        }
        return screenHeight;
    }

    /**
     * 获取手机状态栏的高度
     * @return
     */
    public static int getStatusBarHeight(Context context){
        if(statusBarHeight <= 0) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
