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
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerAddFollowupRecordFragment;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.NewCustomerStepThreePresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

/**
 * 新潜客第3步
 * Created by xupan on 22/05/2017.
 */
@Deprecated
public class NewCustomerStepThreeActivity extends BaseFrameActivity<NewCustomerStepThreePresenter, NewCustomerModel> implements NewCustomerContract.StepThreeView {

    private static final String TAG = "NewCustomerStepThreeActivity";
    private String mFollowupId, mOppId;
    private NewCustomerAddFollowupRecordFragment mFragment;
    private boolean mShouldFinish;
    private Button mNextBt;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setListeners();
        mFragment = NewCustomerAddFollowupRecordFragment.newInstance(null);
        addFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_customer_step_three;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this, "建卡第二页");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "建卡第二页");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TalkingDataUtils.onEvent(this, "建卡第二页-上一步");
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mFollowupId = intent.getStringExtra(NewCustomerConstants.KEY_FOLLOWUP_ID);
            mOppId = intent.getStringExtra(NewCustomerConstants.KEY_OPP_ID);
        }
    }

    private void setListeners() {
        mNextBt = (Button) findViewById(R.id.new_customer_step_3_next_bt);
        mNextBt.setOnClickListener(this);
    }

    private void addFragment() {
        CustomSectionViewGroup addFollowupRecordViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_step_3_container);
        addFollowupRecordViewGroup.disableCollapse();
        addFollowupRecordViewGroup.setTitleText(R.string.new_customer_followup_record_header);
        addFollowupRecordViewGroup.addFragment(mFragment);
    }

    @Override
    public void initView() {
        super.initView();
        mRightIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_title_bar_right_iv:
                showCloseDialog();
                break;
            case R.id.new_customer_step_3_next_bt:
                if (checkDataValidation()) {
                    prepareToSubmit();
                } else {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                }
                TalkingDataUtils.onEvent(this, "建卡第二页-下一步");
                break;
            default:
                break;
        }
    }

    private void showCloseDialog() {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, dialogAdapter);
        dialogAdapter.setDialogMessageText(getString(R.string.new_customer_step_3_cancel_tip));
        dialog.setBtnCancelText(getString(R.string.dialog_cancel));
        dialog.setBtnConfirmText(getString(R.string.new_customer_cancel_exit));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
                TalkingDataUtils.onEvent(NewCustomerStepThreeActivity.this, "建卡第二页-取消提示-取消");
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                goToOppList();
                TalkingDataUtils.onEvent(NewCustomerStepThreeActivity.this, "建卡第二页-取消提示-退出");
            }
        });
        dialog.show();
    }

    //跳转到首页的资源/潜客列表
    private void goToOppList() {
        Intent i = new Intent(NewCustomerStepThreeActivity.this, RdMainActivity.class);
        i.putExtra("firstNavPosition", 1);
        i.putExtra("secondNavPosition", 0);
        startActivity(i);
    }

    @Override
    public void onSubmitFollowupInfoSuccess(String msg) {
        mFragment.onSubmitSuccess();
        if (mShouldFinish) {
            ToastUtils.showToast(getString(R.string.new_customer_create_complete));
            NewCustomerConstants.setFollowupDateStr("");//建卡完成时清空第一步传入的下次跟进日期
            goToOppList();
        } else {
            Intent i = new Intent(this, NewCustomerStepFourActivity.class);
            i.putExtra(NewCustomerConstants.KEY_OPP_ID, mOppId);
            i.putExtra(NewCustomerConstants.KEY_FOLLOWUP_ID, mFollowupId);
            startActivity(i);
        }
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

    private void prepareToSubmit() {
        FollowupCreateReqEntity entity = mFragment.getParameters();
        entity.setFollowupId(mFollowupId);
        entity.setOppId(mOppId);
        //获取第一步生成的日期，以免此时第二步后最后一步不填写导致没有待跟进记录
//        entity.setScheduleDateStr(NewCustomerConstants.getFollowupDateStr());//导致出现两条待跟进
        JLog.d(TAG, "FollowupCreateReqEntity: " + entity);
        mPresenter.submitFollowupInfo(entity);
    }

    /**
     * 检查数据输入完整性
     *
     * @return
     */
    private boolean checkDataValidation() {
        return mFragment.checkDataValidation();
    }

    /**
     * 本页是否已经建卡成功且无需进入第四页
     *
     * @param shouldFinish 是否无需进入第四页
     */
    public void markAsFinished(boolean shouldFinish) {
        mShouldFinish = shouldFinish;
    }

    public void checkIfNeedToEnableSubmit() {
        mNextBt.setEnabled(checkDataValidation());
    }
}
