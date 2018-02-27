package com.svw.dealerapp.ui.newcustomer.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.newcustomer.RemarkListDataListApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.RemarkEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 7/14/2017.
 */

public class RemarkListModel implements ListFragmentContract.Model<ResEntity<RemarkEntity>> {

    @Override
    public Observable<ResEntity<RemarkEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
//        params.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        if(null != moreParams && moreParams.length > 0){
            if(null != moreParams[0]){
                params.put("oppId", moreParams[0]);
            }
        }
        return NetworkManager.getInstance().getApiInstance(RemarkListDataListApi.class, true)
                .getRemarkListData(params)
                .compose(RxSchedulers.<ResEntity<RemarkEntity>>io_main());
    }
}
