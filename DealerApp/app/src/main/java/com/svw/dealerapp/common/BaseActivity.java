
package com.svw.dealerapp.common;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.login.LoginActivity;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.splash.SplashActivity;
import com.svw.dealerapp.ui.update.UpdateActivity;
import com.svw.dealerapp.util.AppManager;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.ScreenUtils;

/**
 * Created by ibm on 2017/4/20.
 */
public class BaseActivity extends SlidingActivity implements BaseFuncIml, View.OnClickListener {

//    protected Fragment mCurrFragment;
//
//    private int mFragmentId;

    private static final String TAG = "BaseActivity";

    protected MaterialDialog.Builder mLoadingDialog;

    private long mExitTime;

    private boolean isExit = false;

    protected ImageView mBackIv, mRightIv;//依次为标题栏返回键，右侧图片按钮
    protected TextView mTitleTv, mRightTv;//依次为标题文字TextView，右侧文字按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView();
        setBaseView();
        setBaseListeners();

        initData();
        initView();
        initListener();
        initLoad();
    }

    private void setContentView() {
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if(isSetStatusBarColor()) {
            setStatusBarColor(getResources().getColor(R.color.title_blue));
        }
    }

    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onStart() {
//        JLog.logd(TAG, "onStart");
        super.onStart();
//        initData();
//        initView();
//        initListener();
//        initLoad();
    }

    @Override
    protected void onResume() {
//        JLog.logd(TAG, "onResume");
        super.onResume();
//        MobclickAgent.onPageStart(getClass().getName());
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
//        JLog.logd(TAG, "onPause");
        super.onPause();
//        MobclickAgent.onPageEnd(getClass().getName());
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
//        JLog.logd(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        JLog.logd(TAG, "onDestroy");
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void finish() {
        //如果当前只有一个Activity，且这个Activity不是MainActivity或LoginActivity
        // 或SplashActivity或UpdateActivity，则关闭时打开MainActivity
        if (!(this instanceof LoginActivity) &&
                !(this instanceof RdMainActivity) &&
                !(this instanceof SplashActivity) &&
                !(this instanceof UpdateActivity) &&
                AppManager.getAppManager().getActivityNumber() == 1) {
            Intent intent = new Intent(this, RdMainActivity.class);
            startActivity(intent);
        }
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setBaseView() {
        mBackIv = (ImageView) findViewById(R.id.common_title_bar_left_iv);
        mRightIv = (ImageView) findViewById(R.id.common_title_bar_right_iv);
        mRightTv = (TextView) findViewById(R.id.common_title_bar_right_textview);
        mTitleTv = (TextView) findViewById(R.id.common_title_bar_title_textview);
        initLoadingDialog();
    }

    private void initLoadingDialog() {
        mLoadingDialog = new MaterialDialog.Builder(this);
        mLoadingDialog.title("请稍候");
        mLoadingDialog.progress(true, 0);
    }

    private void setBaseListeners() {
        if (mBackIv != null) {
            mBackIv.setOnClickListener(this);
        }
        if (mRightIv != null) {
            mRightIv.setOnClickListener(this);
        }
        if (mRightTv != null) {
            mRightTv.setOnClickListener(this);
        }
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_title_bar_left_iv:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);

    }

    public MaterialDialog.Builder getLoadingDialog() {
        return mLoadingDialog;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    showShortToast("再按一次退出程序");
                    mExitTime = System.currentTimeMillis();
                } else {
                    AppManager.getAppManager().finishAllActivity();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    /**
     * 实现点击空白处，软键盘消失事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 设置状态栏的颜色
     * @param color
     */
    protected void setStatusBarColor(int color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            // 绘制一个和状态栏一样高的矩形
            View statusView = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getStatusBarHeight(this));
            statusView.setLayoutParams(params);
            statusView.setBackgroundColor(color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(statusView);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    public boolean isSetStatusBarColor (){
        return false;
    }

}
