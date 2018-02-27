package com.svw.dealerapp.ui.order.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.ui.newcustomer.fragment.BaseCustomerFragment;
import com.svw.dealerapp.ui.order.activity.OrderActivity;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForCheckbox;
import com.svw.dealerapp.ui.widget.CustomItemViewForDatePicker;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionalPackage;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 订单-订单信息(不包括意向车系)
 * Created by xupan on 01/08/2017.
 */
@Deprecated
public class OrderInfoFragment extends BaseCustomerFragment {

    private static final String TAG = "OrderInfoFragment";
    private CustomItemViewForOptionsPicker mOuterColorView, mInnerColorView;
    private CustomItemViewForCheckbox mReplacementView, mRecommendedView;
    private CustomItemViewForEditText mDownPaymentView, mDealPaymentAmountView, mPackageView;
    private CustomItemViewForDatePicker mDateOfDeliveryView;
    private CustomItemViewForOptionalPackage mOptionalPackage;

    private boolean mFirstEnter = true;

    public static OrderInfoFragment newInstance(Serializable entity) {
        OrderInfoFragment fragment = new OrderInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_info;
    }

    @Override
    protected void initViews(View view) {
        mOuterColorView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.order_outer_decor_view);
        mOuterColorView.setTitleText("外饰");
        mOuterColorView.setHintTextForContentView("请选择外饰");
        mOuterColorView.setMandatory(true);
        mInnerColorView = (CustomItemViewForOptionsPicker) view.findViewById(R.id.order_inner_decor_view);
        mInnerColorView.setTitleText("内饰");
        mInnerColorView.setHintTextForContentView("请选择内饰");
        mInnerColorView.setMandatory(true);
        mPackageView = (CustomItemViewForEditText) view.findViewById(R.id.order_package_view);
        mPackageView.setTitleText("选装包");
        mPackageView.setHintTextForContentView("请输入选装包");
        mOptionalPackage = (CustomItemViewForOptionalPackage) view.findViewById(R.id.order_optional_package);
        mOptionalPackage.setTitleText("选装包");
        mOptionalPackage.setHintTextForContentView("请选择选装包");
        mReplacementView = (CustomItemViewForCheckbox) view.findViewById(R.id.order_replacement_view);
        mReplacementView.setTitleText("是否会员");
        mRecommendedView = (CustomItemViewForCheckbox) view.findViewById(R.id.order_recommended_view);
        mRecommendedView.setTitleText("是否朋友推荐");
        mDownPaymentView = (CustomItemViewForEditText) view.findViewById(R.id.order_down_payment_amount_view);
        mDownPaymentView.setMandatory(false);
        mDownPaymentView.setTitleText("定金金额");
        mDownPaymentView.setHintTextForContentView("请输入定金金额");
        mDownPaymentView.limitContentToNumber();
        mDealPaymentAmountView = (CustomItemViewForEditText) view.findViewById(R.id.order_deal_amount_view);
        mDealPaymentAmountView.setTitleText("车辆实际成交价");
        mDealPaymentAmountView.setHintTextForContentView("请输入车辆实际成交价");
        mDealPaymentAmountView.limitContentToNumber();
        mDateOfDeliveryView = (CustomItemViewForDatePicker) view.findViewById(R.id.order_delivery_date_view);
        mDateOfDeliveryView.setMandatory(true);
        mDateOfDeliveryView.setTitleText("预计交车时间");
        mDateOfDeliveryView.setHintTextForContentView("请选择预计交车时间");
        mDateOfDeliveryView.setShowBottomLine(false);
        mDateOfDeliveryView.initPicker(new boolean[]{true, true, true, false, false, false}, Calendar.getInstance(), null);
        mDateOfDeliveryView.setDateFormatStr("yyyy-MM-dd");
        mDateOfDeliveryView.setDateFormatStrForParameter("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    @Override
    protected void initBaseViews() {
        super.initBaseViews();
        mAllBaseView = new CustomItemViewBase[]{mOuterColorView, mInnerColorView, mPackageView, mReplacementView,
                mRecommendedView, mDownPaymentView, mDealPaymentAmountView, mDateOfDeliveryView, mOptionalPackage};
    }

    @Override
    protected void onDataChanged(Object object) {
        super.onDataChanged(object);
        ((OrderActivity) getActivity()).checkFragmentsValidation();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mReplacementView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                boolean isChecked = (Boolean) object;
                ((OrderActivity) getActivity()).showReplacementFragment(isChecked);
                ((OrderActivity) getActivity()).checkFragmentsValidation();
            }
        });
        mRecommendedView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                boolean isChecked = (Boolean) object;
                ((OrderActivity) getActivity()).showRecommendFragment(isChecked);
                ((OrderActivity) getActivity()).checkFragmentsValidation();
            }
        });
        mOptionalPackage.setOnBaseViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askActivityToQueryOptionalPackage();
            }
        });
        mInnerColorView.setOnBaseViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askActivityToResetOptionalPackage();
            }
        });
    }

    private void askActivityToQueryOptionalPackage() {
        if (getActivity() instanceof OrderActivity) {
            ((OrderActivity) getActivity()).queryOptionalPackage();
        }
    }

    private void askActivityToResetOptionalPackage() {
        if (getActivity() instanceof OrderActivity) {
            ((OrderActivity) getActivity()).resetOptionalPackage();
        }
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        //创建订单模式
        if (mEntity instanceof OpportunityDetailEntity) {
            OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
            mRecommendedView.setData(!TextUtils.isEmpty(entity.getRecomName()));
            mPackageView.setData(entity.getOptionPackage());
            ((OrderActivity) getActivity()).showRecommendFragment(!TextUtils.isEmpty(entity.getRecomName()));
//            ((OrderActivity) getActivity()).showReplacementFragment("13530".equals(entity.getPropertyId()));
            ((OrderActivity) getActivity()).showReplacementFragment(false);
            //将潜客详情里的选装包带入到创建订单页
            List<String> resultList = CommonUtils.getOptionalPackage(entity.getEcCarOptions(), true);
            mOptionalPackage.setData(resultList);
        } else if (mEntity instanceof QueryOrderDetailResponseEntity) {//显示订单模式
            QueryOrderDetailResponseEntity entity = (QueryOrderDetailResponseEntity) mEntity;
            enableBaseViews(entity.isBuyerInfoEditable());
            mReplacementView.setData(!TextUtils.isEmpty(entity.getCarOwnerName()));
            mRecommendedView.setData(!TextUtils.isEmpty(entity.getRecomName()));
            //编辑模式且订单状态为2时，只允许编辑车主信息
            if (entity.isBuyerInfoEditable() && !TextUtils.isEmpty(entity.getOrderStatus())
                    && "2".equals(entity.getOrderStatus())) {
                ((OrderActivity) getActivity()).showRecommendFragment(false);
                ((OrderActivity) getActivity()).showReplacementFragment(false);
            } else {
                ((OrderActivity) getActivity()).showRecommendFragment(!TextUtils.isEmpty(entity.getRecomName()));
                ((OrderActivity) getActivity()).showReplacementFragment(!TextUtils.isEmpty(entity.getCarOwnerName()));
            }
            mDownPaymentView.setData(entity.getPaymentMoney());
            mDealPaymentAmountView.setData(entity.getCurrentMoney());
            mDateOfDeliveryView.setData(entity.getDeliveryDate());
            List<String> resultList = CommonUtils.getOptionalPackage(entity.getEcCarOptions(), entity.isBuyerInfoEditable());
            mOptionalPackage.setData(resultList);
            if (entity.isBuyerInfoEditable()) {
                mOuterColorView.setData(entity.getOutsideColorId());
                mInnerColorView.setData(entity.getInsideColorId());
            }
        }
    }

    public Boolean getReplacementViewData() {
        return mReplacementView.getInputData();
    }

    public Boolean getRecommendedViewData() {
        return mRecommendedView.getInputData();
    }

    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        initCarTypeColorOptions(entity);
        if (mFirstEnter) {
            mFirstEnter = false;
            if (mEntity instanceof OpportunityDetailEntity) {
                OpportunityDetailEntity entityFromIntent = (OpportunityDetailEntity) mEntity;
                mOuterColorView.setData(entityFromIntent.getOutsideColorId());
                mInnerColorView.setData(entityFromIntent.getInsideColorId());
            } else if (mEntity instanceof QueryOrderDetailResponseEntity) {
                QueryOrderDetailResponseEntity entityFromServer = (QueryOrderDetailResponseEntity) mEntity;
                mOuterColorView.setData(entityFromServer.getOutsideColorId());
                mInnerColorView.setData(entityFromServer.getInsideColorId());
            }
        }
    }

    public void resetCarTypeColor() {
        if (mOuterColorView != null) {
            mOuterColorView.clearData();
        }
        if (mInnerColorView != null) {
            mInnerColorView.clearData();
        }
    }

    public void resetOptionalPackage() {
        if (mOptionalPackage != null) {
            mOptionalPackage.setData(null);
        }
    }

    private void initCarTypeColorOptions(CarTypesEntity entity) {
        List<CarTypesEntity.ColorNListBean> colorNList = entity.getColorNList();
        Map<String, String> innerColorMap = new LinkedHashMap<>();
        for (CarTypesEntity.ColorNListBean innerColorBean : colorNList) {
            innerColorMap.put(innerColorBean.getColorId(), innerColorBean.getColorNameCn());
        }
        CommonUtils.initOptionsView(innerColorMap, mInnerColorView);

        List<CarTypesEntity.ColorWListBean> colorWList = entity.getColorWList();
        Map<String, String> outerColorMap = new LinkedHashMap<>();
        for (CarTypesEntity.ColorWListBean outerColorBean : colorWList) {
            outerColorMap.put(outerColorBean.getColorId(), outerColorBean.getColorNameCn());
        }
        CommonUtils.initOptionsView(outerColorMap, mOuterColorView);
    }

    @Override
    public CreateOrderEntity getParameters() {
        CreateOrderEntity entity = new CreateOrderEntity();
        entity.setOutsideColorId(mOuterColorView.getInputData());
        entity.setInsideColorId(mInnerColorView.getInputData());
        entity.setOptionPackage(mPackageView.getInputData());//选装包
        mReplacementView.getInputData();
        mRecommendedView.getInputData();
        entity.setPaymentMoney(mDownPaymentView.getInputData());
        entity.setCurrentMoney(mDealPaymentAmountView.getInputData());
        entity.setDeliveryDate(mDateOfDeliveryView.getInputData());
        return entity;
    }

    @Override
    public boolean checkDataCorrectness() {
//        if (!mCarTypeView.isInputValid()) {
//            ToastUtils.showToast(getString(R.string.customer_detail_car_type_try_again_hint));
//            return false;
//        }
        String dateStr = mDateOfDeliveryView.getInputData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CHINA);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar old = Calendar.getInstance();
        old.setTime(date);
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        if (date != null && old.before(now)) {
            ToastUtils.showToast("交车时间不能早于今天");
            return false;
        }
        return true;
    }

    public void displayOptionalPackage(List<String> list) {
        mOptionalPackage.setData(list);
    }
}
