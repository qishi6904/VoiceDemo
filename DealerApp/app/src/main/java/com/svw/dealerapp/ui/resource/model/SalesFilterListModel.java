package com.svw.dealerapp.ui.resource.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.ManagerSalesApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.resource.TransferDataEntity;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 11/21/2017.
 */

public class SalesFilterListModel implements ListFragmentContract.Model<SMResEntity<TransferDataEntity>> {
    @Override
    public Observable<SMResEntity<TransferDataEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        String orgId = "";
        if(null != moreParams && moreParams.length > 0){
            orgId = moreParams[0];
        }
        Map<String, Object> params = new HashMap<>();
        params.put("orgId", orgId);
        params.put("pageSize", Integer.MAX_VALUE);
        params.put("pageNo", "1");
        return NetworkManager.getInstance().getApiInstance(ManagerSalesApi.class, true)
                .getManagerSales(params)
                .compose(RxSchedulers.<SMResEntity<TransferDataEntity>>io_main());
    }
}
