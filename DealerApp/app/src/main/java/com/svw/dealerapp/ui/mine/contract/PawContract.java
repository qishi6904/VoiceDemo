package com.svw.dealerapp.ui.mine.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ResetPassEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import rx.Observable;

/**
 * Created by qinshi on 6/1/2017.
 */

@Deprecated
public class PawContract {

    public interface View extends ShowToastView{
        /**
         * 重设密码成功刷新页面
         */
        void showResetPawSuccess();

        /**
         * 原始密码错误提示
         */
        void showOldPawErrorToast();
    }

    public interface Model{
        /**
         * 提交重设密码的请求
         * @param userId
         * @param oldPaw
         * @param newPaw
         * @return
         */
        Observable<ResEntity<ResetPassEntity>> postResetPaw(String userId, String oldPaw, String newPaw);
    }


    public interface Presenter{
        /**
         * 提交重设密码的请求
         * @param context
         * @param userId
         * @param oldPaw
         * @param newPaw
         */
        void postResetPaw(Context context, String userId, String oldPaw, String newPaw);
    }
}
