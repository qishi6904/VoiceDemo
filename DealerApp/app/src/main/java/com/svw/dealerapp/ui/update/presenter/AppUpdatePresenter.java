package com.svw.dealerapp.ui.update.presenter;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;

import com.facebook.common.util.ExceptionWithNoStacktrace;
import com.svw.dealerapp.entity.update.AppUpdateEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.update.contract.AppUpdateContract;
import com.svw.dealerapp.util.PackageUtils;
import com.svw.dealerapp.util.PermissionUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 7/3/2017.
 */

public class AppUpdatePresenter extends BaseHandlerPresenter<AppUpdateContract.Model, AppUpdateContract.View>
        implements AppUpdateContract.Presenter{

    private Subscription checkUpdateSubscription;
    private boolean isShowLoading = true;  //是否显示加载框和异常信息

    public AppUpdatePresenter(AppUpdateContract.View view, AppUpdateContract.Model model) {
        super(view, model);
    }

    @Override
    public void checkAppUpdate(final Activity activity, final boolean isShowLoading) {
        this.isShowLoading = isShowLoading;
        Observable observable = mModel.checkAppUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ToastObserver observer = new ToastObserver<AppUpdateEntity>(mView, handler, handlerAction1, isShowLoading){
            @Override
            public void dealResult(final AppUpdateEntity entity) {
                if(null != entity && !TextUtils.isEmpty(entity.getVersion()) && !TextUtils.isEmpty(entity.getInstall_url())){
                    try{
                        int newVersionCode = Integer.parseInt(entity.getVersion());
                        if(newVersionCode > PackageUtils.getAppVersionNumber(activity)) {
                            mView.hasNewVersion(entity);
                        }else {
                            if(isShowLoading){
                                mView.isLatestVersion();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        if(isShowLoading) {
                            mView.checkUpdateFail();
                        }
                    }
                }else {
                    if(isShowLoading) {
                        mView.checkUpdateFail();
                    }
                }
            }
        };

        checkUpdateSubscription = requestByToast(activity, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {     //请求超时后执行
        if(null != checkUpdateSubscription){
            checkUpdateSubscription.unsubscribe();
            if(isShowLoading) {
                mView.showTimeOutToast();
            }
        }
        if(isShowLoading) {
            mView.hideLoadingDialog();
        }
    }
}
