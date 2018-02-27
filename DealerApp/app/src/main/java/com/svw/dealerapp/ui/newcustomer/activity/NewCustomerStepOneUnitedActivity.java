package com.svw.dealerapp.ui.newcustomer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityRelationsBean;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.entity.task.TaskLeadsEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.base.BaseFrameActivity;
import com.svw.dealerapp.ui.adapter.CustomDialogAdapter;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerBasicInfoFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerCurrentCarFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOppLevelFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOtherInfoFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerRequirementsDetailFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerRequirementsFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerStepOneFollowUpPlanFragment;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.NewCustomerPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新潜客第一步（合卡之后的新画面）
 * Created by xupan on 18/07/2017.
 */
@Deprecated
public class NewCustomerStepOneUnitedActivity extends BaseFrameActivity<NewCustomerPresenter, NewCustomerModel> implements NewCustomerContract.View {

    private static final String TAG = "NewCustomerStepOneUnitedActivity";
    private NewCustomerBasicInfoFragment mBasicInfoFragment;
    private NewCustomerRequirementsFragment mRequirementsFragment;
    private NewCustomerCurrentCarFragment mCurrentCarFragment;
    private NewCustomerRequirementsDetailFragment mRequirementDetailsFragment;
    private NewCustomerOppLevelFragment mOppLevelFragment;
    private NewCustomerOtherInfoFragment mOtherInfoFragment;
    private NewCustomerStepOneFollowUpPlanFragment mFollowUpPlanFragment;//未显示在UI上，仅用来提供跟进日期

