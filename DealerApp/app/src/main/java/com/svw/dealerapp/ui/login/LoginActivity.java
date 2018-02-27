package com.svw.dealerapp.ui.login;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.svw.dealerapp.BuildConfig;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginEntity;
import com.svw.dealerapp.entity.login.SMUserInfoEntity;
import com.svw.dealerapp.entity.login.SMUserPrivilegeByAppIdEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.bindpushdevice.BindPushDeviceContract;
import com.svw.dealerapp.ui.bindpushdevice.BindPushDeviceModel;
import com.svw.dealerapp.ui.bindpushdevice.BindPushDevicePresenter;
import com.svw.dealerapp.ui.dictionary.DictionaryContract;
import com.svw.dealerapp.ui.dictionary.DictionaryModel;
import com.svw.dealerapp.ui.dictionary.DictionaryPresenter;
import com.svw.dealerapp.ui.login.contract.SMLoginContract;
import com.svw.dealerapp.ui.login.contract.SMUserInfoContract;
import com.svw.dealerapp.ui.login.contract.SMUserPrivilegeContract;
import com.svw.dealerapp.ui.login.model.SMLoginModel;
import com.svw.dealerapp.ui.login.model.SMUserInfoModel;
import com.svw.dealerapp.ui.login.model.SMUserPrivilegeModel;
import com.svw.dealerapp.ui.login.presenter.SMLoginPresenter;
import com.svw.dealerapp.ui.login.presenter.SMUserInfoPresenter;
import com.svw.dealerapp.ui.login.presenter.SMUserPrivilegePresenter;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.AesUtils;
import com.svw.dealerapp.util.AppManager;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.NetworkUtils;
import com.svw.dealerapp.util.dbtools.PrivilegeDBUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UIHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xupan on 05/05/2017.
 */

