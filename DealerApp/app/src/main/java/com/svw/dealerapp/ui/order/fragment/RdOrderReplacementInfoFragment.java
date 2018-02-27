package com.svw.dealerapp.ui.order.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.order.CheckMembershipReqEntity;
import com.svw.dealerapp.entity.order.CheckMembershipResEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.ui.newcustomer.fragment.RdBaseCustomerFragment;
import com.svw.dealerapp.ui.order.activity.RdOrderActivity;
import com.svw.dealerapp.ui.order.contract.MembershipContract;
import com.svw.dealerapp.ui.order.model.MembershipModel;
import com.svw.dealerapp.ui.order.presenter.MembershipPresenter;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.ui.widget.RdCustomItemViewBase;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForButton;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForEditText;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.Serializable;

/**
 * 订单-置换信息
 * Created by xupan on 01/08/2017.
 */

public class RdOrderReplacementInfoFragment extends RdBaseCustomerFragment implements MembershipContract.View {

    private static final String TAG = "OrderReplacementInfoFragment";
    private RdCustomItemViewForEditText mNameView, mMobileView, mVinView, mMemberNumberView;
    private RdCustomItemViewForButton mCheckView;
    private MembershipPresenter mPresenter;
    private boolean mMemberInfoValid;
    private LoadingDialog mLoadingDialog;

    public static RdOrderReplacementInfoFragment newInstance(Serializable entity) {
        RdOrderReplacementInfoFragment fragment = new RdOrderReplacementInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_fragment_order_replacement_info;
    }

    @Override
    protected void initViews(View view) {
        initMVPData();
        mNameView = (RdCustomItemViewForEditText) view.findViewById(R.id.order_replacement_name_view);
        mNameView.setMandatory(true);
        mNameView.setTitleText(getResources().getString(R.string.order_create_order_member_owner_name));
        mNameView.setHintTextForContentView(getResources().getString(R.string.order_create_order_member_owner_name_hint));
        mMobileView = (RdCustomItemViewForEditText) view.findViewById(R.id.order_replacement_mobile_view);

        mMobileView.setTitleText(getResources().getString(R.string.order_create_order_member_owner_mobile));
        mMobileView.setHintTextForContentView(getResources().getString(R.string.order_create_order_member_owner_mobile_hint));
        mMobileView.limitContentToNumber();
        mMobileView.setMaxLengthForContentView(11);

        mVinView = (RdCustomItemViewForEditText) view.findViewById(R.id.order_replacement_vin_view);
        mVinView.setTitleText(getResources().getString(R.string.order_create_order_member_owner_vin));
        mVinView.setHintTextForContentView(getResources().getString(R.string.order_create_order_member_owner_vin_hint));

        mMemberNumberView = (RdCustomItemViewForEditText) view.findViewById(R.id.order_replacement_member_number_view);
        mMemberNumberView.setTitleText(getResources().getString(R.string.order_create_order_member_owner_member_id));
        mMemberNumberView.setHintTextForContentView(getResources().getString(R.string.order_create_order_member_owner_member_id_hint));

        mCheckView = (RdCustomItemViewForButton) view.findViewById(R.id.order_replacement_member_check_view);
        mCheckView.setMandatory(true);
        mCheckView.setTitleText(getResources().getString(R.string.order_create_order_member_owner_verify));
        mCheckView.setShowBottomLine(false);
    }

    @Override
    protected void initBaseViews() {
        super.initBaseViews();
        mAllBaseView = new RdCustomItemViewBase[]{mNameView, mMobileView, mVinView, mMemberNumberView, mCheckView};
    }

    private void initMVPData() {
        mPresenter = new MembershipPresenter(new MembershipModel(), this);
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
        String mobile = mMobileView.getInputData();
        String memberNumber = mMemberNumberView.getInputData();
        String vin = mVinView.getInputData();
        if (TextUtils.isEmpty(mobile) && TextUtils.isEmpty(memberNumber) && TextUtils.isEmpty(vin)) {
            ToastUtils.showToast(getString(R.string.order_membership_validation_hint));
            return false;
        }
        return true;
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        ((RdOrderActivity) getActivity()).checkFragmentsValidation();
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        if (mEntity instanceof QueryOrderDetailResponseEntity) {
            QueryOrderDetailResponseEntity entity = (QueryOrderDetailResponseEntity) mEntity;
            enableBaseViews(entity.isBuyerInfoEditable());
            mNameView.setData(entity.getCarOwnerName());
            mMobileView.setData(entity.getCarOwnerTel());
            mVinView.setData(entity.getCarOwnerVin());
            mMemberNumberView.setData(entity.getCarOwnerCard());
        }
    }

    @Override
    public CreateOrderEntity getParameters() {
        CreateOrderEntity entity = new CreateOrderEntity();
        entity.setCarOwnerName(mNameView.getInputData());
        entity.setCarOwnerTel(mMobileView.getInputData());
        entity.setCarOwnerVin(mVinView.getInputData());
        entity.setCarOwnerCard(mMemberNumberView.getInputData());
        return entity;
    }

    @Override
    public boolean checkDataCorrectness() {
        String mobile = mMobileView.getInputData();
        if (!TextUtils.isEmpty(mobile) && !StringUtils.isValidMobile(mobile)) {
            ToastUtils.showToast("置换信息的联系电话格式有误");
            return false;
        }
        return true;
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
            mMemberInfoValid = false;
            mCheckView.setData(null);
            mCheckView.setOptionalFalseText("无效会员，请核对会员信息");
        } else {
            mMemberInfoValid = true;
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
}
