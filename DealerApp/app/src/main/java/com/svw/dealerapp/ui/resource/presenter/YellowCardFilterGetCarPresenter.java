package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.R;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.ui.resource.contract.YellowCardFiterGetCarContract;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;
import com.svw.dealerapp.ui.resource.entity.YellowCardFilterIntentEntity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by qinshi on 5/19/2017.
 */

public class YellowCardFilterGetCarPresenter extends BaseHandlerPresenter<YellowCardFiterGetCarContract.Model, YellowCardFiterGetCarContract.View>
        implements YellowCardFiterGetCarContract.Presenter{

    public YellowCardFilterGetCarPresenter(YellowCardFiterGetCarContract.View view,
                                           YellowCardFiterGetCarContract.Model model){
        super(view, model);
    }

    /**
     * 初始化从数据字典中获得的数据
     * @param sourceList
     * @param resultList
     * @param rankList
     */
    public void initDictionaryData(Context context, List<ResourceFilterItemEntity> sourceList,
                                   List<ResourceFilterItemEntity> resultList,
                                   List<ResourceFilterItemEntity> rankList,
                                   YellowCardFilterIntentEntity filterIntentEntity){

        sourceList.clear();
        resultList.clear();
        rankList.clear();

        //获取传来的筛选条件数据
        List<ResourceFilterItemEntity> sourceSelectList = null;
        List<ResourceFilterItemEntity> followResultSelectList = null;
        List<ResourceFilterItemEntity> rankSelectList = null;
        if(null != filterIntentEntity) {
            sourceSelectList = filterIntentEntity.getSourceSelectList();
            followResultSelectList = filterIntentEntity.getFollowResultSelectList();
            rankSelectList = filterIntentEntity.getRankSelectList();
        }

        //设置“全部”item
        if(null == sourceSelectList || sourceSelectList.size() <= 0 || sourceSelectList.get(0).isAll()) {
            sourceList.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_source_all), "", true, true));
        }else {
            sourceList.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_source_all), "", true));
        }

        if(null == followResultSelectList || followResultSelectList.size() <= 0 || followResultSelectList.get(0).isAll()) {
            resultList.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_follow_result_all), "", true, true));
        }else {
            resultList.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_follow_result_all), "", true));
        }

        if(null == rankSelectList || rankSelectList.size() <= 0 || rankSelectList.get(0).isAll()) {
            rankList.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_follow_result_all), "", true, true));
        }else {
            rankList.add(new ResourceFilterItemEntity(
                    context.getResources().getString(R.string.resource_yellow_filter_follow_result_all), "", true));
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

        if(null != NewCustomerConstants.ycStatusMap && NewCustomerConstants.ycStatusMap.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.ycStatusMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if("11510".equals(entry.getKey()) || "11512".equals(entry.getKey())) {
                    int position = isContainFilterEntity(followResultSelectList, entry.getKey());
                    if (position >= 0) {
                        followResultSelectList.get(position).setInitSelect(true);
                        resultList.add(followResultSelectList.get(position));
                    } else {
                        ResourceFilterItemEntity filterItemEntity = new ResourceFilterItemEntity(entry.getValue(), entry.getKey(), false);
                        resultList.add(filterItemEntity);
                    }
                }
            }
        }

        if(null != NewCustomerConstants.levelMap && NewCustomerConstants.levelMap.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.levelMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                int position = isContainFilterEntity(rankSelectList, entry.getKey());
                if(position >= 0){
                    rankSelectList.get(position).setInitSelect(true);
                    rankList.add(rankSelectList.get(position));
                }else {
                    ResourceFilterItemEntity filterItemEntity = new ResourceFilterItemEntity(entry.getValue(), entry.getKey(), false);
                    rankList.add(filterItemEntity);
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
}
