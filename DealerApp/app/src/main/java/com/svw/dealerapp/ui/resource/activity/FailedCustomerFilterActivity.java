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
import com.svw.dealerapp.ui.resource.contract.DealCusFilterContract;
import com.svw.dealerapp.ui.resource.entity.FailedCusFilterIntentEntity;
import com.svw.dealerapp.ui.resource.entity.FailedCustomerFilterEntity;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;
import com.svw.dealerapp.ui.resource.model.DealCusFilterModel;
import com.svw.dealerapp.ui.resource.presenter.DealCusFilterPresenter;
import com.svw.dealerapp.ui.widget.DatePeriodPickerView;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 5/16/2017.
 */

public class FailedCustomerFilterActivity extends BaseFilterActivity implements DealCusFilterContract.View {

    private TextView tvConfirm;
    private RecyclerView rvFailedReason;
    private DatePeriodPickerView failPeriodDatePicker;

    private ResourceFilterAdapter failedReasonAdapter;
    private List<ResourceFilterItemEntity> reasonList = new ArrayList<>();
    private List<ResourceFilterItemEntity> reasonSelectList = new ArrayList<>();

    private DealCusFilterPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new DealCusFilterPresenter(this, new DealCusFilterModel());
        if(null != filterIntentEntity) {
            presenter.initDictionaryData(this, sourceList, filterIntentEntity.getSourceSelectList());
        }else {
            presenter.initDictionaryData(this, sourceList, null);
        }

        tvTitle.setText(getResources().getString(R.string.resource_failed_cus_filter_title));

        rvYellowCardSource.setLayoutManager(new GridLayoutManager(this, 4));
        rvFailedReason.setLayoutManager(new GridLayoutManager(this, 4));

        if(null != filterIntentEntity) {
            presenter.dealFailedReasonData(this, reasonList,
                    ((FailedCusFilterIntentEntity)filterIntentEntity).getReasonSelectList());
        }else {
            presenter.dealFailedReasonData(this, reasonList, null);
        }
        failedReasonAdapter = new ResourceFilterAdapter(this, reasonList, true, true);

        initSelectItem((FailedCusFilterIntentEntity)filterIntentEntity);

        rvFailedReason.setAdapter(failedReasonAdapter);

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        rlShowSelfSwitch.setOnClickListener(this);
        llSalesFilter.setOnClickListener(this);
        llSeriesFilter.setOnClickListener(this);

