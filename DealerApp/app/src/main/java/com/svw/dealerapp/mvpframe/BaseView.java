package com.svw.dealerapp.mvpframe;

/**
 * Created by ibm on 2017/4/20.
 */
public interface  BaseView {

    void onRequestStart();
    void onRequestError(String msg);
    void onRequestEnd();
    void onInternetError();

}
