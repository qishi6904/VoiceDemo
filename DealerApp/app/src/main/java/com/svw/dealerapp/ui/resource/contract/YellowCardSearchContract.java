package com.svw.dealerapp.ui.resource.contract;

/**
 * Created by qinshi on 6/12/2017.
 */

public class YellowCardSearchContract {

    public interface View{
        /**
         * 设置搜索返回的总数
         * @param total
         */
        void setSearchTotal(String total);
    }
}
