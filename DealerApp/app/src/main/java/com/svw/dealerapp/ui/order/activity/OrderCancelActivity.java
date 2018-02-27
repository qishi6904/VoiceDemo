package com.svw.dealerapp.ui.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.order.CancelOrderReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.order.contract.CancelOrderFollowupContract;
import com.svw.dealerapp.ui.order.model.CancelOrderFollowupModel;
import com.svw.dealerapp.ui.order.presenter.CancelOrderFollowupPresenter;
import com.svw.dealerapp.ui.resource.adapter.ResourceFilterAdapter;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;
import com.svw.dealerapp.ui.resource.fragment.FailedCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.OrderCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.SleepCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.YellowCardFragment;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForDatePicker;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by qinshi on 9/29/2017.
 */
@Deprecated
public class OrderCancelActivity extends BaseActivity implements CancelOrderFollowupContract.View {

    public static final String FOLLOW_RESULT_CONTINUE = "15546";
    public static final String FOLLOW_RESULT_FAILED = "15570";
    public static final String FOLLOW_RESULT_SLEEP = "15590";

    private ImageView ivBack;
    private CustomItemViewForOptionsPicker pickerContactWay;
    private RecyclerView rvFollowResult;
    private CustomItemViewForOptionsPicker pickerFailedReason;
    private LinearLayout llNextFollowUp;
    private LinearLayout llSleep;
    private CustomItemViewForDatePicker pickerNextFollowTime;
    private CustomItemViewForOptionsPicker pickerFollowWay;
    private CustomItemViewForOptionsPicker pickerSleepReason;
    private CustomItemViewForDatePicker pickerSleepTime;
    private Button btnSubmit;
    private View vFailedReasonDivider;
    private EditTextWithMicLayout etFollowPlanMic;
    private ResourceFilterAdapter followResultAdapter;
    private String followResultCode = FOLLOW_RESULT_CONTINUE;

    private CancelOrderReqEntity cancelOrderReqEntity;
    private LoadingDialog loadingDialog;
    private int dealListPosition;
    private CancelOrderFollowupContract.Presenter presenter;

    private boolean isFromOrderList = false;
    private String oppStatusId;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cancel);

        presenter = new CancelOrderFollowupPresenter(this, new CancelOrderFollowupModel());

        Intent intent = getIntent();
        if(null != intent) {
            cancelOrderReqEntity = new CancelOrderReqEntity();
            cancelOrderReqEntity.setOrderId(intent.getStringExtra("orderId"));
            cancelOrderReqEntity.setOppId(intent.getStringExtra("oppId"));
            dealListPosition = intent.getIntExtra("dealListPosition", 0);  //订单列表需要传
            isFromOrderList = intent.getBooleanExtra("isFromOrderList", false);
            oppStatusId = intent.getStringExtra("oppStatusId");
        }

        assignViews();

        followResultAdapter.setOnItemClickListener(new ResourceFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ResourceFilterItemEntity entity, boolean isMultiSelect, boolean isSelect) {
                if(FOLLOW_RESULT_CONTINUE.equals(entity.getCode())){
                    if(isSelect) {
                        pickerFailedReason.setVisibility(View.GONE);
                        llSleep.setVisibility(View.GONE);
                        llNextFollowUp.setVisibility(View.VISIBLE);
                        followResultCode = entity.getCode();
                    }
                }else if(FOLLOW_RESULT_FAILED.equals(entity.getCode())) {
                    if(isSelect) {
                        pickerFailedReason.setVisibility(View.VISIBLE);
                        llNextFollowUp.setVisibility(View.GONE);
                        llSleep.setVisibility(View.GONE);
                        followResultCode = entity.getCode();
                    }
                }else if(FOLLOW_RESULT_SLEEP.equals(entity.getCode())) {
                    if(isSelect) {
                        llSleep.setVisibility(View.VISIBLE);
                        llNextFollowUp.setVisibility(View.GONE);
                        pickerFailedReason.setVisibility(View.GONE);
                        followResultCode = entity.getCode();
                    }
                }
                setBtnSubmitColor();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFinishInput(followResultCode)){
                    cancelOrderReqEntity.setModeId(pickerContactWay.getInputData());
                    cancelOrderReqEntity.setNextScheduleDesc(etFollowPlanMic.getText());
                    cancelOrderReqEntity.setNextModeId(pickerFollowWay.getInputData());
                    cancelOrderReqEntity.setScheduleDateStr(pickerNextFollowTime.getInputData());
                    List<CancelOrderReqEntity.CancelOrderFollowupResult> resultList = new ArrayList();
                    if(FOLLOW_RESULT_CONTINUE.equals(followResultCode)) {
                        resultList.add(new CancelOrderReqEntity.CancelOrderFollowupResult(followResultCode, ""));
                    }else if(FOLLOW_RESULT_FAILED.equals(followResultCode)){
                        resultList.add(new CancelOrderReqEntity.CancelOrderFollowupResult(followResultCode, pickerFailedReason.getInputData()));
                    }else if(FOLLOW_RESULT_SLEEP.equals(followResultCode)){
                        resultList.add(new CancelOrderReqEntity.CancelOrderFollowupResult(followResultCode, pickerSleepReason.getInputData(), pickerSleepTime.getInputData()));
                    }
                    cancelOrderReqEntity.setFollowupResults(resultList);
                    presenter.cancelOrderFollowup(cancelOrderReqEntity, dealListPosition);
                }
            }
        });

        etFollowPlanMic.setOnTextChangeListener(new EditTextWithMicLayout.OnTextChangeListener() {
            @Override
            public void onAfterTextChange(CharSequence s) {
                setBtnSubmitColor();
            }
        });

