package com.svw.dealerapp.ui.resource.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.PostTransferYellowCardApi;
import com.svw.dealerapp.api.resource.TransferSalesApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.resource.TransferDataEntity;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.resource.contract.YellowCardTransferContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 5/22/2017.
 */

public class YellowCardTransferModel implements ListFragmentContract.Model<SMResEntity<TransferDataEntity>>, YellowCardTransferContract.Model {

    @Override
    public Observable<ResEntity<Object>> postTransferYellowCard(String oppId, String oppOwner) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("oppId", oppId);
        params.put("oppOwner", oppOwner);
        return NetworkManager.getInstance().getApiInstance(PostTransferYellowCardApi.class, true)
                .postTransferYellowCard(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<SMResEntity<TransferDataEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String...moreParams) {
        String orgId = "";
        if(null != moreParams && moreParams.length > 0){
            orgId = moreParams[0];
        }
        Map<String, Object> params = new HashMap<>();
//        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("orgId", orgId);
        params.put("pageSize", Integer.MAX_VALUE);
        params.put("pageNo", "1");
        return NetworkManager.getInstance().getApiInstance(TransferSalesApi.class, true)
                .getTransferSales(params)
                .compose(RxSchedulers.<SMResEntity<TransferDataEntity>>io_main());

    }
}
