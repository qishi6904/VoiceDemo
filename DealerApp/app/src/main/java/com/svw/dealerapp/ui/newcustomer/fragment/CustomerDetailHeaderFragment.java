package com.svw.dealerapp.ui.newcustomer.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.contract.YCDetailHeadContract;
import com.svw.dealerapp.ui.newcustomer.model.YCDetailHeadModel;
import com.svw.dealerapp.ui.newcustomer.presenter.YCDetailHeadPresenter;
import com.svw.dealerapp.ui.order.activity.OrderActivity;
import com.svw.dealerapp.ui.order.activity.RdOrderActivity;
import com.svw.dealerapp.ui.resource.adapter.YellowVipDialogAdapter;
import com.svw.dealerapp.ui.task.adapter.BenefitDialogAdapter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 黄卡详情页-头部蓝色区域Fragment
 * Created by xupan on 08/06/2017.
 */

public class CustomerDetailHeaderFragment extends BaseCustomerFragment implements View.OnClickListener, YCDetailHeadContract.View {

    private final static int NORMAL_STATUS = 1001;
    private final static int SLEEP_STATUS = 1002;
    private static OpportunityDetailEntity detailEntity;

    private static final String TAG = "CustomerDetailHeaderFragment";
    private LinearLayout llRootView;
    private LinearLayout llSmsPhoneOuter;
    private RelativeLayout mOrderLayout;
    private TextView mNameTv, mCarModelTv, mOuterColorTv, mInnerColorTv, mTvPhoneNumber;
    private TextView mInvitationTv, mTrialTv, mOrderTv, mDealTv;
    private ImageView mInvitationIv, mTrialIv, mOrderIv, mDealIv;
    private ImageView mInvitationCheckedIv, mTrialCheckedIv, mOrderCheckedIv, mDealCheckedIv;
    private ImageView mKeyCustomerIv, mOppLevelIv;
    private ImageView mSmsIv, mPhoneIv, mHeadIv;
    private ImageView mOTDOrderStatusIv;
    private CustomDialog otdOrderStatusDialog;
    private RecyclerView mDemandRv;
    //    private SimpleDraweeView mChannelSdv;
    private ImageView mChannelIv;
    private TextView mActivityTv;
    private YCDetailHeadPresenter headPresenter;
    private CustomDialog transferVipDialog;
    private YellowVipDialogAdapter dialogAdapter;
    private CustomDialog benefitDialog;
    private BenefitDialogAdapter benefitDialogAdapter;
    private String mIsOtdOrder;//0：是，1：否
    private String mOrderId;
    private String mOrderStatus;//-1:首次创建，0：有效，1：无效，1.1：无效要创建，2：OTD，3：OTD取消中，4：开票中，5：已开票，6：已交车

