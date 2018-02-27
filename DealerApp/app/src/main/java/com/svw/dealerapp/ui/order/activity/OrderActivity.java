package com.svw.dealerapp.ui.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.fragment.CarTypeFragment;
import com.svw.dealerapp.ui.optionalpackage.activity.OptionalPackageListActivity;
import com.svw.dealerapp.ui.order.contract.OrderContract;
import com.svw.dealerapp.ui.order.fragment.OrderBuyerInfoFragment;
import com.svw.dealerapp.ui.order.fragment.OrderInfoFragment;
import com.svw.dealerapp.ui.order.fragment.OrderOppInfoFragment;
import com.svw.dealerapp.ui.order.fragment.OrderRecommendedInfoFragment;
import com.svw.dealerapp.ui.order.fragment.OrderReplacementInfoFragment;
import com.svw.dealerapp.ui.order.model.OrderModel;
import com.svw.dealerapp.ui.order.presenter.OrderPresenter;
import com.svw.dealerapp.ui.resource.fragment.OrderCustomerFragment;
import com.svw.dealerapp.ui.resource.fragment.YellowCardFragment;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.CustomSectionViewGroup;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.svw.dealerapp.global.NewCustomerConstants.REQUEST_CODE_PACKAGE;

/**
 * 新建/查看订单
 * Created by xupan on 28/07/2017.
 */
@Deprecated
public class OrderActivity extends BaseActivity implements OrderContract.View {

    private static final String TAG = "OrderActivity";
    private CustomSectionViewGroup mReplacementInfoVg, mRecommendedInfoVg, mBuyerInfoVg, mOppInfoVg, mOrderInfoVg;
    private OrderOppInfoFragment mOppInfoFragment;//潜客信息
    private OrderBuyerInfoFragment mBuyerFragment;//车主信息
    private OrderInfoFragment mOrderInfoFragment;//订单信息(不包括意向车系)
    private CarTypeFragment mCarTypeFragment;
    private OrderReplacementInfoFragment mReplacementInfoFragment;//置换信息
    private OrderRecommendedInfoFragment mRecommendedInfoFragment;//朋友推荐信息
    private View mFirstGapView;

    private Button mSaveOrOtdBt, mSubmitBt;
    private LoadingDialog mLoadingDialog;
    private OpportunityDetailEntity mOpportunityDetailEntity;
    private QueryOrderDetailResponseEntity mOrderDetailEntity;
    private OrderPresenter mPresenter;
    private LinearLayout submitLL;
    //    private String mOppId;//潜客id
    private String mIsOtdOrder;//0：是，1：否
    private String mOrderId;
    //-1:首次创建，0：有效，1：无效，1.1：无效要创建，2：OTD，3：OTD取消中，4：开票中，5：已开票，6：已交车
    private String mOrderStatus;

    private static final int cancelOrderResult = 1003;
    private ArrayList<OptionalPackageEntity.OptionListBean> mCurrentOptionalPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JLog.d(TAG, "onCreate");
        getDataFromIntent();
        initMVPData();
//        initViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_order;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.d(TAG, "onDestroy");
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (mOrderDetailEntity != null && mOrderDetailEntity.isBuyerInfoEditable()) {
            promptToCancelEditOrder();
            return;
        }
        if (!"-1".equals(mOrderStatus) && !"1.1".equals(mOrderStatus)) {
            super.onBackPressed();
            return;
        }
        SimpleMessageDialogAdapter adapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, adapter);
        adapter.setDialogMessageText(getString(R.string.order_back_hint));
        dialog.setBtnCancelText(getString(R.string.new_customer_cancel_confirm));
        dialog.setBtnConfirmText(getString(R.string.new_customer_cancel_cancel));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
                finish();
                TalkingDataUtils.onEvent(OrderActivity.this, "订单创建页-返回提示-丢弃");
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                TalkingDataUtils.onEvent(OrderActivity.this, "订单创建页-返回提示-取消");
            }
        });
        dialog.show();
    }

    private void promptToCancelEditOrder() {
        SimpleMessageDialogAdapter adapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, adapter);
        adapter.setDialogMessageText("是否确定丢弃更改？");
        dialog.setBtnCancelText(getString(R.string.new_customer_cancel_confirm));
        dialog.setBtnConfirmText(getString(R.string.new_customer_cancel_cancel));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
