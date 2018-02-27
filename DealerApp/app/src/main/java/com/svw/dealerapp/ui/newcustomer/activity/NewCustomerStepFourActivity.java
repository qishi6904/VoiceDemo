package com.svw.dealerapp.ui.newcustomer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.base.BaseFrameActivity;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerFollowUpPlanFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerNewAppointmentFragment;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.NewCustomerStepThreePresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;


/**
 * 新潜客-第四步
 * Created by xupan on 25/05/2017.
 */
@Deprecated
public class NewCustomerStepFourActivity extends BaseFrameActivity<NewCustomerStepThreePresenter, NewCustomerModel> implements NewCustomerContract.StepThreeView {

    private static final String TAG = "NewCustomerStepFourActivity";
    private CustomSectionViewGroup mAppointmentViewGroup;
    private NewCustomerFollowUpPlanFragment mFollowUpPlanFragment;//跟进计划Fragment
    private NewCustomerNewAppointmentFragment mAppointmentFragment;//新建预约Fragment
    private Button mSubmitBt;
    private String mFollowupId, mOppId;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        initFragments();
        addFragments();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_customer_step_four;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this, "建卡第三页");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "建卡第三页");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TalkingDataUtils.onEvent(this, "建卡第三页-上一步");
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mFollowupId = intent.getStringExtra(NewCustomerConstants.KEY_FOLLOWUP_ID);
            mOppId = intent.getStringExtra(NewCustomerConstants.KEY_OPP_ID);
        }
    }

    public void initView() {
        super.initView();
        mRightIv.setVisibility(View.VISIBLE);
        mSubmitBt = (Button) findViewById(R.id.new_customer_step_4_submit_bt);
        mSubmitBt.setOnClickListener(this);
        mSubmitBt.setEnabled(true);
    }

    private void initFragments() {
        mFollowUpPlanFragment = NewCustomerFollowUpPlanFragment.newInstance(null);
        mAppointmentFragment = NewCustomerNewAppointmentFragment.newInstance(null);
    }

    private void addFragments() {
        CustomSectionViewGroup followupPlanViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_step_4_followup_container);
        followupPlanViewGroup.disableCollapse();
        followupPlanViewGroup.setTitleText(R.string.new_customer_next_followup_plan_title);
        followupPlanViewGroup.addFragment(mFollowUpPlanFragment);

        mAppointmentViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_step_4_appointment_container);
        mAppointmentViewGroup.disableCollapse();
        mAppointmentViewGroup.showRightText(true);
        mAppointmentViewGroup.setTitleText(R.string.new_customer_appointment_title);
        mAppointmentViewGroup.addFragment(mAppointmentFragment);
    }

    @Override
    public void onSubmitFollowupInfoSuccess(String msg) {
        ToastUtils.showToast(getString(R.string.new_customer_create_complete));
        NewCustomerConstants.setFollowupDateStr("");//建卡完成时清空第一步传入的下次跟进日期
        goToOppList();
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_title_bar_right_iv:
                showCloseDialog();
                break;
            case R.id.new_customer_step_4_submit_bt:
                TalkingDataUtils.onEvent(this, "建卡第三页-建卡完成");
                if (!checkDataValidation()) {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                    break;
                }
                if (!mFollowUpPlanFragment.validateDate()) {
                    ToastUtils.showToast(getString(R.string.customer_detail_wrong_date_step_four));
                    break;
                }
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

    private void showCloseDialog() {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, dialogAdapter);
        dialogAdapter.setDialogMessageText(getString(R.string.new_customer_step_4_cancel_tip));
        dialog.setBtnCancelText(getString(R.string.dialog_cancel));
        dialog.setBtnConfirmText(getString(R.string.new_customer_cancel_exit));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
                TalkingDataUtils.onEvent(NewCustomerStepFourActivity.this, "建卡第三页-取消提示-取消");
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                goToOppList();
                TalkingDataUtils.onEvent(NewCustomerStepFourActivity.this, "建卡第三页-取消提示-退出");
            }
        });
        dialog.show();
    }

    //跳转到首页的资源/潜客列表
    private void goToOppList() {
        Intent i = new Intent(NewCustomerStepFourActivity.this, RdMainActivity.class);
        i.putExtra("firstNavPosition", 1);
        i.putExtra("secondNavPosition", 0);
        startActivity(i);
    }

    private boolean checkDataValidation() {
        return mFollowUpPlanFragment.checkDataValidation() && mAppointmentFragment.checkDataValidation();
    }

    private void prepareToSubmit() {
        FollowupCreateReqEntity entity = new FollowupCreateReqEntity();
        entity.setOppId(mOppId);
        entity.setFollowupId(mFollowupId);
        FollowupCreateReqEntity entityFromFollowup = mFollowUpPlanFragment.getParameters();
        entity.setScheduleDateStr(entityFromFollowup.getScheduleDateStr());
        entity.setScheduleDesc(entityFromFollowup.getScheduleDesc());
        //虽然都是展厅电话微信短信，但下次跟进计划放在另一个字段里
        entity.setNextModeId(entityFromFollowup.getNextModeId());
        FollowupCreateReqEntity entityFromAppointment = mAppointmentFragment.getParameters();
        entity.setAppmDateStr(entityFromAppointment.getAppmDateStr());
        entity.setAppmTypeId(entityFromAppointment.getAppmTypeId());
        JLog.d(TAG, "entity: " + entity);
        mPresenter.submitFollowupInfo(entity);
    }

    public void checkIfNeedToEnableSubmit() {
        if (mSubmitBt != null) {
            mSubmitBt.setEnabled(checkDataValidation());
        }
    }

    public void enableCustomSectionRightText(boolean enabled) {
        mAppointmentViewGroup.setRightTextEnabled(enabled);
    }

    public void setCustomSectionRightTextListener(View.OnClickListener listener) {
        mAppointmentViewGroup.setRightTextOnClickListener(listener);
    }
}
