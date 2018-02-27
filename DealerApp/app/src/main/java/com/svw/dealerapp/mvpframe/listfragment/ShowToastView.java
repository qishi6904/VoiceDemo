package com.svw.dealerapp.mvpframe.listfragment;

/**
 * Created by qinshi on 6/8/2017.
 */

public interface ShowToastView {

    /**
     * 显示加载的对话框
     * 用于页面内局部的请求
     */
    void showLoadingDialog();

    /**
     * 隐藏加载的对话框
     * 用于页面内局部的请求
     */
    void hideLoadingDialog();

    /**
     * 服务异常据吐丝
     */
    void showServerErrorToast();

    /**
     * 请求超时吐丝
     */
    void showTimeOutToast();

    /**
     * 网络异常吐丝
     */
    void showNetWorkErrorToast();

}
