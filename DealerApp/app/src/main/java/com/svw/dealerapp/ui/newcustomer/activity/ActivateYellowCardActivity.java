package com.svw.dealerapp.ui.newcustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.contract.ActivateYellowCardContract;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerAddFollowupRecordFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerFollowUpPlanFragment2;
import com.svw.dealerapp.ui.newcustomer.model.ActivateYellowCardModel;
import com.svw.dealerapp.ui.newcustomer.presenter.ActivateYellowCardPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.ToastUtils;

import java.util.HashMap;

/**
 * Created by lijinkui on 2017/12/2.
 */

public class ActivateYellowCardActivity extends BaseActivity implements ActivateYellowCardContract.View {
    private static final String TAG = "ActivateYellowCardActivity";
    private NewCustomerAddFollowupRecordFragment mAddFollowUpRecordFragment; //添加跟进记录Fragment
    private NewCustomerFollowUpPlanFragment2 mFollowUpPlanFragment2;//跟进计划Fragment
    private String mOppStatus;//潜客状态
    private String mOppLevel;//潜客状态
    private String mOppId;//潜客编号
    private Button mSubmitBt;
    private CustomSectionViewGroup mFollowupPlanViewGroup, addFollowupRecordViewGroup;
    private ActivateYellowCardPresenter activateYellowCardPresenter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_activate_yellow_card);
        getDataFromIntent();
        initViews();
        activateYellowCardPresenter = new ActivateYellowCardPresenter(this, new ActivateYellowCardModel());
    }

    @Override
    protected void onStart() {
        super.onStart();
//        checkToResetFollowupCalendars();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mOppStatus = intent.getStringExtra("oppStatus");
        mOppLevel = intent.getStringExtra("oppLevel");
        mOppId = intent.getStringExtra("oppId");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activate_yellow_card;
    }

    //    @Override
    private void initViews() {
//        super.initView();
        if ("11530".equals(mOppStatus)) {
            //战败
            mTitleTv.setText(R.string.yellow_card_defeat_activate);
        } else if ("11540".equals(mOppStatus)) {
            //休眠
            mTitleTv.setText(R.string.yellow_card_sleep_activate);
        }
        mSubmitBt = (Button) findViewById(R.id.bt_activate_yellow_card_submit);
        mSubmitBt.setOnClickListener(this);
        initFragments();
    }

    private void initFragments() {
        HashMap<String, String> param1 = new HashMap<>();
        param1.put("orderStatus", "");
        param1.put("oppStatus", mOppStatus);
        param1.put("seriesId", "");
        mAddFollowUpRecordFragment = NewCustomerAddFollowupRecordFragment.newInstance(param1);
        addFollowupRecordViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_add_record_container);
        addFollowupRecordViewGroup.setTitleText(R.string.customer_detail_title_add_followup_record);
        addFollowupRecordViewGroup.disableCollapse();
        addFollowupRecordViewGroup.addFragment(mAddFollowUpRecordFragment);
        mFollowUpPlanFragment2 = NewCustomerFollowUpPlanFragment2.newInstance(null);
        mFollowupPlanViewGroup = (CustomSectionViewGroup) findViewById(R.id.activity_add_followup_record_next_plan_container);
        mFollowupPlanViewGroup.setTitleText(R.string.new_customer_next_followup_plan_title);
        mFollowupPlanViewGroup.disableCollapse();
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
        FollowupCreateReqEntity options = new FollowupCreateReqEntity();
        options.setOppId(mOppId);
        FollowupCreateReqEntity optionsFromAddRecord = mAddFollowUpRecordFragment.getParameters();
        options.setModeId(optionsFromAddRecord.getModeId());
        options.setResults(optionsFromAddRecord.getResults());
        options.setRemark(optionsFromAddRecord.getRemark());
        if (!TextUtils.isEmpty(optionsFromAddRecord.getIsTestdrive())) {
            options.setIsTestdrive(optionsFromAddRecord.getIsTestdrive());
        }
        FollowupCreateReqEntity optionsFromFollowupPlan = mFollowUpPlanFragment2.getParameters();
        options.setScheduleDateStr(optionsFromFollowupPlan.getScheduleDateStr());
        options.setScheduleDesc(optionsFromFollowupPlan.getScheduleDesc());
        options.setNextModeId(optionsFromFollowupPlan.getNextModeId());//下次跟进计划方式
        activateYellowCardPresenter.activateYellowCard(this, options);
    }

    private boolean checkDataValidation() {
        return mAddFollowUpRecordFragment.checkDataValidation()
                && mFollowUpPlanFragment2.checkDataValidation();
    }

    public void checkIfNeedToEnableSubmit() {
        if (mSubmitBt != null) {
            mSubmitBt.setEnabled(checkDataValidation());
        }
    }

    public void checkToResetFollowupCalendars() {
        if (TextUtils.isEmpty(mOppLevel) || TextUtils.isEmpty(mOppStatus)) {
            return;
        }
        int[] delays = CommonUtils.calculateDelayedDays(false, mOppLevel, mOppStatus);
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
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
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
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_timeout));
    }

    @Override
    public void showNetWorkErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.server_error));
    }

    @Override
    public void activateYellowCardSuccess(String msg) {
        ToastUtils.showToast(msg);
        mAddFollowUpRecordFragment.refreshAllLists();//添加跟进记录后刷新资源页全部列表
        refreshSearchYC();
        finish();
    }

    @Override
    public void activateYellowCardFail(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void activateYellowCardTimeout() {
        ToastUtils.showToast(getResources().getString(R.string.timeout_error));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activateYellowCardPresenter != null) {
            activateYellowCardPresenter.onDestroy();
        }
    }

    //刷新search yellow card page
    private void refreshSearchYC(){
        Intent i = new Intent("com.svw.dealerapp.search.yellowcard.refresh");
        sendBroadcast(i);
    }
}
