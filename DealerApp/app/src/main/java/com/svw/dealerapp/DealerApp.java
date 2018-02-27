/*
 * Copyright 2016 Freelander
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.svw.dealerapp;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.util.AliCloudPushUtils;
import com.svw.dealerapp.util.dbtools.DBHelper;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.imageload.ImageLoaderManager;

import java.io.File;

/**
 * Created by ibm on 2016/4/10.
 */
public class DealerApp extends Application {

    public static DealerApp applicationContext;

    public static String APP_CACHE_PATH = "svw/dealerapp/cache";

    public static String APP_WEBVIEW_CACHE_DIR = "";

    public static String ACCESS_TOKEN = "";

    public static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        dbHelper = new DBHelper(this);

        /**
         * 设置 Fresco 图片缓存的路径
         */
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
                .setBaseDirectoryPath(getOwnCacheDirectory(this, APP_CACHE_PATH))
                .build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setMainDiskCacheConfig(diskCacheConfig)
                .setSmallImageDiskCacheConfig(diskCacheConfig)
                .build();

        //初始化 Fresco 图片缓存库
        Fresco.initialize(this, config);

        //初始化ImageLoaderManager
        ImageLoaderManager.getInstance().init(this);

        //初始化日志输出工具
        JLog.initialize(BuildConfig.LOG_DEBUG);
        TalkingDataUtils.initTalkingData(this);
        AliCloudPushUtils.initCloudChannel(this);

        setXtokenFromDB();//when app start to set xtoken from db
        setEnvFromDB();//when app start to set env from db

//        DictionaryPresenter dictionaryPresenter = new DictionaryPresenter();
//        Cursor cursor = dbHelper.rawQuery("select * from Dictionary",null);
//        String dictVersion = "";
//        if(cursor !=null){
//            if(cursor.moveToNext()){
//                dictVersion = cursor.getString(5);
//            }
//            cursor.close();
//        }
//        dictionaryPresenter.getDictionary(dictVersion);

        setVmPolicy();
        setAppWebviewCacheDir();
    }

    public static File getOwnCacheDirectory(Context context, String cachePath) {
        File appCacheDir = null;

        if ("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cachePath);
        }

        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    public static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }

    public static DealerApp getContext() {
        return applicationContext;
    }

    private void setXtokenFromDB() {
        DBHelper dbHelper = DealerApp.dbHelper;
//        Cursor cursor = dbHelper.rawQuery("select * from User", null);
        Cursor cursor = dbHelper.rawQuery("select * from SMToken", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String accessToken = cursor.getString(cursor.getColumnIndex("accessToken"));
                NetworkManager.getInstance().setToken(accessToken);
                DealerApp.ACCESS_TOKEN = accessToken;
            }
            cursor.close();
        }
    }

    private void setEnvFromDB() {
        DBHelper dbHelper = DealerApp.dbHelper;
        dbHelper.delete("Env", null, null);
        for (int i = 0; i < Constants.PRESET_BASE_URL.length; i++) {
            ContentValues values = new ContentValues();
            if (String.valueOf(i).equals(BuildConfig.BASE_ENV)) {
                values.put("status", "1");//1:current env 0:not use
                JLog.e("DealerApp Application", "Current Env num: " + i);
                Constants.setApiBaseUrlService(i);
                Constants.set3thApiBaseUrlService(i);
                Constants.setSMCommonParam(i);
            } else {
                values.put("status", "0");
            }
            values.put("name", "Env" + i);
            dbHelper.insert("Env", values);
        }
    }

    private void setAppWebviewCacheDir() {
//        APP_WEBVIEW_CACHE_DIR = Environment.getExternalStorageDirectory() + "/DealerApp/webviewCache";
        APP_WEBVIEW_CACHE_DIR = "/data/data/com.svw.dealerapp/cache/webviewCache";
        File file = new File(APP_WEBVIEW_CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 为解决部分手机(主要为6.0以上)无法正常拍照、剪切相片的问题
     */
    private void setVmPolicy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

}
