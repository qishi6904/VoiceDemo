package com.svw.dealerapp.ui.newcustomer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.entity.resource.TrafficEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.base.BaseFrameActivity;
import com.svw.dealerapp.ui.adapter.CustomDialogAdapter;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerBasicInfoFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOppLevelFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerRequirementsFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerStepOneFollowUpPlanFragment;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.NewCustomerPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.ToastUtils;

/**
 * (合卡后已弃用)新潜客第一页
 * Created by xupan on 12/05/2017.
 */
@Deprecated
public class NewCustomerStepOneActivity extends BaseFrameActivity<NewCustomerPresenter, NewCustomerModel> implements NewCustomerContract.View {

    private static final String TAG = "NewCustomerStepOneActivity";
    private Button mNextBt;

    private CarTypesEntity mCarTypeEntity;
    private String mLeadsId;//创建黄卡的入参
    private String mOppId;//更新黄卡的入参
    private String mFollowUpId;//更新黄卡的入参
    private int dealPosition;//从上一页带入，建卡成功时发广播传走
    private int fromFlag;//从上一页带入，建卡成功时发广播传走
    private boolean mCardCreated;//标记是否已经建卡成功，避免返回此页时重复建卡
    private OpportunityDetailEntity mEntity;//手动放入姓名，性别，手机

    private NewCustomerBasicInfoFragment mInfoFragment;
    private NewCustomerRequirementsFragment mRequirementsFragment;
    private NewCustomerOppLevelFragment mOppLevelFragment;
    private NewCustomerStepOneFollowUpPlanFragment mFollowUpPlanFragment;

    private boolean mCarTypeValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_yellow_card);
        getDataFromIntent();
        initFragments();
    }

    private void initFragments() {
        mInfoFragment = NewCustomerBasicInfoFragment.newInstance(mEntity);
        mRequirementsFragment = new NewCustomerRequirementsFragment();
        mOppLevelFragment = new NewCustomerOppLevelFragment();
        mFollowUpPlanFragment = new NewCustomerStepOneFollowUpPlanFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.new_customer_step_1_info_container, mInfoFragment)
                .add(R.id.new_customer_step_1_requirements_container, mRequirementsFragment)
                .add(R.id.new_customer_step_1_opp_level_container, mOppLevelFragment)
                .add(R.id.new_customer_step_1_followup_container, mFollowUpPlanFragment).commit();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
