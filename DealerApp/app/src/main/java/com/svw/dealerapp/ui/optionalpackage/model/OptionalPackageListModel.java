package com.svw.dealerapp.ui.optionalpackage.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.optionalpackage.OptionalPackageApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 12/1/2017.
 */

public class OptionalPackageListModel implements ListFragmentContract.Model<ResEntity<OptionalPackageEntity>> {
    @Override
    public Observable<ResEntity<OptionalPackageEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        if(null != moreParams){
            if(moreParams.length > 0){
                params.put("serId", moreParams[0]);
            }
            if(moreParams.length > 1){
                params.put("modelId", moreParams[1]);
            }
            if(moreParams.length > 2 && null != moreParams[2]){
                params.put("color", moreParams[2]);
            }
        }

        return NetworkManager.getInstance().getApiInstance(OptionalPackageApi.class, true)
                .getOptionalPackageData(params)
                .compose(RxSchedulers.<ResEntity<OptionalPackageEntity>>io_main());
    }
}
