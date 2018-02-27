package com.svw.dealerapp.ui.resource.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

/**
 * Created by qinshi on 5/19/2017.
 */

public class YellowCardModelTest {

    private YellowCardModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new YellowCardModel();
    }

    @Test
    public void testGetDataFromServer(){
        mModel.getDataFromServer("1","3","1", "%7B\"oppLevel\":\"A,B,C\",\"isKeyuser\":\"1\",\"followupResult\":\"15510,15520,15530\"%7D").doOnNext(new Action1<ResEntity<YellowCardEntity>>() {
            @Override
            public void call(ResEntity<YellowCardEntity> yellowCardEntityResEntity) {
                System.out.println(new Gson().toJson(yellowCardEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testPostVipCustomer(){
        mModel.postVipCustomer("02996a48-bbf1-4451-86b4-f82597371197", "0").doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

}
