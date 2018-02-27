package com.svw.dealerapp.ui.resource.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.ui.resource.adapter.ResourceFilterAdapter;
import com.svw.dealerapp.ui.resource.contract.YellowCardFiterGetCarContract;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;
import com.svw.dealerapp.ui.resource.entity.YellowCardFilterEntity;
import com.svw.dealerapp.ui.resource.entity.YellowCardFilterIntentEntity;
import com.svw.dealerapp.ui.resource.model.YellowCardFilterGetCarModel;
import com.svw.dealerapp.ui.resource.presenter.YellowCardFilterGetCarPresenter;
import com.svw.dealerapp.ui.widget.DatePeriodPickerView;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 5/16/2017.
 */

public class YellowCardFilterActivity extends BaseFilterActivity implements YellowCardFiterGetCarContract.View {

    private TextView tvConfirm;
    private RecyclerView rvYellowCardRank;
    private RelativeLayout rlVipSwitch;
    private View vSwitchBg;
    private ImageView ivSwitchLeftIcon;
    private ImageView ivSwitchRightIcon;

    private ResourceFilterAdapter rankAdapter;
    private List<ResourceFilterItemEntity> followResultSelectList = new ArrayList<>();
        private List<ResourceFilterItemEntity> followResultList = new ArrayList<>();
    private List<ResourceFilterItemEntity> rankList = new ArrayList<>();
    private List<ResourceFilterItemEntity> rankSelectList = new ArrayList<>();

    private boolean isOpenOnlyVip = false;

    private YellowCardFilterGetCarPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new YellowCardFilterGetCarPresenter(this, new YellowCardFilterGetCarModel());
        presenter.initDictionaryData(this, sourceList, followResultList, rankList,
                (YellowCardFilterIntentEntity)filterIntentEntity);

        initSelectItem((YellowCardFilterIntentEntity)filterIntentEntity);

        tvTitle.setText(getResources().getString(R.string.resource_yellow_filter_title));

        rvYellowCardSource.setLayoutManager(new GridLayoutManager(this, 4));
//        rvYellowCardResult.setLayoutManager(new GridLayoutManager(this, 4));
        rvYellowCardRank.setLayoutManager(new GridLayoutManager(this, 4));

        rankAdapter = new ResourceFilterAdapter(this, rankList, true, true);
        rvYellowCardRank.setAdapter(rankAdapter);

        tvConfirm.setOnClickListener(this);
        rlVipSwitch.setOnClickListener(this);
        rlShowSelfSwitch.setOnClickListener(this);

//        ycStatusAdapter.setOnItemClickListener(new ResourceFilterAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, ResourceFilterItemEntity entity, boolean isMultiSelect, boolean isSelect) {
//                dealFilterItemClick(isSelect, followResultSelectList, entity);
//            }
//        });

