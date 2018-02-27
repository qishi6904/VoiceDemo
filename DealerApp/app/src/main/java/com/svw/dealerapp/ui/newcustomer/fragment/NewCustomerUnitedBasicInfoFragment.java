package com.svw.dealerapp.ui.newcustomer.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;
import com.svw.dealerapp.entity.order.AppraiserEntity;
import com.svw.dealerapp.entity.order.CheckMembershipReqEntity;
import com.svw.dealerapp.entity.order.CheckMembershipResEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.order.activity.AppraiserListActivity;
import com.svw.dealerapp.ui.order.contract.MembershipContract;
import com.svw.dealerapp.ui.order.model.MembershipModel;
import com.svw.dealerapp.ui.order.presenter.MembershipPresenter;
import com.svw.dealerapp.ui.resource.contract.CheckRepeatContract;
import com.svw.dealerapp.ui.resource.model.CheckRepeatModel;
import com.svw.dealerapp.ui.resource.presenter.CheckRepeatPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForButton;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditTextWithMic;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.CustomItemViewForRadioButton;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.PermissionUtils;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 建卡-基本信息页
 * Created by xupan on 25/09/2017.
 */

public class NewCustomerUnitedBasicInfoFragment extends BaseCustomerFragment implements MembershipContract.View, CheckRepeatContract.View {

    private CustomItemViewForEditText mNameView, mMobileView;
    private CustomItemViewForRadioButton mGenderView;
    private CustomItemViewForOptionsPicker mBuyerView, mPropertyView, mEvaluatorView, mOppLevelView;
    private CustomItemViewForEditTextWithMic mReasonView;
    private CustomItemViewForButton mCheckView;
    private CarTypeFragment mCarTypeFragment;
    private MembershipPresenter mPresenter;
    private CheckRepeatPresenter mCRPresenter;
    private LoadingDialog mLoadingDialog;
    private RelativeLayout rlCheckRepeat;
    private static final int CODE_EVALUATOR = 1000;
    private AppraiserEntity.AppraiserInfoEntity mEvaluatorEntity;

