package com.svw.dealerapp.ui.resource.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.resource.SMYCTransferSalesEntity;
import com.svw.dealerapp.ui.resource.adapter.YellowTransferDialogAdapter;
import com.svw.dealerapp.entity.resource.YCTransferSalesEntity;
import com.svw.dealerapp.ui.resource.fragment.YellowCardTransferListFragment;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.ToastUtils;

/**
 * Created by qinshi on 5/18/2017.
 */

public class YellowCardTransferActivity extends BaseActivity {

    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvConfirm;
    private ImageView ivSearchIcon;
    private EditText etSearchText;
    private FrameLayout flYellowCardTransfer;

    private YellowCardTransferListFragment fragment;
    private CustomDialog confirmDialog;
    private YellowTransferDialogAdapter dialogAdapter;
    private String customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow_card_transfer);
        assignViews();

        tvTitle.setText(getResources().getString(R.string.resource_yellow_transfer_title));

        Intent intent = getIntent();
        if(null != intent){
            customerName = intent.getStringExtra("customerName");
        }

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        etSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragment.searchTransferSales(s.toString());
            }
        });
    }

    private void assignViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        ivSearchIcon = (ImageView) findViewById(R.id.iv_search_icon);
        etSearchText = (EditText) findViewById(R.id.et_search_text);
        flYellowCardTransfer = (FrameLayout) findViewById(R.id.fl_yellow_card_transfer);

        fragment = new YellowCardTransferListFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl_yellow_card_transfer, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                setResult(0, new Intent());
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.tv_confirm:
                SMYCTransferSalesEntity infoEntity = fragment.getSelectEntity();
                if(null == infoEntity){
                    ToastUtils.showToast(getResources().getString(R.string.resource_yellow_transfer_not_select_sales));
                    return;
                }
                if(null == confirmDialog){
                    dialogAdapter = new YellowTransferDialogAdapter();
                    confirmDialog = new CustomDialog(YellowCardTransferActivity.this, dialogAdapter);
                    confirmDialog.setDialogTitle(getResources().getString(R.string.resource_yellow_transfer_dialog_title));
                    confirmDialog.hideTitleIcon();
                }
                dialogAdapter.setNames(customerName, infoEntity.getDisplayName());
                confirmDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                    @Override
                    public void onCancelBtnClick() {
                        confirmDialog.dismiss();
                    }

                    @Override
                    public void onConfirmBtnClick() {
                        fragment.postYellowCardTransfer();
                        confirmDialog.dismiss();
                    }
                });
                confirmDialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
    }
}