    private Button mNextBt;
    private CarTypesEntity mCarTypeEntity;
    private String mLeadsId;//创建黄卡的入参
    private String mOppId;//更新黄卡的入参
    private String mFollowUpId;//更新黄卡的入参
    private int dealPosition;//从上一页带入，建卡成功时发广播传走
    private int fromFlag;//从上一页带入，建卡成功时发广播传走
    private boolean mCardCreated;//标记是否已经建卡成功，避免返回此页时重复建卡
    private OpportunityDetailEntity mEntity;//手动放入姓名，性别，手机
    private boolean mCarTypeValid;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        initFragments();
        addFragmentsToViewGroup();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_customer_step_one_united;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this, "建卡第一页");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "建卡第一页");
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        dealPosition = intent.getIntExtra("dealPosition", -1);
        fromFlag = intent.getIntExtra("fromFlag", -1);
        Parcelable parcelable = intent.getParcelableExtra("trafficInfoEntity");
        if (parcelable instanceof TaskLeadsEntity.TaskLeadsInfoEntity) {
            TaskLeadsEntity.TaskLeadsInfoEntity entity = (TaskLeadsEntity.TaskLeadsInfoEntity) parcelable;
            mEntity = new OpportunityDetailEntity();
            mEntity.setCustName(entity.getCustName());
            mEntity.setCustGender(entity.getCustGender());
            mEntity.setCustMobile(entity.getCustMobile());
            mEntity.setSrcTypeId(entity.getSrcTypeId());
            mEntity.setChannelId(entity.getChannelId());
            mEntity.setCarModelId(entity.getCarModelId());
            mEntity.setRecomName(entity.getRecomName());
            mEntity.setRecomMobile(entity.getRecomMobile());
            mEntity.setActivityId(entity.getActivityId());
            mEntity.setActivitySubject(entity.getActivitySubject());
            mEntity.setSeriesId(entity.getSeriesId());
            mLeadsId = entity.getLeadsId();
        } else if (parcelable instanceof TaskTrafficEntity.TaskTrafficInfoEntity) {
            TaskTrafficEntity.TaskTrafficInfoEntity entity = (TaskTrafficEntity.TaskTrafficInfoEntity) parcelable;
            mEntity = new OpportunityDetailEntity();
            mEntity.setCustName(entity.getCustName());
            mEntity.setCustGender(entity.getCustGender());
            mEntity.setCustMobile(entity.getCustMobile());
            mEntity.setSrcTypeId(entity.getSrcTypeId());
            mEntity.setChannelId(entity.getChannelId());
            mEntity.setCarModelId(entity.getCarModelId());
            mLeadsId = entity.getLeadsId();
        }
        JLog.d(TAG, "leadsId: " + mLeadsId);
    }

    private void initFragments() {
        mBasicInfoFragment = NewCustomerBasicInfoFragment.newInstance(mEntity);
        mRequirementsFragment = NewCustomerRequirementsFragment.newInstance(null);
        mRequirementsFragment.hideCarTypeRow();
        mCurrentCarFragment = NewCustomerCurrentCarFragment.newInstance(null);
        mRequirementDetailsFragment = NewCustomerRequirementsDetailFragment.newInstance(mEntity);
        mOppLevelFragment = NewCustomerOppLevelFragment.newInstance(null);
        mOtherInfoFragment = NewCustomerOtherInfoFragment.newInstance(null);
        mFollowUpPlanFragment = new NewCustomerStepOneFollowUpPlanFragment();
        getSupportFragmentManager().beginTransaction()
                //不在UI上显示，只为了获取下次跟进时间
                .add(R.id.new_customer_step_1_requirements_container, mFollowUpPlanFragment).hide(mFollowUpPlanFragment)
                .commit();
    }

    private void addFragmentsToViewGroup() {
        CustomSectionViewGroup basicInfoViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_step_1_basic_info_container);
        basicInfoViewGroup.setTitleText(R.string.new_customer_basic_info_header);
        basicInfoViewGroup.addFragment(mBasicInfoFragment);
        CustomSectionViewGroup requirementsViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_step_1_requirements_container);
        requirementsViewGroup.setTitleText(R.string.new_customer_requirements_header);
        requirementsViewGroup.addFragment(mRequirementsFragment);
        requirementsViewGroup.addFragment(mCurrentCarFragment);
        requirementsViewGroup.showFragment(mCurrentCarFragment, false);
        requirementsViewGroup.addFragment(mRequirementDetailsFragment);
        CustomSectionViewGroup oppLevelViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_step_1_opp_level_container);
        oppLevelViewGroup.setTitleText(R.string.new_customer_opp_level_header);
        oppLevelViewGroup.addFragment(mOppLevelFragment);
        CustomSectionViewGroup otherInfoViewGroup = (CustomSectionViewGroup) findViewById(R.id.new_customer_step_1_others_container);
        otherInfoViewGroup.setTitleText(R.string.new_customer_intention_other_header);
        otherInfoViewGroup.addFragment(mOtherInfoFragment);
    }

    @Override
    public void initView() {
        super.initView();
        mBackIv.setVisibility(View.GONE);
        mRightIv.setVisibility(View.VISIBLE);
        mNextBt = (Button) findViewById(R.id.activity_new_yellow_card_step_one_next_bt);
    }

    @Override
    public void initListener() {
        super.initListener();
        mNextBt.setOnClickListener(this);
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
                TalkingDataUtils.onEvent(NewCustomerStepOneUnitedActivity.this, "建卡第一页-取消提示-丢弃");
                dialog.dismiss();
                finish();
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                TalkingDataUtils.onEvent(NewCustomerStepOneUnitedActivity.this, "建卡第一页-取消提示-取消");
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_title_bar_right_iv:
                onBackPressed();
                break;
            case R.id.activity_new_yellow_card_step_one_next_bt:
                TalkingDataUtils.onEvent(this, "建卡第一页-下一步");
                //先检查数据是否输入完整
                if (!checkDataValidation()) {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                    break;
                }
                if (!mBasicInfoFragment.checkMobile()) {
                    break;
                }
//                //再检查车型是否输入正确
//                if (!mCarTypeValid) {
//                    ToastUtils.showToast(getString(R.string.customer_detail_car_type_try_again_hint));
//                    break;
//                }
//                //最后检查下次跟进日期是否输入合法
//                if (!mFollowUpPlanFragment.validateDate()) {
//                    ToastUtils.showToast(this, getString(R.string.customer_detail_wrong_date));
//                    break;
//                }
                prepareToSubmit();
                break;
            default:
                break;
        }
    }

    /**
     * 检查用户输入是否完整
     *
     * @return true为完整，false不完整
     */
    private boolean checkDataValidation() {
        return mBasicInfoFragment.checkDataValidation() && mRequirementsFragment.checkDataValidation()
                && mCurrentCarFragment.checkDataValidation() && mRequirementDetailsFragment.checkDataValidation()
                && mOppLevelFragment.checkDataValidation() /*&& mFollowUpPlanFragment.checkDataValidation()*/;
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
        OpportunitySubmitReqEntity infoEntity = mBasicInfoFragment.getParameters();
        entity.setCustName(infoEntity.getCustName());//姓名
        entity.setCustGender(infoEntity.getCustGender());//性别
        entity.setCustMobile(infoEntity.getCustMobile());//电话
        entity.setCustAge(infoEntity.getCustAge());//年龄
        entity.setSrcTypeId(infoEntity.getSrcTypeId());//客户来源
        if (!TextUtils.isEmpty(infoEntity.getChannelId())) {
            entity.setChannelId(infoEntity.getChannelId());//媒体
        }
        if (!TextUtils.isEmpty(infoEntity.getActivityId())) {
            entity.setActivityId(infoEntity.getActivityId());//外拓
        }
        entity.setRecomName(infoEntity.getRecomName());//基盘姓名
        entity.setRecomMobile(infoEntity.getRecomMobile());//基盘手机
        OpportunitySubmitReqEntity requirementsEntity = mRequirementsFragment.getParameters();
        entity.setPurchaseId(requirementsEntity.getPurchaseId());//购买区分
        entity.setPropertyId(requirementsEntity.getPropertyId());//购买性质
        OpportunitySubmitReqEntity oppLevelEntity = mOppLevelFragment.getParameters();
        entity.setOppLevel(oppLevelEntity.getOppLevel());//潜客级别
        entity.setIsKeyuser(oppLevelEntity.getIsKeyuser());//是否重点客户
        entity.setFollowupDesc(oppLevelEntity.getFollowupDesc());//继续跟进理由
        OpportunitySubmitReqEntity followupPlanEntity = mFollowUpPlanFragment.getParameters();
        entity.setScheduleDateStr(followupPlanEntity.getScheduleDateStr());//计划日期
        OpportunityUpdateReqEntity detailEntity = mRequirementDetailsFragment.getParameters();
        entity.setPaymentMode(detailEntity.getPaymentMode());//分期或全款
        entity.setBudgetMin(detailEntity.getBudgetMin());
        entity.setBudgetMax(detailEntity.getBudgetMax());
        entity.setSeriesId(detailEntity.getSeriesId());//车系
        entity.setCarModelId(detailEntity.getCarModelId());//意向车型
        entity.setOptionPackage(detailEntity.getOptionPackage());
        entity.setOutsideColorId(detailEntity.getOutsideColorId());
        entity.setInsideColorId(detailEntity.getInsideColorId());
        OpportunityUpdateReqEntity otherInfoEntity = mOtherInfoFragment.getParameters();
        entity.setIsWechat(otherInfoEntity.getIsWechat());
        List<OpportunityRelationsBean> opportunityRelationsList = new ArrayList<>();//多选数组
        opportunityRelationsList.addAll(detailEntity.getOpportunityRelations());
        opportunityRelationsList.addAll(otherInfoEntity.getOpportunityRelations());
        entity.setOpportunityRelations(opportunityRelationsList);
        //当前车型Fragment显示时才提交里面数据
        if (!mCurrentCarFragment.isHidden()) {
            OpportunityUpdateReqEntity currentCarEntity = mCurrentCarFragment.getParameters();
            entity.setCurrentModel(currentCarEntity.getCurrentModel());//现用车型
            entity.setModelYear(currentCarEntity.getModelYear());//购车年份
            entity.setCurrentMileage(currentCarEntity.getCurrentMileage());//当前里程
        }
        JLog.d(TAG, "submit: " + entity);
        mPresenter.submitOpportunities(entity);
    }

    /**
     * 更新黄卡
     */
    private void updateCard() {
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
        entity.setOppId(mOppId);
        entity.setFollowupId(mFollowUpId);
        OpportunitySubmitReqEntity infoEntity = mBasicInfoFragment.getParameters();
        entity.setCustName(infoEntity.getCustName());//姓名
        entity.setCustGender(infoEntity.getCustGender());//性别
        entity.setCustMobile(infoEntity.getCustMobile());//电话
        entity.setCustAge(infoEntity.getCustAge());//年龄
        entity.setSrcTypeId(infoEntity.getSrcTypeId());//客户来源
        if (!TextUtils.isEmpty(infoEntity.getChannelId())) {
            entity.setChannelId(infoEntity.getChannelId());//媒体
        }
        if (!TextUtils.isEmpty(infoEntity.getActivityId())) {
            entity.setActivityId(infoEntity.getActivityId());//外拓
        }
        entity.setRecomName(infoEntity.getRecomName());//基盘姓名
        entity.setRecomMobile(infoEntity.getRecomMobile());//基盘手机
        OpportunitySubmitReqEntity requirementsEntity = mRequirementsFragment.getParameters();
        entity.setPurchaseId(requirementsEntity.getPurchaseId());//购买区分
        entity.setPropertyId(requirementsEntity.getPropertyId());//购买性质
        OpportunitySubmitReqEntity oppLevelEntity = mOppLevelFragment.getParameters();
        entity.setOppLevel(oppLevelEntity.getOppLevel());//潜客级别
        entity.setIsKeyuser(oppLevelEntity.getIsKeyuser());//是否重点客户
        entity.setFollowupDesc(oppLevelEntity.getFollowupDesc());//继续跟进理由
        OpportunitySubmitReqEntity followupPlanEntity = mFollowUpPlanFragment.getParameters();
        entity.setScheduleDateStr(followupPlanEntity.getScheduleDateStr());//计划日期
        OpportunityUpdateReqEntity detailEntity = mRequirementDetailsFragment.getParameters();
        entity.setPaymentMode(detailEntity.getPaymentMode());//分期或全款
        entity.setBudgetMin(detailEntity.getBudgetMin());
        entity.setBudgetMax(detailEntity.getBudgetMax());
        entity.setSeriesId(detailEntity.getSeriesId());//车系
        entity.setCarModelId(detailEntity.getCarModelId());//意向车型
        entity.setOptionPackage(detailEntity.getOptionPackage());
        entity.setOutsideColorId(detailEntity.getOutsideColorId());
        entity.setInsideColorId(detailEntity.getInsideColorId());
        OpportunityUpdateReqEntity otherInfoEntity = mOtherInfoFragment.getParameters();
        entity.setIsWechat(otherInfoEntity.getIsWechat());
        List<OpportunityRelationsBean> opportunityRelationsList = new ArrayList<>();//多选数组
        opportunityRelationsList.addAll(detailEntity.getOpportunityRelations());
        opportunityRelationsList.addAll(otherInfoEntity.getOpportunityRelations());
        entity.setOpportunityRelations(opportunityRelationsList);
        //当前车型Fragment显示时才提交里面数据
        if (!mCurrentCarFragment.isHidden()) {
            OpportunityUpdateReqEntity currentCarEntity = mCurrentCarFragment.getParameters();
            entity.setCurrentModel(currentCarEntity.getCurrentModel());//现用车型
            entity.setModelYear(currentCarEntity.getModelYear());//购车年份
            entity.setCurrentMileage(currentCarEntity.getCurrentMileage());//当前里程
        }
        mPresenter.updateOpportunities(entity);
    }

    public void queryCarType(String carTypeId) {
        mPresenter.searchByCarType(carTypeId);
    }

    /**
     * 标记意向车型不合法
     */
    public void invalidateCarType() {
        mCarTypeValid = false;
    }

    /**
     * 生成下次跟进时间，默认为第二天
     *
     * @param scheduleDateStr
     */
    public void changeScheduleDate(String scheduleDateStr) {
        mFollowUpPlanFragment.setScheduleDateString(scheduleDateStr);
    }

    public void showCurrentCarFragment(boolean show) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            fragmentTransaction.show(mCurrentCarFragment).commit();
        } else {
            fragmentTransaction.hide(mCurrentCarFragment).commit();
        }
    }

    @Override
    public void onStepOneSubmitSuccess(OpportunityEntity entity) {
        //创建成功
        if (!mCardCreated) {
            mCardCreated = true;
            //跳转至后续页面
            Intent i = new Intent(this, NewCustomerStepThreeActivity.class);
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
            Intent i = new Intent(this, NewCustomerStepThreeActivity.class);
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
            mRequirementDetailsFragment.onQueryCarTypeSuccess(entity);
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
    public void onQuickCreateSuccess() {

    }

    @Override
    public void onQuickCreateError(String message) {

    }

    public void checkIfNeedToEnableSubmit() {
        if (mNextBt != null) {
            mNextBt.setEnabled(checkDataValidation());
        }
    }
}
