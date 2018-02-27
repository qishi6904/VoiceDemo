package com.svw.dealerapp.ui.newcustomer.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;
import com.svw.dealerapp.entity.order.CheckMembershipReqEntity;
import com.svw.dealerapp.entity.order.CheckMembershipResEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.order.contract.MembershipContract;
import com.svw.dealerapp.ui.order.model.MembershipModel;
import com.svw.dealerapp.ui.order.presenter.MembershipPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.ui.widget.RdCustomItemViewBase;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForButton;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForCheckbox;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForOptionsPicker;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.dbtools.DBHelper;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.Serializable;

/**
 * 建卡-潜客信息补充-年龄、来源、媒体、推荐人姓名手机
 * Created by xupan on 26/01/2018.
 */

public class RdNewCustomerUnitedOtherInfoFragment extends RdBaseCustomerFragment implements MembershipContract.View {

    private static final String TAG = "RdNewCustomerUnitedOtherInfoFragment";
    private RdCustomItemViewForOptionsPicker mAgeView, mSourceTypeView, mMediaView, mActivityView;
    private RdCustomItemViewForCheckbox mIsRecommendedView;
    private RdCustomItemViewForEditText mRecommendedNameView, mRecommendedMobileView;
    private RdCustomItemViewForButton mCheckView;
    private MembershipPresenter mPresenter;
    private LoadingDialog mLoadingDialog;

    public static NewCustomerUnitedOtherInfoFragment newInstance(Serializable entity) {
        NewCustomerUnitedOtherInfoFragment fragment = new NewCustomerUnitedOtherInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_fragment_new_customer_united_other_info;
    }