    public static CustomerDetailHeaderFragment newInstance(OpportunityDetailEntity entity) {
        detailEntity = entity;
        CustomerDetailHeaderFragment fragment = new CustomerDetailHeaderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headPresenter = new YCDetailHeadPresenter(new YCDetailHeadModel(), this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_customer_detail_header;
    }

    protected void initViews(View view) {
        mOrderLayout = (RelativeLayout) view.findViewById(R.id.customer_detail_header_order_rl);
        llRootView = (LinearLayout) view.findViewById(R.id.ll_root_view);
        llSmsPhoneOuter = (LinearLayout) view.findViewById(R.id.ll_sms_phone_outer);
        mNameTv = (TextView) view.findViewById(R.id.customer_detail_header_name_tv);
        mHeadIv = (ImageView) view.findViewById(R.id.customer_detail_header_head_iv);
        mOppLevelIv = (ImageView) view.findViewById(R.id.customer_detail_header_level_iv);
        mCarModelTv = (TextView) view.findViewById(R.id.customer_detail_header_car_model_tv);
        mTvPhoneNumber = (TextView) view.findViewById(R.id.tv_phone_number);
        mKeyCustomerIv = (ImageView) view.findViewById(R.id.customer_detail_header_vip_iv);
        mOuterColorTv = (TextView) view.findViewById(R.id.customer_detail_header_outer_color_tv);
        mInnerColorTv = (TextView) view.findViewById(R.id.customer_detail_header_inner_color_tv);
        mDemandRv = (RecyclerView) view.findViewById(R.id.customer_detail_header_demand_rl);
        mInvitationTv = (TextView) view.findViewById(R.id.customer_detail_header_invitation_tv);
        mTrialTv = (TextView) view.findViewById(R.id.customer_detail_header_trial_tv);
        mOrderTv = (TextView) view.findViewById(R.id.customer_detail_header_order_tv);
        mDealTv = (TextView) view.findViewById(R.id.customer_detail_header_deal_tv);
        mInvitationIv = (ImageView) view.findViewById(R.id.customer_detail_header_invitation_iv);
        mTrialIv = (ImageView) view.findViewById(R.id.customer_detail_header_trial_iv);
        mOrderIv = (ImageView) view.findViewById(R.id.customer_detail_header_order_iv);
        mDealIv = (ImageView) view.findViewById(R.id.customer_detail_header_deal_iv);
        mInvitationCheckedIv = (ImageView) view.findViewById(R.id.customer_detail_header_invitation_checked_iv);
        mTrialCheckedIv = (ImageView) view.findViewById(R.id.customer_detail_header_trial_checked_iv);
        mOrderCheckedIv = (ImageView) view.findViewById(R.id.customer_detail_header_order_checked_iv);
        mDealCheckedIv = (ImageView) view.findViewById(R.id.customer_detail_header_deal_checked_iv);
        mSmsIv = (ImageView) view.findViewById(R.id.customer_detail_header_sms_iv);
        mPhoneIv = (ImageView) view.findViewById(R.id.customer_detail_header_phone_iv);
//        mChannelSdv = (SimpleDraweeView) view.findViewById(R.id.customer_detail_header_channel_sdv);
        mChannelIv = (ImageView) view.findViewById(R.id.customer_detail_header_channel_iv);
        mActivityTv = (TextView) view.findViewById(R.id.customer_detail_header_activity_tv);
        mOTDOrderStatusIv = (ImageView) view.findViewById(R.id.customer_detail_header_otd_order_status_iv);
        // 休眠状态/战败UI
        if ((null != detailEntity && "11540".equals(detailEntity.getOppStatusId())) ||
                (null != detailEntity && "11530".equals(detailEntity.getOppStatusId()))) {
            setUIByStatus(CustomerDetailHeaderFragment.SLEEP_STATUS);
        } else {
            setUIByStatus(CustomerDetailHeaderFragment.NORMAL_STATUS);
        }

        mTvPhoneNumber.setText(detailEntity.getCustMobile());
    }

    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        mNameTv.setText(entity.getCustName());
        mCarModelTv.setText(entity.getCarModelName());//显示车型描述而不是id
        if (entity.getIsKeyuser().equals("0")) {
            mKeyCustomerIv.setImageResource(R.mipmap.ic_yellow_card_item_vip_selected);
        } else {
            mKeyCustomerIv.setImageResource(R.mipmap.ic_yellow_card_item_vip_unselected);
        }
        mHeadIv.setImageResource("0".equals(entity.getCustGender())
                ? R.mipmap.customer_detail_man : R.mipmap.customer_detail_woman);
        mOuterColorTv.setText(entity.getOutsideColorName());
        mInnerColorTv.setText(entity.getInsideColorName());
        mActivityTv.setText(entity.getActivitySubject());//外拓活动文字
        JLog.d(TAG, "url: " + entity.getChannelImageUrl());
        renderChannel(entity);
        renderOppLevel(entity.getOppLevelName());
        renderRecyclerView();
        renderFourLabels(entity);
        checkOrderStatus();
        renderOrderStatus();
    }

    private void renderChannel(OpportunityDetailEntity entity) {
        String srcTypeId = entity.getSrcTypeId();
        if (TextUtils.isEmpty(srcTypeId)) {
            mChannelIv.setVisibility(View.GONE);
            return;
        }
        if (!TextUtils.isEmpty(entity.getChannelImageUrl())) {
            Glide.with(this).load(entity.getChannelImageUrl()).into(mChannelIv);
            return;
        }
        int res = 0;
        switch (srcTypeId) {
            case "11010"://到店
                res = R.mipmap.customer_detail_channel_store;
                break;
            case "11020"://电商
                res = R.mipmap.customer_detail_channel_ecommerce;
                break;
            case "11030"://线索
                res = R.mipmap.customer_detail_channel_leads;
                break;
            case "11040"://来电
                res = R.mipmap.customer_detail_channel_phone_call;
                break;
            case "11050"://外拓
                res = R.mipmap.customer_detail_channel_outside_activity;
                break;
        }
        if (res != 0) {
//            String result = String.format(Locale.CHINA, "res://%s/%d", getContext().getPackageName(), res);
//            Uri uri = Uri.parse(result);
//            mChannelSdv.setImageURI(uri);
            Glide.with(this).load(res).into(mChannelIv);
        } else {
            mChannelIv.setVisibility(View.GONE);
        }
    }