        failedReasonAdapter.setOnItemClickListener(new ResourceFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ResourceFilterItemEntity entity, boolean isMultiSelect, boolean isSelect) {
                dealFilterItemClick(isSelect, reasonSelectList, entity);
                if(reasonSelectList.size() > 1 ||
                        (reasonSelectList.size() == 1 && !reasonSelectList.get(0).isAll())){
                    setCanReset(true);
                }else {
                    checkAndSetCanReset();
                }
            }
        });

        setPeriodPickerListener(failPeriodDatePicker);

    }

    /**
     * 初始化选中的item
     * @param filterIntentEntity
     */
    private void initSelectItem(FailedCusFilterIntentEntity filterIntentEntity){
        if (null != filterIntentEntity && null != filterIntentEntity.getReasonSelectList() &&
                filterIntentEntity.getReasonSelectList().size() > 0) {
            reasonSelectList.addAll(filterIntentEntity.getReasonSelectList());
        }else  {
            reasonSelectList.add(reasonList.get(0));
        }

        if(null == filterIntentEntity ||
                (TextUtils.isEmpty(filterIntentEntity.getFailedStartTime()) && null == filterIntentEntity.getFailedEndTimeDate()) ||
                filterIntentEntity.isFailedSelectAllTime()) {
            failPeriodDatePicker.setSelectAllTime(true);

        }else {
            if(!TextUtils.isEmpty(filterIntentEntity.getFailedStartTime())){
                failPeriodDatePicker.setStartTimeStr(filterIntentEntity.getFailedStartTime());
            }

            if(null != filterIntentEntity.getFailedEndTimeDate()){
                failPeriodDatePicker.setEndTimeDate(filterIntentEntity.getFailedEndTimeDate());
            }
            failPeriodDatePicker.setSelectAllTime(false);
        }

        initSelectedCommonUI(filterIntentEntity);
    }

    @Override
    public void assignViews() {
        setContentView(R.layout.activity_failed_cus_filter);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvReset = (TextView) findViewById(R.id.tv_reset);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        rvYellowCardSource = (RecyclerView) findViewById(R.id.rv_yellow_card_source);
        rvFailedReason = (RecyclerView) findViewById(R.id.rv_failed_reason);
        rlShowSelfSwitch = (RelativeLayout) findViewById(R.id.rl_show_self_switch);
        vShowSelfSwitchBg = findViewById(R.id.v_show_self_switch_bg);
        ivShowSelfSwitchLeftIcon = (ImageView) findViewById(R.id.iv_show_self_switch_left_icon);
        ivShowSelfSwitchRightIcon = (ImageView) findViewById(R.id.iv_show_self_switch_right_icon);
        etCarTypeCode = (EditText) findViewById(R.id.et_car_type_code);
        tvCarName = (TextView) findViewById(R.id.tv_car_name);
        vShowSelfUnderLine = findViewById(R.id.v_only_self_under_line);
        llSalesFilter = (LinearLayout) findViewById(R.id.ll_filter_sales);
        tvSalesName = (TextView) findViewById(R.id.tv_sales);
        llSeriesFilter = (LinearLayout) findViewById(R.id.ll_filter_series);
        tvSeriesName = (TextView) findViewById(R.id.tv_series);
        vSalesFilterDivider = findViewById(R.id.v_sales_filter_divider);
        createCardPeriodPicker = (DatePeriodPickerView) findViewById(R.id.dppv_create_time);
        failPeriodDatePicker = (DatePeriodPickerView) findViewById(R.id.dppv_delivery_time);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_cancel:    //取消
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_confirm:   //确定
                Intent intent = new Intent();
                intent.putExtra("filterString", generateFilterString());
                FailedCusFilterIntentEntity filterIntentEntity = new FailedCusFilterIntentEntity(sourceSelectList, reasonSelectList,
                        createCardPeriodPicker.getEndTimeDate(), createCardPeriodPicker.getStartTime(), createCardPeriodPicker.isSelectAllTime(),
                        failPeriodDatePicker.getEndTimeDate(), failPeriodDatePicker.getStartTime(), failPeriodDatePicker.isSelectAllTime(),
                        isOpenShowSelf, inputCarCode, tvCarName.getText().toString(), userId, userName, seriesCode, seriesName);
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
        FailedCustomerFilterEntity filterEntity = new FailedCustomerFilterEntity();
//        DealCustomerFilterEntity filterEntity = new DealCustomerFilterEntity("15550");
        //战败原因
        if(reasonSelectList.size() > 0 && !reasonSelectList.get(0).isAll()) {
            StringBuilder reason = new StringBuilder();
            for (int i = 0; i < reasonSelectList.size(); i++) {
                reason.append(reasonSelectList.get(i).getCode());
                if (i != reasonSelectList.size() - 1) {
                    reason.append(",");
                }
            }
            if(!TextUtils.isEmpty(reason.toString())) {
                filterEntity.setFailureDesc(reason.toString());
            }
        }
        //预计交车时间
        if(!failPeriodDatePicker.isSelectAllTime()){
            if(!TextUtils.isEmpty(failPeriodDatePicker.getStartTime())){
                String startFormatTime = DateUtils.dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy年MM月dd日",
                        failPeriodDatePicker.getStartTime());
                if(!TextUtils.isEmpty(startFormatTime)) {
                    filterEntity.setFailureTimeFrom(startFormatTime);
                }
            }
            //结束时间转为已选时间的下一天的0点0分0秒
            if(null != failPeriodDatePicker.getEndTimeDate()) {
                String endTime = DateUtils.addDay("yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                        failPeriodDatePicker.getEndTimeDate(), 0);
                if(!TextUtils.isEmpty(endTime)){
                    filterEntity.setFailureTimeTo(endTime);
                }
            }
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
        failPeriodDatePicker.reset();
        resetGridList(reasonList, reasonSelectList, failedReasonAdapter);
    }

    @Override
    protected void checkAndSetCanResetImpl() {
        if(!TextUtils.isEmpty(failPeriodDatePicker.getStartTime()) ||
                null != failPeriodDatePicker.getEndTimeDate()) {
            setCanReset(true);
            return;
        }
        if(reasonSelectList.size() > 1 ||
                (reasonSelectList.size() == 1 && !reasonSelectList.get(0).isAll())){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(null != data) {
            if (requestCode == salesFilterCode) {
                userId = data.getStringExtra("userId");
                userName = data.getStringExtra("userName");
                tvSalesName.setText(userName);
            } else if(requestCode == seriesFilterCode) {
                seriesCode = data.getStringExtra("seriesCode");
                seriesName = data.getStringExtra("seriesName");
                tvSeriesName.setText(seriesName);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != presenter){
            presenter.onDestroy();
        }
        if(null != failPeriodDatePicker){
            failPeriodDatePicker.dismissPicker();
        }
    }
}
