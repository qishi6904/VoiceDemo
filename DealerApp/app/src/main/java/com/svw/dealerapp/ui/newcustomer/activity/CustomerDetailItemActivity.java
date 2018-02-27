package com.svw.dealerapp.ui.newcustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityRelationsBean;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.base.BaseFrameActivity;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.ui.newcustomer.fragment.BaseCustomerFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.CarTypeFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.CustomerDetailRemarkFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerBasicInfoFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerCurrentCarFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOppLevelFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerOtherInfoFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerRequirementsDetailFragment;
import com.svw.dealerapp.ui.newcustomer.fragment.NewCustomerRequirementsFragment;
import com.svw.dealerapp.ui.newcustomer.model.NewCustomerModel;
import com.svw.dealerapp.ui.newcustomer.presenter.UpdateOpportunityPresenterImpl;
import com.svw.dealerapp.ui.optionalpackage.activity.OptionalPackageListActivity;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.svw.dealerapp.global.NewCustomerConstants.FRAGMENT_TAG_FOLLOWUP;
import static com.svw.dealerapp.global.NewCustomerConstants.FRAGMENT_TAG_INFO;
import static com.svw.dealerapp.global.NewCustomerConstants.FRAGMENT_TAG_REMARK;
import static com.svw.dealerapp.global.NewCustomerConstants.FRAGMENT_TAG_REQUIREMENTS;
import static com.svw.dealerapp.global.NewCustomerConstants.REQUEST_CODE_PACKAGE;

/**
 * 黄卡详情 - Item详情页(从黄卡详情底部Item点击跳转后的页面).
 * Created by xupan on 10/06/2017.
 */

public class CustomerDetailItemActivity extends BaseFrameActivity<UpdateOpportunityPresenterImpl, NewCustomerModel> implements NewCustomerContract.UpdateOpportunityView {

    private static final String TAG = "CustomerDetailItemActivity";
    private BaseCustomerFragment mFragment;
    private NewCustomerBasicInfoFragment mBasicInfoFragment;
    private NewCustomerRequirementsFragment mRequirementsFragment;
    private CarTypeFragment mCarTypeFragment;
    private NewCustomerCurrentCarFragment mCurrentCarFragment;
    private NewCustomerRequirementsDetailFragment mRequirementsDetailFragment;
    private NewCustomerOtherInfoFragment mOtherInfoFragment;
    private NewCustomerOppLevelFragment mFollowupReasonFragment;
    private CustomerDetailRemarkFragment mRemarkFragment;
    private CustomSectionViewGroup mCustomSectionViewGroup;
    private OpportunityDetailEntity mEntity;
    private String mFragmentTag;
    private String mOppId;
    private boolean mCarTypeValid;

