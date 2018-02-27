package com.svw.dealerapp.ui.resource.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.TrafficEntity;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneActivity;
import com.svw.dealerapp.ui.newcustomer.activity.NewCustomerStepOneUnitedActivity;
import com.svw.dealerapp.ui.resource.activity.YellowCardSearchActivity;
import com.svw.dealerapp.ui.resource.adapter.CanNotCreateCardDialogAdapter;
import com.svw.dealerapp.ui.resource.adapter.TrafficRecyclerViewAdapter;
import com.svw.dealerapp.ui.resource.contract.TrafficContract;
import com.svw.dealerapp.ui.resource.entity.CannotCreateReasonItemEntity;
import com.svw.dealerapp.ui.resource.entity.TrafficFilterEntity;
import com.svw.dealerapp.ui.resource.model.TrafficModel;
import com.svw.dealerapp.ui.resource.presenter.TrafficFragmentPresenter;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinshi on 5/5/2017.
 */
@Deprecated
public class TrafficFragment extends BaseListFragment<TrafficEntity, TrafficEntity.TrafficInfoEntity>
        implements TrafficContract.View{

    private CustomDialog canNotCreateDialog;
    private TrafficRecyclerViewAdapter adapter;
    private CannotCreateReasonItemEntity selectReasonEntity;
    private CreateYellowCardSuccessReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TrafficFragmentPresenter(this, new TrafficModel());

        receiver = new CreateYellowCardSuccessReceiver();
        IntentFilter filter = new IntentFilter("com.svw.dealerapp.create.yellow.card.success");
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new TrafficRecyclerViewAdapter(getActivity(), presenter.getDataList());
        //左滑出来的按钮点击事件
        adapter.setOnBackViewClickListener(new TrafficRecyclerViewAdapter.OnBackViewClickListener() {
            @Override
            public void onBackViewOneClick(TrafficEntity.TrafficInfoEntity entity, int position) {
                // TODO: 5/15/2017 左侧第一个按钮点击
                Intent intent = new Intent(getActivity(), NewCustomerStepOneUnitedActivity.class);
                intent.putExtra("dealPosition", position);
                intent.putExtra("trafficInfoEntity", entity);
                intent.putExtra("fromFlag", Constants.CREATE_YELLOW_CAR_FROM_RESOURCE_TRAFFIC);
                getActivity().startActivity(intent);
            }

            @Override
            public void onBackViewTwoClick(TrafficEntity.TrafficInfoEntity entity, int position) {
                // TODO: 5/15/2017 左侧第二个按钮点击
                Intent intent = new Intent(getActivity(), YellowCardSearchActivity.class);
                startActivity(intent);
            }

            @Override
            public void onBackViewThreeClick(final TrafficEntity.TrafficInfoEntity infoEntity, final int position) {
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
                        if (presenter instanceof TrafficFragmentPresenter) {
                            TrafficFragmentPresenter fragmentPresenter = (TrafficFragmentPresenter) presenter;
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
    public void showChangeToInvalidSuccessToast() {
        ToastUtils.showToast(getResources().getString(R.string.can_not_create_post_success));
    }

    /**
     * 从筛选Activity返回后调用
     * @param filter
     */
    public void requestDateAfterTakeFilter(String filter){
        filterString = filter;
        showLoadingLayout();
        presenter.getDataFromServer(getActivity(), "1", pageSize, "1", filter, ListFragmentPresenter.REQUEST_BY_INIT);
    }


    private class CreateYellowCardSuccessReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(null != intent){
                int position = intent.getIntExtra("dealPosition", -1);
                int fromFlag = intent.getIntExtra("fromFlag", -1);
                String oppId = intent.getStringExtra("oppId");

                if(position >= 0 && fromFlag == Constants.CREATE_YELLOW_CAR_FROM_RESOURCE_TRAFFIC){
                    if(!TextUtils.isEmpty(oppId)){
                        presenter.getDataList().get(position).setOppId(oppId);
                    }

                    presenter.getDataList().get(position).setLeadsStatus("Created");
                    adapter.notifyDataSetChanged();
                    adapter.clearIntercept();
                }
            }
        }
    }

    @Override
    public String getFilter() {
        try {
            TrafficFilterEntity filterEntity = new TrafficFilterEntity();
            return URLEncoder.encode(GsonUtils.changeEntityToJsonStr(filterEntity), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.getFilter();
    }
}
