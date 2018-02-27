package com.svw.dealerapp.ui.resource.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import rx.Observable;

/**
 * Created by qinshi on 12/19/2017.
 */

public class FilterGetCarNameContractor  {

    /**
     * 筛选的视图接口
     */
    public interface View extends ShowToastView {

        /**
         * 无数据吐丝
         */
        void showNoDataToast();

        /**
         * 显示车型匹配成功的提示
         */
        void showMatchCarSuccess();

        /**
         * 显示车型匹配失败的提示
         */
        void showMatchCarFailed();

        /**
         * 获取车型成功后刷新页面
         * @param entity
         */
        void refreshView(CarTypesEntity entity);
    }

    public interface Presenter{
        void getCarTypeByCode(Context context, String code);
    }

    public interface Model{

        /**
         * 根据车型代码获取车型信息
         * @param code
         * @return
         */
        Observable<ResEntity<CarTypesEntity>> getCarTypeByCode(String code);

    }
}
