package com.svw.dealerapp.ui.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.order.CancelOrderReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerAddFollowupRecordFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerFollowUpPlanFragment2;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOppLevelFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.RdNewCustomerAddFollowupRecordFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.RdNewCustomerFollowUpPlanFragment2;
import com.svw.dealerapp.ui.order.contract.CancelOrderFollowupContract;
import com.svw.dealerapp.ui.order.model.CancelOrderFollowupModel;
import com.svw.dealerapp.ui.order.presenter.CancelOrderFollowupPresenter;
import com.svw.dealerapp.ui.resource.fragment.FailedCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.OrderCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.SleepCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.YellowCardFragment;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lijinkui on 2017/12/7.
 */

public class RdOrderCancelActivity2 extends BaseActivity implements CancelOrderFollowupContract.View {

    private static final String TAG = "ActivateYellowCardActivity";

    public static final String FOLLOW_RESULT_CONTINUE = "15546";
    public static final String FOLLOW_RESULT_FAILED = "15570";
    public static final String FOLLOW_RESULT_SLEEP = "15590";

    private NewCustomerOppLevelFragment mOppLevelFragment;//潜客等级Fragment
    private RdNewCustomerAddFollowupRecordFragment mAddFollowUpRecordFragment; //添加跟进记录Fragment
    private RdNewCustomerFollowUpPlanFragment2 mFollowUpPlanFragment2;//跟进计划Fragment
    private CustomSectionViewGroup oppLevelViewGroup, mFollowupPlanViewGroup, addFollowupRecordViewGroup;
    private Button mSubmitBt;
    private String oppStatusId, mSeriesId, mOppLevelId;
    private LoadingDialog loadingDialog;
    private CancelOrderFollowupPresenter cancelOrderFollowupPresenter;
    private int dealListPosition;
    private boolean isFromOrderList = false;
    private CancelOrderReqEntity cancelOrderReqEntity;
    private String followResultCode = FOLLOW_RESULT_CONTINUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        initViews();
        cancelOrderFollowupPresenter = new CancelOrderFollowupPresenter(this, new CancelOrderFollowupModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_activity_order_cancel2;
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (null != intent) {
            cancelOrderReqEntity = new CancelOrderReqEntity();
            cancelOrderReqEntity.setOrderId(intent.getStringExtra("orderId"));
            cancelOrderReqEntity.setOppId(intent.getStringExtra("oppId"));
            dealListPosition = intent.getIntExtra("dealListPosition", 0);  //订单列表需要传
            isFromOrderList = intent.getBooleanExtra("isFromOrderList", false);
            oppStatusId = intent.getStringExtra("oppStatusId");
            mSeriesId = intent.getStringExtra("seriesId");
            mOppLevelId = intent.getStringExtra("oppLevelId");
        }
    }

    private void initViews() {
        mTitleTv.setText(R.string.cancel_order_title);
        mSubmitBt = (Button) findViewById(R.id.bt_activate_yellow_card_submit);
        mSubmitBt.setOnClickListener(this);
        initFragments();
    }

    private void initFragments() {
        OpportunityDetailEntity entity = new OpportunityDetailEntity();
        entity.setOppLevel(mOppLevelId);
        mOppLevelFragment = NewCustomerOppLevelFragment.newInstance(entity);
        mOppLevelFragment.setFlag(NewCustomerConstants.FLAG_OPP_LEVEL);
        oppLevelViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_opp_level_container);
        oppLevelViewGroup.setTitleText(R.string.new_customer_opp_level_header);
//        oppLevelViewGroup.disableCollapse();
        oppLevelViewGroup.addFragment(mOppLevelFragment);
        HashMap<String, String> param1 = new HashMap<>();
        param1.put("isFrom", "0");
        param1.put("oppStatus", oppStatusId);
        param1.put("seriesId", mSeriesId);
        mAddFollowUpRecordFragment = RdNewCustomerAddFollowupRecordFragment.newInstance(param1);
        mAddFollowUpRecordFragment.setEnabled(false);
        addFollowupRecordViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_add_record_container);
        addFollowupRecordViewGroup.setTitleText(R.string.customer_detail_title_add_followup_record);
//        addFollowupRecordViewGroup.disableCollapse();
        addFollowupRecordViewGroup.addFragment(mAddFollowUpRecordFragment);
        mFollowUpPlanFragment2 = RdNewCustomerFollowUpPlanFragment2.newInstance(null);
        mFollowupPlanViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_next_plan_container);
        mFollowupPlanViewGroup.setTitleText(R.string.new_customer_next_followup_plan_title);
