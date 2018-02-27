package com.svw.dealerapp.ui.home;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.ActivityEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.home.model.CreateTrafficModel;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by lijinkui on 2017/6/5.
 */
public class CreateTrafficModelTest {

    CreateTrafficModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();
    
    @Before
    public void setUp() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();
        mModel = new CreateTrafficModel();
    }

    @Test
    public void TestCreateTraffic() throws Exception {
        mModel.createTraffic(optionsForCreateTraffic())
                .doOnNext(new Action1<ResEntity<Object>>() {
                    @Override
                    public void call(ResEntity<Object> entity) {
                        System.out.println(new Gson().toJson(entity));
                    }
                }).toBlocking().single();
    }

    private Map<String, Object> optionsForCreateTraffic(){
        Map<String, Object> options = new HashMap<>();
        options.put("custName","Lily");//custName
        options.put("custGender","1");//custGender
        options.put("custAge","");//custAge
        options.put("custDescription","");//custDescription
        options.put("custMobile","13800138000");//custMobile
        options.put("custTelephone","");//custTelephone
        options.put("custWechat","");//custWechat
        options.put("custEmail","");//custEmail
        options.put("walkinDate","");//walkinDate
        options.put("provinceId","");//provinceId
        options.put("cityId","");//cityId
        options.put("orgId","123456");//orgId
        options.put("salesConsultant","");//salesConsultant
        options.put("carModelId","");//carModelId
        options.put("channelId","");//channelId
        options.put("srcTypeId","11010");//srcTypeId
        options.put("isFlow","0");//isFlow
        options.put("createUser","");//createUser
        options.put("remark","gogogo");//remark
        return options;
    }

    @Test
    public void testGetActivityList() throws Exception {
        Map<String, Object> options = new HashMap<>();
        mModel.getActivityList(options)
                .doOnNext(new Action1<ResEntity<ActivityEntity>>() {
                    @Override
                    public void call(ResEntity<ActivityEntity> activityEntityResEntity) {
                        System.out.println(new Gson().toJson(activityEntityResEntity));
                    }
                }).toBlocking().single();
    }

}