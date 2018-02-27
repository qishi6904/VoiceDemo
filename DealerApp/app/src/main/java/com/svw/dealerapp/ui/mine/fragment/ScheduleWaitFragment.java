package com.svw.dealerapp.ui.mine.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity.ScheduleWaitInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.mine.activity.ScheduleUpdateActivity;
import com.svw.dealerapp.ui.mine.adapter.ScheduleWaitAdapter;
import com.svw.dealerapp.ui.mine.contract.ScheduleWaitContract;
import com.svw.dealerapp.ui.mine.model.ScheduleWaitModel;
import com.svw.dealerapp.ui.mine.presenter.ScheduleWaitPresenter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinshi on 6/1/2017.
 */

public class ScheduleWaitFragment extends BaseListFragment<ScheduleWaitEntity, ScheduleWaitInfoEntity>
        implements ScheduleWaitContract.View {

    private static final int updateScheduleRequestCode = 1001;

    private ScheduleWaitAdapter adapter;
    private CustomDialog cancelDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScheduleWaitPresenter(this, new ScheduleWaitModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new ScheduleWaitAdapter(getActivity(), presenter.getDataList());

        adapter.setOnBackViewClickListener(new ScheduleWaitAdapter.OnBackViewClickListener() {
            @Override
            public void onUpdateClick(ScheduleWaitInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "更改预约", "我的-预约列表");
                Intent intent = new Intent(getActivity(), ScheduleUpdateActivity.class);
                intent.putExtra("scheduleId", entity.getAppmId());
                intent.putExtra("dateTimeStr", entity.getAppmDateStr());
                intent.putExtra("appTypeId", entity.getAppmTypeId());
                intent.putExtra("position", position);
                startActivityForResult(intent, updateScheduleRequestCode);
            }

            @Override
            public void onCancelClick(ScheduleWaitInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "取消预约", "我的-预约列表");
                showCancelDialog(entity, position);
            }

            @Override
            public void onCheckYCClick(ScheduleWaitInfoEntity entity, int position) {
                TalkingDataUtils.onEvent(getActivity(), "查看黄卡", "我的-预约列表");
                Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                intent.putExtra("oppId", entity.getOppId());
                startActivity(intent);
            }
        });

        return adapter;
    }

    private void showCancelDialog(final ScheduleWaitInfoEntity entity, final int position){
        if(null == cancelDialog) {
            StringCustomDialogAdapter dialogAdapter = new StringCustomDialogAdapter();
            cancelDialog = new CustomDialog(getActivity(), dialogAdapter);
            dialogAdapter.setContent(getResources().getString(R.string.mine_schedule_cancel_tip));
        }
        cancelDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                cancelDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                ScheduleWaitPresenter waitPresenter = (ScheduleWaitPresenter) presenter;
                Map<String, Object> options = new HashMap<>();
                options.put("appmId", entity.getAppmId());
                waitPresenter.cancelSchedule(getActivity(), options, position);
                cancelDialog.dismiss();
            }
        });
        cancelDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != cancelDialog){
            cancelDialog.dismiss();
        }
    }

    @Override
    public void cancelScheduleSuccess(int position) {
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_cancel_success));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void cancelScheduleFailed() {
        ToastUtils.showToast(getResources().getString(R.string.mine_schedule_cancel_fail));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case updateScheduleRequestCode:
                if(null != data){
                    int dealPosition = data.getIntExtra("dealPosition", -1);
                    if(dealPosition >= 0) {
                        String appmDateStr = data.getStringExtra("appmDateStr");
                        String appmTypeId = data.getStringExtra("appmTypeId");
                        ScheduleWaitPresenter waitPresenter = (ScheduleWaitPresenter) presenter;
                        waitPresenter.updateSchedule(dealPosition, appmDateStr, appmTypeId);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    @Override
    public String getFilter() {
        try {
            return URLEncoder.encode("{\"appmStatusId\": \"0\"}", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.getFilter();
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "我的-预约-待完成");
        JLog.i("talkingDataFlag-show", "我的-预约-待完成");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "我的-预约-待完成");
        JLog.i("talkingDataFlag-hide", "我的-预约-待完成");
    }
}
