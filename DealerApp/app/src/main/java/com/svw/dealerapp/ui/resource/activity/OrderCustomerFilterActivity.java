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
import com.svw.dealerapp.ui.resource.entity.DealCusFilterIntentEntity;
import com.svw.dealerapp.ui.resource.entity.DealCustomerFilterEntity;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;
import com.svw.dealerapp.ui.resource.model.DealCusFilterModel;
import com.svw.dealerapp.ui.resource.presenter.DealCusFilterPresenter;
import com.svw.dealerapp.ui.widget.DatePeriodPickerView;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by qinshi on 5/16/2017.
 */

public class OrderCustomerFilterActivity extends BaseFilterActivity implements DealCusFilterContract.View {

    private TextView tvConfirm;
    private DatePeriodPickerView deliveryPeriodDatePicker;

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

        initSelectItem((DealCusFilterIntentEntity)filterIntentEntity);

        tvTitle.setText(getResources().getString(R.string.resource_order_filter_title));

        rvYellowCardSource.setLayoutManager(new GridLayoutManager(this, 4));

        tvConfirm.setOnClickListener(this);
        rlShowSelfSwitch.setOnClickListener(this);

        setPeriodPickerListener(deliveryPeriodDatePicker);
    }

    /**
     * 初始化选中的item
     * @param filterIntentEntity
     */
    private void initSelectItem(DealCusFilterIntentEntity filterIntentEntity){
        if(null == filterIntentEntity ||
                (TextUtils.isEmpty(filterIntentEntity.getDeliveryStartTime()) && null == filterIntentEntity.getDeliveryEndTimeDate()) ||
                filterIntentEntity.isDeliverySelectAllTime()) {
            deliveryPeriodDatePicker.setSelectAllTime(true);
        }else {
            if(!TextUtils.isEmpty(filterIntentEntity.getDeliveryStartTime())){
                deliveryPeriodDatePicker.setStartTimeStr(filterIntentEntity.getDeliveryStartTime());
            }

            if(null != filterIntentEntity.getDeliveryEndTimeDate()){
                deliveryPeriodDatePicker.setEndTimeDate(filterIntentEntity.getDeliveryEndTimeDate());
            }
            deliveryPeriodDatePicker.setSelectAllTime(false);
        }
        initSelectedCommonUI(filterIntentEntity);

    }

    @Override
    public void assignViews() {
        setContentView(R.layout.activity_order_cus_filter);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvReset = (TextView) findViewById(R.id.tv_reset);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        rvYellowCardSource = (RecyclerView) findViewById(R.id.rv_yellow_card_source);
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
        deliveryPeriodDatePicker = (DatePeriodPickerView) findViewById(R.id.dppv_delivery_time);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_confirm:   //确定
                Intent intent = new Intent();
                intent.putExtra("filterString", generateFilterString());
                DealCusFilterIntentEntity filterIntentEntity = new DealCusFilterIntentEntity(sourceSelectList,
                        createCardPeriodPicker.getEndTimeDate(), createCardPeriodPicker.getStartTime(),
                        createCardPeriodPicker.isSelectAllTime(), deliveryPeriodDatePicker.getEndTimeDate(),
                        deliveryPeriodDatePicker.getStartTime(), deliveryPeriodDatePicker.isSelectAllTime(),
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
        DealCustomerFilterEntity filterEntity = new DealCustomerFilterEntity();
        if(!deliveryPeriodDatePicker.isSelectAllTime()){
            if(!TextUtils.isEmpty(deliveryPeriodDatePicker.getStartTime())){
                String startFormatTime = DateUtils.dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy年MM月dd日",
                        deliveryPeriodDatePicker.getStartTime());
                if(!TextUtils.isEmpty(startFormatTime)) {
                    filterEntity.setDeliveryTimeFrom(startFormatTime);
                }
            }
            //结束时间转为已选时间的下一天的0点0分0秒
            if(null != deliveryPeriodDatePicker.getEndTimeDate()) {
                String endTime = DateUtils.addDay("yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                        deliveryPeriodDatePicker.getEndTimeDate(), 0);
                if(!TextUtils.isEmpty(endTime)){
                    filterEntity.setDeliveryTimeTo(endTime);
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
        deliveryPeriodDatePicker.reset();
    }

    @Override
    protected void checkAndSetCanResetImpl() {
        if(!TextUtils.isEmpty(deliveryPeriodDatePicker.getStartTime()) ||
                null != deliveryPeriodDatePicker.getEndTimeDate()) {
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

        if(null != deliveryPeriodDatePicker){
            deliveryPeriodDatePicker.dismissPicker();
        }
    }
}