    private void renderOppLevel(String levelName) {
        if (TextUtils.isEmpty(levelName)) {
            return;
        }
        if (levelName.equalsIgnoreCase("A")) {
            mOppLevelIv.setImageResource(R.mipmap.customer_detail_followup_a);
        } else if (levelName.equalsIgnoreCase("B")) {
            mOppLevelIv.setImageResource(R.mipmap.customer_detail_followup_b);
        } else if (levelName.equalsIgnoreCase("C")) {
            mOppLevelIv.setImageResource(R.mipmap.customer_detail_followup_c);
        } else if (levelName.equalsIgnoreCase("H")) {
            mOppLevelIv.setImageResource(R.mipmap.customer_detail_followup_h);
        } else if (levelName.equalsIgnoreCase("N")) {
            mOppLevelIv.setImageResource(R.mipmap.customer_detail_followup_n);
        }
    }

    /**
     * 根据状态设置不同UI
     *
     * @param status
     */
    public void setUIByStatus(int status) {
        switch (status) {
            case NORMAL_STATUS:
                showNormalStatusUI();
                break;
            case SLEEP_STATUS:
                showSleepStatusUI();
                break;
        }
    }

    /**
     * 显示正常状态的UI
     */
    private void showNormalStatusUI() {
        llRootView.setBackgroundResource(R.mipmap.customer_detail_header_bg);
        llSmsPhoneOuter.setBackground(getResources().getDrawable(R.drawable.customer_detail_header_yellow_bg));
    }

    /**
     * 显示休眼状态的UI
     */
    private void showSleepStatusUI() {
        llRootView.setBackgroundResource(R.mipmap.customer_detail_header_sleep_bg);
        llSmsPhoneOuter.setBackground(getResources().getDrawable(R.drawable.customer_detail_header_gray_bg));
    }