public class LoginActivity extends BaseActivity implements
        SMLoginContract.View, DictionaryContract.View,
        BindPushDeviceContract.View, SMUserInfoContract.View,
        SMUserPrivilegeContract.View {
//public class LoginActivity extends BaseActivity implements LoginContract.View, DictionaryContract.View, BindPushDeviceContract.View, SMUserInfoContract.View {

    private static final int DIC_REQUEST = 10001;
    private static final int PUSH_BIND_DEVICE_REQUEST = 10002;
    private static final int SM_USER_INFO_REQUEST = 10003;
    private static final int SM_PRIVILEGE_REQUEST = 10004;
    private static final int REQUEST_ERROR = -99999;

    private static LoadingDialog loadingDialog;

    EditText mUserNameEt;
    EditText mPasswordEt;
    Button mSubmitBt;
    TextView mForgetPass;
    TextView mChangeEnv;
    ImageView mLogo;
    ImageView mUserNameClean;
    ImageView mPasswordClean;
    LinearLayout loginMainLL;
    LinearLayout loginContentLL;

    private CustomDialog validDialog;
    private CustomDialog forgetDialog;

    private OptionsPickerView mEnvPicker;
    private List<String> mEnvDataList;
    private int count;

    //    private LoginPresenter loginPresenter;
    private SMLoginPresenter smLoginPresenter;
    private DictionaryPresenter dictionaryPresenter;
    private BindPushDevicePresenter bindPushDevicePresenter;
    private SMUserInfoPresenter smUserInfoPresenter;
    private SMUserPrivilegePresenter smUserPrivilegePresenter;

    private boolean dictionaryResOK = false;
    private boolean bindPushDeviceResOK = false;
    private boolean smUserInfoResOK = false;
    private boolean smPrivilegeResOK = false;

    private UIMyHandler loginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLoginView();
        initButton();
        setExit(true);
        getUserIdDB();
        setUserNameStatus();
        setPasswordStatus();
        keepLoginBtnNotOver(loginMainLL, loginContentLL);

        loadingDialog = new LoadingDialog(this);

//        loginPresenter = new LoginPresenter(new LoginModel(), this);
        smLoginPresenter = new SMLoginPresenter(new SMLoginModel(), this);
        dictionaryPresenter = new DictionaryPresenter(new DictionaryModel(), this);
        bindPushDevicePresenter = new BindPushDevicePresenter(new BindPushDeviceModel(), this);
        smUserInfoPresenter = new SMUserInfoPresenter(new SMUserInfoModel(), this);
        smUserPrivilegePresenter = new SMUserPrivilegePresenter(this, new SMUserPrivilegeModel());
        loginHandler = new UIMyHandler(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        TalkingDataUtils.onPageStart(this, "登录页");
    }

    private void initLoginView() {
        mUserNameEt = (EditText) findViewById(R.id.login_username_et);
        mPasswordEt = (EditText) findViewById(R.id.login_password_et);
        mSubmitBt = (Button) findViewById(R.id.login_submit_bt);
        mForgetPass = (TextView) findViewById(R.id.login_forget_password);
        mChangeEnv = (TextView) findViewById(R.id.tv_change_env);
        mLogo = (ImageView) findViewById(R.id.iv_logo);
        mUserNameClean = (ImageView) findViewById(R.id.username_clean_iv);
        mPasswordClean = (ImageView) findViewById(R.id.password_clean_iv);
        loginMainLL = (LinearLayout) findViewById(R.id.login_main_ll);
        loginContentLL = (LinearLayout) findViewById(R.id.login_content_ll);
        mSubmitBt.setOnClickListener(this);
        mForgetPass.setOnClickListener(this);
        mChangeEnv.setOnClickListener(this);
        mLogo.setOnClickListener(this);
        mUserNameClean.setOnClickListener(this);
        mPasswordClean.setOnClickListener(this);
    }

    void submit() {

        dictionaryResOK = false;
        bindPushDeviceResOK = false;
        smUserInfoResOK = false;
        smPrivilegeResOK = false;

        loadingDialog.show();

        String username = mUserNameEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString();
        //SM不支持MD5
//        try {
//            password = StringUtils.md5Encode(password);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        loginPresenter.requestLogin(username, password);
        Map<String, Object> options = new HashMap<>();
        options.put("username", username);
        options.put("password", password);
        options.put("client_id", Constants.CLIENT_ID);
        options.put("category", "08");
        smLoginPresenter.smLogin(options);
        TalkingDataUtils.onEvent(this, "登录", "登录页");
    }

    void showForgetPassword() {
        showForgetDialog();
    }

    void changeEnv() {
        mEnvPicker.show();
    }

    void showChangeEnvButton() {
        count++;
        if (count == 8) {
            //add get evn data from db
            //to avoid unable to enter into mine page to change env
            if ("1".equals(BuildConfig.ENV_CHANGE_ALLOW)) {
                mChangeEnv.setVisibility(View.VISIBLE);
                initPickerViews();
            }
        }
    }

    void cleanUserName() {
        mUserNameEt.setText("");
        mUserNameEt.clearFocus();
        mPasswordEt.clearFocus();
        mUserNameEt.requestFocus();
    }

    void cleanPassword() {
        mPasswordEt.setText("");
        mUserNameEt.clearFocus();
        mPasswordEt.clearFocus();
        mPasswordEt.requestFocus();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_submit_bt:
                submit();
                break;
            case R.id.login_forget_password:
                showForgetPassword();
                break;
            case R.id.tv_change_env:
                changeEnv();
                break;
            case R.id.iv_logo:
                showChangeEnvButton();
                break;
            case R.id.username_clean_iv:
                cleanUserName();
                break;
            case R.id.password_clean_iv:
                cleanPassword();
                break;
        }
    }

    private void setUserNameStatus() {
        if (TextUtils.isEmpty(mUserNameEt.getText())) {
            mUserNameClean.setVisibility(View.INVISIBLE);
        } else {
            mUserNameClean.setVisibility(View.VISIBLE);
        }
    }

    private void setPasswordStatus() {
        if (TextUtils.isEmpty(mPasswordEt.getText())) {
            mPasswordClean.setVisibility(View.INVISIBLE);
        } else {
            mPasswordClean.setVisibility(View.VISIBLE);
        }
    }

    private void showForgetDialog() {
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        forgetDialog = new CustomDialog(this, dialogAdapter);
        dialogAdapter.setDialogMessageText(getResources().getString(R.string.login_forget_password_dialog));
        forgetDialog.setBtnConfirmText(getResources().getString(R.string.resource_select_filter_confirm));
        forgetDialog.setBtnCancelText("");
        forgetDialog.hideShowCancelBtn();
        forgetDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {

            }

            @Override
            public void onConfirmBtnClick() {
                forgetDialog.dismiss();
                mUserNameEt.clearFocus();
                mPasswordEt.clearFocus();
                mUserNameEt.requestFocus();

            }
        });
        forgetDialog.show();
    }

    private void initButton() {
        mSubmitBt.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isInputValid(mUserNameEt) && isInputValid(mPasswordEt)) {
                    mSubmitBt.setEnabled(true);
                } else {
                    mSubmitBt.setEnabled(false);
                }
                setUserNameStatus();
                setPasswordStatus();
            }
        };
        mUserNameEt.addTextChangedListener(textWatcher);
        mPasswordEt.addTextChangedListener(textWatcher);
    }

    private boolean isInputValid(EditText editText) {
        return editText != null && !editText.getText().toString().trim().isEmpty();
    }

    private void checkDictionary() {
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from Dictionary", null);
        String dictVersion = "0";
        if (cursor != null) {
            if (cursor.moveToNext()) {
                dictVersion = cursor.getString(cursor.getColumnIndex("version"));
            }
            cursor.close();
        }
        dictionaryPresenter.getDictionary(dictVersion);
    }

    private void bindPushDevice() {
        bindPushDevicePresenter.aliPushRegisterDevice();
    }

    private void getSMUserInfo() {
        Map<String, Object> options = new HashMap<>();
        smUserInfoPresenter.smUserInfo(options);
    }

    private void getSMUserPrivilege() {
        Map<String, Object> options = new HashMap<>();
        options.put("appId", Constants.APP_ID);
        options.put("category", "08");
        smUserPrivilegePresenter.getSmUserPrivilege(options);
    }

    private void initPickerViews() {
        mEnvPicker = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mChangeEnv.setText(mEnvDataList.get(options1));
                Cursor cursor = DealerApp.dbHelper.rawQuery("select * from Env", null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        ContentValues values = new ContentValues();
                        String envName = cursor.getString(cursor.getColumnIndex("name"));
                        if (mEnvDataList.get(options1).equals(envName)) {
                            values.put("status", "1");
                            int num = Integer.parseInt(envName.substring(3, 4));
                            NetworkManager.clearApiInstanceMap();
                            Constants.setApiBaseUrlService(num);
                            Constants.set3thApiBaseUrlService(num);
                            Constants.setSMCommonParam(num);
                            DealerApp.dbHelper.delete("Dictionary", null, null);
                            NetworkManager.getInstance().setToken("");
                        } else {
                            values.put("status", "0");
                        }
                        DealerApp.dbHelper.update("Env", values, "name=?", new String[]{envName});
                    }
                    cursor.close();
                }
            }
        }).build();
        mEnvDataList = new ArrayList<>();
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from Env", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (!"Env2".equals(cursor.getString(cursor.getColumnIndex("name")))) {
                    //禁止开发模式选择pro环境
                    mEnvDataList.add(cursor.getString(cursor.getColumnIndex("name")));
                    if ("1".equals(cursor.getString(cursor.getColumnIndex("status")))) {
                        mChangeEnv.setText(cursor.getString(cursor.getColumnIndex("name")));
                    }
                }
            }
            cursor.close();
        }
        mEnvPicker.setNPicker(mEnvDataList, null, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        TalkingDataUtils.onPageEnd(this, "登录页");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (null != loginPresenter) {
//            loginPresenter.onDestroy();
//        }
        if (null != smLoginPresenter) {
            smLoginPresenter.onDestroy();
        }
        if (null != dictionaryPresenter) {
            dictionaryPresenter.onDestroy();
        }
        if (null != bindPushDevicePresenter) {
            bindPushDevicePresenter.onDestroy();
        }
        if (null != smUserInfoPresenter) {
            smUserInfoPresenter.onDestroy();
        }
        if (null != loginHandler) {
            loginHandler.removeCallbacksAndMessages(null);
        }
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 保持登录按钮始终不会被覆盖
     *
     * @param root
     * @param subView
     */
    private void keepLoginBtnNotOver(final View root, final View subView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                // 若不可视区域高度大于200，则键盘显示,其实相当于键盘的高度
                if (rootInvisibleHeight > 200) {
                    // 显示键盘时
                    int srollHeight = rootInvisibleHeight - (root.getHeight() - subView.getHeight()) - CommonUtils.getNavigationBarHeight(root.getContext());
                    if (srollHeight > 0) {
                        root.scrollTo(0, srollHeight);
                    }
                } else {
                    // 隐藏键盘时
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    private void getUserIdDB() {
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from SMUser", null);
        if (cursor != null && cursor.moveToNext()) {
            mUserNameEt.setText(AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, cursor.getString(cursor.getColumnIndex("mobile"))));
            cursor.close();
        }
    }

    // 权限接口回调start
    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public void showServerErrorToast() {
        loginHandler.sendEmptyMessage(REQUEST_ERROR);
    }

    @Override
    public void showTimeOutToast() {
        loginHandler.sendEmptyMessage(REQUEST_ERROR);
    }

    @Override
    public void showNetWorkErrorToast() {
        loginHandler.sendEmptyMessage(REQUEST_ERROR);
    }

    @Override
    public void onUserPrivilegeSuccess(final List<SMUserPrivilegeByAppIdEntity> dataList) {
        if (null != dataList && dataList.size() > 0) {
            new Thread() {
                @Override
                public void run() {
                    PrivilegeDBUtils.clearPrivilegeTable();
                    boolean isSaveSuccess = PrivilegeDBUtils.savePrivilegeData(dataList);
                    if (isSaveSuccess) {
                        smPrivilegeResOK = true;
                        loginHandler.sendEmptyMessage(SM_PRIVILEGE_REQUEST);
                    } else {
                        loginHandler.sendEmptyMessage(REQUEST_ERROR);
                    }
                }
            }.start();
        } else {
            loginHandler.sendEmptyMessage(REQUEST_ERROR);
        }
    }

    @Override
    public void onUserPrivilegeError(String msg) {
        loginHandler.sendEmptyMessage(REQUEST_ERROR);
    }
    // 权限接口回调end

    private static class UIMyHandler extends UIHandler<LoginActivity> {

        public UIMyHandler(LoginActivity cls) {
            super(cls);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginActivity activity = ref.get();
            if (activity != null) {
                if (activity.isFinishing())
                    return;
                switch (msg.what) {
                    case DIC_REQUEST:
                    case PUSH_BIND_DEVICE_REQUEST:
                    case SM_USER_INFO_REQUEST:
                    case SM_PRIVILEGE_REQUEST:
                        activity.enterHomePage();
                        break;
                    case REQUEST_ERROR:
                        // TODO: 1/27/2018 处理请求失败, 不能跳到登录页
                        loadingDialog.dismiss();
                        break;
                }
            }
        }
    }

    private void enterHomePage() {
        if (dictionaryResOK && bindPushDeviceResOK && smUserInfoResOK && smPrivilegeResOK) {
            Constants.IS_FIRST_START = false;
            ToastUtils.showToast(getResources().getString(R.string.login_success));
            AppManager.getAppManager().finishAllActivity();
            Intent i = new Intent(this, RdMainActivity.class);
            startActivity(i);
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onLoginSuccess(boolean isNewUser, SMResEntity<SMLoginEntity> smLoginEntitySMResEntity) {
        if (isNewUser || Constants.IS_FIRST_START) {
            checkDictionary();
            bindPushDevice();
            getSMUserInfo();
            getSMUserPrivilege();
        } else {
            finish();
        }
    }

    @Override
    public void onLoginError(String msg) {
        loginHandler.sendEmptyMessage(REQUEST_ERROR);

        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        validDialog = new CustomDialog(this, dialogAdapter);
        dialogAdapter.setDialogMessageText(msg);
        validDialog.setBtnConfirmText(getResources().getString(R.string.login_enter_again));
        validDialog.setBtnCancelText(getResources().getString(R.string.login_forget_password));
        validDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                TalkingDataUtils.onEvent(LoginActivity.this, "忘记密码", "登录页密码错误提示框");
                validDialog.dismiss();
                showForgetDialog();
            }

            @Override
            public void onConfirmBtnClick() {
                TalkingDataUtils.onEvent(LoginActivity.this, "重新输入", "登录页密码错误提示框");
                validDialog.dismiss();
                mUserNameEt.setText("");
                mPasswordEt.setText("");
                mUserNameEt.clearFocus();
                mPasswordEt.clearFocus();
                mUserNameEt.requestFocus();
            }
        });
        validDialog.show();
    }

    @Override
    public void getDictSuccess() {
        dictionaryResOK = true;
        loginHandler.sendEmptyMessage(DIC_REQUEST);
    }

    @Override
    public void getDictFail() {
        loginHandler.sendEmptyMessage(REQUEST_ERROR);
    }

    @Override
    public void onBindPushDeviceSuccess() {
        bindPushDeviceResOK = true;
        loginHandler.sendEmptyMessage(PUSH_BIND_DEVICE_REQUEST);
    }

    @Override
    public void onBindPushDeviceFail() {
        loginHandler.sendEmptyMessage(REQUEST_ERROR);
    }

    @Override
    public void onUserInfoSuccess(SMUserInfoEntity smUserInfoEntity) {
        smUserInfoResOK = true;
        loginHandler.sendEmptyMessage(SM_USER_INFO_REQUEST);
    }

    @Override
    public void onUserInfoError(String msg) {
        loginHandler.sendEmptyMessage(REQUEST_ERROR);
    }

}
