package com.svw.dealerapp.ui.resource.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.order.activity.OrderCancelActivity;
import com.svw.dealerapp.ui.order.activity.OrderCancelActivity2;
import com.svw.dealerapp.ui.resource.activity.YellowCardTransferActivity;
import com.svw.dealerapp.ui.resource.adapter.OrderCustomerAdapter;
import com.svw.dealerapp.ui.resource.adapter.RdOrderAdapter;
import com.svw.dealerapp.ui.resource.adapter.YellowVipDialogAdapter;
import com.svw.dealerapp.ui.resource.contract.OrderCustomerContract;
import com.svw.dealerapp.ui.resource.model.OrderCustomerModel;
import com.svw.dealerapp.ui.resource.presenter.OrderCustomerPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by qinshi on 5/5/2017.
 */

public class OrderCustomerFragment extends BaseListFragment<OrderCustomerEntity, OrderCustomerEntity.OrderCustomerInfoEntity>
        implements OrderCustomerContract.View{

    public static final String REFRESH_FILTER_STRING = "com.svw.dealerapp.order.customer.refresh";

    private static final int transferYellowCardResult = 1002;
    private static final int cancelOrderResult = 1003;

    private CustomDialog cancelDialog;
    private YellowVipDialogAdapter cancelDialogAdapter;
    private RdOrderAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new OrderCustomerPresenter(this, new OrderCustomerModel());

        registerFreshReceiver(REFRESH_FILTER_STRING);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new RdOrderAdapter(getActivity(), presenter.getDataList(), presenter.getShowMonthEntities());

        //显示月份的横条
        setShowHorizontalTag(true);

        return adapter;
    }

    /**
     * 显示设置消取订单对话框
     * @param entity
     * @param position
     */
    private void showCancelDialog(final OrderCustomerEntity.OrderCustomerInfoEntity entity, final int position,
                                  boolean isShowIcon, boolean isShowConfirmBtn, String content){
        if (null == cancelDialog) {
            cancelDialogAdapter = new YellowVipDialogAdapter();
            cancelDialog = new CustomDialog(getActivity(), cancelDialogAdapter);
            cancelDialog.setDialogTitle(getActivity().getResources().getString(R.string.order_customer_cancel_dialog_title));
        }

        if(isShowIcon) {
            cancelDialog.showTitleIcon();
        }else {
            cancelDialog.hideTitleIcon();
        }
        if(isShowConfirmBtn){
            cancelDialog.showConfirmBtn();
            cancelDialog.setBtnCancelText(getResources().getString(R.string.dialog_cancel));
        }else {
            cancelDialog.hideShowConfirmBtn();
            cancelDialog.setBtnCancelText(getResources().getString(R.string.dialog_confirm));
        }
        cancelDialogAdapter.setContentText(content);

        cancelDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                cancelDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                if(presenter instanceof OrderCustomerPresenter){
                    OrderCustomerPresenter fragmentPresenter = (OrderCustomerPresenter) presenter;
                    Map<String, Object> options = new HashMap<>();
                    options.put("orderId", entity.getOrderId());
                    fragmentPresenter.postCancelOrder(getActivity(), options, position);
                }
            }
        });
        cancelDialog.show();
    }

    /**
     * 从筛选Activity返回调用
     * @param filter
     */
    public void requestDateAfterTakeFilter(String filter){
        filterString = filter;
        showLoadingLayout();
        presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filter, ListFragmentPresenter.REQUEST_BY_INIT);
    }

//    @Override
//    public String getFilter() {
//        String filter = "{\"followupResult\":\"15540\"}";
//        try {
//            return URLEncoder.encode(filter, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != cancelDialog){
            cancelDialog.dismiss();
            cancelDialog = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case transferYellowCardResult:
                if(null != data) {
                    final int dealPosition = data.getIntExtra("dealPosition", -1);
                    if (dealPosition >= 0) {
                        presenter.getDataList().remove(dealPosition);
                        adapter.notifyDataSetChanged();
                    }
                    adapter.clearIntercept();
                }
                break;
            case cancelOrderResult:
                if(null != data) {
                    int dealPosition = data.getIntExtra("dealListPosition", -1);
                    String orderStatus = data.getStringExtra("orderStatus");
                    if (dealPosition >= 0) {
//                        presenter.getDataList().get(dealPosition).setOrderStatus("1");
                        if(!"3".equals(orderStatus)) {
                            presenter.getDataList().remove(dealPosition);
                        }else {
                            presenter.getDataList().get(dealPosition).setOrderStatus("3");
                        }
                        adapter.notifyDataSetChanged();
                    }
                    adapter.clearIntercept();
                }
                break;
        }
    }

    @Override
    public void cancelSuccess(int dealPosition) {
        adapter.clearIntercept();
        adapter.notifyDataSetChanged();
        ToastUtils.showToast(getResources().getString(R.string.order_customer_cancel_success));
    }

    @Override
    public void cancelFail() {
        ToastUtils.showToast(getResources().getString(R.string.order_customer_cancel_fail));
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "资源-订单客户");
        JLog.i("talkingDataFlag-show", "资源-订单客户");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "资源-订单客户");
        JLog.i("talkingDataFlag-hide", "资源-订单客户");
    }

    @Override
    public void setTabTipNumber(String number) {
        RdMainActivity mainActivity = (RdMainActivity)getActivity();
        (mainActivity.getResourceFragment()).setTabTipNumber(1, number);
    }
}
