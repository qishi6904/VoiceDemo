package com.svw.dealerapp.ui.resource.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.SleepCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.newcustomer.activity.ActivateYellowCardActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.newcustomer.contract.CheckBeforeActivateYCContract;
import com.svw.dealerapp.ui.newcustomer.model.CheckBeforeActivateYCModel;
import com.svw.dealerapp.ui.newcustomer.presenter.CheckBeforeActivateYCPresenter;
import com.svw.dealerapp.ui.resource.activity.YellowCardTransferActivity;
import com.svw.dealerapp.ui.resource.adapter.RdSleepAdapter;
import com.svw.dealerapp.ui.resource.adapter.SleepCustomerAdapter;
import com.svw.dealerapp.ui.resource.adapter.YellowVipDialogAdapter;
import com.svw.dealerapp.ui.resource.contract.SleepCustomerContract;
import com.svw.dealerapp.ui.resource.model.SleepCustomerModel;
import com.svw.dealerapp.ui.resource.presenter.FailedCustomerPresenter;
import com.svw.dealerapp.ui.resource.presenter.SleepCustomerPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinshi on 9/25/2017.
 */

public class SleepCustomerFragment extends BaseListFragment<SleepCustomerEntity, SleepCustomerEntity.SleepCustomerInfoEntity>
        implements SleepCustomerContract.View, CheckBeforeActivateYCContract.View {

    public final static String FRESH_FILTER_STRING = "com.svw.dealerapp.sleep.customer.fresh";

    private static final int transferYellowCardResult = 1001;

    private CustomDialog activeDialog;
    private RdSleepAdapter adapter;
    private VipReceiver vipReceiver;
    private YellowVipDialogAdapter vipDialogAdapter;
    private CustomDialog transferVipDialog;

    // 查检是否可以激活
    private CheckBeforeActivateYCPresenter checkBeforeActivateYCPresenter;
    private SleepCustomerEntity.SleepCustomerInfoEntity activeEntity;
    private CustomDialog checkBeforeActivateDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SleepCustomerPresenter(this, new SleepCustomerModel());

        vipReceiver = new VipReceiver();
        IntentFilter filter = new IntentFilter("com.svw.dealerapp.vip.status.change");
        getActivity().registerReceiver(vipReceiver, filter);

        registerFreshReceiver(FRESH_FILTER_STRING);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {

        adapter = new RdSleepAdapter(getActivity(), presenter.getDataList(), presenter.getShowMonthEntities());

        setShowHorizontalTag(true);

        adapter.setOnBtnClickListener(new RdSleepAdapter.OnBtnClickListener() {
            @Override
            public void onBtnOneClick(View v, SleepCustomerEntity.SleepCustomerInfoEntity entity, int position) {
                activeEntity = entity;
                if(null == checkBeforeActivateYCPresenter) {
                    checkBeforeActivateYCPresenter = new CheckBeforeActivateYCPresenter(SleepCustomerFragment.this, new CheckBeforeActivateYCModel());
                }
                Map<String, Object> options = new HashMap<>();
                options.put("oppId", entity.getOppId());
                checkBeforeActivateYCPresenter.checkBeforeActivateYC(options);

            }
        });

        return adapter;
    }

    /**
     * 显示激活的对话框
     */
    private void showActiveDialog(final SleepCustomerEntity.SleepCustomerInfoEntity entity, final int position) {
        if (null == activeDialog) {
            StringCustomDialogAdapter activeDialogAdapter = new StringCustomDialogAdapter();
            activeDialog = new CustomDialog(getActivity(), activeDialogAdapter);
            activeDialog.hideTitleIcon();
            activeDialog.setDialogTitle(getActivity().getResources().getString(R.string.yellow_back_view_active));
            activeDialogAdapter.setContent(getResources().getString(R.string.yellow_active_tip));
        }
        activeDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                activeDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                activeDialog.dismiss();
                SleepCustomerPresenter fragmentPresenter = (SleepCustomerPresenter) presenter;
                Map<String, Object> options = new HashMap<>();
                options.put("oppId", entity.getOppId());
                fragmentPresenter.postActiveYellow(options, position);
            }
        });
        activeDialog.show();
    }

    /**
     * 显示设置重点客户对话框
     *
     * @param entity
     * @param position
     */
    private void showVipDialog(final SleepCustomerEntity.SleepCustomerInfoEntity entity, final int position) {
        if (null == transferVipDialog) {
            vipDialogAdapter = new YellowVipDialogAdapter();
            transferVipDialog = new CustomDialog(getActivity(), vipDialogAdapter);
            transferVipDialog.hideTitleIcon();
            transferVipDialog.setDialogTitle(getActivity().getResources().getString(R.string.yellow_vip_dialog_title));
        }
        if ("0".equals(entity.getIsKeyuser())) {
            vipDialogAdapter.setContentText(getResources().getString(R.string.yellow_vip_dialog_cancel_tip));
        } else {
            vipDialogAdapter.setContentText(getResources().getString(R.string.yellow_vip_dialog_tip));
        }
        transferVipDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                transferVipDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                if (presenter instanceof SleepCustomerPresenter) {
                    SleepCustomerPresenter fragmentPresenter = (SleepCustomerPresenter) presenter;
                    if ("0".equals(entity.getIsKeyuser())) {
                        fragmentPresenter.postVipCustomer(getActivity(), entity.getOppId(), "1", position);
                    } else {
                        fragmentPresenter.postVipCustomer(getActivity(), entity.getOppId(), "0", position);
                    }
                }
                transferVipDialog.dismiss();
            }
        });
        transferVipDialog.show();
    }

    @Override
    public void activeYellowCardSuccess(int dealPosition) {
        adapter.clearIntercept();
        adapter.notifyDataSetChanged();
        getActivity().sendBroadcast(new Intent(YellowCardFragment.FRESH_FILTER_STRING));
        ToastUtils.showToast(getResources().getString(R.string.yellow_active_success));
        if (presenter.getDataList().size() == 0) {
            showNoDataLayout();
        }
    }

    @Override
    public void activeYellowCardFail() {
        ToastUtils.showToast(getResources().getString(R.string.yellow_active_fail));
    }

    @Override
    public void showSetVipSuccessToast(String isKeyuser) {
        if ("0".equals(isKeyuser)) {
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_success));
        } else {
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_cancel_success));
        }

    }

    @Override
    public void showSetVipFailedToast(String isKeyuser) {
        if ("0".equals(isKeyuser)) {
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_fail));
        } else {
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_cancel_fail));
        }
    }

    @Override
    public void notifyListView(int dealPosition) {
        adapter.clearIntercept();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTabTipNumber(String number) {
        RdMainActivity mainActivity = (RdMainActivity)getActivity();
        (mainActivity.getResourceFragment()).setTabTipNumber(4, number);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case transferYellowCardResult:
                if (null != data) {
                    final int dealPosition = data.getIntExtra("dealPosition", -1);
                    if (dealPosition >= 0) {
                        presenter.getDataList().remove(dealPosition);
                        adapter.notifyDataSetChanged();
                    }
                    adapter.clearIntercept();
                }
                break;
        }
    }

    /**
     * 从筛选Activity返回调用
     *
     * @param filter
     */
    public void requestDateAfterTakeFilter(String filter) {
        filterString = filter;
        showLoadingLayout();
        presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filter, ListFragmentPresenter.REQUEST_BY_INIT);
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "资源-休眠客户");
        JLog.i("talkingDataFlag-show", "资源-休眠客户");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "资源-休眠客户");
        JLog.i("talkingDataFlag-hide", "资源-休眠客户");
    }

    @Override
    public void checkBeforeActivateYCSuccess(String returnCode, String msg) {
        if ("200".equals(returnCode)) {
            Intent j = new Intent(getActivity(), ActivateYellowCardActivity.class);
            j.putExtra("oppId", activeEntity.getOppId());
            j.putExtra("oppStatus", activeEntity.getOppStatusId());
            j.putExtra("oppLevel", activeEntity.getOppLevel());
            startActivity(j);
        } else if ("023004".equals(returnCode)) {
            showCheckBeforeActivateDialog(msg);
        }
    }

    @Override
    public void checkBeforeActivateYCFail(String msg) {
        ToastUtils.showToast(msg);
    }

    private void showCheckBeforeActivateDialog(String msg) {
        if(null == checkBeforeActivateDialog) {
            SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
            checkBeforeActivateDialog = new CustomDialog(getActivity(), dialogAdapter);
            dialogAdapter.setDialogMessageText(msg);
            dialogAdapter.setTextViewPadding(DensityUtil.dp2px(getActivity(), 30), 0, DensityUtil.dp2px(getActivity(), 30), 0);
            checkBeforeActivateDialog.hideShowCancelBtn();
            checkBeforeActivateDialog.setBtnConfirmText(getResources().getString(R.string.dialog_confirm));
            checkBeforeActivateDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                @Override
                public void onCancelBtnClick() {

                }

                @Override
                public void onConfirmBtnClick() {
                    checkBeforeActivateDialog.dismiss();
                }
            });
        }
        checkBeforeActivateDialog.show();
    }

    /**
     * 黄卡详情页设置为重点客户后，通过这个广播接收者修列表对应条目的状态
     */
    private class VipReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String oppId = intent.getStringExtra("oppId");
            String isKeyuser = intent.getStringExtra("isKeyuser");
            if (null != presenter && null != presenter.getDataList() &&
                    presenter.getDataList().size() > 0 && !TextUtils.isEmpty(oppId)) {
                for (int i = 0; i < presenter.getDataList().size(); i++) {
                    if (oppId.equals(presenter.getDataList().get(i).getOppId())) {
                        presenter.getDataList().get(i).setIsKeyuser(isKeyuser);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != vipReceiver) {
            getActivity().unregisterReceiver(vipReceiver);
        }
        if(null != checkBeforeActivateDialog) {
            checkBeforeActivateDialog.dismiss();
            checkBeforeActivateDialog = null;
        }
    }
}
