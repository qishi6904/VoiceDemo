package com.svw.dealerapp.ui.mine.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.mine.contract.ApproveRejectContract;
import com.svw.dealerapp.ui.mine.fragment.ApproveCompleteFragment;
import com.svw.dealerapp.ui.mine.model.ApproveRejectModel;
import com.svw.dealerapp.ui.mine.presenter.ApproveRejectPresenter;
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.PermissionUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinshi on 6/5/2017.
 */

public class RejectApproveActivity extends BaseActivity implements ApproveRejectContract.View{

    private ImageView ivBack;
    private Button btnSubmit;
    private LoadingDialog loadingDialog;
    private TextView tvTitle;
    private TextView tvTabTitle;

    private ApproveRejectPresenter presenter;

    private String approveId;
    private int dealPosition;
    private boolean isRejectApprove = true;

    private EditTextWithMicLayout editTextWithMicLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_approve);

        Intent intent = getIntent();
        if(null != intent) {
            isRejectApprove = intent.getBooleanExtra("isRejectApprove", true);
        }

        assignViews();

        getIntentData();

        presenter = new ApproveRejectPresenter(this, new ApproveRejectModel());

        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this,"我的-拒绝审批");
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if(null != intent){
            approveId = intent.getStringExtra("approveId");
            dealPosition = intent.getIntExtra("dealPosition", -1);
        }
    }

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTabTitle = (TextView) findViewById(R.id.tv_tab_title);
        editTextWithMicLayout = (EditTextWithMicLayout) findViewById(R.id.et_with_mic);
        editTextWithMicLayout.initEditWithMic(this, null);
        editTextWithMicLayout.setMaxTextNum(50);
        editTextWithMicLayout.setEnabled(true);
        if(isRejectApprove) {
            editTextWithMicLayout.setHint(getResources().getString(R.string.mine_approve_reject_reason_hint));
        }else {
            tvTitle.setText(getResources().getString(R.string.mine_approve_support_title));
            editTextWithMicLayout.setHint(getResources().getString(R.string.mine_approve_support_reason_hint));
            tvTabTitle.setText(getResources().getString(R.string.mine_approve_support_reason));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                if(!TextUtils.isEmpty(editTextWithMicLayout.getTextContent()) || !isRejectApprove) {
                    Map<String, Object> options = new HashMap<>();
                    options.put("approvalId", approveId);
                    options.put("approvalDesc", editTextWithMicLayout.getTextContent());
                    if(!isRejectApprove) {
                        options.put("approvalStatusId", "18520");
                        TalkingDataUtils.onEvent(this, "批准审批", "我的-同意审批");
                    }else {
                        options.put("approvalStatusId", "18530");
                        TalkingDataUtils.onEvent(this, "提交拒绝原因", "我的-拒绝审批");
                    }
                    presenter.rejectApprove(this, options);
                }else {
                    ToastUtils.showToast(getResources().getString(R.string.mine_approve_reject_reason_empty));
                }
                break;
        }
    }

    @Override
    public void rejectApproveSuccess() {
        Intent data = new Intent();
        data.putExtra("dealPosition", dealPosition);
        setResult(0, data);
        sendBroadcast(new Intent(ApproveCompleteFragment.FRESH_FILTER_STRING));
        ToastUtils.showToast(getResources().getString(R.string.mine_approve_reject_success));
        finish();
    }

    @Override
    public void showServerErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.mine_approve_reject_fail));
    }

    @Override
    public void showTimeOutToast() {
        ToastUtils.showToast(getResources().getString(R.string.network_error));

    }

    @Override
    public void showNetWorkErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void showLoadingDialog() {
        if(null == loadingDialog){
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this,"我的-拒绝审批");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
        if(null != presenter){
            presenter.onDestroy();
        }
        editTextWithMicLayout.stopNLS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION:
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    editTextWithMicLayout.clickRecordButton();
                }
                break;
        }
    }
}
