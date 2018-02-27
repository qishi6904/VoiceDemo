package com.svw.dealerapp.ui.resource.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.PostInvalidTrafficStatusApi;
import com.svw.dealerapp.api.resource.TrafficListApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.TrafficEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.resource.contract.TrafficContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 5/11/2017.
 */
@Deprecated
public class TrafficModel implements ListFragmentContract.Model<ResEntity<TrafficEntity>>, TrafficContract.Model {

    @Override
    public Observable<ResEntity<TrafficEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
//        params.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        return NetworkManager.getInstance().getApiInstance(TrafficListApi.class, true)
                .getTrafficList(params)
                .compose(RxSchedulers.<ResEntity<TrafficEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> postInvalidTrafficStatus(String trafficId, String status, String reason){
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("leadsId", trafficId);
        params.put("leadsStatusAlis", status);
        params.put("statusReason", reason);
        return NetworkManager.getInstance().getApiInstance(PostInvalidTrafficStatusApi.class, true)
                .postInvalidTrafficStatus(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
