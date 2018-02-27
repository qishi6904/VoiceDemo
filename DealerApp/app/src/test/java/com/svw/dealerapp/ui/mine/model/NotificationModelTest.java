package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.NotificationEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/6/7.
 */
public class NotificationModelTest {

    private NotificationModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new NotificationModel();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","").doOnNext(new Action1<ResEntity<NotificationEntity>>() {
            @Override
            public void call(ResEntity<NotificationEntity> notificationEntityResEntity) {
                System.out.println(new Gson().toJson(notificationEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testSetNotificationRead() throws Exception {
        Map<String,Object> options = new HashMap<>();
        List<String> noticeIDList = new ArrayList<>();
        noticeIDList.add("6A497579-A574-451E-9259-3D06DCB877B0");
        options.put("noticeIDList",noticeIDList);
        mModel.setNotificationRead(options).doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testPostDeleteNotification() throws Exception {
        Map<String,Object> options = new HashMap<>();
        List<String> noticeIDList = new ArrayList<>();
        noticeIDList.add("6A497579-A574-451E-9259-3D06DCB877B0");
        noticeIDList.add("76086542-645C-4701-AFC5-2D84A0E33F49");
        options.put("noticeIDList",noticeIDList);
        mModel.postDeleteNotification(options).doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

}