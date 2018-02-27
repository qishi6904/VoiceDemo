package com.svw.dealerapp.ui.resource.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.AddFollowupRecordActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardSearchActivity;
import com.svw.dealerapp.ui.resource.adapter.RdYellowCardRecyclerViewAdapter;
import com.svw.dealerapp.ui.resource.contract.LeadRelateValidContract;
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
 * Created by lijinkui on 2018/1/15.
 */

public class RdYellowCardSearchFragment extends BaseListFragment<YellowCardEntity, YellowCardEntity.YellowCardInfoEntity>
        implements YellowCardSearchContract.View, LeadRelateValidContract.View {


    private RdYellowCardRecyclerViewAdapter adapter;
    private YellowCardEntity.YellowCardInfoEntity currentYCEntity;
    //线索绑定使用start
    private String leadId;
    private int enterTo = 0;//0:YC detail 1:YC followup
    private boolean isRelated = false;
    private CustomDialog notRelateDialog, relateDialog, relateSuccessDialog;
    private RelateSuccessReceiver relateSuccessReceiver;
    private LeadRelateValidPresenter leadRelateValidPresenter;
    //线索绑定使用end

    public static RdYellowCardSearchFragment newInstance(HashMap<String, String> map) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", map);
        RdYellowCardSearchFragment fragment = new RdYellowCardSearchFragment();
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
            adapter = new RdYellowCardRecyclerViewAdapter(getActivity(), presenter.getDataList(), presenter.getShowMonthEntities(), true);
        } else {
            adapter = new RdYellowCardRecyclerViewAdapter(getActivity(), presenter.getDataList(), presenter.getShowMonthEntities(), false);
        }

        adapter.setOnItemClickListener(new RdYellowCardRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(YellowCardEntity.YellowCardInfoEntity entity, int position) {
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
        });

        //显示月份的横条
        setShowHorizontalTag(true);

        return adapter;
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
    public void setSearchTotal(String total) {
        YellowCardSearchActivity activity = (YellowCardSearchActivity) getActivity();
        if (presenter.getDataList().size() == 0) {
            activity.hideSearchTotalTextView();
        } else {
            activity.setSearchTotal(total);
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
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
