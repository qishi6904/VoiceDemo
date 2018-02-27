package com.svw.dealerapp.ui.login.contract;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserPrivilegeByAppIdEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMUserPrivilegeContract {
    interface Model {
        Observable<SMResEntity<List<SMUserPrivilegeByAppIdEntity>>> smUserPrivilege(Map<String,Object> options);
    }

    interface View extends ShowToastView {
        void onUserPrivilegeSuccess(List<SMUserPrivilegeByAppIdEntity> dataList);

        void onUserPrivilegeError(String msg);
    }

    abstract class Presenter extends BaseHandlerPresenter<Model, View> {
        public Presenter(View view, Model model) {
            super(view, model);
        }

        public abstract void getSmUserPrivilege(Map<String,Object> options);
    }
}
