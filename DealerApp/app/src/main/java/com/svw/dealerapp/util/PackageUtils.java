package com.svw.dealerapp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.TextView;

import com.svw.dealerapp.DealerApp;

import java.util.Locale;

/**
 * Created by qinshi on 6/2/2017.
 */

public class PackageUtils {

    /**
     * 获取应用版本名称
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context){
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取当前版本号
     * @param context
     * @return
     */
    public static int getAppVersionNumber(Context context){
        int versionNumber = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionNumber = pi.versionCode;
        }catch (Exception e){
            e.printStackTrace();
        }
        return versionNumber;
    }

    /**
     * 当前手机是否是中文语言环境
     * @return
     */
    public static boolean isZhEvn(){
        Locale locale = DealerApp.getContext().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (!TextUtils.isEmpty(language) && language.toLowerCase().endsWith("zh")){
            return true;
        }
        return false;
    }
}
