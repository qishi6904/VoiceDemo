package com.svw.dealerapp.ui.login.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshSubTokenEntity;
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
public class SMRefreshSubTokenModelTest {

    private SMRefreshSubTokenModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new SMRefreshSubTokenModel();
    }

    @Test
    public void smRefreshSubToken() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();

        Map<String, Object> options = new HashMap<>();
        options.put("parentToken","oNyl3hrsSx44AAAAAAAACZ2Ij3rovc-H");
        options.put("sonClientId","c82a8fa7ecc44606af1491bcec3c1f41");
        options.put("source","2");
        mModel.smRefreshSubToken(options).doOnNext(new Action1<SMResEntity<SMRefreshSubTokenEntity>>() {
            @Override
            public void call(SMResEntity<SMRefreshSubTokenEntity> smRefreshSubTokenEntitySMResEntity) {
                System.out.println(new Gson().toJson(smRefreshSubTokenEntitySMResEntity));
            }
        }).toBlocking().single();
    }

}