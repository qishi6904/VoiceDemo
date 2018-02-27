package com.svw.dealerapp.ui.mine.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/6/2017.
 */

public class NotificationContract {

    public interface View{

        /**
         * 设置为已读成功
         */
        void setNotificationReadSuccess();

        /**
         * 设置为已读失败
         */
        void setNotificationReadFail();

        /**
         * 删除成功
         */
        void deleteNotificationSuccess();

        /**
         * 删除失败
         */
        void deleteNotificationFail();
    }

    public interface Model{
        Observable<ResEntity<Object>> setNotificationRead(Map<String, Object> options);

        Observable<ResEntity<Object>> postDeleteNotification(Map<String, Object> options);
    }

    public interface Presenter{
        void setNotificationRead(Context context, Map<String, Object> options, boolean isShowLoading);

        void postDeleteNotification(Context context, Map<String, Object> options);
    }
}
