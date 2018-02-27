package com.svw.dealerapp.ui.report;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.WebTokenEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.report.model.ReportModel;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

/**
 * Created by lijinkui on 2017/8/3.
 */
public class ReportModelTest {

    private ReportModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ReportModel();

    }

    @Test
    public void updateWebToken() throws Exception {
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        String webToken = "6b3f0acd-e2ea-4188-ab64-602f1f5a791f";
        mModel.updateWebToken(webToken).doOnNext(new Action1<ResEntity<WebTokenEntity>>() {
            @Override
            public void call(ResEntity<WebTokenEntity> webTokenEntityResEntity) {
                System.out.println(new Gson().toJson(webTokenEntityResEntity));
            }
        }).toBlocking().single();
    }

}