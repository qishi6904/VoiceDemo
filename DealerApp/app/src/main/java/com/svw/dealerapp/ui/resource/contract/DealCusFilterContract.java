package com.svw.dealerapp.ui.resource.contract;

import android.content.Context;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;

import java.util.List;

/**
 * Created by qinshi on 5/19/2017.
 */

public class DealCusFilterContract {

    /**
     * 筛选的视图接口
     */
    public interface View extends ShowToastView{

    }

    public interface Presenter{
        /**
         * 初始化从数据字典中获得的数据
         * @param sourceList
         */
        void initDictionaryData(Context context, List<ResourceFilterItemEntity> sourceList,
                                List<ResourceFilterItemEntity> sourceSelectList);

    }

    public interface Model{

    }
}