    @Override
    protected void initViews(View view) {
        mPresenter = new MembershipPresenter(new MembershipModel(), this);
        mAgeView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_other_info_age_view);
        mAgeView.setTitleText(R.string.new_customer_age);
        mAgeView.setHintTextForContentView(R.string.new_customer_age_hint);
        mSourceTypeView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_other_info_src_type_view);
        mSourceTypeView.setTitleText(R.string.new_customer_source);
        mMediaView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_other_info_media_view);
        mMediaView.setTitleText(R.string.new_customer_media);
        mActivityView = (RdCustomItemViewForOptionsPicker) view.findViewById(R.id.new_customer_other_info_activity_view);
        mActivityView.setTitleText("外拓");
        mIsRecommendedView = (RdCustomItemViewForCheckbox) view.findViewById(R.id.new_customer_other_info_recommended_view);
        mIsRecommendedView.setTitleText(R.string.new_customer_recommended_by_friend);
        mRecommendedNameView = (RdCustomItemViewForEditText) view.findViewById(R.id.new_customer_other_info_recommended_name_view);
        mRecommendedNameView.setMandatory(true);
        mRecommendedNameView.setTitleText(R.string.new_customer_jipan_customer);
        mRecommendedNameView.setHintTextForContentView(R.string.new_customer_jipan_customer_hint);
        mRecommendedMobileView = (RdCustomItemViewForEditText) view.findViewById(R.id.new_customer_other_info_recommended_mobile_view);
        mRecommendedMobileView.setMandatory(true);
        mRecommendedMobileView.limitContentToNumber();
        mRecommendedMobileView.setMaxLengthForContentView(11);
        mRecommendedMobileView.setTitleText(R.string.new_customer_jipan_customer_contact);
        mRecommendedMobileView.setHintTextForContentView(R.string.new_customer_jipan_customer_contact_hint);
        mCheckView = (RdCustomItemViewForButton) view.findViewById(R.id.new_customer_other_info_recommended_check_view);
        mCheckView.setTitleText("校验结果");
    }

    @Override
    protected void initBaseViews() {
        super.initBaseViews();
        mAllBaseView = new RdCustomItemViewBase[]{mAgeView, mSourceTypeView, mMediaView,
                mActivityView, mIsRecommendedView, mRecommendedNameView, mRecommendedMobileView, mCheckView};
    }

    @Override
    protected void initOptions() {
        super.initOptions();
        CommonUtils.rdInitOptionsView(NewCustomerConstants.ageMap, mAgeView);
        CommonUtils.rdInitOptionsView(NewCustomerConstants.sourceMap, mSourceTypeView);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mIsRecommendedView.setOnDataChangedListener(new RdCustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                boolean checked = (Boolean) object;
                mRecommendedNameView.setVisibility(checked ? View.VISIBLE : View.GONE);
                mRecommendedMobileView.setVisibility(checked ? View.VISIBLE : View.GONE);
                mCheckView.setVisibility(checked ? View.VISIBLE : View.GONE);
                if (!checked) {
                    mRecommendedNameView.clearData();
                    mRecommendedMobileView.clearData();
                    mRecommendedMobileView.setOnEditTextFocusChangedListener(null);
                } else {
                    mRecommendedMobileView.setOnEditTextFocusChangedListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus && mRecommendedMobileView.getInputData().length() == 11) {

                            }
                        }
                    });
                }
                onDataChanged(object);
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
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewCustomerUnitedActivity) {
            ((NewCustomerUnitedActivity) activity).checkIfNeedToEnableSubmit();
        }
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        if (mEntity instanceof OpportunityDetailEntity) {
            mSourceTypeView.setEnabled(false);
            mMediaView.setEnabled(false);
            OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
            String srcTypeId = entity.getSrcTypeId();
            mSourceTypeView.setData(srcTypeId);
            //外拓
            if ("11050".equals(srcTypeId)) {
                mActivityView.setVisibility(View.VISIBLE);
                mActivityView.setStaticData(entity.getActivityId(), entity.getActivitySubject());
            } else if (!"11010".equals(srcTypeId)) {//非到店
                setMediaMap(srcTypeId);
                mMediaView.setVisibility(View.VISIBLE);
                JLog.d(TAG, "channelId: " + entity.getChannelId());
                mMediaView.setData(entity.getChannelId());
            }
            String recomMobile = entity.getRecomMobile();
            String recomName = entity.getRecomName();
            if (!TextUtils.isEmpty(recomMobile) || !TextUtils.isEmpty(recomName)) {
                mIsRecommendedView.setData(true);
                mRecommendedNameView.setVisibility(View.VISIBLE);
                mRecommendedMobileView.setVisibility(View.VISIBLE);
                mRecommendedNameView.setData(recomName);
                mRecommendedMobileView.setData(recomMobile);
            }
        }

    }

    private void submitToCheckMembership() {
        CheckMembershipReqEntity entity = new CheckMembershipReqEntity();
        entity.setQueryType("1");
        entity.setCustomerName(mRecommendedNameView.getInputData());
        entity.setCustomerMobile(mRecommendedMobileView.getInputData());
        mPresenter.checkMembership(entity);
    }

    /**
     * 提示姓名或电话不能为空
     *
     * @return true:通过验证 false:未通过
     */
    private boolean validateMembershipInput() {
        String name = mRecommendedNameView.getInputData();
        String mobile = mRecommendedMobileView.getInputData();
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(name)) {
            ToastUtils.showToast(getString(R.string.new_customer_reference_name_mobile_null_message));
            return false;
        }
        return true;
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

    private void setMediaMap(String srcType) {
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("select * from DictionaryRel where dictId=? and relaTypeId=?", new String[]{srcType, "999"});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                NewCustomerConstants.mediaMap.put(cursor.getString(5), cursor.getString(6));
            }
            cursor.close();
        }
        CommonUtils.rdInitOptionsView(NewCustomerConstants.mediaMap, mMediaView);
    }

    @Override
    public OpportunitySubmitReqEntityV2 getParameters() {
        OpportunitySubmitReqEntityV2 entityV2 = new OpportunitySubmitReqEntityV2();
        entityV2.setCustAge(mAgeView.getInputData());
        entityV2.setSrcTypeId(mSourceTypeView.getInputData());
        if (mMediaView.getVisibility() == View.VISIBLE)
            entityV2.setChannelId(mMediaView.getInputData());
        if (mActivityView.getVisibility() == View.VISIBLE)
            entityV2.setActivityId(mActivityView.getInputData());
        if (mIsRecommendedView.getInputData()) {
            entityV2.setRecomName(mRecommendedNameView.getInputData());
            entityV2.setRecomMobile(mRecommendedMobileView.getInputData());
        }
        return entityV2;
    }

    @Override
    public boolean checkDataCorrectness() {
        if (mIsRecommendedView.getInputData() && !StringUtils.isValidMobile(mRecommendedMobileView.getInputData())) {
            ToastUtils.showToast(getString(R.string.new_customer_jipan_wrong_mobile_format));
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

    @Override
    public void onCheckMembershipFailure(String msg) {

    }
}
