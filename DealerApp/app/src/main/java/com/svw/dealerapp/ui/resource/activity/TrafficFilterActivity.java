package com.svw.dealerapp.ui.resource.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.resource.adapter.ResourceFilterAdapter;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;
import com.svw.dealerapp.ui.resource.entity.TrafficFilterEntity;
import com.svw.dealerapp.ui.resource.entity.TrafficFilterIntentEntity;
import com.svw.dealerapp.util.GsonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.filter;

/**
 * Created by qinshi on 5/12/2017.
 */

public class TrafficFilterActivity extends BaseActivity {

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvConfirm;
    private RecyclerView rvTrafficTime;
    private RecyclerView rvTrafficStatus;

    private List<ResourceFilterItemEntity> timeList = new ArrayList<>();
    private List<ResourceFilterItemEntity> statusList = new ArrayList<>();
    private ResourceFilterAdapter filterTimeAdapter;
    private ResourceFilterAdapter filterStatusAdapter;

    private ResourceFilterItemEntity timeFilterSelectEntity;
    private List<ResourceFilterItemEntity> statusFilterSelectList = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_filter);
        assignViews();

        tvTitle.setText(getResources().getString(R.string.resource_traffic_filter_title));

        TrafficFilterIntentEntity filterIntentEntity = getIntentFilterEntity();

        initTimeData(filterIntentEntity);
        initStatusData(filterIntentEntity);

        rvTrafficTime.setLayoutManager(new GridLayoutManager(this, 4));
        filterTimeAdapter = new ResourceFilterAdapter(this, timeList, false, true);
        rvTrafficTime.setAdapter(filterTimeAdapter);

        rvTrafficStatus.setLayoutManager(new GridLayoutManager(this, 4));
        filterStatusAdapter = new ResourceFilterAdapter(this, statusList, true, true);
        rvTrafficStatus.setAdapter(filterStatusAdapter);

        filterTimeAdapter.setOnItemClickListener(new ResourceFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ResourceFilterItemEntity entity, boolean isMultiSelect, boolean isSelect) {
                if(isSelect){
                    timeFilterSelectEntity = entity;
                }else {
                    timeFilterSelectEntity = null;
                }
            }
        });

        filterStatusAdapter.setOnItemClickListener(new ResourceFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ResourceFilterItemEntity entity, boolean isMultiSelect, boolean isSelect) {
                if(isSelect){
                    boolean isContainAll = false;
                    for(int i = 0; i < statusFilterSelectList.size(); i++){
                        if(statusFilterSelectList.get(i).isAll()){
                            isContainAll = true;
                            break;
                        }
                    }
                    if(isContainAll || entity.isAll()){
                        statusFilterSelectList.clear();
                    }
                    statusFilterSelectList.add(entity);
                }else {
                    statusFilterSelectList.remove(entity);
                }
            }
        });

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    private void assignViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        rvTrafficTime = (RecyclerView) findViewById(R.id.rv_traffic_time);
        rvTrafficStatus = (RecyclerView) findViewById(R.id.rv_traffic_status);
    }

    //获取传过来的筛选条件数据对象
    private TrafficFilterIntentEntity getIntentFilterEntity(){
        Intent intent = getIntent();
        if(null != intent){
            TrafficFilterIntentEntity filterIntentEntity = intent.getParcelableExtra("filterIntentEntity");
            return filterIntentEntity;
        }
        return null;
    }

    /**
     * 初始化时间选项的数据
     */
    private void initTimeData(TrafficFilterIntentEntity filterIntentEntity){
        timeList.add(new ResourceFilterItemEntity(this.getResources().getString(R.string.resource_filter_all), "All", true));
        timeList.add(new ResourceFilterItemEntity(this.getResources().getString(R.string.resource_filter_today), "<=1", false));
        timeList.add(new ResourceFilterItemEntity(this.getResources().getString(R.string.resource_filter_three_day), "<=3", false));
        timeList.add(new ResourceFilterItemEntity(this.getResources().getString(R.string.resource_filter_seven_day), "<=7", false));
        timeList.add(new ResourceFilterItemEntity(this.getResources().getString(R.string.resource_filter_week), ">7", false));

        if(null == filterIntentEntity || null == filterIntentEntity.getTimeFilterSelectEntity()) {
            timeFilterSelectEntity = timeList.get(0);
            timeList.get(0).setInitSelect(true);
        }else {
            timeFilterSelectEntity = filterIntentEntity.getTimeFilterSelectEntity();
            int position = isContainFilterEntity(timeList, timeFilterSelectEntity.getCode());
            if(position >= 0){
                timeList.set(position, timeFilterSelectEntity);
                timeFilterSelectEntity.setInitSelect(true);
            }
        }
    }

    /**
     * 初始化状态选项的数据
     */
    private void initStatusData(TrafficFilterIntentEntity filterIntentEntity){

        statusList.add(new ResourceFilterItemEntity(this.getResources().getString(R.string.resource_filter_all), "All", true));

        //有传过来的状态选择列表
        if(null == filterIntentEntity || null == filterIntentEntity.getStatusFilterSelectList() ||
                filterIntentEntity.getStatusFilterSelectList().size() == 0) {
            statusList.get(0).setInitSelect(true);
            statusFilterSelectList.add(statusList.get(0));
        }else {     //没有传过来的状态选择列表
            statusFilterSelectList = filterIntentEntity.getStatusFilterSelectList();
        }

        if(null != NewCustomerConstants.trafficStatusMap && NewCustomerConstants.trafficStatusMap.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.trafficStatusMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                int position = isContainFilterEntity(statusFilterSelectList, entry.getKey());
                if (position >= 0) {
                    statusFilterSelectList.get(position).setInitSelect(true);
                    statusList.add(statusFilterSelectList.get(position));
                } else {
                    ResourceFilterItemEntity filterItemEntity = new ResourceFilterItemEntity(entry.getValue(), entry.getKey(), false);
                    statusList.add(filterItemEntity);
                }
            }
        }
    }

    /**
     * 过滤条件的列表数据是否包含指定code的对象
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_confirm:
                Intent intent = new Intent();
                TrafficFilterIntentEntity filterIntentEntity = new TrafficFilterIntentEntity(timeFilterSelectEntity, statusFilterSelectList);
                intent.putExtra("filterIntentEntity", filterIntentEntity);
                intent.putExtra("filterString", createFilterString());
                setResult(0, intent);
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
        }
    }

    /**
     * 生成筛选条件的字符串
     * @return
     */
    private String createFilterString(){
        TrafficFilterEntity filterEntity = new TrafficFilterEntity();
        if(null != timeFilterSelectEntity && !timeFilterSelectEntity.isAll()){
            filterEntity.setCreateTime(timeFilterSelectEntity.getCode());
        }
        if(statusFilterSelectList.size() > 0 && !statusFilterSelectList.get(0).isAll()){
            StringBuilder statusFilter = new StringBuilder();
            for(int i = 0; i < statusFilterSelectList.size(); i++){
                statusFilter.append(statusFilterSelectList.get(i).getCode());
                if(i != statusFilterSelectList.size() - 1){
                    statusFilter.append(",");
                }
            }
            filterEntity.setLeadsStatusId(statusFilter.toString());
        }

        try {
            return URLEncoder.encode(GsonUtils.changeEntityToJsonStr(filterEntity), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
    }
}
