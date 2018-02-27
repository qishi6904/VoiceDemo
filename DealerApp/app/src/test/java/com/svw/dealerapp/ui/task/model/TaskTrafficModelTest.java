package com.svw.dealerapp.ui.task.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/5/31.
 */
public class TaskTrafficModelTest {

    private TaskTrafficModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new TaskTrafficModel();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","").doOnNext(new Action1<ResEntity<TaskTrafficEntity>>() {
            @Override
            public void call(ResEntity<TaskTrafficEntity> taskTrafficEntityResEntity) {
                System.out.println(new Gson().toJson(taskTrafficEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testPostInvalidTrafficStatus(){
        mModel.postInvalidTrafficStatus("3001ba31-95ae-4e8d-ac52-32eba3980095", "InValid", "任性，不想建卡")
                .doOnNext(new Action1<ResEntity<Object>>() {
                    @Override
                    public void call(ResEntity<Object> entity) {
                        System.out.println(new Gson().toJson(entity));
                    }
                }).toBlocking().single();
    }

    @Test
    public void testCheckYCExisted() throws Exception {
//        mModel.checkYCExisted("111").doOnNext(new Action1<ResEntity<Object>>() {
//            @Override
//            public void call(ResEntity<Object> objectResEntity) {
//                System.out.println(new Gson().toJson(objectResEntity));
//            }
//        }).toBlocking().single();
    }

}