    private ScrollView svContainer;
    private FrameLayout flRemark;
    private ArrayList<OptionalPackageEntity.OptionListBean> mCurrentOptionalPackage;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        svContainer = (ScrollView) findViewById(R.id.sv_detail_item_container);
        flRemark = (FrameLayout) findViewById(R.id.fl_remark);
        mCustomSectionViewGroup = (CustomSectionViewGroup) findViewById(R.id.customer_detail_item_container);
        mCustomSectionViewGroup.disableCollapse();
        getDataFromIntent();
        initFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_detail_item;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initTitle();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mFragmentTag = intent.getStringExtra("fragment");
        mEntity = (OpportunityDetailEntity) intent.getSerializableExtra("entity");
        mOppId = intent.getStringExtra("oppId");
        if (null == mEntity) {
            mEntity = new OpportunityDetailEntity();
            mEntity.setOppId(mOppId);
        }
        mCurrentOptionalPackage = mEntity.getEcCarOptions();
    }

    public void initView() {
        super.initView();
        findViewById(R.id.common_title_bar_right_textview).setVisibility(View.VISIBLE);
//        initTitle();
    }

    private void initTitle() {
        if (!TextUtils.isEmpty(mFragmentTag)) {
            switch (mFragmentTag) {
                case FRAGMENT_TAG_INFO:
                    TalkingDataUtils.onPageStart(this, "客户基本信息");
                    mTitleTv.setText(R.string.customer_detail_title_info);
                    hideSaveBtn();
                    break;
                case FRAGMENT_TAG_REQUIREMENTS:
                    TalkingDataUtils.onPageStart(this, "产品需求");
                    mTitleTv.setText(R.string.customer_detail_title_requirements);
                    hideSaveBtn();
                    break;
                case FRAGMENT_TAG_FOLLOWUP:
                    TalkingDataUtils.onPageStart(this, "继续跟进理由");
                    mTitleTv.setText(R.string.customer_detail_title_followup_reason);
                    hideSaveBtn();
                    break;
                case FRAGMENT_TAG_REMARK:
                    TalkingDataUtils.onPageStart(this, "备注");
                    mTitleTv.setText(R.string.customer_detail_title_remark);
                    findViewById(R.id.common_title_bar_right_textview).setVisibility(View.GONE);
                    break;
            }
        }
    }

    private void initFragment() {
        if (!TextUtils.isEmpty(mFragmentTag)) {
            switch (mFragmentTag) {
                case FRAGMENT_TAG_INFO:
                    flRemark.setVisibility(View.GONE);
                    mFragment = mBasicInfoFragment = NewCustomerBasicInfoFragment.newInstance(mEntity);
                    mCustomSectionViewGroup.setTitleText(R.string.new_customer_basic_info_header);
                    addFragment();
                    break;
                case FRAGMENT_TAG_REQUIREMENTS:
                    flRemark.setVisibility(View.GONE);
                    mFragment = mRequirementsFragment = NewCustomerRequirementsFragment.newInstance(mEntity);
                    mRequirementsFragment.hideCarTypeRow();
                    mCarTypeFragment = CarTypeFragment.newInstance(mEntity);
                    mCarTypeFragment.setViewMandatory(true, false, false);
                    mCurrentCarFragment = NewCustomerCurrentCarFragment.newInstance(mEntity);
                    mRequirementsDetailFragment = NewCustomerRequirementsDetailFragment.newInstance(mEntity);
                    mRequirementsDetailFragment.setChangeStyleTag();
                    mOtherInfoFragment = NewCustomerOtherInfoFragment.newInstance(mEntity);
                    mOtherInfoFragment.setChangeStyleFlag();
                    mCustomSectionViewGroup.setTitleText(R.string.new_customer_requirements_header);
                    mCustomSectionViewGroup.addFragment(mRequirementsFragment);
                    mCustomSectionViewGroup.addFragment(mCarTypeFragment);
                    mCustomSectionViewGroup.addFragment(mCurrentCarFragment);
                    mCustomSectionViewGroup.addFragment(mRequirementsDetailFragment);
                    mCustomSectionViewGroup.addFragment(mOtherInfoFragment);
                    break;
                case FRAGMENT_TAG_FOLLOWUP:
                    flRemark.setVisibility(View.GONE);
                    mFragment = mFollowupReasonFragment = NewCustomerOppLevelFragment.newInstance(mEntity);
                    mFollowupReasonFragment.setFlag(NewCustomerConstants.FLAG_FOLLOWUP_REASON);
                    addFragment();
                    mCustomSectionViewGroup.setTitleText(R.string.new_customer_opp_level_header_as_followup);
                    break;
                case FRAGMENT_TAG_REMARK:
                    svContainer.setVisibility(View.GONE);
                    mFragment = mRemarkFragment = CustomerDetailRemarkFragment.newInstance(mEntity);
                    if (null != mFragment) {
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_remark, mFragment)
                                .commit();
                    }
                    break;
            }
        }
    }

    /**
     * 如果当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态，隐藏 保存 按钮
     */
    private void hideSaveBtn() {
        if ((null != mEntity && !mEntity.isYellowCardOwner()) ||
                (null != mEntity && mEntity.isSleepStatus()) ||
                (null != mEntity && mEntity.isFailedStatus())) {
            findViewById(R.id.common_title_bar_right_textview).setVisibility(View.GONE);
        }
    }

    private void addFragment() {
        if (mFragment != null) {
            mCustomSectionViewGroup.addFragment(mFragment);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_title_bar_right_textview:
                prepareToSubmit();
                break;
            default:
                break;
        }
    }

    private void prepareToSubmit() {
        switch (mFragmentTag) {
            case FRAGMENT_TAG_INFO:
                if (!mBasicInfoFragment.checkDataValidation()) {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                    break;
                }
                if (!mBasicInfoFragment.checkMobile()) {
                    break;
                }
                prepareToUpdateBasicInfo();
                TalkingDataUtils.onEvent(this, "保存客户基本信息", "潜客详情-客户基本信息");
                break;
            case FRAGMENT_TAG_REQUIREMENTS:
                if (!mRequirementsFragment.checkDataValidation()) {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                    break;
                }
//                if (!mCarTypeValid) {
//                    ToastUtils.showToast(getString(R.string.customer_detail_car_type_try_again_hint));
//                    break;
//                }
                prepareToUpdateRequirements();
                TalkingDataUtils.onEvent(this, "保存产品需求", "潜客详情-产品需求");
                break;
            case FRAGMENT_TAG_FOLLOWUP:
                if (!mFollowupReasonFragment.checkDataValidation()) {
                    ToastUtils.showToast(getString(R.string.new_customer_validation_hint));
                    break;
                }
                prepareToUpdateFollowupReason();
                TalkingDataUtils.onEvent(this, "保存继续跟进理由", "潜客详情-继续跟进理由");
                break;
            case FRAGMENT_TAG_REMARK:
                prepareToUpdateRemark();
                TalkingDataUtils.onEvent(this, "保存备注", "潜客详情-备注");
                break;
            default:
                break;
        }
    }

    /**
     * 基本信息二级页保存
     */
    private void prepareToUpdateBasicInfo() {
        OpportunitySubmitReqEntity entityFromInfo = mBasicInfoFragment.getParameters();
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
        entity.setOppId(mOppId);
        entity.setCustGender(entityFromInfo.getCustGender());//性别
        entity.setCustName(entityFromInfo.getCustName());//姓名
        entity.setCustMobile(entityFromInfo.getCustMobile());//电话
        entity.setCustAge(entityFromInfo.getCustAge());//年龄
        entity.setSrcTypeId(entityFromInfo.getSrcTypeId());//客户来源
        entity.setChannelId(entityFromInfo.getChannelId());//媒体
        entity.setRecomName(entityFromInfo.getRecomName());//基盘姓名
        entity.setRecomMobile(entityFromInfo.getRecomMobile());//基盘电话
        mPresenter.updateOpportunities(entity);
    }

    /**
     * 产品需求二级面保存
     */
    private void prepareToUpdateRequirements() {
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
        entity.setOppId(mOppId);
        OpportunitySubmitReqEntity entityFromInfo = mRequirementsFragment.getParameters();
        entity.setPurchaseId(entityFromInfo.getPurchaseId());//购买区分
        entity.setPropertyId(entityFromInfo.getPropertyId());//购买性质
        entity.setAppraiserId(entityFromInfo.getAppraiserId());//评估师
        OpportunitySubmitReqEntity entityFromCarFragment = mCarTypeFragment.getParameters();
        entity.setSeriesId(entityFromCarFragment.getSeriesId());//意向车系
        entity.setCarModelId(entityFromCarFragment.getCarModelId());//意向车型
        OpportunityUpdateReqEntity entityFromRequirementsDetail = mRequirementsDetailFragment.getParameters();
        entity.setPaymentMode(entityFromRequirementsDetail.getPaymentMode());//分期或全款
        entity.setBudgetMin(entityFromRequirementsDetail.getBudgetMin());
        entity.setBudgetMax(entityFromRequirementsDetail.getBudgetMax());
        entity.setOptionPackage(entityFromRequirementsDetail.getOptionPackage());
        entity.setOutsideColorId(entityFromRequirementsDetail.getOutsideColorId());
        entity.setInsideColorId(entityFromRequirementsDetail.getInsideColorId());
        List<OpportunityRelationsBean> opportunityRelationsList = new ArrayList<>();//多选数组
        opportunityRelationsList.addAll(entityFromRequirementsDetail.getOpportunityRelations());
        OpportunityUpdateReqEntity entityFromOtherInfo = mOtherInfoFragment.getParameters();
        opportunityRelationsList.addAll(entityFromOtherInfo.getOpportunityRelations());
        entity.setOpportunityRelations(opportunityRelationsList);
        //当前车型Fragment显示时才提交里面数据
        if (!mCurrentCarFragment.isHidden()) {
            OpportunityUpdateReqEntity entityFromCurrentCar = mCurrentCarFragment.getParameters();
            entity.setCurrentModel(entityFromCurrentCar.getCurrentModel());//现用车型
            entity.setModelYear(entityFromCurrentCar.getModelYear());//购车年份
            entity.setCurrentMileage(entityFromCurrentCar.getCurrentMileage());//当前里程
        }
        if (mCurrentOptionalPackage != null) {
            entity.setEcCarOptions(mCurrentOptionalPackage);
        } else {
            entity.setEcCarOptions(new ArrayList<OptionalPackageEntity.OptionListBean>());
        }
        JLog.d(TAG, "entity: " + entity);
        mPresenter.updateOpportunities(entity);
    }

    /**
     * 继续跟进理由二级画面
     */
    private void prepareToUpdateFollowupReason() {
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
        OpportunitySubmitReqEntity entityFromFollowupReason = mFollowupReasonFragment.getParameters();
        entity.setOppId(mOppId);
        entity.setFollowupDesc(entityFromFollowupReason.getFollowupDesc());//继续跟进理由
        mPresenter.updateOpportunities(entity);
    }

    /**
     * 提交备注
     */
    public void prepareToUpdateRemark() {
        if (null != mEntity && !TextUtils.isEmpty(mEntity.getOppId()) &&
                !TextUtils.isEmpty(mRemarkFragment.getRemarkStr())) {
            Map<String, Object> options = new HashMap<>();
            options.put("oppId", mEntity.getOppId());
            options.put("oppComment", mRemarkFragment.getRemarkStr());
            mPresenter.submitRemark(options);
        } else {
            submitRemarkFail();
        }
    }

    @Override
    public void onUpdateOpportunitySuccess(OpportunityEntity entity) {
        if (FRAGMENT_TAG_REMARK.equals(mFragmentTag)) {
            mRemarkFragment.dealSubmitSuccess();
        } else {
            onRequestEnd();
            finish();
        }
    }

    @Override
    public void onUpdateOpportunityFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    public void queryCarType(String carTypeId) {
        mPresenter.searchByCarType(carTypeId);
    }

    @Override
    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        mCarTypeValid = true;
        mRequirementsFragment.onQueryCarTypeSuccess(entity);
        mRequirementsDetailFragment.onQueryCarTypeSuccess(entity);
    }

    @Override
    public void onQueryCarTypeError(String msg) {
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
    public void submitRemarkSuccess() {
        mRemarkFragment.dealSubmitSuccess();
        ToastUtils.showToast(getResources().getString(R.string.customer_detail_remark_success));
    }

    @Override
    public void submitRemarkFail() {
        ToastUtils.showToast(getResources().getString(R.string.customer_detail_remark_fail));
    }

    public void invalidateCarType() {
        mCarTypeValid = false;
    }

    public void resetCarTypeColor() {
        mRequirementsDetailFragment.resetCarTypeColor();
    }

    public void resetOptionalPackage() {
        mRequirementsDetailFragment.resetOptionalPackage();
        mCurrentOptionalPackage = null;
    }

    public void showCurrentCarFragment(boolean show) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            fragmentTransaction.show(mCurrentCarFragment).commit();
        } else {
            fragmentTransaction.hide(mCurrentCarFragment).commit();
        }
    }

    public void queryOptionalPackage() {
        OpportunitySubmitReqEntity entityFromCarType = mCarTypeFragment.getParameters();
        String seriesId = entityFromCarType.getSeriesId();
        String carModelId = entityFromCarType.getCarModelId();
        OpportunityUpdateReqEntity entityFromDetail = mRequirementsDetailFragment.getParameters();
        String insideColorId = entityFromDetail.getInsideColorId();
        if (TextUtils.isEmpty(seriesId) || TextUtils.isEmpty(carModelId)) {
            ToastUtils.showToast("车系车型必填");
        } else {
            Intent i = new Intent(this, OptionalPackageListActivity.class);
            i.putExtra("serId", seriesId);
            i.putExtra("modelId", carModelId);
            i.putExtra("color", insideColorId);
            i.putExtra("selectData", mCurrentOptionalPackage);
            startActivityForResult(i, REQUEST_CODE_PACKAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PACKAGE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<OptionalPackageEntity.OptionListBean> list =
                        mCurrentOptionalPackage = data.getParcelableArrayListExtra("selectData");
                List<String> resultList = CommonUtils.getOptionalPackage(list, true);
                mRequirementsDetailFragment.displayOptionalPackage(resultList);
            }
        }
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
    protected void onStop() {
        super.onStop();
        onStopNoteTK();
    }

    private void onStopNoteTK() {
        if (!TextUtils.isEmpty(mFragmentTag)) {
            switch (mFragmentTag) {
                case FRAGMENT_TAG_INFO:
                    TalkingDataUtils.onPageEnd(this, "客户基本信息");
                    break;
                case FRAGMENT_TAG_REQUIREMENTS:
                    TalkingDataUtils.onPageEnd(this, "产品需求");
                    break;
                case FRAGMENT_TAG_FOLLOWUP:
                    TalkingDataUtils.onPageEnd(this, "继续跟进理由");
                    break;
                case FRAGMENT_TAG_REMARK:
                    TalkingDataUtils.onPageEnd(this, "备注");
                    break;
            }
        }
    }
}
