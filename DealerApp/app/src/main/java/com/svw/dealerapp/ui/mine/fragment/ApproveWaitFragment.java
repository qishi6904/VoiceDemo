package com.svw.dealerapp.ui.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.mine.ApproveWaitEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.mine.activity.RejectApproveActivity;
import com.svw.dealerapp.ui.mine.adapter.ApproveWaitAdapter;
import com.svw.dealerapp.ui.mine.contract.ApproveWaitContract;
import com.svw.dealerapp.ui.mine.model.ApproveWaitModel;
import com.svw.dealerapp.ui.mine.presenter.ApproveWaitPresenter;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinshi on 6/1/2017.
 */

public class ApproveWaitFragment extends BaseListFragment<ApproveWaitEntity, ApproveWaitEntity.ApproveWaitInfoEntity>
        implements ApproveWaitContract.View {

    private static final int rejectApproveRequest = 1001;

    private ApproveWaitAdapter adapter;
    private CustomDialog supportDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ApproveWaitPresenter(this, new ApproveWaitModel());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new ApproveWaitAdapter(getActivity(), presenter.getDataList());
        adapter.setOnIconClickListener(new ApproveWaitAdapter.OnIconClickListener() {
            @Override
            public void onSupportApprove(View view, final ApproveWaitEntity.ApproveWaitInfoEntity entity, final int position) {
//                if(null == supportDialog){
//                    StringCustomDialogAdapter dialogAdapter = new StringCustomDialogAdapter();
//                    supportDialog = new CustomDialog(getActivity(), dialogAdapter);
//                    dialogAdapter.setContent(getResources().getString(R.string.mine_approve_support));
//                }
//
//                supportDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
//                    @Override
//                    public void onCancelBtnClick() {
//                        supportDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onConfirmBtnClick() {
//                        supportDialog.dismiss();
//                        if(presenter instanceof ApproveWaitContract.Presenter) {
//                            Map<String, Object> options = new HashMap<>();
//                            options.put("approvalId",entity.getApprovalId());
//                            options.put("approvalStatusId","18520");
//                            ApproveWaitContract.Presenter approvePresenter = (ApproveWaitContract.Presenter) presenter;
//                            approvePresenter.postApprove(getActivity(), options, position);
//                        }
//                        TalkingDataUtils.onEvent(getActivity(),"批准审批","我的-待审批列表");
//                    }
//                });
//
//                supportDialog.show();

                Intent intent = new Intent(getActivity(), RejectApproveActivity.class);
                intent.putExtra("approveId", entity.getApprovalId());
                intent.putExtra("dealPosition", position);
                intent.putExtra("isRejectApprove", false);
                startActivityForResult(intent, rejectApproveRequest);
                TalkingDataUtils.onEvent(getActivity(),"同意审批","我的-待审批列表");
            }

            @Override
            public void onRejectApprove(View view, ApproveWaitEntity.ApproveWaitInfoEntity entity, int position) {
                Intent intent = new Intent(getActivity(), RejectApproveActivity.class);
                intent.putExtra("approveId", entity.getApprovalId());
                intent.putExtra("dealPosition", position);
                intent.putExtra("isRejectApprove", true);
                startActivityForResult(intent, rejectApproveRequest);
                TalkingDataUtils.onEvent(getActivity(),"拒绝审批","我的-待审批列表");
            }

            @Override
            public void onItemClick(View view, ApproveWaitEntity.ApproveWaitInfoEntity entity, int position) {
                Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
                intent.putExtra("oppId", entity.getOppId());
                startActivity(intent);
            }
        });
        return adapter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(null != data){
            final int dealPosition = data.getIntExtra("dealPosition", -1);
            if(dealPosition >=0 ){
                presenter.getDataList().remove(dealPosition);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != supportDialog) {
            supportDialog.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void approveSuccess(int position) {
        ToastUtils.showToast(getResources().getString(R.string.mine_approve_support_success));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void approveFail() {
        ToastUtils.showToast(getResources().getString(R.string.mine_approve_support_fail));
    }

    @Override
    public String getFilter() {
        return "0";
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "我的-审批-待审批");
        JLog.i("talkingDataFlag-show", "我的-审批-待审批");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "我的-审批-待审批");
        JLog.i("talkingDataFlag-hide", "我的-审批-待审批");
    }
}