    public static NewCustomerUnitedBasicInfoFragment newInstance(Serializable entity) {
        NewCustomerUnitedBasicInfoFragment fragment = new NewCustomerUnitedBasicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCarTypeFragment(CarTypeFragment carTypeFragment) {
        mCarTypeFragment = carTypeFragment;
    }

    public String getOppLevel() {
        return mOppLevelView.getInputData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_customer_basic_info;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mReasonView.stopNLS();
    }

    @Override
    protected void initViews(View view) {
        mPresenter = new MembershipPresenter(new MembershipModel(), this);
        mCRPresenter = new CheckRepeatPresenter(this, new CheckRepeatModel());
        addCarTypeFragment();
        mNameView = (CustomItemViewForEditText) view.findViewById(R.id.new_customer_basic_info_name);
        mNameView.setMandatory(true);
        mNameView.setTitleText(R.string.new_customer_name);
        mNameView.setHintTextForContentView(R.string.new_customer_name_hint);
        mGenderView = (CustomItemViewForRadioButton) view.findViewById(R.id.new_customer_basic_info_gender);
        mGenderView.setMandatory(true);
        mGenderView.setTitleText(R.string.new_customer_gender);
        mMobileView = (CustomItemViewForEditText) view.findViewById(R.id.new_customer_basic_info_mobile);
        mMobileView.setMandatory(true);
        mMobileView.setTitleText(R.string.new_customer_mobile);
        mMobileView.setHintTextForContentView(R.string.new_customer_mobile_hint);
        mMobileView.limitContentToNumber();
        mMobileView.setMaxLengthForContentView(11);
        mCheckView = (CustomItemViewForButton) view.findViewById(R.id.new_customer_basic_info_check);
        mCheckView.setTitleText("校验结果");
        mBuyerView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_basic_info_buyer);
        mBuyerView.setMandatory(true);
        mBuyerView.setTitleText(R.string.new_customer_property);
        mBuyerView.setHintTextForContentView(R.string.new_customer_property_hint);
        mPropertyView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_basic_info_property);
        mPropertyView.setMandatory(true);
        mPropertyView.setTitleText(R.string.new_customer_purpose);
        mPropertyView.setHintTextForContentView(R.string.new_customer_purpose_hint);
        mEvaluatorView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_basic_info_evaluator);
        mEvaluatorView.setMandatory(true);
        mEvaluatorView.setTitleText("评估师");
        mEvaluatorView.setHintTextForContentView("请选择评估师");
        mOppLevelView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_basic_info_opp_level);
        mOppLevelView.setMandatory(true);
        mOppLevelView.setTitleText(R.string.new_customer_opp_level_header);
        mOppLevelView.setHintTextForContentView(R.string.new_customer_opp_level_hint);
        mReasonView = (CustomItemViewForEditTextWithMic) view.findViewById(R.id.new_customer_basic_info_followup_reason);
        mReasonView.setMandatory(true);
        mReasonView.setTitleText(R.string.new_customer_opp_level_followup_reason_title);
        mReasonView.setHint(R.string.new_customer_opp_level_followup_reason_hint);
        mReasonView.setMaxTextNum(100);
        mReasonView.initMicEditText(getActivity(), this);
        rlCheckRepeat = (RelativeLayout) view.findViewById(R.id.rl_check_repeat);
    }

    @Override
    protected void initBaseViews() {
        mAllBaseView = new CustomItemViewBase[]{mNameView, mGenderView, mMobileView,
                mCheckView, mBuyerView, mPropertyView, mEvaluatorView, mOppLevelView, mReasonView};
    }

    private void addCarTypeFragment() {
        mCarTypeFragment.setViewMandatory(true, false, false);
        getChildFragmentManager().beginTransaction()
                .add(R.id.new_customer_basic_info_car_type_container, mCarTypeFragment).commit();
    }

    @Override
    protected void initOptions() {
        super.initOptions();
        CommonUtils.initOptionsView(NewCustomerConstants.genderMap, mGenderView);
        CommonUtils.initOptionsView(NewCustomerConstants.purposeMap, mBuyerView);
        CommonUtils.initOptionsView(NewCustomerConstants.propertyMap, mPropertyView);
        CommonUtils.initOptionsView(NewCustomerConstants.levelMap, mOppLevelView);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mOppLevelView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
//                getScheduleDate();
//                setFollowupCalendars(1, 0, 1);
                informFollowupResultChanged();
                NewCustomerUnitedBasicInfoFragment.this.onDataChanged(object);
            }
        });
        mPropertyView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String property = (String) object;
                //增购或置换时产品需求描述才显示现用车型相关信息
                if ("13520".equals(property)) {//增购
                    askActivityToShowCurrentCarFragment(true);
                    mEvaluatorView.setVisibility(View.GONE);
                } else if ("13530".equals(property)) {//置换
                    askActivityToShowCurrentCarFragment(true);
                    mEvaluatorView.setVisibility(View.VISIBLE);
                } else {
                    askActivityToShowCurrentCarFragment(false);
                    mEvaluatorView.setVisibility(View.GONE);
                }
                NewCustomerUnitedBasicInfoFragment.this.onDataChanged(object);
            }
        });
        mEvaluatorView.setOnBaseViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AppraiserListActivity.class);
                i.putExtra("defaultAppraiserInfoEntity", mEvaluatorEntity);
                startActivityForResult(i, CODE_EVALUATOR);
            }
        });
        mCheckView.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateMembershipInput()) {
                    return;
                }
                submitToCheckMembership();
            }
        });
        rlCheckRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mMobileView.getInputData().trim();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_phone_empty_tip));
                    return;
                }
                if (!StringUtils.isMatchPattern("^1[34578]\\d{9}$", mobile)) {
                    ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_phone_error_tip));
                    return;
                }
                Map<String, Object> options = new HashMap<>();
                options.put("leadsId", ((OpportunityDetailEntity) mEntity).getLeadsId());
                options.put("mobile", mobile);
                options.put("orgId", UserInfoUtils.getOrgId());
                mCRPresenter.checkRepeat(getContext(), options);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_EVALUATOR && resultCode == RESULT_OK) {
            AppraiserEntity.AppraiserInfoEntity appraiserInfoEntity = data.getParcelableExtra("defaultAppraiserInfoEntity");
            mEvaluatorEntity = appraiserInfoEntity;
            mEvaluatorView.setStaticData(appraiserInfoEntity.getAppraiserId(), appraiserInfoEntity.getAppraiserName(), true);
            NewCustomerUnitedBasicInfoFragment.this.onDataChanged(appraiserInfoEntity.getAppraiserId());
        }
    }

    private void askActivityToShowCurrentCarFragment(boolean show) {
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).showCurrentCarFragment(show);
        }
    }

    private void informFollowupResultChanged() {
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).checkToResetFollowupCalendars();
        }
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        mNameView.setData(entity.getCustName());
        String gender = entity.getCustGender();
        if (!TextUtils.isEmpty(gender)) {
            mGenderView.setData(gender);
        }
        mMobileView.setData(entity.getCustMobile());
    }

    @Override
    public OpportunitySubmitReqEntityV2 getParameters() {
        OpportunitySubmitReqEntityV2 entityV2 = new OpportunitySubmitReqEntityV2();
        entityV2.setCustName(mNameView.getInputData());//姓名
        entityV2.setCustGender(mGenderView.getInputData());//性别
        entityV2.setCustMobile(mMobileView.getInputData());//电话
        entityV2.setPurchaseId(mBuyerView.getInputData());//购买区分
        entityV2.setPropertyId(mPropertyView.getInputData());//购买性质
        entityV2.setOppLevel(mOppLevelView.getInputData());//潜客级别
        entityV2.setFollowupDesc(mReasonView.getInputData());//继续跟进理由
        entityV2.setAppraiserId(mEvaluatorView.getInputData());//评估师id
        return entityV2;
    }

    @Override
    public boolean checkDataCorrectness() {
        if (!StringUtils.isValidMobile(mMobileView.getInputData())) {
            ToastUtils.showToast(getString(R.string.new_customer_wrong_mobile_format));
            return false;
        }
        return true;
    }

    @Override
    protected void onDataChanged(Object object) {
        FragmentActivity activity = getActivity();
        if (activity instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) activity).checkIfNeedToEnableSubmit();
        }
    }

    /**
     * 根据选择的潜客级别生成计划跟进日期（建卡时默认为第二天）
     */
    private void getScheduleDate() {
        long DAY = 24 * 60 * 60 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CHINA);
        Calendar now = Calendar.getInstance();
        long resultDateInMillis = now.getTimeInMillis() + DAY;
        Date date = new Date(resultDateInMillis);
        String result = sdf.format(date);
        ((NewCustomerUnitedActivity) getActivity()).changeScheduleDate(result);
    }

    private void setFollowupCalendars(int delayOfDefault, int delayOfStart, int delayOfEnd) {
        if (getActivity() instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) getActivity()).setFollowupCalendars(delayOfDefault, delayOfStart, delayOfEnd);
        }
    }

    private void submitToCheckMembership() {
        CheckMembershipReqEntity entity = new CheckMembershipReqEntity();
        entity.setQueryType("1");
        entity.setCustomerName(mNameView.getInputData());
        entity.setCustomerMobile(mMobileView.getInputData());
        mPresenter.checkMembership(entity);
    }

    /**
     * 提示姓名或电话不能为空
     *
     * @return true:通过验证 false:未通过
     */
    private boolean validateMembershipInput() {
        String name = mNameView.getInputData();
        String mobile = mMobileView.getInputData();
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(name)) {
            ToastUtils.showToast(getString(R.string.new_customer_basic_name_mobile_null_message));
            return false;
        }
        return true;
    }

    @Override
    public void onRequestStart() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        mLoadingDialog.show();
    }

    @Override
    public void onRequestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onInternetError() {

    }

    @Override
    public void onCheckMembershipSuccess(CheckMembershipResEntity bean) {
        if (bean == null) {
            return;
        }
        String name = bean.getCustomerName();
        String mobile = bean.getCustomerMobile();
        String vin = bean.getVin();
        String cardNo = bean.getCardNo();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile)
                || TextUtils.isEmpty(vin) || TextUtils.isEmpty(cardNo)) {
            mCheckView.setOptionalFalseText("无效会员，请核对会员信息");
            showMembershipDialog();
        } else {
            mCheckView.setOptionalTrueText("有效会员");
        }
    }

    private void showMembershipDialog() {
        SimpleMessageDialogAdapter adapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(getContext(), adapter);
        adapter.setDialogMessageText(getString(R.string.new_customer_membership_hint));
        dialog.setBtnConfirmText(getString(R.string.public_submit));
        dialog.hideShowCancelBtn();
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {

            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckMembershipFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION:
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mReasonView.clickRecordButton();
                }
                break;
        }
    }

    @Override
    public void showLoadingDialog() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
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
    public void checkRepeatSuccess(int repeatNum) {
        if (repeatNum == 0) {
            ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_check_repeat_no_repeat));
        } else {
            ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_check_repeat_tip_1) + repeatNum +
                    getResources().getString(R.string.home_create_traffic_check_repeat_tip_2));
        }
    }

    @Override
    public void checkRepeatFailed() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_check_repeat_failed));
    }

    @Override
    public void showCheckRepeatTimeout() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_check_repeat_time_out));
    }
}
