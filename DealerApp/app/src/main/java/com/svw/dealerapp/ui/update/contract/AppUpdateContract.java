package com.svw.dealerapp.ui.update.contract;

import android.app.Activity;

import com.svw.dealerapp.entity.update.AppUpdateEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;


import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by qinshi on 7/3/2017.
 */

public class AppUpdateContract {

    public interface View extends ShowToastView{

        /**
         * 有新版本
         */
        void hasNewVersion(AppUpdateEntity updateEntity);

        /**
         * 当前为最新版本
         */
        void isLatestVersion();

        /**
         * 版本检查失败
         */
        void checkUpdateFail();

    }

    public interface Model{

        /**
         * 检查应用是否有版本
         * @return
         */
        Observable<AppUpdateEntity> checkAppUpdate();
    }

    public interface Presenter{

        /**
         * 检查应用是否有版本
         * @param activity
         * @param isShowLoading  是在页面上显示加载进度框和相关请求的提示信息
         */
        void checkAppUpdate(Activity activity, boolean isShowLoading);
    }
}