//        mFollowupPlanViewGroup.disableCollapse();
        mFollowupPlanViewGroup.addFragment(mFollowUpPlanFragment2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkToResetFollowupCalendars();
            }
        }, 200);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_activate_yellow_card_submit:
                if (!checkDataValidation()) {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                    break;
                }
                prepareToSubmit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        SimpleMessageDialogAdapter adapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, adapter);
        adapter.setDialogMessageText(getString(R.string.customer_detail_add_followup_record_back_hint));
        dialog.setBtnCancelText(getString(R.string.new_customer_cancel_confirm));
        dialog.setBtnConfirmText(getString(R.string.new_customer_cancel_cancel));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
                finish();
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void prepareToSubmit() {
        FollowupCreateReqEntity optionsFromAddRecord = mAddFollowUpRecordFragment.getParameters();
        cancelOrderReqEntity.setModeId(optionsFromAddRecord.getModeId());
        String dictId = optionsFromAddRecord.getResults().get(0).getDictId();
        String resultDesc = optionsFromAddRecord.getResults().get(0).getResultDesc();
        String suspendDate = optionsFromAddRecord.getResults().get(0).getSuspendDate();
        cancelOrderReqEntity.setCompetitor(optionsFromAddRecord.getCompetitor());
        if (!TextUtils.isEmpty(optionsFromAddRecord.getIsTestdrive())) {
            cancelOrderReqEntity.setIsTestdrive(optionsFromAddRecord.getIsTestdrive());
        }
        followResultCode = dictId;
        List<CancelOrderReqEntity.CancelOrderFollowupResult> resultList = new ArrayList();
        resultList.add(new CancelOrderReqEntity.CancelOrderFollowupResult(dictId, resultDesc, suspendDate));
        cancelOrderReqEntity.setFollowupResults(resultList);
        //当选择休眠后，不提交下次跟进计划和新建预约相关参数
        if (mFollowupPlanViewGroup.getVisibility() == View.VISIBLE) {
            FollowupCreateReqEntity optionsFromFollowupPlan = mFollowUpPlanFragment2.getParameters();
            cancelOrderReqEntity.setScheduleDateStr(optionsFromFollowupPlan.getScheduleDateStr());
            cancelOrderReqEntity.setNextScheduleDesc(optionsFromFollowupPlan.getScheduleDesc());
            cancelOrderReqEntity.setNextModeId(optionsFromFollowupPlan.getNextModeId());//下次跟进计划方式
        }
        cancelOrderFollowupPresenter.cancelOrderFollowup(cancelOrderReqEntity, dealListPosition);
    }

    private boolean checkDataValidation() {
        if (mFollowupPlanViewGroup.getVisibility() == View.GONE) {
            return mOppLevelFragment.checkDataValidation() && mAddFollowUpRecordFragment.checkDataValidation();
        }
        return mOppLevelFragment.checkDataValidation()
                && mAddFollowUpRecordFragment.checkDataValidation()
                && mFollowUpPlanFragment2.checkDataValidation();
    }

    public void checkIfNeedToEnableSubmit() {
        if (mSubmitBt != null) {
            mSubmitBt.setEnabled(checkDataValidation());
        }
    }

    public void setPlanVisibility(int visibility) {
        mFollowupPlanViewGroup.setVisibility(visibility);
    }

    public void checkToResetFollowupCalendars() {
        String levelCode = mOppLevelFragment.getOppLevel();
        if (TextUtils.isEmpty(levelCode) || TextUtils.isEmpty(oppStatusId)) {
            return;
        }
        int[] delays = CommonUtils.calculateDelayedDays(false, levelCode, oppStatusId);
        if (delays.length != 3) {
            JLog.d(TAG, "checkToResetFollowupCalendars days array length error: " + delays.length);
            return;
        }
        mFollowUpPlanFragment2.setCalendarDelay(delays[0], delays[1], delays[2]);
    }

    @Override
    public void showLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
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
    public void cancelOrderSuccess(int dealListPosition, String orderStatus) {
        if ("3".equals(orderStatus)) {
            ToastUtils.showToast(getResources().getString(R.string.cancel_otd_order_request_success));
        } else {
            ToastUtils.showToast(getResources().getString(R.string.cancel_order_success));
        }
        if (!isFromOrderList) {
            Intent receiverIntent = new Intent(OrderCustomerFragment.REFRESH_FILTER_STRING);
            sendBroadcast(receiverIntent);
        }
        if (RdOrderCancelActivity2.FOLLOW_RESULT_CONTINUE.equals(followResultCode)) {
            Intent continueIntent = new Intent(YellowCardFragment.FRESH_FILTER_STRING);
            sendBroadcast(continueIntent);
        } else if (RdOrderCancelActivity2.FOLLOW_RESULT_FAILED.equals(followResultCode)) {
            Intent failedIntent = new Intent(FailedCustomerFragment.FRESH_FILTER_STRING);
            sendBroadcast(failedIntent);
        } else if (RdOrderCancelActivity2.FOLLOW_RESULT_SLEEP.equals(followResultCode)) {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancelOrderFollowupPresenter != null) {
            cancelOrderFollowupPresenter.onDestroy();
        }
    }
}
