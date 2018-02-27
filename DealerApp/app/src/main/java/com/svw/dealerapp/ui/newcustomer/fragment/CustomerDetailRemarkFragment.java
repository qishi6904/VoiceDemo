package com.svw.dealerapp.ui.newcustomer.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailItemActivity;
import com.svw.dealerapp.ui.widget.EditTextWithMicLayout;
import com.svw.dealerapp.util.PermissionUtils;
import com.svw.dealerapp.util.UserInfoUtils;

/**
 * 黄卡详情 - 备注
 * Created by xupan on 12/06/2017.
 */

public class CustomerDetailRemarkFragment extends BaseCustomerFragment implements View.OnClickListener {

    private Button btnSubmit;
    private CustomerDetailRemarkListFragment remarkListFragment;
    private TextView tvOperator;
    private EditTextWithMicLayout editTextWithMicLayout;

    public static CustomerDetailRemarkFragment newInstance(OpportunityDetailEntity entity) {
        CustomerDetailRemarkFragment fragment = new CustomerDetailRemarkFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_customer_detail_remark;
    }

    @Override
    protected void initViews(View view) {
        btnSubmit = (Button) view.findViewById(R.id.btn_remark_submit);
        tvOperator = (TextView) view.findViewById(R.id.tv_remark_operator);

        remarkListFragment = new CustomerDetailRemarkListFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_remark_list, remarkListFragment).commit();

        editTextWithMicLayout = (EditTextWithMicLayout) view.findViewById(R.id.et_with_mic);
        editTextWithMicLayout.initEditWithMic(getActivity(), CustomerDetailRemarkFragment.this);
        editTextWithMicLayout.setMaxTextNum(100);
        editTextWithMicLayout.setEnabled(true);
        editTextWithMicLayout.setHint(getResources().getString(R.string.customer_detail_remark_hint));

        editTextWithMicLayout.setOnTextChangeListener(new EditTextWithMicLayout.OnTextChangeListener() {
            @Override
            public void onAfterTextChange(CharSequence s) {
                if(!TextUtils.isEmpty(s.toString().trim())){
                    btnSubmit.setBackgroundColor(getResources().getColor(R.color.mine_blue));
                }else {
                    btnSubmit.setBackgroundColor(getResources().getColor(R.color.gray_999));
                }
            }
        });

        btnSubmit.setOnClickListener(this);
    }


    @Override
    protected void initOptions() {

    }

    @Override
    protected void initPickerViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        remarkListFragment.setEntity(entity);
        editTextWithMicLayout.setText(entity.getRemark());
        tvOperator.setText(getResources().getString(R.string.customer_detail_remark_operator) + UserInfoUtils.getUserName());

        // 如果是休眠/战败状态或未输入内容，按钮为灰
        if(TextUtils.isEmpty(editTextWithMicLayout.getText())){
            //如果是休眠状态输入框不可用
            if(entity.isSleepStatus() || entity.isFailedStatus()) {
                editTextWithMicLayout.setEnabled(false);
            }
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.gray_999));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editTextWithMicLayout.stopNLS();
    }

    public String getRemarkStr() {
        return editTextWithMicLayout.getTextContent();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_remark_submit:
                OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                // 如果是休眠/战败状态，提交不可用
                if(entity.isSleepStatus() || entity.isFailedStatus()){{
                    return;
                }}

                // 如果未输入内容
                if(TextUtils.isEmpty(editTextWithMicLayout.getText())){
                    return;
                }

                ((CustomerDetailItemActivity)getActivity()).prepareToUpdateRemark();
                break;
        }
    }

    /**
     * 处理提交成功
     */
    public void dealSubmitSuccess(){
        // 提交成功后，刷新列表数据
        if(null != remarkListFragment){
            remarkListFragment.requestDataFromServer();
        }
        editTextWithMicLayout.setText("");
        btnSubmit.setBackgroundColor(getResources().getColor(R.color.gray_999));
    }
}
