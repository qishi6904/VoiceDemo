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
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.AddFollowupRecordActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardSearchActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardTransferActivity;
import com.svw.dealerapp.ui.resource.adapter.YellowCardRecyclerViewAdapter;
import com.svw.dealerapp.ui.resource.adapter.YellowVipDialogAdapter;
import com.svw.dealerapp.ui.resource.contract.LeadRelateValidContract;
import com.svw.dealerapp.ui.resource.contract.YellowCardContract;
import com.svw.dealerapp.ui.resource.contract.YellowCardSearchContract;
import com.svw.dealerapp.ui.resource.entity.YellowCardFilterEntity;
import com.svw.dealerapp.ui.resource.model.LeadRelateValidModel;
import com.svw.dealerapp.ui.resource.model.YellowCardModel;
import com.svw.dealerapp.ui.resource.presenter.LeadRelateValidPresenter;
import com.svw.dealerapp.ui.resource.presenter.YellowSearchFragmentPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by qinshi on 5/5/2017.
 */
@Deprecated
public class YellowCardSearchFragment extends BaseListFragment<YellowCardEntity, YellowCardEntity.YellowCardInfoEntity>
        implements YellowCardContract.VipView, YellowCardSearchContract.View, LeadRelateValidContract.View {

    private static final int transferYellowCardResult = 1001;

    private CustomDialog transferVipDialog, activeDialog, notRelateDialog, relateDialog, relateSuccessDialog;
    private YellowVipDialogAdapter dialogAdapter;
    private StringCustomDialogAdapter activeDialogAdapter;
    private YellowCardRecyclerViewAdapter adapter;
    private String leadId;
    private LeadRelateValidPresenter leadRelateValidPresenter;
    private YellowCardEntity.YellowCardInfoEntity currentYCEntity;
    private boolean isRelated = false;
    private RelateSuccessReceiver relateSuccessReceiver;
    private int enterTo = 0;//0:YC detail 1:YC followup

    public static YellowCardSearchFragment newInstance(HashMap<String, String> map) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", map);
        YellowCardSearchFragment fragment = new YellowCardSearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Map<String, String> mParaMap = (HashMap<String, String>) bundle.get("key");
            if (mParaMap != null) {
                leadId = mParaMap.get("leadId");
            }
        }
        //注册关联成功广播
        relateSuccessReceiver = new RelateSuccessReceiver();
        IntentFilter filter = new IntentFilter("com.svw.dealerapp.leadId.relate.success");
        getActivity().registerReceiver(relateSuccessReceiver, filter);

        presenter = new YellowSearchFragmentPresenter(this, new YellowCardModel());
        leadRelateValidPresenter = new LeadRelateValidPresenter(this, new LeadRelateValidModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        if (!TextUtils.isEmpty(leadId)) {
            adapter = new YellowCardRecyclerViewAdapter(getActivity(), presenter.getDataList(), true, true);
        } else {
            adapter = new YellowCardRecyclerViewAdapter(getActivity(), presenter.getDataList(), false, true);
        }
        adapter.setOnIconClickListener(new YellowCardRecyclerViewAdapter.OnIconClickListener() {
            @Override
            public void onPhoneClick(View view, YellowCardEntity.YellowCardInfoEntity entity) {
                TalkingDataUtils.onEvent(getActivity(), "打电话", "资源潜客");
                String phoneNumber = entity.getCustMobile();
                if (TextUtils.isEmpty(phoneNumber)) {
                    phoneNumber = entity.getCustTelephone();
                }
                if (!TextUtils.isEmpty(phoneNumber)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneNumber);
                    intent.setData(data);
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(getResources().getString(R.string.yellow_phone_number_empty));
                }
            }

            @Override
            public void onVipClick(View view, final YellowCardEntity.YellowCardInfoEntity entity, final int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划标记重点客户", "资源潜客");
                if ("11540".equals(entity.getOppStatusId())) {
                    showActiveDialog(entity, position, getResources().getString(R.string.yellow_active_tip));
                } else if ("11530".equals(entity.getOppStatusId())) {
                    showActiveDialog(entity, position, getResources().getString(R.string.yellow_active_fail_tip));
                } else {
                    showVipDialog(entity, position);
                }
            }

            @Override
            public void onFollowUpClick(View view, YellowCardEntity.YellowCardInfoEntity entity, int position) {

                if (TextUtils.isEmpty(leadId)) {
                    TalkingDataUtils.onEvent(getActivity(), "左划跟进", "资源潜客");
                    Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                    intent.putExtra("oppId", entity.getOppId());
                    startActivity(intent);
                } else {
                    if (isRelated) {
                        Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                        intent.putExtra("oppId", entity.getOppId());
                        startActivity(intent);
                    } else {
                        currentYCEntity = entity;
                        if (!UserInfoUtils.getUserId().equals(entity.getOppOwner())) {
                            showNotRelateDialog(entity, getResources().getString(R.string.resource_yellow_search_pop_other), getResources().getString(R.string.dialog_cancel), getResources().getString(R.string.resource_yellow_search_pop_detail_bt));
                        } else if ("11540".equals(entity.getOppStatusId())) {
                            // 休眠状态
                            showNotRelateDialog(entity, getResources().getString(R.string.resource_yellow_search_pop_sleep), getResources().getString(R.string.dialog_cancel), getResources().getString(R.string.resource_yellow_search_pop_active_bt));
                        } else if ("11530".equals(entity.getOppStatusId())) {
                            // 战败状态
                            showNotRelateDialog(entity, getResources().getString(R.string.resource_yellow_search_pop_fail), getResources().getString(R.string.dialog_cancel), getResources().getString(R.string.resource_yellow_search_pop_active_bt));
                        } else {
                            //请求关联
                            showRelateDialog(entity, getResources().getString(R.string.resource_yellow_search_pop_relate), getResources().getString(R.string.dialog_cancel), getResources().getString(R.string.dialog_confirm));
                        }
                    }
                }
            }

            @Override
            public void onTransferClick(View view, YellowCardEntity.YellowCardInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划潜客转移", "资源潜客");
                Intent intent = new Intent(getActivity(), YellowCardTransferActivity.class);
                intent.putExtra("orgId", entity.getOrgId());
                intent.putExtra("oppId", entity.getOppId());
                intent.putExtra("customerName", entity.getCustName());
                intent.putExtra("dealPosition", position);
                startActivityForResult(intent, transferYellowCardResult);
                getActivity().overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
            }
        });

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
            dialogAdapter = new YellowVipDialogAdapter();
            transferVipDialog = new CustomDialog(getActivity(), dialogAdapter);
            transferVipDialog.hideTitleIcon();
            transferVipDialog.setDialogTitle(getActivity().getResources().getString(R.string.yellow_vip_dialog_title));
        }
        if ("0".equals(entity.getIsKeyuser())) {
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
                if (presenter instanceof YellowSearchFragmentPresenter) {
                    YellowSearchFragmentPresenter fragmentPresenter = (YellowSearchFragmentPresenter) presenter;
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
                YellowSearchFragmentPresenter fragmentPresenter = (YellowSearchFragmentPresenter) presenter;
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
        if (null != notRelateDialog) {
            notRelateDialog.dismiss();
            notRelateDialog = null;
        }
        if (null != relateDialog) {
            relateDialog.dismiss();
            relateDialog = null;
        }
        if (null != relateSuccessDialog) {
            relateSuccessDialog.dismiss();
            relateSuccessDialog = null;
        }
        if (null != relateSuccessReceiver) {
            getActivity().unregisterReceiver(relateSuccessReceiver);
        }

        if (null != leadRelateValidPresenter) {
            leadRelateValidPresenter.onDestroy();
        }
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
    public void activeYellowCardSuccess(int dealPosition) {
        adapter.clearIntercept();
        adapter.notifyDataSetChanged();
        ToastUtils.showToast(getResources().getString(R.string.yellow_active_success));
    }

    @Override
    public void activeYellowCardFail() {
        ToastUtils.showToast(getResources().getString(R.string.yellow_active_fail));
    }

    @Override
    public void setTabTipNumber(String number) {

    }

    /**
     * 搜索页面调用
     *
     * @param filter
     * @param filterEntity
     */
    public void searchYellowCard(String filter, YellowCardFilterEntity filterEntity) {
        filterString = filter;
        showLoadingLayout();
        if (null != filterEntity && !TextUtils.isEmpty(filterEntity.getCustMobile()) && StringUtils.isMatchPattern("^[0-9]{11}$", filterEntity.getCustMobile())) {
            presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filter, ListFragmentPresenter.REQUEST_BY_INIT, YellowCardModel.SEARCH_ALL);
        } else {
            presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filter, ListFragmentPresenter.REQUEST_BY_INIT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case transferYellowCardResult:
                if (null != data) {
                    int dealPosition = data.getIntExtra("dealPosition", -1);
                    if (dealPosition >= 0) {
                        presenter.getDataList().remove(dealPosition);
                    }
                    adapter.clearIntercept();
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void setSearchTotal(String total) {
        YellowCardSearchActivity activity = (YellowCardSearchActivity) getActivity();
        if (presenter.getDataList().size() == 0) {
            activity.hideSearchTotalTextView();
        } else {
            activity.setSearchTotal(total);
        }
    }

    private void showNotRelateDialog(final YellowCardEntity.YellowCardInfoEntity entity, String content, String button1, String button2) {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        notRelateDialog = new CustomDialog(getContext(), dialogAdapter);
        dialogAdapter.setDialogMessageText(content);
        dialogAdapter.setTextViewPadding(DensityUtil.dp2px(getContext(), 30), 0, DensityUtil.dp2px(getContext(), 30), 0);
        notRelateDialog.setBtnCancelText(button1);
        notRelateDialog.setBtnConfirmText(button2);
        notRelateDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                notRelateDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                notRelateDialog.dismiss();
                if (enterTo == 1) {
                    enterFollowUp();
                } else {
                    Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                    intent.putExtra("oppId", entity.getOppId());
                    intent.putExtra("leadId", leadId);
                    startActivity(intent);
                }
            }
        });
        notRelateDialog.show();
    }

    private void showRelateDialog(final YellowCardEntity.YellowCardInfoEntity entity, String content, String button1, String button2) {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        relateDialog = new CustomDialog(getContext(), dialogAdapter);
        dialogAdapter.setDialogMessageText(content);
        dialogAdapter.setTextViewPadding(DensityUtil.dp2px(getContext(), 30), 0, DensityUtil.dp2px(getContext(), 30), 0);
        relateDialog.setBtnCancelText(button1);
        relateDialog.setBtnConfirmText(button2);
        relateDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                relateDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                relateDialog.dismiss();
                Map<String, Object> options = new HashMap<>();
                options.put("leadsId", leadId);
                options.put("oppId", entity.getOppId());
                leadRelateValidPresenter.leadRelateValid(getContext(), options);
            }
        });
        relateDialog.show();
    }

    private void showRelateSuccessDialog(String msg) {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        relateSuccessDialog = new CustomDialog(getContext(), dialogAdapter);
        dialogAdapter.setDialogMessageText(msg);
        dialogAdapter.setTextViewPadding(DensityUtil.dp2px(getContext(), 30), 0, DensityUtil.dp2px(getContext(), 30), 0);
        relateSuccessDialog.hideShowCancelBtn();
        relateSuccessDialog.setBtnConfirmText(getResources().getString(R.string.dialog_confirm));
        relateSuccessDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                relateSuccessDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                relateSuccessDialog.dismiss();
            }
        });
        relateSuccessDialog.show();
    }

    @Override
    public void leadRelateValidSuccess(String msg) {
        isRelated = true;
        if (!TextUtils.isEmpty(leadId)) {
            //通知客源列表页自动刷新
            Intent intent = new Intent("com.svw.dealerapp.task.leads.refresh");
            getActivity().sendBroadcast(intent);
        }
        showRelateSuccessDialog(msg);
    }

    @Override
    public void leadRelateValidFail(String returnCode, String msg) {
        if ("10002".equals(returnCode)) {
            //创建lead时间和跟进时间不满足条件2
            enterTo = 1;
            enterFollowUp();
        } else if ("10001".equals(returnCode)) {
            //电话号码不一样不满足条件1
            enterTo = 1;
            showNotRelateDialog(currentYCEntity, msg, getResources().getString(R.string.dialog_cancel), getResources().getString(R.string.dialog_confirm));
        } else {
            enterTo = 0;
        }
    }

    @Override
    public void leadRelateValidTimeout() {
        ToastUtils.showToast(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void showServerErrorLayout() {
        super.showServerErrorLayout();
        ((YellowCardSearchActivity) getActivity()).hideSearchTotalTextView();
    }

    private void enterFollowUp() {
        Intent i = new Intent(getActivity(), AddFollowupRecordActivity.class);
        i.putExtra("oppId", currentYCEntity.getOppId());
        i.putExtra("followupId", currentYCEntity.getFollowupId());
        i.putExtra("leadId", leadId);
        i.putExtra("oppLevelId", currentYCEntity.getOppLevel());
        i.putExtra("oppStatus", currentYCEntity.getOppStatusId());
        i.putExtra("seriesId", currentYCEntity.getSeriesId());
        startActivity(i);
    }

    /**
     * 通知搜索页客源和黄卡关联成功
     */
    private class RelateSuccessReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isRelated = true;
        }
    }

}
