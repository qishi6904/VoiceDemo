package com.svw.dealerapp.common.drawer;

import android.support.v4.app.Fragment;

/**
 * Created by qinshi on 1/11/2018.
 */

public abstract class RdLeftListDrawerActivity extends RdDrawerActivity {

    @Override
    protected Fragment getLeftDrawerFragment() {
        return new RdLeftListDrawerFragment();
    }
}