//        mLeadsId = intent.getStringExtra("leadsId");
        dealPosition = intent.getIntExtra("dealPosition", -1);
        fromFlag = intent.getIntExtra("fromFlag", -1);
        Parcelable parcelable = intent.getParcelableExtra("trafficInfoEntity");
        mEntity = new OpportunityDetailEntity();
        if (parcelable instanceof TrafficEntity.TrafficInfoEntity) {
            TrafficEntity.TrafficInfoEntity entity = (TrafficEntity.TrafficInfoEntity) parcelable;
            mEntity.setCustName(entity.getCustName());
            mEntity.setCustGender(entity.getCustGender());
            mEntity.setCustMobile(entity.getCustMobile());
            mEntity.setSrcTypeId(entity.getSrcTypeId());
            mEntity.setChannelId(entity.getChannelId());
            mLeadsId = entity.getLeadsId();
        } else if (parcelable instanceof TaskTrafficEntity.TaskTrafficInfoEntity) {
            TaskTrafficEntity.TaskTrafficInfoEntity entity = (TaskTrafficEntity.TaskTrafficInfoEntity) parcelable;
            mEntity.setCustName(entity.getCustName());
            mEntity.setCustGender(entity.getCustGender());
            mEntity.setCustMobile(entity.getCustMobile());
            mEntity.setSrcTypeId(entity.getSrcTypeId());
            mEntity.setChannelId(entity.getChannelId());
            mLeadsId = entity.getLeadsId();
        }
        JLog.d(TAG, "leadsId: " + mLeadsId);
    }

    public void initView() {
        super.initView();
        mBackIv.setVisibility(View.GONE);
        mRightIv.setVisibility(View.VISIBLE);
        mNextBt = (Button) findViewById(R.id.activity_new_yellow_card_step_one_next_bt);
    }


    public void initListener() {
        super.initListener();
        mNextBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_title_bar_right_iv:
                onBackPressed();
                break;
            case R.id.activity_new_yellow_card_step_one_next_bt:
                //先检查数据是否输入完整
                if (!checkDataValidation()) {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                    break;
                }
                //再检查车型是否输入正确
                if (!mCarTypeValid) {
                    ToastUtils.showToast(getString(R.string.customer_detail_car_type_try_again_hint));
                    break;
                }
                //最后检查下次跟进日期是否输入合法
                if (!mFollowUpPlanFragment.validateDate()) {
                    ToastUtils.showToast(getString(R.string.customer_detail_wrong_date));
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
        if (mCardCreated) {
            super.onBackPressed();
            return;
        }
        final CustomDialog dialog = new CustomDialog(this, new BackDialogAdapter());
        dialog.setBtnConfirmText(getString(R.string.new_customer_cancel_cancel));
        dialog.setBtnCancelText(getString(R.string.new_customer_cancel_confirm));
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

    private class BackDialogAdapter implements CustomDialogAdapter {
        @Override
        public View getDialogView(Context context) {
            return View.inflate(context, R.layout.dialog_new_cusomter_cancel, null);
        }
    }

    @Override
    public void onQuickCreateSuccess() {

    }

    @Override
    public void onQuickCreateError(String message) {

    }

    @Override
    public void onStepOneSubmitSuccess(OpportunityEntity entity) {
//        ToastUtils.showToast(this, "Success");
        //创建成功
        if (!mCardCreated) {
            mCardCreated = true;
            //跳转至后续页面
            Intent i = new Intent(this, NewCustomerStepTwoActivity.class);
            i.putExtra(NewCustomerConstants.CAR_TYPE_ENTITY, mCarTypeEntity);
            mOppId = entity.getOppId();
            mFollowUpId = entity.getFollowupId();
            i.putExtra(NewCustomerConstants.KEY_OPP_ID, entity.getOppId());
            i.putExtra(NewCustomerConstants.KEY_FOLLOWUP_ID, entity.getFollowupId());
            startActivity(i);
            //同时发送广播
            Intent broadcastIntent = new Intent("com.svw.dealerapp.create.yellow.card.success");
            broadcastIntent.putExtra("dealPosition", dealPosition);
            broadcastIntent.putExtra("fromFlag", fromFlag);
            broadcastIntent.putExtra("oppId", mOppId);
            sendBroadcast(broadcastIntent);
        } else {
            //更新成功 跳转至后续页面
            Intent i = new Intent(this, NewCustomerStepTwoActivity.class);
            i.putExtra(NewCustomerConstants.CAR_TYPE_ENTITY, mCarTypeEntity);
            i.putExtra(NewCustomerConstants.KEY_OPP_ID, mOppId);
            i.putExtra(NewCustomerConstants.KEY_FOLLOWUP_ID, mFollowUpId);
            startActivity(i);
        }
    }

    @Override
    public void onStepOneSubmitError(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        if (entity != null) {
            mCarTypeValid = true;
            mCarTypeEntity = entity;
            mRequirementsFragment.onQueryCarTypeSuccess(entity);
        }
    }

    @Override
    public void onQueryCarTypeFailure(String msg) {
        mCarTypeValid = false;
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        final CustomDialog carTypeDialog = new CustomDialog(this, dialogAdapter);
        dialogAdapter.setDialogMessageText(msg);
        carTypeDialog.setBtnCancelText(getString(R.string.dialog_cancel));
        carTypeDialog.setBtnConfirmText(getString(R.string.customer_detail_car_type_try_again));
        carTypeDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                carTypeDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                carTypeDialog.dismiss();
            }
        });
        carTypeDialog.show();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    private void prepareToSubmit() {
        if (mCardCreated) {
            updateCard();
        } else {
            createCard();
        }
    }

    /**
     * 创建黄卡
     */
    private void createCard() {
        OpportunitySubmitReqEntity entity = new OpportunitySubmitReqEntity();
        entity.setLeadsId(mLeadsId);
        OpportunitySubmitReqEntity infoEntity = mInfoFragment.getParameters();
        entity.setCustName(infoEntity.getCustName());//姓名
        entity.setCustGender(infoEntity.getCustGender());//性别
        entity.setCustMobile(infoEntity.getCustMobile());//电话
        entity.setCustAge(infoEntity.getCustAge());//年龄
        entity.setSrcTypeId(infoEntity.getSrcTypeId());//客户来源
        entity.setChannelId(infoEntity.getChannelId());//媒体
        entity.setRecomName(infoEntity.getRecomName());//基盘姓名
        entity.setRecomMobile(infoEntity.getRecomMobile());//基盘手机
        OpportunitySubmitReqEntity requirementsEntity = mRequirementsFragment.getParameters();
        entity.setPurchaseId(requirementsEntity.getPurchaseId());//购买区分
        entity.setPropertyId(requirementsEntity.getPropertyId());//购买性质
        entity.setCarModelId(requirementsEntity.getCarModelId());//意向车型
        OpportunitySubmitReqEntity oppLevelEntity = mOppLevelFragment.getParameters();
        entity.setOppLevel(oppLevelEntity.getOppLevel());//潜客级别
        entity.setIsKeyuser(oppLevelEntity.getIsKeyuser());//是否重点客户
        entity.setFollowupDesc(oppLevelEntity.getFollowupDesc());//继续跟进理由
        OpportunitySubmitReqEntity followupPlanEntity = mFollowUpPlanFragment.getParameters();
        entity.setScheduleDateStr(followupPlanEntity.getScheduleDateStr());//计划日期
        JLog.d(TAG, "create entity: " + entity);
        mPresenter.submitOpportunities(entity);
    }

    /**
     * 更新黄卡
     */
    private void updateCard() {
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
//        entity.setLeadsId(mLeadsId);
        entity.setOppId(mOppId);
        entity.setFollowupId(mFollowUpId);
        OpportunitySubmitReqEntity infoEntity = mInfoFragment.getParameters();
        entity.setCustName(infoEntity.getCustName());//姓名
        entity.setCustGender(infoEntity.getCustGender());//性别
        entity.setCustMobile(infoEntity.getCustMobile());//电话
        entity.setCustAge(infoEntity.getCustAge());//年龄
        entity.setSrcTypeId(infoEntity.getSrcTypeId());//客户来源
        entity.setChannelId(infoEntity.getChannelId());//媒体
        entity.setRecomName(infoEntity.getRecomName());//基盘姓名
        entity.setRecomMobile(infoEntity.getRecomMobile());//基盘手机
        OpportunitySubmitReqEntity requirementsEntity = mRequirementsFragment.getParameters();
        entity.setPurchaseId(requirementsEntity.getPurchaseId());//购买区分
        entity.setPropertyId(requirementsEntity.getPropertyId());//购买性质
        entity.setCarModelId(requirementsEntity.getCarModelId());//意向车型
        OpportunitySubmitReqEntity oppLevelEntity = mOppLevelFragment.getParameters();
        entity.setOppLevel(oppLevelEntity.getOppLevel());//潜客级别
        entity.setIsKeyuser(oppLevelEntity.getIsKeyuser());//是否重点客户
        entity.setFollowupDesc(oppLevelEntity.getFollowupDesc());//继续跟进理由
        OpportunitySubmitReqEntity followupPlanEntity = mFollowUpPlanFragment.getParameters();
        entity.setScheduleDateStr(followupPlanEntity.getScheduleDateStr());//计划日期
        JLog.d(TAG, "update entity: " + entity);
        mPresenter.updateOpportunities(entity);
    }

    /**
     * 检查用户输入是否完整
     *
     * @return true为完整，false不完整
     */
    private boolean checkDataValidation() {
        return mInfoFragment.checkDataValidation() && mRequirementsFragment.checkDataValidation()
                && mOppLevelFragment.checkDataValidation() && mFollowUpPlanFragment.checkDataValidation();
    }

    public void queryCarType(String carTypeId) {
        mPresenter.searchByCarType(carTypeId);
    }

    public void changeScheduleDate(String scheduleDateStr) {
        mFollowUpPlanFragment.setScheduleDateString(scheduleDateStr);
    }

    public void invalidateCarType() {
        mCarTypeValid = false;
    }
}
