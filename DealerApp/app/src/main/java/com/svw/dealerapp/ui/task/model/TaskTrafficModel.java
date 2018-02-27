package com.svw.dealerapp.ui.task.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.PostInvalidTrafficStatusApi;
import com.svw.dealerapp.api.task.TaskTrafficApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.task.contract.TaskTrafficContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 5/25/2017.
 */
@Deprecated
public class TaskTrafficModel implements ListFragmentContract.Model<ResEntity<TaskTrafficEntity>>, TaskTrafficContract.Model {

    @Override
    public Observable<ResEntity<TaskTrafficEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
//        params.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        return NetworkManager.getInstance().getApiInstance(TaskTrafficApi.class, true)
                .getTaskTrafficList(params)
                .compose(RxSchedulers.<ResEntity<TaskTrafficEntity>>io_main());
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