    private void renderRecyclerView() {
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        List<OpportunityDetailEntity.RelationsBean> list = entity.getOpportunityRelations();
        List<OpportunityDetailEntity.RelationsBean> resultList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return;
        }
        //筛选出flag为“0”的bean（"1"的bean表示信息途径）
        for (OpportunityDetailEntity.RelationsBean bean : list) {
            if (bean.getRelaFlag().equals("0")) {
                resultList.add(bean);
            }
        }
        DemandAdapter adapter = new DemandAdapter(resultList);
        mDemandRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mDemandRv.setAdapter(adapter);
    }

    private void renderFourLabels(OpportunityDetailEntity entity) {
        checkIfIsExhibition(entity);
        checkIfIsTestDrive(entity);
        List<OpportunityDetailEntity.FollowupTagsBean> list = entity.getFollowupTags();
        if (list == null || list.isEmpty()) {
            return;
        }
        JLog.d(TAG, "map: " + NewCustomerConstants.contactResultMultiChoicesMap);
        for (OpportunityDetailEntity.FollowupTagsBean bean : list) {
            String dictId = bean.getDictId();
            if (!TextUtils.isEmpty(dictId)) {
                switch (dictId) {
                    case "15530":
                        //试乘试驾
                        mTrialIv.setImageResource(R.mipmap.customer_detail_try_car_s);
                        mTrialCheckedIv.setVisibility(View.VISIBLE);
                        mTrialTv.setAlpha(1);
                        checkExhibition();
                        break;
                    case "15540":
                        //订单
                        break;
                    case "15560":
                        //交车
                        mDealIv.setImageResource(R.mipmap.customer_detail_delivery_s);
                        mDealCheckedIv.setVisibility(View.VISIBLE);
                        mDealTv.setAlpha(1);
                        checkExhibition();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //check跟进记录里是否有某条记录选择了展厅
    private void checkIfIsExhibition(OpportunityDetailEntity entity) {
        List<OpportunityDetailEntity.FollowupsBean> followupsList = entity.getFollowups();
        if (followupsList == null || followupsList.isEmpty()) {
            return;
        }
        for (OpportunityDetailEntity.FollowupsBean bean : followupsList) {
            String modeId = bean.getModeId();
            //15010:展厅
            if ("15010".equals(modeId)) {
                checkExhibition();
            }
        }
    }

    /**
     * 给展厅打勾
     */
    private void checkExhibition() {
        mInvitationIv.setImageResource(R.mipmap.customer_detail_walk_in_s);
        mInvitationCheckedIv.setVisibility(View.VISIBLE);
        mInvitationTv.setAlpha(1);
    }

    /**
     * 新的检查是否试乘试驾的逻辑
     */
    private void checkIfIsTestDrive(OpportunityDetailEntity entity) {
        List<OpportunityDetailEntity.FollowupsBean> followupsList = entity.getFollowups();
        if (followupsList == null || followupsList.isEmpty()) {
            return;
        }
        for (OpportunityDetailEntity.FollowupsBean bean : followupsList) {
            if ("0".equals(bean.getIsTestdrive())) {
                //试乘试驾打勾
                mTrialIv.setImageResource(R.mipmap.customer_detail_try_car_s);
                mTrialCheckedIv.setVisibility(View.VISIBLE);
                mTrialTv.setAlpha(1);
                checkExhibition();//同时也给展厅打勾
            }
        }
    }

    @Override
    protected void setListeners() {
        mSmsIv.setOnClickListener(this);
        mPhoneIv.setOnClickListener(this);
        mKeyCustomerIv.setOnClickListener(this);
        mChannelIv.setOnClickListener(this);
//        mOrderTv.setOnClickListener(this);
//        mOrderLayout.setOnClickListener(this);
        mOrderIv.setOnClickListener(this);
        mOTDOrderStatusIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String mobile = ((OpportunityDetailEntity) mEntity).getCustMobile();
        Intent intent = null;
        switch (v.getId()) {
            case R.id.customer_detail_header_sms_iv:
                // 非owner或黄卡是休眠/战败状态
                if ((null != detailEntity && !detailEntity.isYellowCardOwner()) ||
                        (null != detailEntity && detailEntity.isSleepStatus()) ||
                        (null != detailEntity && detailEntity.isFailedStatus())) {
                    return;
                }
                TalkingDataUtils.onEvent(getActivity(), "发短信", "潜客详情");
                intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mobile));
                break;
            case R.id.customer_detail_header_phone_iv:
                // 非owner或黄卡是休眠/战败状态
                if ((null != detailEntity && !detailEntity.isYellowCardOwner()) ||
                        (null != detailEntity && detailEntity.isSleepStatus()) ||
                        (null != detailEntity && detailEntity.isFailedStatus())) {
                    return;
                }
                TalkingDataUtils.onEvent(getActivity(), "打电话", "潜客详情");
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
                break;
            case R.id.customer_detail_header_vip_iv:
                // 非owner或黄卡是休眠/战败状态
                if ((null != detailEntity && !detailEntity.isYellowCardOwner()) ||
                        (null != detailEntity && detailEntity.isSleepStatus()) ||
                        (null != detailEntity && detailEntity.isFailedStatus())) {
                    return;
                }
                TalkingDataUtils.onEvent(getActivity(), "标记重点客户", "潜客详情");
                onVipClick();
                break;
            case R.id.customer_detail_header_channel_iv:
                // 非owner或黄卡是休眠/战败状态
                if (null != detailEntity && !detailEntity.isYellowCardOwner() ||
                        (null != detailEntity && detailEntity.isSleepStatus()) ||
                        (null != detailEntity && detailEntity.isFailedStatus())) {
                    return;
                }
                onBenefitClick();
                break;
            case R.id.customer_detail_header_order_iv:
                // 非owner
                if (null != detailEntity && !detailEntity.isYellowCardOwner()) {
                    return;
                }
                if (!TextUtils.isEmpty(mOrderStatus)) {
                    TalkingDataUtils.onEvent(getActivity(), "点击订单", "潜客详情");
                    intent = new Intent(getActivity(), RdOrderActivity.class);
                    intent.putExtra("entity", mEntity);
                }
                break;
            case R.id.customer_detail_header_otd_order_status_iv:
                showOTDOrderStatusDialog("经销商已付款等待总部发运");
                break;

        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void showOTDOrderStatusDialog(String msg) {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        otdOrderStatusDialog = new CustomDialog(getContext(), dialogAdapter);
        dialogAdapter.setDialogMessageText(msg);
        otdOrderStatusDialog.setDialogTitle(getResources().getString(R.string.otd_order_status));
        otdOrderStatusDialog.setTitleIcon(R.mipmap.ic_home_schedule_delay);
        otdOrderStatusDialog.setBtnConfirmText(getResources().getString(R.string.public_submit));
        otdOrderStatusDialog.hideShowCancelBtn();
        otdOrderStatusDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {

            }

            @Override
            public void onConfirmBtnClick() {
                otdOrderStatusDialog.dismiss();
            }
        });
        otdOrderStatusDialog.show();
    }

    /**
     * 检查跟进结果是否选择过订单，以及orderId和orderStatus
     */
    private void checkOrderStatus() {

        if (mEntity != null) {
            mOrderId = ((OpportunityDetailEntity) mEntity).getOrderId();
            mOrderStatus = ((OpportunityDetailEntity) mEntity).getOrderStatus();
            mIsOtdOrder = ((OpportunityDetailEntity) mEntity).getIsOtdOrder();
        }
    }

    /**
     * 显示订单状态（小红点、已完成、已取消）
     */
    private void renderOrderStatus() {
        if (!TextUtils.isEmpty(mOrderStatus)) {
            mOrderCheckedIv.setVisibility(View.VISIBLE);
            checkExhibition();
            if ("-1".equals(mOrderStatus)) {
                mOrderIv.setImageResource(R.mipmap.customer_detail_order_s);
                mOrderTv.setAlpha(1);
                mOrderCheckedIv.setImageResource(R.mipmap.customer_detail_pending);
            } else if ("0".equals(mOrderStatus)) {
                mOrderIv.setImageResource(R.mipmap.customer_detail_order_s);
                mOrderTv.setAlpha(1);
                mOrderCheckedIv.setImageResource(R.mipmap.customer_detail_finished);
            } else if ("1".equals(mOrderStatus)) {
                mOrderIv.setImageResource(R.mipmap.customer_detail_order_s);
                mOrderTv.setAlpha(1);
                mOrderCheckedIv.setImageResource(R.mipmap.customer_detail_order_cancelled);
            } else if ("1.1".equals(mOrderStatus)) {
                mOrderIv.setImageResource(R.mipmap.customer_detail_order_s);
                mOrderTv.setAlpha(1);
                mOrderCheckedIv.setImageResource(R.mipmap.customer_detail_pending);
            } else if ("2".equals(mOrderStatus) || "3".equals(mOrderStatus)) {
                mOrderIv.setImageResource(R.mipmap.customer_detail_otd_order_s);
                mOrderTv.setAlpha(1);
                mOrderCheckedIv.setImageResource(R.mipmap.customer_detail_finished);
            } else if ("4".equals(mOrderStatus) || "5".equals(mOrderStatus) || "6".equals(mOrderStatus)) {
                if ("0".equals(mIsOtdOrder)) {
                    //OTD订单
                    mOrderIv.setImageResource(R.mipmap.customer_detail_otd_order_s);
                    mOrderTv.setAlpha(1);
                    mOrderCheckedIv.setImageResource(R.mipmap.customer_detail_finished);
                } else if ("1".equals(mIsOtdOrder)) {
                    //非OTD订单
                    mOrderIv.setImageResource(R.mipmap.customer_detail_order_s);
                    mOrderTv.setAlpha(1);
                    mOrderCheckedIv.setImageResource(R.mipmap.customer_detail_finished);
                }
            }
        }
    }

    private void onVipClick() {
        if (null == transferVipDialog) {
            dialogAdapter = new YellowVipDialogAdapter();
            transferVipDialog = new CustomDialog(getActivity(), dialogAdapter);
            transferVipDialog.hideTitleIcon();
            transferVipDialog.setDialogTitle(getActivity().getResources().getString(R.string.yellow_vip_dialog_title));
        }
        if ("0".equals(((OpportunityDetailEntity) mEntity).getIsKeyuser())) {
            dialogAdapter.setContentText(getResources().getString(R.string.yellow_vip_dialog_cancel_tip));
        } else {
            dialogAdapter.setContentText(getResources().getString(R.string.yellow_vip_dialog_tip));
        }
        transferVipDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                transferVipDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                if (headPresenter instanceof YCDetailHeadPresenter) {
                    if ("0".equals(((OpportunityDetailEntity) mEntity).getIsKeyuser())) {
                        headPresenter.postVipCustomer(((OpportunityDetailEntity) mEntity).getOppId(), "1");
                    } else {
                        headPresenter.postVipCustomer(((OpportunityDetailEntity) mEntity).getOppId(), "0");
                    }

                }
                transferVipDialog.dismiss();
            }
        });
        transferVipDialog.show();
    }

    private void onBenefitClick() {
        if (headPresenter instanceof YCDetailHeadPresenter) {
            Map<String, Object> options = new HashMap();
            options.put("leadsId", ((OpportunityDetailEntity) mEntity).getLeadsId());
            headPresenter.getBenefitDate(options);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != headPresenter) {
            headPresenter.onDestroy();
        }
    }

    @Override
    public void setVipStatusSuccess(String status) {
        if ("0".equals(status)) {
            mKeyCustomerIv.setImageResource(R.mipmap.ic_yellow_card_item_vip_selected);
            ((OpportunityDetailEntity) mEntity).setIsKeyuser("0");
        } else if ("1".equals(status)) {
            mKeyCustomerIv.setImageResource(R.mipmap.ic_yellow_card_item_vip_unselected);
            ((OpportunityDetailEntity) mEntity).setIsKeyuser("1");
        }
        sendBroadcastForVipOperation(status);
    }

    private void sendBroadcastForVipOperation(String result) {
        Intent i = new Intent("com.svw.dealerapp.vip.status.change");
        i.putExtra("oppId", ((OpportunityDetailEntity) mEntity).getOppId());
        i.putExtra("isKeyuser", result);
        getContext().sendBroadcast(i);
    }

    @Override
    public void setVipStatusFail(String message) {

    }

    @Override
    public void getBenefitDataSuccess(String benefitStr) {
        if (null == benefitDialog) {
            benefitDialogAdapter = new BenefitDialogAdapter();
            benefitDialog = new CustomDialog(getActivity(), benefitDialogAdapter);
            benefitDialog.hideTitleIcon();
            benefitDialog.setDialogTitle(getActivity().getResources().getString(R.string.task_e_commerce_benefit_dialog_title));
            benefitDialog.hideShowConfirmBtn();
            benefitDialog.setBtnCancelText(getActivity().getResources().getString(R.string.task_e_commerce_benefit_dialog_close));
            benefitDialog.showTitleUnderLine();
            benefitDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                @Override
                public void onCancelBtnClick() {
                    benefitDialog.dismiss();
                }

                @Override
                public void onConfirmBtnClick() {

                }
            });
        }
        // TODO: 5/27/2017
        benefitDialogAdapter.setTvContent(benefitStr);
        benefitDialog.show();
    }

    @Override
    public void getBenefitDataFail() {
        ToastUtils.showToast(getResources().getString(R.string.task_e_commerce_get_benefit_fail));
    }

    @Override
    public void getBenefitDataEmpty() {
        ToastUtils.showToast(getResources().getString(R.string.task_e_commerce_get_benefit_empty));
    }

    private class DemandAdapter extends RecyclerView.Adapter<DemandAdapter.RecyclerViewHolder> {

        private List<OpportunityDetailEntity.RelationsBean> mList;

        DemandAdapter(List<OpportunityDetailEntity.RelationsBean> list) {
            mList = list;
        }

        @Override
        public DemandAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_detail_demand, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DemandAdapter.RecyclerViewHolder holder, int position) {
            OpportunityDetailEntity.RelationsBean bean = mList.get(position);
            holder.mText.setText(bean.getRelaDesc());
        }

        @Override
        public int getItemCount() {
            if (mList != null && !mList.isEmpty()) {
                return mList.size();
            } else {
                return 0;
            }
        }


        class RecyclerViewHolder extends RecyclerView.ViewHolder {

            TextView mText;

            RecyclerViewHolder(View itemView) {
                super(itemView);
                mText = (TextView) itemView.findViewById(R.id.item_customer_detail_textview);
            }
        }
    }
}
