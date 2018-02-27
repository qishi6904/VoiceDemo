package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.R;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.ui.resource.contract.DealCusFilterContract;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by qinshi on 5/19/2017.
 */

public class DealCusFilterPresenter extends BaseHandlerPresenter<DealCusFilterContract.Model, DealCusFilterContract.View>
        implements DealCusFilterContract.Presenter{

    private List<ResourceFilterItemEntity> reasonList;

    public DealCusFilterPresenter(DealCusFilterContract.View view,
                                  DealCusFilterContract.Model model){
        super(view, model);
    }

    /**
     * 初始化从数据字典中获得的数据
     * @param sourceList
     */
    public void initDictionaryData(Context context, List<ResourceFilterItemEntity> sourceList,
                                   List<ResourceFilterItemEntity> sourceSelectList){

        sourceList.clear();

        //获取传来的筛选条件数据
//        List<ResourceFilterItemEntity> sourceSelectList = null;
//        if(null != filterIntentEntity) {
//            sourceSelectList = filterIntentEntity.getSourceSelectList();
//        }

        //设置“全部”item
        if(null == sourceSelectList || sourceSelectList.size() <= 0 || sourceSelectList.get(0).isAll()) {
            sourceList.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_source_all), "", true, true));
        }else {
            sourceList.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_source_all), "", true));
        }

        if(null != NewCustomerConstants.sourceMap && NewCustomerConstants.sourceMap.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.sourceMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                int position = isContainFilterEntity(sourceSelectList, entry.getKey());
                if(position >=0){
                    sourceSelectList.get(position).setInitSelect(true);
                    sourceList.add(sourceSelectList.get(position));
                }else {
                    ResourceFilterItemEntity filterItemEntity = new ResourceFilterItemEntity(entry.getValue(), entry.getKey(), false);
                    sourceList.add(filterItemEntity);
                }
            }
        }
    }

    /**
     * 指定的列表是否包含指定的对象
     * @param list
     * @param code
     * @return
     */
    private int isContainFilterEntity(List<ResourceFilterItemEntity> list, String code){
        if(null != list) {
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).getCode()) && list.get(i).getCode().equals(code)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void dealFailedReasonData(Context context, List<ResourceFilterItemEntity> list, List<ResourceFilterItemEntity> selectList){

        if(null == reasonList) {
            reasonList = new ArrayList<>();
            reasonList.add(new ResourceFilterItemEntity(context.getResources().getString(R.string.resource_failed_cus_filter_reason_1),
                    "1", false, false));
            reasonList.add(new ResourceFilterItemEntity(context.getResources().getString(R.string.resource_failed_cus_filter_reason_2),
                    "2", false, false));
        }

        //设置“全部”item
        if(null == selectList || selectList.size() <= 0 || selectList.get(0).isAll()) {
            list.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_source_all), "", true, true));
        }else {
            list.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_source_all), "", true));
        }

        for (int i = 0; i < reasonList.size(); i++){
            int position = isContainFilterEntity(selectList, reasonList.get(i).getCode());
            if(position >=0){
                selectList.get(position).setInitSelect(true);
                list.add(selectList.get(position));
            }else {
                ResourceFilterItemEntity filterItemEntity = new ResourceFilterItemEntity(reasonList.get(i).getName(),
                        reasonList.get(i).getCode(), false, false);
                list.add(filterItemEntity);
            }
        }
    }
}
