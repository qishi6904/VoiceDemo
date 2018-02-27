package com.svw.dealerapp.ui.login.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserInfoEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2018/1/25.
 */
public class SMUserInfoModelTest {

    private SMUserInfoModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new SMUserInfoModel();
    }

    @Test
    public void smUserInfo() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();

        Map<String, Object> options = new HashMap<>();
        mModel.smUserInfo(options).doOnNext(new Action1<SMResEntity<SMUserInfoEntity>>() {
            @Override
            public void call(SMResEntity<SMUserInfoEntity> smUserInfoEntitySMResEntity) {
                System.out.println(new Gson().toJson(smUserInfoEntitySMResEntity));
            }
        }).toBlocking().single();
    }

}