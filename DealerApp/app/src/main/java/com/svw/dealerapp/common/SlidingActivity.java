package com.svw.dealerapp.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.svw.dealerapp.ui.widget.SlidingLayout;

/**
 * Created by lijinkui on 2017/9/1.
 */

public class SlidingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    protected boolean enableSliding() {
//        return true;
        return false;
    }
}
