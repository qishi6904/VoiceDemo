package com.svw.dealerapp.ui.newcustomer.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.newcustomer.CarTypesApi;
import com.svw.dealerapp.api.newcustomer.NewCustomerApi_NotUsed;
import com.svw.dealerapp.api.newcustomer.ShowOpportunitiesDetailApi;
import com.svw.dealerapp.api.newcustomer.SubmitFollowupInfoApi;
import com.svw.dealerapp.api.newcustomer.SubmitOpportunitiesV2Api;
import com.svw.dealerapp.api.newcustomer.SubmitRemarkApi;
import com.svw.dealerapp.api.newcustomer.UpdateOpportunitiesApi;
import com.svw.dealerapp.api.resource.PostActiveYellowCardApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/5/15.
 */

public class NewCustomerModel implements NewCustomerContract.Model {

    private Map<String, String> optionsForOpportunities(Map<String, String> options) {

        options = RequestBaseParamsConfig.addRequestBaseParams(options);

        return options;
    }

    private Map<String, String> optionsForSearchByCarType(String ecModelId) {

        Map<String, String> options = new HashMap<String, String>();
        options.put("ecModelId", ecModelId);
        options = RequestBaseParamsConfig.addRequestBaseParams(options);

        return options;
    }

    private Map<String, Object> optionsForOpportunityDetail(String oppId) {

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("oppId", oppId);
        options = RequestBaseParamsConfig.addRequestBaseParams2(options);

        return options;
    }

    private Map<String, String> optionsForFollowupInfo(Map<String, String> options) {

        options = RequestBaseParamsConfig.addRequestBaseParams(options);

        return options;
    }

    @Override
    public Observable<ResEntity<OpportunityEntity>> submitOpportunities(Map<String, String> options) {
        return NetworkManager.getInstance().getApiInstance(NewCustomerApi_NotUsed.class, true)
                .submitOpportunities(optionsForOpportunities(options))
                .compose(RxSchedulers.<ResEntity<OpportunityEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<OpportunityEntity>> submitOpportunities(OpportunitySubmitReqEntity options) {
        return NetworkManager.getInstance().getApiInstance(NewCustomerApi_NotUsed.class, true)
                .submitOpportunities(options)
                .compose(RxSchedulers.<ResEntity<OpportunityEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<OpportunityEntity>> submitOpportunitiesV2(OpportunitySubmitReqEntityV2 options) {
        return NetworkManager.getInstance().getApiInstance(SubmitOpportunitiesV2Api.class, true)
                .submitOpportunitiesV2(options)
                .compose(RxSchedulers.<ResEntity<OpportunityEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<OpportunityEntity>> updateOpportunities(Map<String, String> options) {
        return NetworkManager.getInstance().getApiInstance(NewCustomerApi_NotUsed.class, true)
                .updateOpportunities(optionsForOpportunities(options))
                .compose(RxSchedulers.<ResEntity<OpportunityEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<OpportunityEntity>> updateOpportunities(OpportunityUpdateReqEntity options) {
        return NetworkManager.getInstance().getApiInstance(UpdateOpportunitiesApi.class, true)
                .updateOpportunities(options)
                .compose(RxSchedulers.<ResEntity<OpportunityEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<CarTypesEntity>> searchByCarType(String ecModelId) {
        return NetworkManager.getInstance().getApiInstance(CarTypesApi.class, true)
                .searchByCarType(optionsForSearchByCarType(ecModelId))
                .compose(RxSchedulers.<ResEntity<CarTypesEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> submitFollowupInfo(Map<String, String> options) {
        return NetworkManager.getInstance().getApiInstance(NewCustomerApi_NotUsed.class, true)
                .submitFollowupInfo(optionsForFollowupInfo(options))
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> submitFollowupInfo(FollowupCreateReqEntity options) {
        return NetworkManager.getInstance().getApiInstance(SubmitFollowupInfoApi.class, true)
                .submitFollowupInfo(options)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<OpportunityDetailEntity>> showOpportunitiesDetail(String oppId) {
        return NetworkManager.getInstance().getApiInstance(ShowOpportunitiesDetailApi.class, true)
                .showOpportunitiesDetail(optionsForOpportunityDetail(oppId))
                .compose(RxSchedulers.<ResEntity<OpportunityDetailEntity>>io_main());

    }

    @Override
    public Observable<ResEntity<Object>> postActiveYellow(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(PostActiveYellowCardApi.class, true)
                .postActiveYellowCard(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> submitRemark(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(SubmitRemarkApi.class, true)
                .submitRemark(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

}
