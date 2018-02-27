package com.example.qinshi.voicedemo;

import android.app.Application;

/**
 * Created by qinshi on 2/27/2018.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaiDuVoiceUtils.init(this);
    }
}