        rankAdapter.setOnItemClickListener(new ResourceFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ResourceFilterItemEntity entity, boolean isMultiSelect, boolean isSelect) {
                dealFilterItemClick(isSelect, rankSelectList, entity);
                if(rankSelectList.size() > 1 ||
                        (rankSelectList.size() == 1 && !rankSelectList.get(0).isAll())){
                    setCanReset(true);
                }else {
                    checkAndSetCanReset();
                }
            }
        });
    }

    /**
     * 初始化选中的item
     * @param filterIntentEntity
     */
    private void initSelectItem(YellowCardFilterIntentEntity filterIntentEntity){

        //潜客状态
        if (null != filterIntentEntity && null != filterIntentEntity.getFollowResultSelectList() &&
                filterIntentEntity.getFollowResultSelectList().size() > 0) {
            followResultSelectList.addAll(filterIntentEntity.getFollowResultSelectList());
        }else {
            followResultSelectList.add(followResultList.get(0));
        }

        //潜在客户级别
        if (null != filterIntentEntity && null != filterIntentEntity.getRankSelectList() &&
                filterIntentEntity.getRankSelectList().size() > 0) {
            rankSelectList.addAll(filterIntentEntity.getRankSelectList());
        }else {
            rankSelectList.add(rankList.get(0));
        }

        //只显示重点客户
        if(null != filterIntentEntity && filterIntentEntity.isOpenOnlyVip()){
            isOpenOnlyVip = true;
            vSwitchBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_yellow_filter_switch_open_bg));
            ivSwitchRightIcon.setVisibility(View.VISIBLE);
            ivSwitchLeftIcon.setVisibility(View.GONE);
            isOpenOnlyVip = true;
            setCanReset(true);
        }

        initSelectedCommonUI(filterIntentEntity);
    }

    @Override
    public void assignViews() {
        setContentView(R.layout.activity_yellow_card_filter);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvReset = (TextView) findViewById(R.id.tv_reset);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        rvYellowCardSource = (RecyclerView) findViewById(R.id.rv_yellow_card_source);
        rlVipSwitch = (RelativeLayout) findViewById(R.id.rl_vip_switch);
        vSwitchBg = findViewById(R.id.v_switch_bg);
        ivSwitchLeftIcon = (ImageView) findViewById(R.id.iv_switch_left_icon);
        ivSwitchRightIcon = (ImageView) findViewById(R.id.iv_switch_right_icon);
        rlShowSelfSwitch = (RelativeLayout) findViewById(R.id.rl_show_self_switch);
        vShowSelfSwitchBg = findViewById(R.id.v_show_self_switch_bg);
        ivShowSelfSwitchLeftIcon = (ImageView) findViewById(R.id.iv_show_self_switch_left_icon);
        ivShowSelfSwitchRightIcon = (ImageView) findViewById(R.id.iv_show_self_switch_right_icon);
        vShowSelfUnderLine = findViewById(R.id.v_only_self_under_line);
        etCarTypeCode = (EditText) findViewById(R.id.et_car_type_code);
        tvCarName = (TextView) findViewById(R.id.tv_car_name);
        rvYellowCardRank = (RecyclerView) findViewById(R.id.rv_yellow_card_rank);
        llSalesFilter = (LinearLayout) findViewById(R.id.ll_filter_sales);
        tvSalesName = (TextView) findViewById(R.id.tv_sales);
        llSeriesFilter = (LinearLayout) findViewById(R.id.ll_filter_series);
        tvSeriesName = (TextView) findViewById(R.id.tv_series);
        vSalesFilterDivider = findViewById(R.id.v_sales_filter_divider);
        createCardPeriodPicker = (DatePeriodPickerView) findViewById(R.id.dppv_create_time);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rl_vip_switch:    //只显示重点客户开关
                if(isOpenOnlyVip){
                    vSwitchBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_yellow_filter_switch_close_bg));
                    ivSwitchRightIcon.setVisibility(View.GONE);
                    ivSwitchLeftIcon.setVisibility(View.VISIBLE);
                    isOpenOnlyVip = false;
                    checkAndSetCanReset();
                }else {
                    vSwitchBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_yellow_filter_switch_open_bg));
                    ivSwitchRightIcon.setVisibility(View.VISIBLE);
                    ivSwitchLeftIcon.setVisibility(View.GONE);
                    isOpenOnlyVip = true;
                    setCanReset(true);
                }
                break;
            case R.id.tv_confirm:   //确定
                Intent intent = new Intent();
                intent.putExtra("filterString", generateFilterString());
                YellowCardFilterIntentEntity filterIntentEntity = new YellowCardFilterIntentEntity(sourceSelectList,
                        followResultSelectList, rankSelectList, createCardPeriodPicker.getEndTimeDate(),
                        createCardPeriodPicker.getStartTime(), isOpenOnlyVip, createCardPeriodPicker.isSelectAllTime(),
                        inputCarCode, tvCarName.getText().toString(),
                        userId, userName, isOpenShowSelf, seriesCode, seriesName);
                intent.putExtra("filterIntentEntity", filterIntentEntity);
                intent.putExtra("hasFilter", canReset);
                this.setResult(0, intent);
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
        }
    }

    /**
     * 生成filter字符串
     * @return
     */
    private String generateFilterString(){
        YellowCardFilterEntity filterEntity = new YellowCardFilterEntity();
        //潜客级别
        if(rankSelectList.size() > 0 && !rankSelectList.get(0).isAll()){
            StringBuilder customerRank = new StringBuilder();
            for (int i = 0; i < rankSelectList.size(); i++) {
                customerRank.append(rankSelectList.get(i).getCode());
                if (i != rankSelectList.size() - 1) {
                    customerRank.append(",");
                }
            }
            if(!TextUtils.isEmpty(customerRank.toString())) {
                filterEntity.setOppLevel(customerRank.toString());
            }
        }

        //重点客户
        if(isOpenOnlyVip){
            filterEntity.setIsKeyuser("0");
        }

        setCommonFilterValue(filterEntity);

        try {
            String filterString = URLEncoder.encode(GsonUtils.changeEntityToJsonStr(filterEntity), "utf-8");
            return filterString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void resetOtherFilter() {
        resetGridList(rankList, rankSelectList, rankAdapter);
    }

    @Override
    protected void checkAndSetCanResetImpl(){
        if(rankSelectList.size() > 1 ||
                (rankSelectList.size() == 1 && !rankSelectList.get(0).isAll())){
            setCanReset(true);
            return;
        }
        if(isOpenOnlyVip){
            setCanReset(true);
            return;
        }
        setCanReset(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
    }

    @Override
    public void showNoDataToast() {
        ToastUtils.showToast(getResources().getString(R.string.resource_filter_get_car_empty_data));
    }

    @Override
    public void showNetWorkErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void showServerErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.server_error));
    }

    @Override
    public void showTimeOutToast() {
        ToastUtils.showToast(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void refreshView(CarTypesEntity entity) {
        tvCarName.setText(entity.getModelDescCn());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != presenter){
            presenter.onDestroy();
        }
    }
}
