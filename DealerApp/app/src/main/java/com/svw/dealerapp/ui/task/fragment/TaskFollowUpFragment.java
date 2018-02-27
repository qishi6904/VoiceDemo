package com.svw.dealerapp.ui.task.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.entity.task.TaskFollowUpEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardTransferActivity;
import com.svw.dealerapp.ui.resource.adapter.YellowVipDialogAdapter;
import com.svw.dealerapp.ui.task.activity.TaskActivity;
import com.svw.dealerapp.ui.task.adapter.TaskFollowUpAdapter;
import com.svw.dealerapp.ui.task.contract.TaskFollowUpContract;
import com.svw.dealerapp.ui.task.entity.ECommerceFilterEntity;
import com.svw.dealerapp.ui.task.entity.FollowUpFilterEntity;
import com.svw.dealerapp.ui.task.model.TaskFollowUpModel;
import com.svw.dealerapp.ui.task.presenter.TaskFollowUpPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.net.URLEncoder;


/**
 * Created by qinshi on 5/5/2017.
 */

public class TaskFollowUpFragment extends BaseListFragment<TaskFollowUpEntity, TaskFollowUpEntity.TaskFollowInfoEntity>
        implements TaskFollowUpContract.View {

    private static final int transferYellowCardResult = 1001;

    private CustomDialog transferVipDialog;
    private YellowVipDialogAdapter dialogAdapter;
    private TaskFollowUpAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TaskFollowUpPresenter(this, new TaskFollowUpModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new TaskFollowUpAdapter(getActivity(), presenter.getDataList());
        adapter.setOnIconClickListener(new TaskFollowUpAdapter.OnIconClickListener() {
            @Override
            public void onPhoneClick(android.view.View view, TaskFollowUpEntity.TaskFollowInfoEntity entity) {
                TalkingDataUtils.onEvent(getActivity(), "打电话", "跟进任务");
                String phoneNumber = entity.getCustMobile();
                if(TextUtils.isEmpty(phoneNumber)){
                    phoneNumber = entity.getCustTelephone();
                }
                if(!TextUtils.isEmpty(phoneNumber)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneNumber);
                    intent.setData(data);
                    startActivity(intent);
                }else {
                    ToastUtils.showToast(getResources().getString(R.string.yellow_phone_number_empty));
                }
            }

            @Override
            public void onVipClick(android.view.View view, final TaskFollowUpEntity.TaskFollowInfoEntity entity, final int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划标记重点客户", "跟进任务");
                if (null == transferVipDialog) {
                    dialogAdapter = new YellowVipDialogAdapter();
                    transferVipDialog = new CustomDialog(getActivity(), dialogAdapter);
                    transferVipDialog.hideTitleIcon();
                    transferVipDialog.setDialogTitle(getActivity().getResources().getString(R.string.yellow_vip_dialog_title));
                }
                if("0".equals(entity.getIsKeyuser())){
                    dialogAdapter.setContentText(getResources().getString(R.string.yellow_vip_dialog_cancel_tip));
                }else {
                    dialogAdapter.setContentText(getResources().getString(R.string.yellow_vip_dialog_tip));
                }
                transferVipDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                    @Override
                    public void onCancelBtnClick() {
                        transferVipDialog.dismiss();
                    }

                    @Override
                    public void onConfirmBtnClick() {
                        if(presenter instanceof TaskFollowUpPresenter){
                            TaskFollowUpPresenter fragmentPresenter = (TaskFollowUpPresenter) presenter;
                            if("0".equals(entity.getIsKeyuser())){
                                fragmentPresenter.postVipCustomer(getActivity(), entity.getOppId(), "1", position);
                            }else {
                                fragmentPresenter.postVipCustomer(getActivity(), entity.getOppId(), "0", position);
                            }
                        }
                        transferVipDialog.dismiss();
                    }
                });
                transferVipDialog.show();
            }

            @Override
            public void onFollowUpClick(android.view.View view, TaskFollowUpEntity.TaskFollowInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划跟进", "跟进任务");
                Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                intent.putExtra("oppId", entity.getOppId());
                intent.putExtra("leadsId", entity.getLeadsId());
                startActivity(intent);
            }

            @Override
            public void onTransferClick(android.view.View view, TaskFollowUpEntity.TaskFollowInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划潜客转移", "跟进任务");
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
     * 从筛选Activity返回调用
     * @param filterEntity
     */
    public void requestDateAfterTakeFilter(FollowUpFilterEntity filterEntity){
        filterEntity.setUserId(UserInfoUtils.getUserId());
        filterEntity.setSrcTypeIdNot("11020");
        filterEntity.setTaskTypeIdNot("16020");
        int fromFlag = ((TaskActivity) getActivity()).getFromFlag();
        if(fromFlag == TaskActivity.FROM_HOME) {
            filterEntity.setIsHomePage("0");
        }else {
            filterEntity.setIsHomePage("1");
        }
        try {
            filterString = URLEncoder.encode(GsonUtils.changeEntityToJsonStr(filterEntity), "utf-8");
        }catch (Exception e){
            e.printStackTrace();
            filterString = "";

        }
        showLoadingLayout();
        presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_INIT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != transferVipDialog){
            transferVipDialog.dismiss();
            transferVipDialog = null;
        }
    }

    @Override
    public void showSetVipSuccessToast(String isKeyuser) {
        if("0".equals(isKeyuser)){
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_success));
        }else {
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_cancel_success));
        }
        presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filterString, ListFragmentPresenter.REQUEST_BY_INIT, getMoreParams());
        getActivity().sendBroadcast(new Intent("com.svw.dealerapp.yellow.card.fresh"));
    }

    @Override
    public void showSetVipFailedToast(String isKeyuser) {
        if("0".equals(isKeyuser)){
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_fail));
        }else {
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
        ((TaskActivity)getActivity()).setTabTipNumber(2, number);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case transferYellowCardResult:
                if(null != data) {
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
    public String getFilter() {
        FollowUpFilterEntity filterEntity = new FollowUpFilterEntity();
        filterEntity.setUserId(UserInfoUtils.getUserId());
        filterEntity.setSrcTypeIdNot("11020");
        filterEntity.setTaskTypeIdNot("16020");
        int fromFlag = ((TaskActivity) getActivity()).getFromFlag();
        if(fromFlag == TaskActivity.FROM_HOME) {
            filterEntity.setIsHomePage("0");
        }else {
            filterEntity.setIsHomePage("1");
        }
        try {
            return URLEncoder.encode(GsonUtils.changeEntityToJsonStr(filterEntity), "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "任务-跟进");
        JLog.i("talkingDataFlag-show", "任务-跟进");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "任务-跟进");
        JLog.i("talkingDataFlag-hide", "任务-跟进");
    }
}
