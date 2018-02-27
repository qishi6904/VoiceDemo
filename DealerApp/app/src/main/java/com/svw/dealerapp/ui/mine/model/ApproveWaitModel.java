package com.svw.dealerapp.ui.mine.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.PostApproveApi;
import com.svw.dealerapp.api.mine.WaitApproveDataApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ApproveWaitEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.mine.contract.ApproveWaitContract;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/6/2017.
 */

public class ApproveWaitModel implements ListFragmentContract.Model<ResEntity<ApproveWaitEntity>>, ApproveWaitContract.Model {


    @Override
    public Observable<ResEntity<Object>> postApprove(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(PostApproveApi.class, true)
                .postApprove(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<ApproveWaitEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, Object> options = new HashMap<>();
        options.put("currentPage", pageIndex);
        options.put("pageSize", pageSize);
//        options.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            options.put("flag", filter);
        }
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(WaitApproveDataApi.class, true)
                .getWaitApproveData(params)
                .compose(RxSchedulers.<ResEntity<ApproveWaitEntity>>io_main());
    }
}
