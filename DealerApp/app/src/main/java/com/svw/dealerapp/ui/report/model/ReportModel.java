package com.svw.dealerapp.ui.report.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.report.ReportApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.WebTokenEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.report.contract.ReportContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/8/1.
 */

@Deprecated
public class ReportModel implements ReportContract.Model{
    @Override
    public Observable<ResEntity<WebTokenEntity>> updateWebToken(String webToken) {
        Map<String, Object> options = RequestBaseParamsConfig.getRequestBaseParams2();
        options.put("xtoken", webToken);
        return NetworkManager.getInstance().getApiInstance(ReportApi.class, true)
                .updateWebToken(options)
                .compose(RxSchedulers.<ResEntity<WebTokenEntity>>io_main());
    }
}
