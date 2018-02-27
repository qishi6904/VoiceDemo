package com.svw.dealerapp.ui.resource.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.svw.dealerapp.R;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.resource.adapter.RdFollowUpAdapter;
import com.svw.dealerapp.ui.resource.adapter.YellowVipDialogAdapter;
import com.svw.dealerapp.ui.resource.contract.YellowCardContract;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.ui.resource.entity.YellowCardFilterEntity;
import com.svw.dealerapp.ui.resource.model.YellowCardModel;
import com.svw.dealerapp.ui.resource.presenter.YellowFragmentPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by qinshi on 5/5/2017.
 */

public class YellowCardFragment extends BaseListFragment<YellowCardEntity, YellowCardEntity.YellowCardInfoEntity>
        implements YellowCardContract.VipView {

    public final static String FRESH_FILTER_STRING = "com.svw.dealerapp.yellow.card.fresh";
    private static final int transferYellowCardResult = 1001;

    private CustomDialog transferVipDialog;
    private CustomDialog activeDialog;
    private YellowVipDialogAdapter vipDialogAdapter;
    private StringCustomDialogAdapter activeDialogAdapter;
    private RdFollowUpAdapter adapter;
    private VipReceiver vipReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new YellowFragmentPresenter(this, new YellowCardModel());

        vipReceiver = new VipReceiver();
        IntentFilter filter = new IntentFilter("com.svw.dealerapp.vip.status.change");
        getActivity().registerReceiver(vipReceiver, filter);

        registerFreshReceiver(FRESH_FILTER_STRING);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new RdFollowUpAdapter(getActivity(), presenter.getDataList(),
                presenter.getShowMonthEntities());

        //显示月份的横条
        setShowHorizontalTag(true);

        return adapter;
    }

    /**
     * 显示设置重点客户对话框
     *
     * @param entity
     * @param position
     */
    private void showVipDialog(final YellowCardEntity.YellowCardInfoEntity entity, final int position) {
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
                if (presenter instanceof YellowFragmentPresenter) {
                    YellowFragmentPresenter fragmentPresenter = (YellowFragmentPresenter) presenter;
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

    /**
     * 显示激活的对话框
     */
    private void showActiveDialog(final YellowCardEntity.YellowCardInfoEntity entity, final int position, String content) {
        if (null == activeDialog) {
            activeDialogAdapter = new StringCustomDialogAdapter();
            activeDialog = new CustomDialog(getActivity(), activeDialogAdapter);
            activeDialog.hideTitleIcon();
            activeDialog.setDialogTitle(getActivity().getResources().getString(R.string.yellow_back_view_active));
        }
        activeDialogAdapter.setContent(content);
        activeDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                activeDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                activeDialog.dismiss();
                YellowFragmentPresenter fragmentPresenter = (YellowFragmentPresenter) presenter;
                Map<String, Object> options = new HashMap<>();
                options.put("oppId", entity.getOppId());
                fragmentPresenter.postActiveYellow(options, position);
            }
        });
        activeDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != transferVipDialog) {
            transferVipDialog.dismiss();
            transferVipDialog = null;
        }
        if (null != activeDialog) {
            activeDialog.dismiss();
            activeDialog = null;
        }
        if (null != vipReceiver) {
            getActivity().unregisterReceiver(vipReceiver);
        }
    }

    @Override
    public void showSetVipSuccessToast(String isKeyuser) {
        if ("0".equals(isKeyuser)) {
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_success));
        } else {
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_cancel_success));
        }
        presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_INIT, getMoreParams());
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
    public void activeYellowCardSuccess(int dealPosition) {
        adapter.clearIntercept();
        adapter.notifyDataSetChanged();
        ToastUtils.showToast(getResources().getString(R.string.yellow_active_success));
    }

    @Override
    public void activeYellowCardFail() {
        ToastUtils.showToast(getResources().getString(R.string.yellow_active_fail));
    }

    /**
     * 从筛选Activity返回或在搜索页面调用
     *
     * @param filter
     */
    public void requestDateAfterTakeFilter(String filter) {
        try {
            Gson gson = new Gson();
            YellowCardFilterEntity entity = gson.fromJson(URLDecoder.decode(filter, "utf-8"), YellowCardFilterEntity.class);
//            if (TextUtils.isEmpty(entity.getOppStatus())) {
//                entity.setOppStatus("11510,11512");
//            }
            entity.setOppStatus("11510");
            filter = URLEncoder.encode(GsonUtils.changeEntityToJsonStr(entity), "utf-8");
            filterString = filter;
            showLoadingLayout();
            presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filter, ListFragmentPresenter.REQUEST_BY_INIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case transferYellowCardResult:
                if (null != data) {
                    final int dealPosition = data.getIntExtra("dealPosition", -1);
                    if (dealPosition >= 0) {
                        presenter.getDataList().remove(dealPosition);
                        if(presenter.getDataList().size() == 0){
                            showNoDataLayout();
                        }
                        adapter.notifyDataSetChanged();
                    }
                    adapter.clearIntercept();
                }
                break;
        }
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
                        adapter.clearIntercept();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String getFilter() {
        try {
            return URLEncoder.encode("{\"oppStatus\":\"11510\"}", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "资源-潜客");
        JLog.i("talkingDataFlag-show", "资源-潜客");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "资源-潜客");
        JLog.i("talkingDataFlag-hide", "资源-潜客");
    }

    @Override
    public void setTabTipNumber(String number) {
        RdMainActivity mainActivity = (RdMainActivity)getActivity();
        (mainActivity.getResourceFragment()).setTabTipNumber(0, number);
    }
}
