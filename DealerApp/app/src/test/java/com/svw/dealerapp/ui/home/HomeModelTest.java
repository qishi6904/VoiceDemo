package com.svw.dealerapp.ui.home;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.HomeEntity;
import com.svw.dealerapp.ui.home.model.HomeModel;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by lijinkui on 2017/6/6.
 */
public class HomeModelTest {

    private HomeModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new HomeModel();
    }

    @Test
    public void TestCancelSchedule() throws Exception {
        Map<String,Object> options = new HashMap<>();
        options.put("appmId","ff4640ac-d597-4ffe-b38e-e2a2588b584a");
        mModel.cancelSchedule(options).doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","%7B\"appmStatusId\":\"0\"%7D").doOnNext(new Action1<ResEntity<HomeEntity>>() {
            @Override
            public void call(ResEntity<HomeEntity> homeEntityResEntity) {
                System.out.println(new Gson().toJson(homeEntityResEntity));
            }
        }).toBlocking().single();
    }

}