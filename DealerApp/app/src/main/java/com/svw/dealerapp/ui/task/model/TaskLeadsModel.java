package com.svw.dealerapp.ui.task.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.PostInvalidTrafficStatusApi;
import com.svw.dealerapp.api.task.TaskLeadApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskLeadsEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.task.contract.TaskLeadsContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 5/25/2017.
 */

public class TaskLeadsModel implements ListFragmentContract.Model<ResEntity<TaskLeadsEntity>>, TaskLeadsContract.Model {

    @Override
    public Observable<ResEntity<TaskLeadsEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
//        params.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        return NetworkManager.getInstance().getApiInstance(TaskLeadApi.class, true)
                .getTaskLeadsList(params)
                .compose(RxSchedulers.<ResEntity<TaskLeadsEntity>>io_main());
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
