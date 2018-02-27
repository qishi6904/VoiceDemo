package com.svw.dealerapp.ui.task.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneUnitedActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardSearchActivity;
import com.svw.dealerapp.ui.resource.adapter.CanNotCreateCardDialogAdapter;
import com.svw.dealerapp.ui.resource.entity.CannotCreateReasonItemEntity;
import com.svw.dealerapp.ui.resource.fragment.TrafficFragment;
import com.svw.dealerapp.ui.resource.presenter.TrafficFragmentPresenter;
import com.svw.dealerapp.ui.task.activity.TaskActivity;
import com.svw.dealerapp.ui.task.adapter.TaskTrafficAdapter;
import com.svw.dealerapp.ui.task.contract.TaskTrafficContract;
import com.svw.dealerapp.ui.task.entity.TrafficFilterEntity;
import com.svw.dealerapp.ui.task.model.TaskTrafficModel;
import com.svw.dealerapp.ui.task.presenter.TaskTrafficPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.net.URLEncoder;

/**
 * Created by qinshi on 5/5/2017.
 */

@Deprecated
public class TaskTrafficFragment extends BaseListFragment<TaskTrafficEntity, TaskTrafficEntity.TaskTrafficInfoEntity>
        implements TaskTrafficContract.View{

    private CustomDialog canNotCreateDialog;
    private TaskTrafficAdapter adapter;
    private CannotCreateReasonItemEntity selectReasonEntity;
    private CreateYellowCardSuccessReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TaskTrafficPresenter(this, new TaskTrafficModel());

        receiver = new CreateYellowCardSuccessReceiver();
        IntentFilter filter = new IntentFilter("com.svw.dealerapp.create.yellow.card.success");
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new TaskTrafficAdapter(getActivity(), presenter.getDataList());
        //左滑出来的按钮点击事件
        adapter.setOnBackViewClickListener(new TaskTrafficAdapter.OnBackViewClickListener() {
            @Override
            public void onBackViewOneClick(TaskTrafficEntity.TaskTrafficInfoEntity entity, int position) {
                Intent intent = new Intent(getActivity(), NewCustomerStepOneUnitedActivity.class);
                intent.putExtra("dealPosition", position);
                intent.putExtra("trafficInfoEntity", entity);
                intent.putExtra("fromFlag", Constants.CREATE_YELLOW_CAR_FROM_TASK_TRAFFIC);
                getActivity().startActivity(intent);
            }

            @Override
            public void onBackViewTwoClick(TaskTrafficEntity.TaskTrafficInfoEntity entity, int position) {
                // TODO: 5/15/2017 左侧第二个按钮点击
                Intent intent = new Intent(getActivity(), YellowCardSearchActivity.class);
                startActivity(intent);
            }

            @Override
            public void onBackViewThreeClick(final TaskTrafficEntity.TaskTrafficInfoEntity infoEntity, final int position) {
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
                        if (presenter instanceof TaskTrafficPresenter) {
                            TaskTrafficPresenter fragmentPresenter = (TaskTrafficPresenter) presenter;
                            fragmentPresenter.postInvalidTrafficStatus(getActivity(), infoEntity.getLeadsId(), "InValid", selectReasonEntity.getDetail(), position);
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
        if(null != canNotCreateDialog){
            canNotCreateDialog.dismiss();
            canNotCreateDialog = null;
        }
        if(null != receiver){
            getActivity().unregisterReceiver(receiver);
        }
    }

    @Override
    public void changeToInvalidStatus(int position) {
        adapter.clearIntercept();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showChangeToInvalidFailToast() {
        ToastUtils.showToast(getResources().getString(R.string.can_not_create_post_fail));
    }

    @Override
    public void setTabTipNumber(String number) {
        ((TaskActivity)getActivity()).setTabTipNumber(0, number);
    }

    @Override
    public void showChangeToInvalidSuccessToast() {
        ToastUtils.showToast(getResources().getString(R.string.can_not_create_post_success));
    }

    @Override
    public String getFilter() {
        TrafficFilterEntity filterEntity = new TrafficFilterEntity();
        // TODO: 6/6/2017
        filterEntity.setUserId(UserInfoUtils.getUserId());
        try {
            return URLEncoder.encode(GsonUtils.changeEntityToJsonStr(filterEntity), "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class CreateYellowCardSuccessReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(null != intent){
                int position = intent.getIntExtra("dealPosition", -1);
                int fromFlag = intent.getIntExtra("fromFlag", -1);
                String oppId = intent.getStringExtra("oppId");

                if(position >= 0 && fromFlag == Constants.CREATE_YELLOW_CAR_FROM_TASK_TRAFFIC){
                    if(!TextUtils.isEmpty(oppId)){
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
}
