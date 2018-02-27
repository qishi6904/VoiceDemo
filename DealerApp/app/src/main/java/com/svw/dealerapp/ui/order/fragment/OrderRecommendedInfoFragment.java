package com.svw.dealerapp.ui.order.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.order.CheckMembershipReqEntity;
import com.svw.dealerapp.entity.order.CheckMembershipResEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.ui.newcustomer.fragment.BaseCustomerFragment;
import com.svw.dealerapp.ui.order.activity.OrderActivity;
import com.svw.dealerapp.ui.order.contract.MembershipContract;
import com.svw.dealerapp.ui.order.model.MembershipModel;
import com.svw.dealerapp.ui.order.presenter.MembershipPresenter;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForButton;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.Serializable;

/**
 * 订单-朋友推荐信息
 * Created by xupan on 02/08/2017.
 */
@Deprecated
public class OrderRecommendedInfoFragment extends BaseCustomerFragment implements MembershipContract.View {

    private CustomItemViewForEditText mNameView, mMobileView, mVinView, mMemberNumberView, mCodeView;
    private CustomItemViewForButton mCheckView;
    private LoadingDialog mLoadingDialog;
    private MembershipPresenter mPresenter;

    public static OrderRecommendedInfoFragment newInstance(Serializable entity) {
        OrderRecommendedInfoFragment fragment = new OrderRecommendedInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_recommended_info;
    }

    @Override
    protected void initViews(View view) {
        initMVPData();
        mNameView = (CustomItemViewForEditText) view.findViewById(R.id.order_recommended_name_view);
        mMobileView = (CustomItemViewForEditText) view.findViewById(R.id.order_recommended_mobile_view);
        mVinView = (CustomItemViewForEditText) view.findViewById(R.id.order_recommended_vin_view);
        mMemberNumberView = (CustomItemViewForEditText) view.findViewById(R.id.order_recommended_member_number_view);
        mCodeView = (CustomItemViewForEditText) view.findViewById(R.id.order_recommended_code_view);
        mCheckView = (CustomItemViewForButton) view.findViewById(R.id.order_recommended_check_view);
        initViewsDetails();
    }

    private void initMVPData() {
        mPresenter = new MembershipPresenter(new MembershipModel(), this);
    }

    private void initViewsDetails() {
        mNameView.setMandatory(true);
        mNameView.setTitleText("推荐人姓名");
        mNameView.setHintTextForContentView("请输入推荐人姓名");
        mMobileView.setTitleText("推荐人手机");
        mMobileView.setHintTextForContentView("请输入推荐人手机");
        mMobileView.limitContentToNumber();
        mMobileView.setMaxLengthForContentView(11);
        mVinView.setTitleText("推荐人VIN");
        mVinView.setHintTextForContentView("请输入推荐人VIN");
        mMemberNumberView.setTitleText("会员卡号");
        mMemberNumberView.setHintTextForContentView("请输入会员卡号");
        mCodeView.setTitleText("售后代码");
        mCodeView.setHintTextForContentView("请输入售后代码");
        mCheckView.setTitleText("校验结果");
        mCheckView.setMandatory(true);
        mCheckView.setShowBottomLine(false);
    }

    @Override
    protected void initBaseViews() {
        super.initBaseViews();
        mAllBaseView = new CustomItemViewBase[]{mNameView, mMobileView,
                mVinView, mMemberNumberView, mCodeView, mCheckView};
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mCheckView.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateMembershipInput()) {
                    return;
                }
                checkMembership();
            }
        });
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        ((OrderActivity) getActivity()).checkFragmentsValidation();
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        if (mEntity instanceof QueryOrderDetailResponseEntity) {
            QueryOrderDetailResponseEntity entity = (QueryOrderDetailResponseEntity) mEntity;
            enableBaseViews(entity.isBuyerInfoEditable());
            mNameView.setData(entity.getRecomName());
            mMobileView.setData(entity.getRecomMobile());
            mVinView.setData(entity.getRecomVin());
            mMemberNumberView.setData(entity.getRecomCard());
            mCodeView.setData(entity.getCarServiceCode());
        } else if (mEntity instanceof OpportunityDetailEntity) {
            OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
            mNameView.setData(entity.getRecomName());
            mMobileView.setData(entity.getRecomMobile());
        }
    }

    @Override
    public CreateOrderEntity getParameters() {
        CreateOrderEntity entity = new CreateOrderEntity();
        entity.setRecomName(mNameView.getInputData());
        entity.setRecomMobile(mMobileView.getInputData());
        entity.setRecomVin(mVinView.getInputData());
        entity.setRecomCard(mMemberNumberView.getInputData());
        entity.setCarServiceCode(mCodeView.getInputData());
        return entity;
    }

    @Override
    public boolean checkDataCorrectness() {
        String mobile = mMobileView.getInputData();
        if (!TextUtils.isEmpty(mobile) && !StringUtils.isValidMobile(mobile)) {
            ToastUtils.showToast("推荐人手机号码格式有误");
            return false;
        }
        return true;
    }

    private void checkMembership() {
        CheckMembershipReqEntity entity = new CheckMembershipReqEntity();
        entity.setQueryType("1");
        entity.setCustomerName(mNameView.getInputData());
        entity.setCustomerMobile(mMobileView.getInputData());
        entity.setCardNo(mMemberNumberView.getInputData());
        entity.setVin(mVinView.getInputData());
        mPresenter.checkMembership(entity);
    }


    /**
     * 提示手机号、VIN、会员卡号至少要填一项
     *
     * @return true:通过验证 false:未通过
     */
    private boolean validateMembershipInput() {
        String name = mNameView.getInputData();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(getString(R.string.order_membership_validation_hint0));
            return false;
        }
        String mobile = mMobileView.getInputData();
        String memberNumber = mMemberNumberView.getInputData();
        String vin = mVinView.getInputData();
        if (TextUtils.isEmpty(mobile) && TextUtils.isEmpty(memberNumber) && TextUtils.isEmpty(vin)) {
            ToastUtils.showToast(getString(R.string.order_membership_validation_hint2));
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
//            mMemberInfoValid = false;
            mCheckView.setData(null);
            mCheckView.setOptionalFalseText("无效会员，请核对会员信息");
        } else {
//            mMemberInfoValid = true;
            mCheckView.setData(true);
            mCheckView.setOptionalTrueText("有效会员");
        }
        if (!TextUtils.isEmpty(name)) {
            mNameView.setData(name);
        }
        if (!TextUtils.isEmpty(mobile)) {
            mMobileView.setData(mobile);
        }
        if (!TextUtils.isEmpty(vin)) {
            mVinView.setData(vin);
        }
        if (!TextUtils.isEmpty(cardNo)) {
            mMemberNumberView.setData(cardNo);
        }
    }

    @Override
    public void onCheckMembershipFailure(String msg) {
        ToastUtils.showToast(msg);
    }
}
