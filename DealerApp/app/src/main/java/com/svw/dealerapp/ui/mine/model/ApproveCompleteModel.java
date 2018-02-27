package com.svw.dealerapp.ui.mine.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.CompleteApproveDataApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ApproveCompleteEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/6/2017.
 */

public class ApproveCompleteModel implements ListFragmentContract.Model<ResEntity<ApproveCompleteEntity>> {
    @Override
    public Observable<ResEntity<ApproveCompleteEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, Object> options = new HashMap<>();
        options.put("currentPage", pageIndex);
        options.put("pageSize", pageSize);
//        options.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            options.put("flag", filter);
        }
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(CompleteApproveDataApi.class, true)
                .getCompleteApproveData(params)
                .compose(RxSchedulers.<ResEntity<ApproveCompleteEntity>>io_main());
    }
}
