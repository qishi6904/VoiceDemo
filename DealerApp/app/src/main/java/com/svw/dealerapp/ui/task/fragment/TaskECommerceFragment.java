package com.svw.dealerapp.ui.task.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.task.TaskECommerceEntity;
import com.svw.dealerapp.entity.task.TaskECommerceEntity.TaskECommerceInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardTransferActivity;
import com.svw.dealerapp.ui.resource.adapter.YellowVipDialogAdapter;
import com.svw.dealerapp.ui.task.activity.TaskActivity;
import com.svw.dealerapp.ui.task.adapter.BenefitDialogAdapter;
import com.svw.dealerapp.ui.task.adapter.TaskECommerceAdapter;
import com.svw.dealerapp.ui.task.contract.ECommerceContract;
import com.svw.dealerapp.ui.task.entity.ECommerceFilterEntity;
import com.svw.dealerapp.ui.task.model.ECommerceModel;
import com.svw.dealerapp.ui.task.presenter.TaskECommercePresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by qinshi on 5/5/2017.
 */

public class TaskECommerceFragment extends BaseListFragment<TaskECommerceEntity, TaskECommerceInfoEntity>
        implements ECommerceContract.View {

    private static final int transferYellowCardResult = 1001;

    private CustomDialog transferVipDialog;
    private YellowVipDialogAdapter vipDialogAdapter;
    private BenefitDialogAdapter benefitDialogAdapter;
    private TaskECommerceAdapter adapter;
    private CustomDialog benefitDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TaskECommercePresenter(this, new ECommerceModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new TaskECommerceAdapter(getActivity(), presenter.getDataList());
        adapter.setOnIconClickListener(new TaskECommerceAdapter.OnIconClickListener() {
            @Override
            public void onPhoneClick(android.view.View view, TaskECommerceInfoEntity entity) {
                TalkingDataUtils.onEvent(getActivity(), "打电话", "电商任务");
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
            public void onVipClick(android.view.View view, final TaskECommerceInfoEntity entity, final int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划标记重点客户", "电商任务");
                if (null == transferVipDialog) {
                    vipDialogAdapter = new YellowVipDialogAdapter();
                    transferVipDialog = new CustomDialog(getActivity(), vipDialogAdapter);
                    transferVipDialog.hideTitleIcon();
                    transferVipDialog.setDialogTitle(getActivity().getResources().getString(R.string.yellow_vip_dialog_title));
                }
                if("0".equals(entity.getIsKeyuser())){
                    vipDialogAdapter.setContentText(getResources().getString(R.string.yellow_vip_dialog_cancel_tip));
                }else {
                    vipDialogAdapter.setContentText(getResources().getString(R.string.yellow_vip_dialog_tip));
                }
                transferVipDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                    @Override
                    public void onCancelBtnClick() {
                        transferVipDialog.dismiss();
                    }

                    @Override
                    public void onConfirmBtnClick() {
                        if(presenter instanceof TaskECommercePresenter){
                            TaskECommercePresenter fragmentPresenter = (TaskECommercePresenter) presenter;
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
            public void onFollowUpClick(android.view.View view, TaskECommerceInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划跟进", "电商任务");
                Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                intent.putExtra("oppId", entity.getOppId());
                intent.putExtra("leadsId", entity.getLeadsId());
                startActivity(intent);
            }

            @Override
            public void onTransferClick(android.view.View view, TaskECommerceInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划潜客转移", "电商任务");
                Intent intent = new Intent(getActivity(), YellowCardTransferActivity.class);
                intent.putExtra("orgId", entity.getOrgId());
                intent.putExtra("oppId", entity.getOppId());
                intent.putExtra("customerName", entity.getCustName());
                intent.putExtra("dealPosition", position);
                startActivityForResult(intent, transferYellowCardResult);
                getActivity().overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
            }

            @Override
            public void onBenefitClick(View view, TaskECommerceInfoEntity entity, int position) {
                if(presenter instanceof TaskECommercePresenter){
                    TaskECommercePresenter fragmentPresenter = (TaskECommercePresenter) presenter;
                    Map<String, Object> options = new HashMap();
                    options.put("leadsId", entity.getLeadsId());
                    fragmentPresenter.getBenefitDate(getActivity(), options);
                }
            }
        });

        return adapter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != transferVipDialog){
            transferVipDialog.dismiss();
            transferVipDialog = null;
        }
        if(null != benefitDialog){
            benefitDialog.dismiss();
            benefitDialog = null;
        }
    }

    @Override
    public void showSetVipSuccessToast(String isKeyuser) {
        if("0".equals(isKeyuser)){
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_success));
        }else {
            ToastUtils.showToast(getResources().getString(R.string.yellow_set_vip_cancel_success));
        }

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
        ((TaskActivity)getActivity()).setTabTipNumber(1, number);
    }

    @Override
    public void getBenefitDataSuccess(String benefitStr) {
        if(null == benefitDialog){
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
        ECommerceFilterEntity filterEntity = new ECommerceFilterEntity();
        filterEntity.setUserId(UserInfoUtils.getUserId());
        filterEntity.setTaskTypeId("16020");
        filterEntity.setSrcTypeId("11020");
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
        TalkingDataUtils.onPageStart(getActivity(), "任务-电商");
        JLog.i("talkingDataFlag-show", "任务-电商");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "任务-电商");
        JLog.i("talkingDataFlag-hide", "任务-电商");
    }
}
