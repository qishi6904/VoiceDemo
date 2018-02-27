package com.svw.dealerapp.ui.mine.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.login.contract.SMResetPassContract;
import com.svw.dealerapp.ui.login.model.SMResetPassModel;
import com.svw.dealerapp.ui.login.presenter.SMResetPassPresenter;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinshi on 5/31/2017.
 */

public class ChangePawActivity extends BaseActivity implements TextWatcher, SMResetPassContract.View {

    private ImageView ivBack;
    private EditText etOriginal;
    private EditText etNew;
    private EditText etNewConfirm;
    private Button btnSubmit;
    private ImageView ivOriginalClear;
    private ImageView ivNewClear;
    private ImageView ivNewConfirmClear;
    private TextView tvPawDiffTip;

    private SMResetPassPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_paw);
        assignViews();

        presenter = new SMResetPassPresenter(new SMResetPassModel(), this);

        etOriginal.addTextChangedListener(this);
        etNew.addTextChangedListener(this);
        etNewConfirm.addTextChangedListener(this);

        btnSubmit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivOriginalClear.setOnClickListener(this);
        ivNewClear.setOnClickListener(this);
        ivNewConfirmClear.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this, "我的-更改密码");
    }

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etOriginal = (EditText) findViewById(R.id.et_original);
        etNew = (EditText) findViewById(R.id.et_new);
        etNewConfirm = (EditText) findViewById(R.id.et_new_confirm);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        ivOriginalClear = (ImageView) findViewById(R.id.iv_original_clear);
        ivNewClear = (ImageView) findViewById(R.id.iv_new_clear);
        ivNewConfirmClear = (ImageView) findViewById(R.id.iv_new_confirm_clear);
        tvPawDiffTip = (TextView) findViewById(R.id.tv_paw_diff_tip);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String originalText = etOriginal.getText().toString().trim();
        String newText = etNew.getText().toString().trim();
        String newConfirmText = etNewConfirm.getText().toString().trim();

        if (!TextUtils.isEmpty(originalText) && !TextUtils.isEmpty(newText) &&
                !TextUtils.isEmpty(newConfirmText) && newText.length() == newConfirmText.length()) {
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.new_yellow_card_dark_blue));
        } else {
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.gray_999));
        }

//        if(!TextUtils.isEmpty(originalText)){
//            ivOriginalClear.setVisibility(View.VISIBLE);
//        }else {
//            ivOriginalClear.setVisibility(View.INVISIBLE);
//        }
//
//        if(!TextUtils.isEmpty(newText)){
//            ivNewClear.setVisibility(View.VISIBLE);
//        }else {
//            ivNewClear.setVisibility(View.INVISIBLE);
//        }
//
//        if(!TextUtils.isEmpty(newConfirmText)){
//            ivNewConfirmClear.setVisibility(View.VISIBLE);
//        }else {
//            ivNewConfirmClear.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String originalText = etOriginal.getText().toString().trim();
                String newText = etNew.getText().toString().trim();
                String newConfirmText = etNewConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(originalText)) {
                    ToastUtils.showToast(getResources().getString(R.string.mine_paw_original_input));
                    return;
                }

                if (TextUtils.isEmpty(newText)) {
                    ToastUtils.showToast(getResources().getString(R.string.mine_paw_new_input));
                    return;
                }

                if (TextUtils.isEmpty(newConfirmText)) {
                    ToastUtils.showToast(getResources().getString(R.string.mine_paw_new_confirm_input));
                    return;
                }

                if (!newText.equals(newConfirmText)) {
                    ToastUtils.showToast(getResources().getString(R.string.mine_paw_difference));
                    tvPawDiffTip.setVisibility(View.VISIBLE);
                    return;
                } else {
                    tvPawDiffTip.setVisibility(View.INVISIBLE);
                }

                String regEx = "^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{8,}$";
                if (!StringUtils.isMatchPattern(regEx, newConfirmText)) {
                    ToastUtils.showToast(getResources().getString(R.string.mine_paw_error));
                    return;
                }

//                try {
//                    String newMd5Paw = StringUtils.md5Encode(newConfirmText);
//                    String originalMd5Paw = StringUtils.md5Encode(originalText);
                Map<String, Object> options = new HashMap<>();
                options.put("oldPassword", originalText);
                options.put("newPassword", newText);
                presenter.smResetPass(options);
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                    ToastUtils.showToast(getResources().getString(R.string.mine_paw_md5_error));
//                    return;
//                }

                TalkingDataUtils.onEvent(this, "提交更改密码", "我的-更改密码");

                break;
            case R.id.iv_original_clear:
                etOriginal.setText("");
                break;
            case R.id.iv_new_clear:
                etNew.setText("");
                break;
            case R.id.iv_new_confirm_clear:
                etNewConfirm.setText("");
                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }

    @Override
    public void showResetPawSuccess() {
        ToastUtils.showToast(getResources().getString(R.string.mine_change_paw_success));
        finish();
    }

    @Override
    public void showOldPawErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.mine_change_old_paw_error));
    }

//    @Override
//    public void showServerErrorToast() {
//        ToastUtils.showToast(getResources().getString(R.string.mine_change_paw_fail));
//    }
//
//    @Override
//    public void showTimeOutToast() {
//        ToastUtils.showToast(getResources().getString(R.string.mine_change_paw_timeout));
//    }
//
//    @Override
//    public void showNetWorkErrorToast() {
//        ToastUtils.showToast(getResources().getString(R.string.server_error));
//    }
//
//    @Override
//    public void showLoadingDialog() {
//        if (null == loadingDialog) {
//            loadingDialog = new LoadingDialog(this);
//        }
//        loadingDialog.show();
//    }
//
//    @Override
//    public void hideLoadingDialog() {
//        if (null != loadingDialog) {
//            loadingDialog.hide();
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "我的-更改密码");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenter) {
            presenter.onDestroy();
        }
    }
}