//                mPresenter.queryOrderDetail(mOrderId);
                if (!TextUtils.isEmpty(mOrderStatus) && "-1".equals(mOrderStatus)) {
                    OrderActivity.super.onBackPressed();
                } else {
                    queryOrderDetail();
                }
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initMVPData() {
        mPresenter = new OrderPresenter(new OrderModel(), this);
//        mPresenter.queryOrderDetail(mOrderId);
//        if (!"-1".equals(mOrderStatus) && !"1.1".equals(mOrderStatus)) {
        if (!"1.1".equals(mOrderStatus)) {
            queryOrderDetail();
        } else {
            //还原黄卡详情里的选装包
            if (mOpportunityDetailEntity != null && mOpportunityDetailEntity.getEcCarOptions() != null) {
                mCurrentOptionalPackage = mOpportunityDetailEntity.getEcCarOptions();
            }
            initViews();
            initFragments();
        }
    }

    private void queryOrderDetail() {
        Map<String, Object> options = new HashMap<>();
//        options.put("oppId", mOppId);
        options.put("orderId", mOrderId);
        mPresenter.queryOrderDetail(options);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mOpportunityDetailEntity = (OpportunityDetailEntity) intent.getSerializableExtra("entity");
        if (mOpportunityDetailEntity != null) {
//            mOppId = mOpportunityDetailEntity.getOppId();
            mOrderId = mOpportunityDetailEntity.getOrderId();
            mOrderStatus = mOpportunityDetailEntity.getOrderStatus();
        }
    }

    private Serializable selectEntity() {
        if (mOrderDetailEntity != null) {
            if (!TextUtils.isEmpty(mOrderStatus) && "-1".equals(mOrderStatus)) {
                mOrderDetailEntity.setBuyerInfoEditable(true);
            }
            return mOrderDetailEntity;
        } else {
            //mOpportunityDetailEntity始终会从上一页带入
            return mOpportunityDetailEntity;
        }
    }

    private void initViews() {
        mTitleTv.setText("订单");
        mSaveOrOtdBt = (Button) findViewById(R.id.order_save_or_otd_bt);
        mSubmitBt = (Button) findViewById(R.id.order_submit_bt);
        submitLL = (LinearLayout) findViewById(R.id.activity_order_bottom_layout);
        mFirstGapView = findViewById(R.id.activity_order_first_gap_view);
        mFirstGapView.setVisibility(View.VISIBLE);
        mRightTv.setVisibility(View.GONE);
        submitLL.setVisibility(View.VISIBLE);
        if ("1.1".equals(mOrderStatus)) {
//            initFragments();
            mSaveOrOtdBt.setVisibility(View.GONE);
        } else if ("-1".equals(mOrderStatus)) {
            mSaveOrOtdBt.setVisibility(View.GONE);
        } else if ("1".equals(mOrderStatus)) {
            mSaveOrOtdBt.setVisibility(View.GONE);
            mSubmitBt.setText("订单已取消");
            mSubmitBt.setEnabled(false);
        } else if ("0".equals(mOrderStatus)) {
            mRightTv.setText("编辑");
            mRightTv.setVisibility(View.VISIBLE);
            mSaveOrOtdBt.setVisibility(View.VISIBLE);
            mSaveOrOtdBt.setText(R.string.order_report_otd);
            mSubmitBt.setText("取消订单");
            mSubmitBt.setEnabled(true);
        } else if ("2".equals(mOrderStatus)) {
            mRightTv.setText("编辑");
            mRightTv.setVisibility(View.VISIBLE);
            mTitleTv.setText("OTD订单");
            mSaveOrOtdBt.setVisibility(View.GONE);
            if (mOrderDetailEntity != null && "0".equals(mOrderDetailEntity.getIsEcOtdOrder())) {
                mSubmitBt.setVisibility(View.GONE);
            } else {
                mSubmitBt.setVisibility(View.VISIBLE);
                mSubmitBt.setText(R.string.order_cancel_otd_order);
                mSubmitBt.setEnabled(true);
            }
        } else if ("3".equals(mOrderStatus)) {
            mTitleTv.setText("OTD订单");
            mSaveOrOtdBt.setVisibility(View.GONE);
            mSubmitBt.setText("OTD订单取消中");
            mSubmitBt.setEnabled(false);
        } else if ("4".equals(mOrderStatus) || "5".equals(mOrderStatus) || "6".equals(mOrderStatus)) {
            submitLL.setVisibility(View.GONE);
            mRightTv.setVisibility(View.GONE);
        }
        mSubmitBt.setOnClickListener(this);
        mSaveOrOtdBt.setOnClickListener(this);
    }

    private void initFragments() {
        Serializable entity = selectEntity();
        mOppInfoVg = (CustomSectionViewGroup) findViewById(R.id.order_opp_info_container);
        mOppInfoVg.setVisibility(View.VISIBLE);
        mOppInfoVg.setTitleText("潜客信息");
        mOppInfoVg.removeFragment();
        //潜客信息始终显示黄卡详情页的潜客姓名和电话
        OrderOppInfoFragment mOppInfoFragment = OrderOppInfoFragment.getInstance(mOpportunityDetailEntity);
        mOppInfoVg.addFragment(mOppInfoFragment);

        mBuyerInfoVg = (CustomSectionViewGroup) findViewById(R.id.order_buyer_info_container);
        mBuyerInfoVg.setVisibility(View.VISIBLE);
        mBuyerInfoVg.setTitleText("车主信息");
        mBuyerFragment = OrderBuyerInfoFragment.newInstance(entity);
        mBuyerInfoVg.removeFragment();
        mBuyerInfoVg.addFragment(mBuyerFragment);

        mOrderInfoVg = (CustomSectionViewGroup) findViewById(R.id.order_order_info_container);
        mOrderInfoVg.setVisibility(View.VISIBLE);
        mOrderInfoVg.setTitleText("订单信息");
        mOrderInfoFragment = OrderInfoFragment.newInstance(entity);
        mCarTypeFragment = CarTypeFragment.newInstance(entity);
        mCarTypeFragment.setViewMandatory(true, true, false);
        mOrderInfoVg.removeFragment();
        mOrderInfoVg.addFragment(mCarTypeFragment);
        mOrderInfoVg.addFragment(mOrderInfoFragment);

        mReplacementInfoVg = (CustomSectionViewGroup) findViewById(R.id.order_replacement_info_container);
        mReplacementInfoVg.setVisibility(View.VISIBLE);
        mReplacementInfoVg.setTitleText("会员信息");
        mReplacementInfoFragment = OrderReplacementInfoFragment.newInstance(entity);
        mReplacementInfoVg.removeFragment();
        mReplacementInfoVg.addFragment(mReplacementInfoFragment);

        mRecommendedInfoVg = (CustomSectionViewGroup) findViewById(R.id.order_recommended_info_container);
        mRecommendedInfoVg.setVisibility(View.VISIBLE);
        mRecommendedInfoVg.setTitleText("朋友推荐信息");
        mRecommendedInfoVg.removeFragment();
        mRecommendedInfoFragment = OrderRecommendedInfoFragment.newInstance(entity);
        mRecommendedInfoVg.addFragment(mRecommendedInfoFragment);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.order_submit_bt:
//                if (mOrderDetailEntity != null && mOrderDetailEntity.isBuyerInfoEditable()) {
//                    if (!checkFragmentCorrectness()) {
//                        break;
//                    }
//                    prepareToEditBuyerInfo();
//                    TalkingDataUtils.onEvent(this, "订单更新页-提交");
//                    return;
//                }
                if ("1.1".equals(mOrderStatus) && checkFragmentCorrectness()) {
                    prepareToCreate();//创建订单
                    TalkingDataUtils.onEvent(this, "订单创建页-提交");
                } else if ((mOrderDetailEntity != null && mOrderDetailEntity.isBuyerInfoEditable())) {
                    if (checkFragmentCorrectness()) {
                        prepareToEditBuyerInfo();
                    }
                    TalkingDataUtils.onEvent(this, "订单更新页-提交");
                } else if ("0".equals(mOrderStatus) || "2".equals(mOrderStatus)) {
//                    promptToCancel();
                    TalkingDataUtils.onEvent(this, "订单查看页-取消订单");
//                    Intent intent = new Intent(this, OrderCancelActivity.class);
                    Intent intent = new Intent(this, OrderCancelActivity2.class);
                    intent.putExtra("orderId", mOrderDetailEntity.getOrderId());
                    intent.putExtra("oppId", mOrderDetailEntity.getOppId());
                    intent.putExtra("oppStatusId", mOpportunityDetailEntity.getOppStatusId());
                    intent.putExtra("oppLevelId", mOpportunityDetailEntity.getOppLevel());
                    intent.putExtra("seriesId", mOrderDetailEntity.getSeriesId());
                    startActivityForResult(intent, cancelOrderResult);
                } /* else if ("viewAndCancelOTD".equals(mIntentFlag)) {
                    //取消OTD订单
                    promptToCancelOtdOrder();
                }*/
                break;
            case R.id.order_save_or_otd_bt:
                if ("0".equals(mOrderStatus)) {
                    promptToReportOtd();
                }
                break;
            case R.id.common_title_bar_right_textview:
                mRightTv.setVisibility(View.GONE);
                mOrderDetailEntity.setBuyerInfoEditable(true);
//                initViews();
                initFragments();
                mSaveOrOtdBt.setVisibility(View.GONE);
                mSubmitBt.setVisibility(View.VISIBLE);
                mSubmitBt.setEnabled(false);
                mSubmitBt.setText("确定");
                if (!TextUtils.isEmpty(mOrderStatus) && "2".equals(mOrderStatus)) {
                    mFirstGapView.setVisibility(View.GONE);
                    mOppInfoVg.setVisibility(View.GONE);
                    mOrderInfoVg.setVisibility(View.GONE);
                    mRecommendedInfoVg.setVisibility(View.GONE);
                    mReplacementInfoVg.setVisibility(View.GONE);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkFragmentsValidation();
                    }
                }, 1000);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case cancelOrderResult:
                if (null != data) {
                    queryOrderDetail();
                }
                break;
            case REQUEST_CODE_PACKAGE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<OptionalPackageEntity.OptionListBean> list =
                            mCurrentOptionalPackage = data.getParcelableArrayListExtra("selectData");
                    List<String> resultList = CommonUtils.getOptionalPackage(list, true);
                    mOrderInfoFragment.displayOptionalPackage(resultList);
                }
                break;
        }
    }

    /**
     * 提示上报OTD订单
     */
    private void promptToReportOtd() {
        SimpleMessageDialogAdapter adapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, adapter);
        adapter.setDialogMessageText("确认上报OTD?");
        dialog.setBtnCancelText(getString(R.string.public_cancel));
        dialog.setBtnConfirmText(getString(R.string.public_submit));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                //调接口上报OTD
                mPresenter.reportOtdOrder(mOrderId);
            }
        });
        dialog.show();
    }

    private void promptToCancelOtdOrder() {
        SimpleMessageDialogAdapter adapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, adapter);
        adapter.setDialogMessageText("是否确定取消OTD上报?");
        dialog.setBtnCancelText(getString(R.string.public_cancel));
        dialog.setBtnConfirmText(getString(R.string.public_submit));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                //调取消OTD订单接口
            }
        });
        dialog.show();
    }

    private void promptToCancel() {
        SimpleMessageDialogAdapter adapter = new SimpleMessageDialogAdapter();
        final CustomDialog dialog = new CustomDialog(this, adapter);
        adapter.setDialogMessageText(getString(R.string.order_cancel_hint));
        dialog.setBtnCancelText(getString(R.string.public_yes));//左边为是，右边为否
        dialog.setBtnConfirmText(getString(R.string.public_no));
        dialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                dialog.dismiss();
                mPresenter.cancelOrder(mOrderId); //取消订单
                TalkingDataUtils.onEvent(OrderActivity.this, "订单查看页-取消订单-是");
            }

            @Override
            public void onConfirmBtnClick() {
                dialog.dismiss();
                TalkingDataUtils.onEvent(OrderActivity.this, "订单查看页-取消订单-否");
            }
        });
        dialog.show();
    }

    private void prepareToCreate() {
        CreateOrderEntity entity = new CreateOrderEntity();
        entity.setOppId(mOpportunityDetailEntity.getOppId());
        entity.setOrgId(mOpportunityDetailEntity.getOrgId());
        entity.setProvinceId(mOpportunityDetailEntity.getProvinceId());
        entity.setCityId(mOpportunityDetailEntity.getCityId());
        entity.setSrcTypeId(mOpportunityDetailEntity.getSrcTypeId());
        entity.setChannelId(mOpportunityDetailEntity.getChannelId());
        CreateOrderEntity entityFromBuyer = mBuyerFragment.getParameters();
        entity.setCustName(entityFromBuyer.getCustName());
        entity.setCustGender(entityFromBuyer.getCustGender());
        entity.setCustMobile(entityFromBuyer.getCustMobile());
        entity.setCustAddress(entityFromBuyer.getCustAddress());
        entity.setCustEmail(entityFromBuyer.getCustEmail());
        entity.setCertType(entityFromBuyer.getCertType());
        entity.setCertNum(entityFromBuyer.getCertNum());
        entity.setCustType(entityFromBuyer.getCustType());
        entity.setCustIndustry(entityFromBuyer.getCustIndustry());
        entity.setCustDuty(entityFromBuyer.getCustDuty());
        entity.setCorpNature(entityFromBuyer.getCorpNature());
        OpportunitySubmitReqEntity entityFromCarType = mCarTypeFragment.getParameters();
        entity.setSeriesId(entityFromCarType.getSeriesId());
        entity.setModelId(entityFromCarType.getCarModelId());
        CreateOrderEntity entityFromOrderInfo = mOrderInfoFragment.getParameters();
        entity.setOutsideColorId(entityFromOrderInfo.getOutsideColorId());
        entity.setInsideColorId(entityFromOrderInfo.getInsideColorId());
//        entity.setOptionPackage(entityFromOrderInfo.getOptionPackage());//旧的选装包
        entity.setPaymentMoney(entityFromOrderInfo.getPaymentMoney());
        entity.setCurrentMoney(entityFromOrderInfo.getCurrentMoney());
        entity.setDeliveryDate(entityFromOrderInfo.getDeliveryDate());
        if (mReplacementInfoVg.getVisibility() == View.VISIBLE) {
            CreateOrderEntity entityFromReplacement = mReplacementInfoFragment.getParameters();
            entity.setCarOwnerName(entityFromReplacement.getCarOwnerName());
            entity.setCarOwnerTel(entityFromReplacement.getCarOwnerTel());
            entity.setCarOwnerVin(entityFromReplacement.getCarOwnerVin());
            entity.setCarOwnerCard(entityFromReplacement.getCarOwnerCard());
        }
        if (mRecommendedInfoVg.getVisibility() == View.VISIBLE) {
            CreateOrderEntity entityFromRecommend = mRecommendedInfoFragment.getParameters();
            entity.setRecomName(entityFromRecommend.getRecomName());
            entity.setRecomMobile(entityFromRecommend.getRecomMobile());
            entity.setRecomVin(entityFromRecommend.getRecomVin());
            entity.setRecomCard(entityFromRecommend.getRecomCard());
            entity.setCarServiceCode(entityFromRecommend.getCarServiceCode());
        }
        entity.setSalesConsultant(mOpportunityDetailEntity.getOppOwner());
        entity.setEcCarOptions(mCurrentOptionalPackage);
        JLog.d(TAG, "entity: " + entity);
        mPresenter.createOrder(entity);
    }

    private void prepareToEditBuyerInfo() {
//        mRefreshOrderListFlag = true;
        CreateOrderEntity entity = new CreateOrderEntity();
        entity.setOrderId(mOrderId);
        entity.setOppId(mOpportunityDetailEntity.getOppId());
        entity.setOrgId(mOpportunityDetailEntity.getOrgId());
        entity.setProvinceId(mOpportunityDetailEntity.getProvinceId());
        entity.setCityId(mOpportunityDetailEntity.getCityId());
        entity.setSrcTypeId(mOpportunityDetailEntity.getSrcTypeId());
        entity.setChannelId(mOpportunityDetailEntity.getChannelId());
        CreateOrderEntity entityFromBuyer = mBuyerFragment.getParameters();
        entity.setCustName(entityFromBuyer.getCustName());
        entity.setCustGender(entityFromBuyer.getCustGender());
        entity.setCustMobile(entityFromBuyer.getCustMobile());
        entity.setCustAddress(entityFromBuyer.getCustAddress());
        entity.setCustEmail(entityFromBuyer.getCustEmail());
        entity.setCertType(entityFromBuyer.getCertType());
        entity.setCertNum(entityFromBuyer.getCertNum());
        entity.setCustType(entityFromBuyer.getCustType());
        entity.setCustIndustry(entityFromBuyer.getCustIndustry());
        entity.setCustDuty(entityFromBuyer.getCustDuty());
        entity.setCorpNature(entityFromBuyer.getCorpNature());
        OpportunitySubmitReqEntity entityFromCarType = mCarTypeFragment.getParameters();
        entity.setSeriesId(entityFromCarType.getSeriesId());
        entity.setModelId(entityFromCarType.getCarModelId());
        CreateOrderEntity entityFromOrderInfo = mOrderInfoFragment.getParameters();
        entity.setOutsideColorId(entityFromOrderInfo.getOutsideColorId());
        entity.setInsideColorId(entityFromOrderInfo.getInsideColorId());
//        entity.setOptionPackage(entityFromOrderInfo.getOptionPackage());//旧的选装包
        entity.setPaymentMoney(entityFromOrderInfo.getPaymentMoney());
        entity.setCurrentMoney(entityFromOrderInfo.getCurrentMoney());
        entity.setDeliveryDate(entityFromOrderInfo.getDeliveryDate());
        if (mReplacementInfoVg.getVisibility() == View.VISIBLE) {
            CreateOrderEntity entityFromReplacement = mReplacementInfoFragment.getParameters();
            entity.setCarOwnerName(entityFromReplacement.getCarOwnerName());
            entity.setCarOwnerTel(entityFromReplacement.getCarOwnerTel());
            entity.setCarOwnerVin(entityFromReplacement.getCarOwnerVin());
            entity.setCarOwnerCard(entityFromReplacement.getCarOwnerCard());
        } else {
            //此时表示orderStatus为2时的编辑订单场景，虽然不显示会员section，但仍然传旧数据过去，不然后台会认为是清空数据
            if (mOrderInfoFragment.getReplacementViewData()) {
                entity.setCarOwnerName(mOrderDetailEntity.getCarOwnerName());
                entity.setCarOwnerTel(mOrderDetailEntity.getCarOwnerTel());
                entity.setCarOwnerVin(mOrderDetailEntity.getCarOwnerVin());
                entity.setCarOwnerCard(mOrderDetailEntity.getCarOwnerCard());
            } else {
                entity.setCarOwnerName("");
                entity.setCarOwnerTel("");
                entity.setCarOwnerVin("");
                entity.setCarOwnerCard("");
            }
        }
        if (mRecommendedInfoVg.getVisibility() == View.VISIBLE) {
            CreateOrderEntity entityFromRecommend = mRecommendedInfoFragment.getParameters();
            entity.setRecomName(entityFromRecommend.getRecomName());
            entity.setRecomMobile(entityFromRecommend.getRecomMobile());
            entity.setRecomVin(entityFromRecommend.getRecomVin());
            entity.setRecomCard(entityFromRecommend.getRecomCard());
            entity.setCarServiceCode(entityFromRecommend.getCarServiceCode());
        } else {
            //此时表示orderStatus为2时的编辑订单场景，虽然不显示会员section，但仍然传旧数据过去，不然后台会认为是清空数据
            if (mOrderInfoFragment.getRecommendedViewData()) {
                entity.setRecomName(mOrderDetailEntity.getRecomName());
                entity.setRecomMobile(mOrderDetailEntity.getRecomMobile());
                entity.setRecomVin(mOrderDetailEntity.getRecomVin());
                entity.setRecomCard(mOrderDetailEntity.getRecomCard());
                entity.setCarServiceCode(mOrderDetailEntity.getCarServiceCode());
            } else {
                entity.setRecomName("");
                entity.setRecomMobile("");
                entity.setRecomVin("");
                entity.setRecomCard("");
                entity.setCarServiceCode("");
            }
        }
        entity.setSalesConsultant(mOpportunityDetailEntity.getOppOwner());
        if (mCurrentOptionalPackage != null) {
            entity.setEcCarOptions(mCurrentOptionalPackage);
        } else {
            entity.setEcCarOptions(new ArrayList<OptionalPackageEntity.OptionListBean>());
        }
        JLog.d(TAG, "entity: " + entity);
        mPresenter.editOrder(entity);
    }

    @Override
    public void onRequestStart() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    @Override
    public void onRequestError(String msg) {
        onRequestEnd();
        finish();
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
    public void onCreateOrderSuccess() {
        Intent i = new Intent(OrderCustomerFragment.REFRESH_FILTER_STRING);
        Intent i2 = new Intent(YellowCardFragment.FRESH_FILTER_STRING);
        sendBroadcast(i);
        sendBroadcast(i2);
        finish();
    }

    @Override
    public void onCreateOrderFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onQueryOrderDetailSuccess(QueryOrderDetailResponseEntity entity) {
        mOrderDetailEntity = entity;
        mOrderStatus = entity.getOrderStatus();
        mCurrentOptionalPackage = entity.getEcCarOptions();
        initViews();
        initFragments();
        refreshOrderList(true);
    }

    @Override
    public void onQueryOrderDetailFailure(String msg) {
        ToastUtils.showToast(msg);
        refreshOrderList(false);
    }

    private void refreshOrderList(boolean editSuccess) {
        if (editSuccess) {
            Intent i = new Intent(OrderCustomerFragment.REFRESH_FILTER_STRING);  //订单客户列表
            sendBroadcast(i);
        }
    }

    @Override
    public void onCancelOrderSuccess() {

    }

    @Override
    public void onCancelOrderFailure(String msg) {

    }

    @Override
    public void onReportOtdOrderSuccess() {
        queryOrderDetail();
        Intent i = new Intent(OrderCustomerFragment.REFRESH_FILTER_STRING);//订单客户列表
        sendBroadcast(i);
    }

    @Override
    public void onReportOtdOrderFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onEditOrderSuccess() {
        ToastUtils.showToast("修改订单信息成功");
        queryOrderDetail();
    }

    @Override
    public void onEditOrderFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    public void showReplacementFragment(boolean show) {
        mReplacementInfoVg.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showRecommendFragment(boolean show) {
        mRecommendedInfoVg.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        mOrderInfoFragment.onQueryCarTypeSuccess(entity);
    }

    public void resetCarTypeColor() {
        mOrderInfoFragment.resetCarTypeColor();
    }

    public void resetOptionalPackage() {
        mOrderInfoFragment.resetOptionalPackage();
        mCurrentOptionalPackage = null;
    }

    /**
     * check各个Fragment中各数据是否输入完整
     */
    public void checkFragmentsValidation() {
        if (mReplacementInfoVg.getVisibility() == View.VISIBLE && !mReplacementInfoFragment.checkDataValidation()) {
            mSubmitBt.setEnabled(false);
            return;
        }
        if (mRecommendedInfoVg.getVisibility() == View.VISIBLE && !mRecommendedInfoFragment.checkDataValidation()) {
            mSubmitBt.setEnabled(false);
            return;
        }
        if (mOrderInfoVg.getVisibility() == View.VISIBLE && (!mOrderInfoFragment.checkDataValidation() || !mCarTypeFragment.checkDataValidation())) {
            mSubmitBt.setEnabled(false);
            return;
        }
        mSubmitBt.setEnabled(mBuyerFragment.checkDataValidation());
    }

    /**
     * check各个Fragment中各数据是否输入合法
     *
     * @return true: 全部合法 false: 至少有一处不合法
     */
    private boolean checkFragmentCorrectness() {
        if (mBuyerInfoVg.getVisibility() == View.VISIBLE && !mBuyerFragment.checkDataCorrectness()) {
            return false;
        }
        if (mOrderInfoVg.getVisibility() == View.VISIBLE && !mOrderInfoFragment.checkDataCorrectness()) {
            return false;
        }
        if (mReplacementInfoVg.getVisibility() == View.VISIBLE && !mReplacementInfoFragment.checkDataCorrectness()) {
            return false;
        }
        if (mRecommendedInfoVg.getVisibility() == View.VISIBLE && !mRecommendedInfoFragment.checkDataCorrectness()) {
            return false;
        }
        return true;
    }

    public void queryOptionalPackage() {
        OpportunitySubmitReqEntity entityFromCarType = mCarTypeFragment.getParameters();
        String seriesId = entityFromCarType.getSeriesId();
        String carModelId = entityFromCarType.getCarModelId();
        CreateOrderEntity entityFromOrderInfo = mOrderInfoFragment.getParameters();
        String insideColorId = entityFromOrderInfo.getInsideColorId();
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
}