//        pickerContactWay.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
//            @Override
//            public void onDateChanged(Object object) {
//                setBtnSubmitColor();
//            }
//        });
        pickerNextFollowTime.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnSubmitColor();
            }
        });
        pickerFailedReason.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnSubmitColor();
            }
        });
        pickerFollowWay.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnSubmitColor();
            }
        });
        pickerSleepReason.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnSubmitColor();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setBtnSubmitColor() {
        if(isFinishInput(followResultCode)) {
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.mine_blue));
        }else {
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.gray_999));
        }
    }

    private boolean isFinishInput(String code) {
//        String contractWay = pickerContactWay.getInputData();
//        if(TextUtils.isEmpty(contractWay)) {
//            return false;
//        }
        if(FOLLOW_RESULT_CONTINUE.equals(code)){
            String followTime = pickerNextFollowTime.getInputData();
            if(TextUtils.isEmpty(followTime)) {
                return false;
            }
            String followWay = pickerFollowWay.getInputData();
            if(TextUtils.isEmpty(followWay)) {
                return false;
            }
            String followPlan = etFollowPlanMic.getText().toString();
            if(TextUtils.isEmpty(followPlan)) {
                return false;
            }
        }else if(FOLLOW_RESULT_FAILED.equals(code)){
            String failedReason = pickerFailedReason.getInputData();
            if(TextUtils.isEmpty(failedReason)) {
                return false;
            }
        }else if(FOLLOW_RESULT_SLEEP.equals(code)){
            String sleepReason = pickerSleepReason.getInputData();
            if(TextUtils.isEmpty(sleepReason)) {
                return false;
            }
        }
        return true;
    }

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        pickerContactWay = (CustomItemViewForOptionsPicker) findViewById(R.id.picker_contact_way);
        rvFollowResult = (RecyclerView) findViewById(R.id.rv_yellow_card_source);
        pickerFailedReason = (CustomItemViewForOptionsPicker) findViewById(R.id.picker_failed_reason);
        llNextFollowUp = (LinearLayout) findViewById(R.id.ll_next_follow_up);
        llSleep = (LinearLayout) findViewById(R.id.ll_sleep);
        pickerNextFollowTime = (CustomItemViewForDatePicker) findViewById(R.id.picker_next_follow_time);
        pickerFollowWay = (CustomItemViewForOptionsPicker) findViewById(R.id.picker_follow_way);
        pickerSleepReason = (CustomItemViewForOptionsPicker) findViewById(R.id.picker_sleep_reason);
        pickerSleepTime = (CustomItemViewForDatePicker) findViewById(R.id.picker_sleep_time);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        vFailedReasonDivider = findViewById(R.id.v_failed_reason);
        etFollowPlanMic = (EditTextWithMicLayout) findViewById(R.id.et_with_mic);

        pickerContactWay.setTitleText(getResources().getString(R.string.cancel_order_contact_way));
        pickerContactWay.setHintTextForContentView(getResources().getString(R.string.cancel_order_contact_way_hint));
        pickerContactWay.setTextSize(14);
        pickerContactWay.setStarTextSize(18);
        pickerContactWay.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        pickerContactWay.setLeftRightMargin(DensityUtil.dp2px(this, 20),DensityUtil.dp2px(this, 20));
        pickerContactWay.setBottomLineMargin(0, 0);
        pickerContactWay.setMandatory(true);
        ResourceFilterItemEntity entity = presenter.getContactWayMap();
        contactId = entity.getCode();
        pickerContactWay.setStaticData(entity.getCode(), entity.getName());

        pickerNextFollowTime.setMandatory(true);
        pickerNextFollowTime.setTitleText(getResources().getString(R.string.cancel_order_follow_up_time));
        pickerNextFollowTime.setHintTextForContentView(getResources().getString(R.string.cancel_order_follow_up_time_hint));
        Calendar sleepMaxCalendar = Calendar.getInstance();
        sleepMaxCalendar.add(Calendar.DAY_OF_YEAR, NewCustomerConstants.CANCEL_ORDER_NO_FIRST_MAX);
        pickerNextFollowTime.initPicker(new boolean[]{true, true, true, false, false, false}, Calendar.getInstance(), sleepMaxCalendar);
        pickerNextFollowTime.setDateFormatStr("yyyy-MM-dd");
        pickerNextFollowTime.setDateFormatStrForParameter("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        pickerNextFollowTime.setTextSize(14);
        pickerNextFollowTime.setStarTextSize(18);
        pickerNextFollowTime.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        pickerNextFollowTime.setLeftRightMargin(DensityUtil.dp2px(this, 20),DensityUtil.dp2px(this, 20));
        pickerNextFollowTime.setBottomLineMargin(0, 0);
        String sleepDefaultDate = DateUtils.addDay("yyyy-MM-dd'T'HH:mm:ss.SSSZ", new Date(), NewCustomerConstants.CANCEL_ORDER_NO_FIRST_DEFAULT);
        pickerNextFollowTime.setData(sleepDefaultDate);

        pickerFollowWay.setTitleText(getResources().getString(R.string.cancel_order_follow_up_way));
        pickerFollowWay.setHintTextForContentView(getResources().getString(R.string.cancel_order_follow_up_way_hint));
        pickerFollowWay.setTextSize(14);
        pickerFollowWay.setStarTextSize(18);
        pickerFollowWay.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        pickerFollowWay.setLeftRightMargin(DensityUtil.dp2px(this, 20),DensityUtil.dp2px(this, 20));
        pickerFollowWay.setBottomLineMargin(0, 0);
        pickerFollowWay.setMandatory(true);
//        CommonUtils.initOptionsView(NewCustomerConstants.followupMethodMap, pickerFollowWay);
        CommonUtils.initOptionsView(NewCustomerConstants.contactMethodMap, pickerFollowWay);

        pickerFailedReason.setTitleText(getResources().getString(R.string.cancel_order_failed_reason));
        pickerFailedReason.setHintTextForContentView(getResources().getString(R.string.cancel_order_failed_reason_hint));
        pickerFailedReason.setTextSize(14);
        pickerFailedReason.setStarTextSize(18);
        pickerFailedReason.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        pickerFailedReason.setLeftRightMargin(DensityUtil.dp2px(this, 20),DensityUtil.dp2px(this, 20));
        pickerFailedReason.setBottomLineMargin(0, 0);
        pickerFailedReason.setMandatory(true);
        CommonUtils.initOptionsView(NewCustomerConstants.failureReasonsMap, pickerFailedReason);

        pickerSleepTime.setMandatory(true);
        pickerSleepTime.setTitleText(getResources().getString(R.string.cancel_order_sleep_end_time));
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.add(Calendar.DAY_OF_YEAR, NewCustomerConstants.SLEEP_NO_FIRST_MAX);
        pickerSleepTime.initPicker(new boolean[]{true, true, true, false, false, false}, Calendar.getInstance(), maxCalendar);
        pickerSleepTime.setDateFormatStr("yyyy-MM-dd");
        pickerSleepTime.setDateFormatStrForParameter("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        pickerSleepTime.setTextSize(14);
        pickerSleepTime.setStarTextSize(18);
        pickerSleepTime.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        pickerSleepTime.setLeftRightMargin(DensityUtil.dp2px(this, 20),DensityUtil.dp2px(this, 20));
        pickerSleepTime.setBottomLineMargin(0, 0);
        String defaultDate = DateUtils.addDay("yyyy-MM-dd'T'HH:mm:ss.SSSZ", new Date(), NewCustomerConstants.SLEEP_NO_FIRST_DEFAULT);
        pickerSleepTime.setData(defaultDate);

        pickerSleepReason.setTitleText(getResources().getString(R.string.cancel_order_sleep_reason));
        pickerSleepReason.setHintTextForContentView(getResources().getString(R.string.cancel_order_select_sleep_reason));
        pickerSleepReason.setTextSize(14);
        pickerSleepReason.setStarTextSize(18);
        pickerSleepReason.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
        pickerSleepReason.setLeftRightMargin(DensityUtil.dp2px(this, 20),DensityUtil.dp2px(this, 20));
        pickerSleepReason.setBottomLineMargin(0, 0);
        pickerSleepReason.setMandatory(true);
        CommonUtils.initOptionsView(NewCustomerConstants.asleepReasonsMap, pickerSleepReason);

        rvFollowResult.setLayoutManager(new GridLayoutManager(this, 4));

//        List<ResourceFilterItemEntity> followResultList = new ArrayList<>();
//        followResultList.add(new ResourceFilterItemEntity(NewCustomerConstants.contactResultMultiChoicesMap.get(FOLLOW_RESULT_CONTINUE), FOLLOW_RESULT_CONTINUE, false, true));
//        followResultList.add(new ResourceFilterItemEntity(NewCustomerConstants.contactResultMultiChoicesMap.get(FOLLOW_RESULT_FAILED), FOLLOW_RESULT_FAILED, false, false));
//        followResultList.add(new ResourceFilterItemEntity(NewCustomerConstants.contactResultMultiChoicesMap.get(FOLLOW_RESULT_SLEEP), FOLLOW_RESULT_SLEEP, false, false));
        List<ResourceFilterItemEntity> followResultList = presenter.getFollowUpList(contactId, oppStatusId);
        followResultAdapter = new ResourceFilterAdapter(this, followResultList, false, false);
        rvFollowResult.setAdapter(followResultAdapter);

        pickerFailedReason.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this, "取消订单");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "取消订单");
    }

    @Override
    public void showServerErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_fail));
    }

    @Override
    public void showTimeOutToast() {
        ToastUtils.showToast(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void showNetWorkErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.server_error));
    }

    @Override
    public void showLoadingDialog() {
        if(null == loadingDialog){
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void cancelOrderSuccess(int dealListPosition, String orderStatus) {
        if("3".equals(orderStatus)){
            ToastUtils.showToast(getResources().getString(R.string.cancel_otd_order_request_success));
        }else {
            ToastUtils.showToast(getResources().getString(R.string.cancel_order_success));
        }
        if(!isFromOrderList) {
            Intent receiverIntent = new Intent(OrderCustomerFragment.REFRESH_FILTER_STRING);
            sendBroadcast(receiverIntent);
        }
        if(OrderCancelActivity.FOLLOW_RESULT_CONTINUE.equals(followResultCode)) {
            Intent continueIntent = new Intent(YellowCardFragment.FRESH_FILTER_STRING);
            sendBroadcast(continueIntent);
        }else if(OrderCancelActivity.FOLLOW_RESULT_FAILED.equals(followResultCode)) {
            Intent failedIntent = new Intent(FailedCustomerFragment.FRESH_FILTER_STRING);
            sendBroadcast(failedIntent);
        }else if(OrderCancelActivity.FOLLOW_RESULT_SLEEP.equals(followResultCode)) {
            Intent failedIntent = new Intent(SleepCustomerFragment.FRESH_FILTER_STRING);
            sendBroadcast(failedIntent);
        }
        Intent intent = new Intent();
        intent.putExtra("dealListPosition", dealListPosition);
        intent.putExtra("orderStatus", orderStatus);
        setResult(0, intent);
        finish();
    }

    @Override
    public void cancelOrderFailure(String msg) {
        ToastUtils.showToast(msg);
//        ToastUtils.showToast(getResources().getString(R.string.cancel_order_failed));
    }
}
