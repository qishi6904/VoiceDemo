package com.svw.dealerapp.ui.login.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshTokenEntity;
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
public class SMRefreshTokenModelTest {

    private SMRefreshTokenModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new SMRefreshTokenModel();
    }

    @Test
    public void smRefreshToken() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();

        Map<String, Object> options = new HashMap<>();
        options.put("grant_type","refresh_token");
        options.put("refresh_token","emS4XKl7RBU4AAAAAAAACZJc2o8udHw9");
        mModel.smRefreshToken(options).doOnNext(new Action1<SMResEntity<SMRefreshTokenEntity>>() {
            @Override
            public void call(SMResEntity<SMRefreshTokenEntity> smRefreshTokenEntitySMResEntity) {
                System.out.println(new Gson().toJson(smRefreshTokenEntitySMResEntity));
            }
        }).toBlocking().single();
    }

}