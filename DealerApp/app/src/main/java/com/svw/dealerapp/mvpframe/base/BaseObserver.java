package com.svw.dealerapp.mvpframe.base;


import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.util.ToastUtils;

import retrofit2.HttpException;
import rx.Observer;

/**
 * Created by qinshi on 1/31/2018.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        if(e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            if (null != e && ("401".equals(String.valueOf(exception.code())) ||
                    "401.0".equals(String.valueOf(exception.code())))){
                dealTokenError();
            }else {
                onServerError(e);
            }
        }else {
            onServerError(e);
        }
    }

    public void onServerError(Throwable e){
        ToastUtils.showToast(DealerApp.getContext().getString(R.string.server_error));
    }

    private void dealTokenError(){
        ToastUtils.showToast(DealerApp.getContext().getString(R.string.login_token_expired));
    }
}
