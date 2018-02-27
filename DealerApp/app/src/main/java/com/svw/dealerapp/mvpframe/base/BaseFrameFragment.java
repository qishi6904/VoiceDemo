package com.svw.dealerapp.mvpframe.base;

import android.os.Bundle;

import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.mvpframe.BaseModel;
import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.mvpframe.BaseView;
import com.svw.dealerapp.mvpframe.util.TUtil;
import com.svw.dealerapp.util.JLog;


/**
 * Created by ibm on 2017/4/20.
 */

public abstract  class BaseFrameFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment implements BaseView {

    public P mPresenter;

    public M mModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.setVM(this, mModel);
        }

    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onInternetError() {
//        showShortToast("网络异常");
    }

    @Override
    public void onRequestError(String msg) {
//        showShortToast(msg);
        JLog.e("REQUEST_ERROR ==== ", msg);
    }

}
