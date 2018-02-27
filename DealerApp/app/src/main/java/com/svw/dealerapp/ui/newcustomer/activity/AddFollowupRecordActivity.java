package com.svw.dealerapp.ui.newcustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.base.BaseFrameActivity;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerAddFollowupRecordFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerFollowUpPlanFragment2;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerNewAppointmentFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOppLevelFragment;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.NewCustomerStepThreePresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.HashMap;

/**
 * 添加跟进记录Activity
 * Created by xupan on 05/06/2017.
 */
public class AddFollowupRecordActivity extends BaseFrameActivity<NewCustomerStepThreePresenter, NewCustomerModel> implements NewCustomerContract.StepThreeView {

    private static final String TAG = "AddFollowupRecordActivity";
    private NewCustomerOppLevelFragment mOppLevelFragment;//潜客等级Fragment
    private NewCustomerAddFollowupRecordFragment mAddFollowUpRecordFragment; //添加跟进记录Fragment
    private NewCustomerFollowUpPlanFragment2 mFollowUpPlanFragment2;//跟进计划Fragment
    private NewCustomerNewAppointmentFragment mAppointmentFragment;//新建预约Fragment

    private CustomSectionViewGroup mFollowupPlanViewGroup;
    private CustomSectionViewGroup mAppointmentViewGroup;
    private Button mNextBt;
    private String mOppId, mFollowupId, mOppLevelId;
//    private String mOrderStatus;
    private LoadingDialog mLoadingDialog;
    private CustomDialog mAlertDialog;
    private boolean mDateLimitationFlag = false;
    private String mOppStatus;
    private String mSeriesId;
    private String leadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        initViews();
        initFragments();
        addFragments();
        setListeners();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_followup_record;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this, "本次跟进记录页");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "本次跟进记录页");
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mOppId = intent.getStringExtra("oppId");
        mFollowupId = intent.getStringExtra("followupId");
        mOppLevelId = intent.getStringExtra("oppLevelId");
