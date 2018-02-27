package com.svw.dealerapp.ui.newcustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.base.BaseFrameActivity;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.contract.CheckBeforeActivateYCContract;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.ui.newcustomer.fragment.CustomerDetailAllAppointmentFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.CustomerDetailFollowUpFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.CustomerDetailHeaderFragment;
import com.svw.dealerapp.ui.newcustomer.model.CheckBeforeActivateYCModel;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.CheckBeforeActivateYCPresenter;
import com.svw.dealerapp.ui.newcustomer.presenter.CustomerDetailPresenterImpl;
import com.svw.dealerapp.ui.resource.fragment.FailedCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.SleepCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.YellowCardFragment;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 黄卡详情页
 * Created by xupan on 06/06/2017.
 */

public class CustomerDetailActivity extends BaseFrameActivity<CustomerDetailPresenterImpl, NewCustomerModel> implements NewCustomerContract.CustomerDetailView, CheckBeforeActivateYCContract.View {

    private static final String TAG = "CustomerDetailActivity";
    private CustomerDetailHeaderFragment mHeaderFragment;
    private CustomerDetailAllAppointmentFragment mAppointmentFragment;
    private CustomerDetailFollowUpFragment mFollowupFragment;
    private ImageView ivActive;

    private OpportunityDetailEntity mEntity;
    private String mOppId;

    private CustomDialog activeDialog;
    private StringCustomDialogAdapter activeDialogAdapter;
    private ImageView mRedDotIv;
    private LinearLayout mBottomTabsLayout;
    private LoadingDialog mLoadingDialog;
    private String leadId;
    private CustomDialog checkBeforeActivateDialog;
    private CheckBeforeActivateYCPresenter checkBeforeActivateYCPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setListeners();
        getDataFromIntent();
        checkBeforeActivateYCPresenter = new CheckBeforeActivateYCPresenter(this, new CheckBeforeActivateYCModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_detail;
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mOppId = intent.getStringExtra("oppId");
        leadId = intent.getStringExtra("leadId");
        JLog.d(TAG, "oppId: " + mOppId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.queryCustomerDetail(mOppId);
        TalkingDataUtils.onPageStart(this, "潜客详情");
    }

    private void initViews() {
        ((TextView) findViewById(R.id.common_title_bar_title_textview)).setText("所有资源");
        mBottomTabsLayout = (LinearLayout) findViewById(R.id.customer_detail_bottom_tabs_layout);
        ivActive = (ImageView) findViewById(R.id.iv_active);
        ivActive.setOnClickListener(this);
    }

    private void setListeners() {
        findViewById(R.id.customer_detail_bottom_info_rl).setOnClickListener(this);
        findViewById(R.id.customer_detail_bottom_requirments_rl).setOnClickListener(this);
        findViewById(R.id.customer_detail_bottom_followup_reason_rl).setOnClickListener(this);
        findViewById(R.id.customer_detail_bottom_remark_rl).setOnClickListener(this);
        mRedDotIv = (ImageView) findViewById(R.id.customer_detail_remark_dot_iv);
    }

    private void initFragments(OpportunityDetailEntity entity) {
        mHeaderFragment = CustomerDetailHeaderFragment.newInstance(entity);
        mAppointmentFragment = CustomerDetailAllAppointmentFragment.newInstance(entity);
        if (!TextUtils.isEmpty(leadId)) {
            entity.setLeadId(leadId);
        }
        mFollowupFragment = CustomerDetailFollowUpFragment.newInstance(entity);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.customer_detail_header_container, mHeaderFragment)
                .add(R.id.customer_detail_appointments_container, mAppointmentFragment)
                .add(R.id.customer_detail_followup_container, mFollowupFragment)
                .commit();
        ivActive.setVisibility(View.GONE);
        //如果为休眠或战败状态且当前用户为该黄卡的owner显示激活按钮
        if (null != entity && ("11540".equals(entity.getOppStatusId()) || "11530".equals(entity.getOppStatusId()))) {
            if (entity.isYellowCardOwner()) {
                ivActive.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initRedDot() {
        if (mEntity == null || TextUtils.isEmpty(mEntity.getIsComment()) || TextUtils.isEmpty(mEntity.getOppOwner())) {
            return;
        }
        String isComment = mEntity.getIsComment();
        String oppOwner = mEntity.getOppOwner();
        if ("1".equals(isComment) && oppOwner.equals(UserInfoUtils.getUserId())) {
            mRedDotIv.setVisibility(View.VISIBLE);
        } else {
            mRedDotIv.setVisibility(View.GONE);
        }
    }

    /**
     * 显示激活的对话框
     */
    private void showActiveDialog(String content) {
        if (null == activeDialog) {
            activeDialogAdapter = new StringCustomDialogAdapter();
            activeDialog = new CustomDialog(this, activeDialogAdapter);
            activeDialog.hideTitleIcon();
            activeDialog.setDialogTitle(this.getResources().getString(R.string.yellow_back_view_active));
        }
        activeDialogAdapter.setContent(content);
        activeDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                activeDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                activeDialog.dismiss();
                if (null != mEntity) {
                    Map<String, Object> options = new HashMap<>();
                    options.put("oppId", mEntity.getOppId());
                    mPresenter.postActiveYellow(options);
                }
            }
        });
        activeDialog.show();
    }

