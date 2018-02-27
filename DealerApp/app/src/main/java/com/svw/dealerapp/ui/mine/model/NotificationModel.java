package com.svw.dealerapp.ui.mine.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.NotificationDataApi;
import com.svw.dealerapp.api.mine.PostDeleteNotificationApi;
import com.svw.dealerapp.api.mine.PostNotificationReadApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.NotificationEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.mine.contract.NotificationContract;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/6/2017.
 */

public class NotificationModel implements ListFragmentContract.Model<ResEntity<NotificationEntity>>, NotificationContract.Model {
    @Override
    public Observable<ResEntity<NotificationEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, Object> options = new HashMap<>();
        options.put("pageIndex", pageIndex);
        options.put("pageSize", pageSize);
//        options.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            options.put("filter", filter);
        }
        options.put("channelType", "100");//100:push
        options.put("status", "2");//2:all
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(NotificationDataApi.class, true)
                .getNotificationData(params)
                .compose(RxSchedulers.<ResEntity<NotificationEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> setNotificationRead(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(PostNotificationReadApi.class, true)
                .postNotificationRead(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> postDeleteNotification(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(PostDeleteNotificationApi.class, true)
                .postDeleteNotification(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