//        mOrderStatus = intent.getStringExtra("orderStatus");
        mOppStatus = intent.getStringExtra("oppStatus");
        mSeriesId = intent.getStringExtra("seriesId");
        leadId = intent.getStringExtra("leadId");
    }

    private void initViews() {
        mTitleTv.setText(R.string.customer_detail_title_add_followup_record);
        mNextBt = (Button) findViewById(R.id.activity_add_followup_record_next_bt);
        mNextBt.setOnClickListener(this);
    }

    private void initFragments() {
        OpportunityDetailEntity entity = new OpportunityDetailEntity();
        entity.setOppLevel(mOppLevelId);
        mOppLevelFragment = NewCustomerOppLevelFragment.newInstance(entity);
        mOppLevelFragment.setFlag(NewCustomerConstants.FLAG_OPP_LEVEL);
        HashMap<String, String> param1 = new HashMap<>();
//        param1.put("orderStatus", mOrderStatus);
        param1.put("oppStatus", mOppStatus);
        param1.put("seriesId", mSeriesId);
        mAddFollowUpRecordFragment = NewCustomerAddFollowupRecordFragment.newInstance(param1);
        mFollowUpPlanFragment2 = NewCustomerFollowUpPlanFragment2.newInstance(null);
        mAppointmentFragment = NewCustomerNewAppointmentFragment.newInstance(null);
    }

    private void addFragments() {
        mFollowupPlanViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_next_plan_container);
        mFollowupPlanViewGroup.setTitleText(R.string.new_customer_next_followup_plan_title);
        mFollowupPlanViewGroup.addFragment(mFollowUpPlanFragment2);
        if (checkIfFollowupPlanOptional()) {
            mFollowupPlanViewGroup.setCollapsed(true);
            mFollowupPlanViewGroup.showRightText(true);
            mFollowupPlanViewGroup.setRightTextEnabled(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFollowUpPlanFragment2.clearSelection();
                }
            }, 100);
        } else {
            mFollowupPlanViewGroup.disableCollapse();
        }

        CustomSectionViewGroup oppLevelViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_opp_level_container);
        oppLevelViewGroup.setTitleText(R.string.new_customer_opp_level_header);
        oppLevelViewGroup.disableCollapse();
        oppLevelViewGroup.addFragment(mOppLevelFragment);

        CustomSectionViewGroup addFollowupRecordViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_add_record_container);
        addFollowupRecordViewGroup.setTitleText(R.string.customer_detail_title_add_followup_record);
        addFollowupRecordViewGroup.disableCollapse();
        addFollowupRecordViewGroup.addFragment(mAddFollowUpRecordFragment);

        mAppointmentViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_new_appointment_container);
        mAppointmentViewGroup.setTitleText(R.string.new_customer_appointment_title);
        mAppointmentViewGroup.showRightText(true);
        mAppointmentViewGroup.setCollapsed(true);
        mAppointmentViewGroup.addFragment(mAppointmentFragment);
    }

    public boolean checkIfFollowupPlanOptional() {
        return ("11514".equals(mOppStatus) || "11516".equals(mOppStatus) || "11518".equals(mOppStatus));
    }

    private void setListeners() {
        mFollowupPlanViewGroup.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFollowUpPlanFragment2.clearSelection();
                v.setEnabled(false);
                checkIfNeedToEnableSubmit();
            }
        });
    }

    /**
     * 验证输入完整性
     *
     * @return true:输入完整
     */
    private boolean checkDataValidation() {
        //当选择休眠后，不检查下次跟进计划和新建预约相关参数是否输入
        if (mFollowupPlanViewGroup.getVisibility() == View.GONE && mAppointmentViewGroup.getVisibility() == View.GONE) {
            return mOppLevelFragment.checkDataValidation() && mAddFollowUpRecordFragment.checkDataValidation();
        }
        return mOppLevelFragment.checkDataValidation()
                && mAddFollowUpRecordFragment.checkDataValidation()
                && mFollowUpPlanFragment2.checkDataValidation()
                && mAppointmentFragment.checkDataValidation();
    }

    private void prepareToSubmit() {
        FollowupCreateReqEntity options = new FollowupCreateReqEntity();
        options.setOppId(mOppId);
        options.setFollowupId(mFollowupId);
        options.setLeadsId(leadId);
        options.setIsNextFollowup(mFollowupPlanViewGroup.isRightTextEnabled() ? "1" : "0");//下次跟进计划是否填写
        OpportunitySubmitReqEntity optionsFromOppLevel = mOppLevelFragment.getParameters();
        options.setOppLevel(optionsFromOppLevel.getOppLevel());
        FollowupCreateReqEntity optionsFromAddRecord = mAddFollowUpRecordFragment.getParameters();
        options.setModeId(optionsFromAddRecord.getModeId());
        options.setResults(optionsFromAddRecord.getResults());
        options.setRemark(optionsFromAddRecord.getRemark());
        options.setCompetitor(optionsFromAddRecord.getCompetitor());
        if (!TextUtils.isEmpty(optionsFromAddRecord.getIsTestdrive())) {
            options.setIsTestdrive(optionsFromAddRecord.getIsTestdrive());
        }
        //当选择休眠后，不提交下次跟进计划和新建预约相关参数
        if (mFollowupPlanViewGroup.getVisibility() == View.VISIBLE &&
                mAppointmentViewGroup.getVisibility() == View.VISIBLE) {
            FollowupCreateReqEntity optionsFromFollowupPlan = mFollowUpPlanFragment2.getParameters();
            options.setScheduleDateStr(optionsFromFollowupPlan.getScheduleDateStr());
            options.setScheduleDesc(optionsFromFollowupPlan.getScheduleDesc());
            options.setNextModeId(optionsFromFollowupPlan.getNextModeId());//下次跟进计划方式
            FollowupCreateReqEntity optionsFromNewAppm = mAppointmentFragment.getParameters();
            options.setAppmDateStr(optionsFromNewAppm.getAppmDateStr());
            options.setAppmTypeId(optionsFromNewAppm.getAppmTypeId());
        }
        mPresenter.submitFollowupInfo(options);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_add_followup_record_next_bt:
                TalkingDataUtils.onEvent(this, "本次跟进记录页-确定");
                if (!checkDataValidation()) {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                    break;
                }
//                if (!TextUtils.isEmpty(mAddFollowUpRecordFragment.checkOrderCorrectness())) {
//                    showAlertDialog(mAddFollowUpRecordFragment.checkOrderCorrectness());
//                    break;
//                }
//                if (mDateLimitationFlag && !mFollowUpPlanFragment.validateDate()) {
//                    ToastUtils.showToast(getString(R.string.customer_detail_wrong_date));
//                    break;
//                }
                if (!mAppointmentFragment.validateDate()) {
                    ToastUtils.showToast(getString(R.string.add_followup_record_invalid_appointment_time));
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
                TalkingDataUtils.onEvent(AddFollowupRecordActivity.this, "本次跟进记录页-返回提示-丢弃");
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                TalkingDataUtils.onEvent(AddFollowupRecordActivity.this, "本次跟进记录页-返回提示-取消");
            }
        });
        dialog.show();
    }

    @Override
    public void onSubmitFollowupInfoSuccess(String msg) {
        if (!TextUtils.isEmpty(leadId)) {
            //通知搜索页面客源和黄卡关联成功
            Intent intent1 = new Intent("com.svw.dealerapp.leadId.relate.success");
            this.sendBroadcast(intent1);
            //通知客源列表页自动刷新
            Intent intent2 = new Intent("com.svw.dealerapp.task.leads.refresh");
            this.sendBroadcast(intent2);
        }
        ToastUtils.showToast(msg);
        mAddFollowUpRecordFragment.refreshAllLists();//添加跟进记录后刷新资源页全部列表
        finish();
    }

    @Override
    public void onSubmitFollowupInfoFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onRequestStart() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    @Override
    public void onRequestEnd() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Deprecated
    public void changeScheduleDate(String scheduleDateStr) {
//        mFollowUpPlanFragment.setScheduleDateString(scheduleDateStr);
    }

    /**
     * 设置跟进计划和预约视图的可见性
     *
     * @param visibility
     */
    public void setPlanAndScheduleVisibility(int visibility) {
        mFollowupPlanViewGroup.setVisibility(visibility);
        mAppointmentViewGroup.setVisibility(visibility);
    }

    public void checkIfNeedToEnableSubmit() {
        if (mNextBt != null) {
            mNextBt.setEnabled(checkDataValidation());
        }
    }

    public void enableCustomSectionRightText(boolean enabled) {
        mAppointmentViewGroup.setRightTextEnabled(enabled);
    }

    public void enableFollowupPlanRightText(boolean enabled) {
        mFollowupPlanViewGroup.setRightTextEnabled(enabled);
    }

    public void setCustomSectionRightTextListener(View.OnClickListener listener) {
        mAppointmentViewGroup.setRightTextOnClickListener(listener);
    }

    private void showAlertDialog(String text) {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        mAlertDialog = new CustomDialog(this, dialogAdapter);
        dialogAdapter.setDialogMessageText(text);
        mAlertDialog.setBtnConfirmText(getResources().getString(R.string.resource_select_filter_confirm));
        mAlertDialog.setBtnCancelText("");
        mAlertDialog.hideShowCancelBtn();
        mAlertDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {

            }

            @Override
            public void onConfirmBtnClick() {
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

    /**
     * 当潜客级别为N时不进行最晚日期校验
     */
    public void setDateLimitationFlag(boolean hasLimitation) {
        mDateLimitationFlag = hasLimitation;
    }

    public void checkToResetFollowupCalendars() {
        String levelCode = mOppLevelFragment.getOppLevel();
        if (TextUtils.isEmpty(levelCode) || TextUtils.isEmpty(mOppStatus)) {
            return;
        }
        int[] delays = CommonUtils.calculateDelayedDays(false, levelCode, mOppStatus);
        if (delays.length != 3) {
            JLog.d(TAG, "checkToResetFollowupCalendars days array length error: " + delays.length);
            return;
        }
        mFollowUpPlanFragment2.setCalendarDelay(delays[0], delays[1], delays[2]);
        if (checkIfFollowupPlanOptional()) {
            mFollowUpPlanFragment2.clearFollowupDateSelection();
        }
    }

}
