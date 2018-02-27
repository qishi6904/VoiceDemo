package com.svw.dealerapp.ui.report.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/8/29.
 */
public class ReportHomeModelTest {

    private ReportHomeModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ReportHomeModel();
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();
    }

    @Test
    public void getDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","").doOnNext(new Action1<ResEntity<ReportHomeEntity>>() {
            @Override
            public void call(ResEntity<ReportHomeEntity> reportHomeEntityResEntity) {
                System.out.println(new Gson().toJson(reportHomeEntityResEntity));
            }
        }).toBlocking().single();
    }

}