    @Override
    public void onQueryCustomerDetailSuccess(OpportunityDetailEntity entity) {
        mEntity = entity;
        if (null == entity || TextUtils.isEmpty(entity.getOppOwner())) {
            entity.setYellowCardOwner(false);
        } else if (null != entity && !TextUtils.isEmpty(entity.getOppOwner()) &&
                !entity.getOppOwner().equals(UserInfoUtils.getUserId())) {
            entity.setYellowCardOwner(false);
        }

        if (null != entity && "11540".equals(entity.getOppStatusId())) {
            entity.setSleepStatus(true);
        } else if (null != entity && "11530".equals(entity.getOppStatusId())) {
            entity.setFailedStatus(true);
        }

        initFragments(entity);
        initRedDot();
    }

    @Override
    public void onQueryCustomerDetailFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void postActiveSuccess() {
        ToastUtils.showToast(getResources().getString(R.string.yellow_active_success));
        ivActive.setVisibility(View.GONE);
        if ("11530".equals(mEntity.getOppStatusId())) {
            //刷新战败列表
            Intent i = new Intent(FailedCustomerFragment.FRESH_FILTER_STRING);
            sendBroadcast(i);
            Intent i2 = new Intent(YellowCardFragment.FRESH_FILTER_STRING);
            sendBroadcast(i2);
        } else if ("11540".equals(mEntity.getOppStatusId())) {
            //刷新休眠列表
            Intent i = new Intent(SleepCustomerFragment.FRESH_FILTER_STRING);
            sendBroadcast(i);
            Intent i2 = new Intent(YellowCardFragment.FRESH_FILTER_STRING);
            sendBroadcast(i2);
        }
        if (null != mEntity) {
            mEntity.setOppStatusId("11510");
            mEntity.setSleepStatus(false);
            mEntity.setFailedStatus(false);
            initFragments(mEntity);
            initRedDot();
        }
        mPresenter.queryCustomerDetail(mOppId);
    }

    @Override
    public void postActiveFail() {
        ToastUtils.showToast(getResources().getString(R.string.yellow_active_fail));
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
        mBottomTabsLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent i = new Intent(this, CustomerDetailItemActivity.class);
        i.putExtra("entity", mEntity);
        i.putExtra("oppId", mOppId);
        switch (v.getId()) {
            case R.id.customer_detail_bottom_info_rl:
                TalkingDataUtils.onEvent(this, "查看客户基本信息", "潜客详情");
                i.putExtra("fragment", NewCustomerConstants.FRAGMENT_TAG_INFO);
                break;
            case R.id.customer_detail_bottom_requirments_rl:
                TalkingDataUtils.onEvent(this, "查看产品需求", "潜客详情");
                i.putExtra("fragment", NewCustomerConstants.FRAGMENT_TAG_REQUIREMENTS);
                break;
            case R.id.customer_detail_bottom_followup_reason_rl:
                TalkingDataUtils.onEvent(this, "查看继续跟进理由", "潜客详情");
                i.putExtra("fragment", NewCustomerConstants.FRAGMENT_TAG_FOLLOWUP);
                break;
            case R.id.customer_detail_bottom_remark_rl:
                TalkingDataUtils.onEvent(this, "查看备注", "潜客详情");
                i.putExtra("fragment", NewCustomerConstants.FRAGMENT_TAG_REMARK);
                break;
            case R.id.iv_active:
                i = null;
                Map<String, Object> options = new HashMap<>();
                options.put("oppId", mEntity.getOppId());
                checkBeforeActivateYCPresenter.checkBeforeActivateYC(options);
                break;
            default:
                return;
        }
        if (null != i) {
            startActivity(i);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "潜客详情");
    }

    private void showCheckBeforeActivateDialog(String msg) {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        checkBeforeActivateDialog = new CustomDialog(this, dialogAdapter);
        dialogAdapter.setDialogMessageText(msg);
        dialogAdapter.setTextViewPadding(DensityUtil.dp2px(this, 30), 0, DensityUtil.dp2px(this, 30), 0);
        checkBeforeActivateDialog.hideShowCancelBtn();
        checkBeforeActivateDialog.setBtnConfirmText(getResources().getString(R.string.dialog_confirm));
        checkBeforeActivateDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {

            }

            @Override
            public void onConfirmBtnClick() {
                checkBeforeActivateDialog.dismiss();
            }
        });
        checkBeforeActivateDialog.show();
    }

    @Override
    public void checkBeforeActivateYCSuccess(String returnCode, String msg) {
        if ("200".equals(returnCode)) {
            Intent j = new Intent(this, ActivateYellowCardActivity.class);
            j.putExtra("oppId", mEntity.getOppId());
            j.putExtra("oppStatus", mEntity.getOppStatusId());
            j.putExtra("oppLevel", mEntity.getOppLevel());
            startActivity(j);
        } else if ("023004".equals(returnCode)) {
            showCheckBeforeActivateDialog(msg);
        }
    }

    @Override
    public void checkBeforeActivateYCFail(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (checkBeforeActivateYCPresenter != null) {
            checkBeforeActivateYCPresenter.onDestroy();
        }
    }
}
