package com.svw.dealerapp.ui.resource.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;

import rx.Observable;

/**
 * Created by qinshi on 5/22/2017.
 */

public class YellowCardTransferContract {

    /**
     * 转移的视图接口
     */
    public interface TransferView{
        /**
         * 处理黄卡转移成功后页面刷新
         */
        void dealTransferSuccess();

        /**
         * 显示黄卡转移失败信息
         */
        void showTransferFailed();
    }

    public interface Presenter{
        /**
         * 提效黄卡移转
         * @param context
         * @param oppId
         * @param oppOwner
         */
        void postTransferYellowCard(Context context, String oppId, String oppOwner);

        /**
         * 查找可转移的销售代表
         * @param searchText
         */
        void searchTransferSales(String searchText);
    }

    public interface Model{

        /**
         * 提效黄卡移转
         * @param oppId
         * @param oppOwner
         * @return
         */
        Observable<ResEntity<Object>> postTransferYellowCard(String oppId, String oppOwner);
    }
}
