package com.svw.dealerapp.ui.resource.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.MyYellowCardListApi;
import com.svw.dealerapp.api.resource.PostActiveYellowCardApi;
import com.svw.dealerapp.api.resource.PostVipCustomerApi;
import com.svw.dealerapp.api.resource.YellowCardListApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.resource.contract.YellowCardContract;
import com.svw.dealerapp.entity.resource.YellowCardEntity;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 5/19/2017.
 */

public class YellowCardModel implements ListFragmentContract.Model<ResEntity<YellowCardEntity>>, YellowCardContract.Model {

    public static final String SEARCH_ALL = "searchAll";

    @Override
    public Observable<ResEntity<YellowCardEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
//        params.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }

        if(null != moreParams && moreParams.length > 0 && SEARCH_ALL.equals(moreParams[0])) {  // 搜索所有
            return NetworkManager.getInstance().getApiInstance(YellowCardListApi.class, true)
                    .getYellowCardList(params)
                    .compose(RxSchedulers.<ResEntity<YellowCardEntity>>io_main());
        }else {     // 搜索自己
            return NetworkManager.getInstance().getApiInstance(MyYellowCardListApi.class, true)
                    .getMyYellowCardList(params)
                    .compose(RxSchedulers.<ResEntity<YellowCardEntity>>io_main());
        }
    }

    @Override
    public Observable<ResEntity<Object>> postVipCustomer(String oppId, String isKeyuser) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("oppId", oppId);
        params.put("isKeyuser", isKeyuser);
        return NetworkManager.getInstance().getApiInstance(PostVipCustomerApi.class, true)
                .postVipCustomer(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> postActiveYellow(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(PostActiveYellowCardApi.class, true)
                .postActiveYellowCard(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
