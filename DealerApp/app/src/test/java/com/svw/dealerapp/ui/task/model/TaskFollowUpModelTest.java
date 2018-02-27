package com.svw.dealerapp.ui.task.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskFollowUpEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/5/31.
 */
public class TaskFollowUpModelTest {

    private TaskFollowUpModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new TaskFollowUpModel();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","").doOnNext(new Action1<ResEntity<TaskFollowUpEntity>>() {
            @Override
            public void call(ResEntity<TaskFollowUpEntity> taskFollowUpEntityResEntity) {
                System.out.println(new Gson().toJson(taskFollowUpEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testPostVipCustomer() throws Exception {
        mModel.postVipCustomer("0121021e-7b75-4552-bc1e-9b882e457c3b", "0").doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

}