package com.svw.dealerapp.mvpframe;

import android.content.Context;

import com.svw.dealerapp.mvpframe.rx.RxManager;


/**
 * Created by ibm on 2017/4/20.
 */
public abstract class BasePresenter<M, V> {
    public Context context;
    public M mModel;
    public V mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }


    public void onDestroy() {
        mRxManager.clear();
    }
}
