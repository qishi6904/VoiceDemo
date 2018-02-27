package com.svw.dealerapp.ui.task.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.entity.task.TaskLeadsEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneUnitedActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerUnitedActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardSearchActivity;
import com.svw.dealerapp.ui.resource.adapter.CanNotCreateCardDialogAdapter;
import com.svw.dealerapp.ui.resource.entity.CannotCreateReasonItemEntity;
import com.svw.dealerapp.ui.task.activity.TaskActivity;
import com.svw.dealerapp.ui.task.adapter.TaskLeadsAdapter;
import com.svw.dealerapp.ui.task.adapter.TaskTrafficAdapter;
import com.svw.dealerapp.ui.task.contract.TaskLeadsContract;
import com.svw.dealerapp.ui.task.contract.TaskTrafficContract;
import com.svw.dealerapp.ui.task.entity.TrafficFilterEntity;
import com.svw.dealerapp.ui.task.model.TaskLeadsModel;
import com.svw.dealerapp.ui.task.model.TaskTrafficModel;
import com.svw.dealerapp.ui.task.presenter.TaskLeadsPresenter;
import com.svw.dealerapp.ui.task.presenter.TaskTrafficPresenter;
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

public class TaskLeadsFragment extends BaseListFragment<TaskLeadsEntity, TaskLeadsEntity.TaskLeadsInfoEntity>
        implements TaskLeadsContract.View {

    public static final String REFRESH_FILTER_STRING = "com.svw.dealerapp.task.leads.refresh";

    private CustomDialog canNotCreateDialog;
    private TaskLeadsAdapter adapter;
    private CannotCreateReasonItemEntity selectReasonEntity;
    private CreateYellowCardSuccessReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TaskLeadsPresenter(this, new TaskLeadsModel());

        receiver = new CreateYellowCardSuccessReceiver();
        IntentFilter filter = new IntentFilter("com.svw.dealerapp.create.yellow.card.success");
        getActivity().registerReceiver(receiver, filter);

        registerFreshReceiver(REFRESH_FILTER_STRING);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new TaskLeadsAdapter(getActivity(), presenter.getDataList());

//      左滑出来的按钮点击事件
        adapter.setOnBackViewClickListener(new TaskLeadsAdapter.OnBackViewClickListener() {
            @Override
            public void onPhoneClick(View view, TaskLeadsEntity.TaskLeadsInfoEntity entity) {
                if (!"11010".equals(entity.getSrcTypeId())) {    //如果不是到店
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
            }

            @Override
            public void onBackViewOneClick(View view, TaskLeadsEntity.TaskLeadsInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划建卡", "客源任务");
                Intent intent = new Intent(getActivity(), NewCustomerUnitedActivity.class);
                intent.putExtra("dealPosition", position);
                intent.putExtra("trafficInfoEntity", entity);
                intent.putExtra("fromFlag", Constants.CREATE_YELLOW_CAR_FROM_TASK_TRAFFIC);
                getActivity().startActivity(intent);
            }

            @Override
            public void onBackViewTwoClick(View view, TaskLeadsEntity.TaskLeadsInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划跟进", "客源任务");
                Intent intent = new Intent(getActivity(), YellowCardSearchActivity.class);
                intent.putExtra("leadId", entity.getLeadsId());
                intent.putExtra("leadMobile", entity.getCustMobile());
                startActivity(intent);

            }

            @Override
            public void onBackViewThreeClick(View view, final TaskLeadsEntity.TaskLeadsInfoEntity entity, final int position) {
                TalkingDataUtils.onEvent(getActivity(), "左划无效", "客源任务");
                if (null == canNotCreateDialog) {
                    CanNotCreateCardDialogAdapter dialogAdapter = new CanNotCreateCardDialogAdapter(getActivity());
                    canNotCreateDialog = new CustomDialog(getActivity(), dialogAdapter);
                    canNotCreateDialog.setDialogTitle(getActivity().getResources().getString(R.string.can_not_create_reason));
                    canNotCreateDialog.setBtnConfirmText(getActivity().getResources().getString(R.string.can_not_create_dialog_post));
                    dialogAdapter.setOnSelectListener(new CanNotCreateCardDialogAdapter.OnSelectListener() {
                        @Override
                        public void onSelect(CannotCreateReasonItemEntity entity) {
                            selectReasonEntity = entity;
                        }
                    });
                }
                canNotCreateDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                    @Override
                    public void onCancelBtnClick() {
                        canNotCreateDialog.dismiss();
                    }

                    @Override
                    public void onConfirmBtnClick() {
                        if (null == selectReasonEntity) {
                            ToastUtils.showToast(getActivity().getResources().getString(R.string.can_not_create_no_select_tip));
                            return;
                        }
                        if (presenter instanceof TaskLeadsPresenter) {
                            TaskLeadsPresenter fragmentPresenter = (TaskLeadsPresenter) presenter;
                            fragmentPresenter.postInvalidTrafficStatus(getActivity(), entity.getLeadsId(), "InValid", selectReasonEntity.getDetail(), position);
                        }
                        canNotCreateDialog.dismiss();
                    }
                });
                canNotCreateDialog.show();
            }
        });

        return adapter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != canNotCreateDialog) {
            canNotCreateDialog.dismiss();
            canNotCreateDialog = null;
        }
        if (null != receiver) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    @Override
    public void changeToInvalidStatus(int position) {
        adapter.clearIntercept();
        adapter.notifyDataSetChanged();

        ((TaskActivity)getActivity()).changeTipNumber(0, -1);
        if (presenter.getDataList().size() == 0) {
            showNoDataLayout();
        }
    }

    @Override
    public void showChangeToInvalidFailToast() {
        ToastUtils.showToast(getResources().getString(R.string.can_not_create_post_fail));
    }

    @Override
    public void setTabTipNumber(String number) {
        ((TaskActivity) getActivity()).setTabTipNumber(0, number);
    }

    @Override
    public void showChangeToInvalidSuccessToast() {
        ToastUtils.showToast(getResources().getString(R.string.can_not_create_post_success));
    }

    @Override
    public String getFilter() {
        TrafficFilterEntity filterEntity = new TrafficFilterEntity();
        filterEntity.setUserId(UserInfoUtils.getUserId());
        int fromFlag = ((TaskActivity) getActivity()).getFromFlag();
        if(fromFlag == TaskActivity.FROM_HOME) {
            filterEntity.setIsHomePage("0");
        }else {
            filterEntity.setIsHomePage("1");
        }
        try {
            return URLEncoder.encode(GsonUtils.changeEntityToJsonStr(filterEntity), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class CreateYellowCardSuccessReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                int position = intent.getIntExtra("dealPosition", -1);
                int fromFlag = intent.getIntExtra("fromFlag", -1);
                String oppId = intent.getStringExtra("oppId");

                if (position >= 0 && fromFlag == Constants.CREATE_YELLOW_CAR_FROM_TASK_TRAFFIC) {
                    if (!TextUtils.isEmpty(oppId)) {
                        presenter.getDataList().get(position).setOppId(oppId);
                    }

                    presenter.getDataList().get(position).setLeadsStatusId("10520");
//                    adapter.notifyDataSetChanged();
                    adapter.clearIntercept();
//                    adapter.notifyItemChanged(position);
                    presenter.getDataList().remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "任务-客源");
        JLog.i("talkingDataFlag-show", "任务-客源");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "任务-客源");
        JLog.i("talkingDataFlag-hide", "任务-客源");
    }
}
