package com.svw.dealerapp.ui.update;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.ui.update.contract.AppDownloadContract;
import com.svw.dealerapp.ui.update.model.AppDownloadModel;
import com.svw.dealerapp.ui.update.presenter.AppDownloadPresenter;
import com.svw.dealerapp.util.FileUtils;
import com.svw.dealerapp.util.NetworkUtils;
import com.svw.dealerapp.util.NumberUtils;
import com.svw.dealerapp.util.PermissionUtils;
import com.svw.dealerapp.util.ToastUtils;

import java.io.File;

/**
 * Created by qinshi on 7/4/2017.
 */

public class UpdateActivity extends BaseActivity implements AppDownloadContract.View {

    private RelativeLayout rlUpdateDialogPbOuter;
    private TextView tvUpdateDialogFileSize;
    private TextView tvUpdateDialogProgress;
    private ProgressBar pbUpdateDialog;
    private TextView tvUpdateDialogTip;
    private Button btnCancel;
    private View vBtnDivider;
    private View vContentDivider;
    private Button btnConfirm;
    private ImageView ivClose;

    private AppDownloadPresenter downloadPresenter;
    private String downloadUrl;
    private int apkFileSize;
    private String fileSize;
    private String downloadAppDir;
    private String downloadAppName;
    private int currentSize;

    private boolean isForceUpdate = true; //是否强制升级
    private NetWorkStateReceiver netWorkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        setContentView(R.layout.dialog_update);
        assignViews();

        downloadPresenter = new AppDownloadPresenter(this, new AppDownloadModel());

        //获取传过来的下载url和要下载的apk文件大小
        Intent intent = getIntent();
        if(null != intent){
            downloadUrl = intent.getStringExtra("downloadUrl");
            apkFileSize = intent.getIntExtra("apkFileSize", 0);
        }

        //设置下载的目录和文件名
        downloadAppDir = Environment.getExternalStorageDirectory() + "/DealerApp";
        downloadAppName = "salesApp.apk";

        btnConfirm.setOnClickListener(this);
        if(isForceUpdate) {
            ivClose.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            vBtnDivider.setVisibility(View.GONE);
        }else {
            ivClose.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
        }
    }

    private void assignViews() {
        rlUpdateDialogPbOuter = (RelativeLayout) findViewById(R.id.rl_update_dialog_pb_outer);
        tvUpdateDialogFileSize = (TextView) findViewById(R.id.tv_update_dialog_file_size);
        tvUpdateDialogProgress = (TextView) findViewById(R.id.tv_update_dialog_progress);
        pbUpdateDialog = (ProgressBar) findViewById(R.id.pb_update_dialog);
        tvUpdateDialogTip = (TextView) findViewById(R.id.tv_update_dialog_tip);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        vBtnDivider = findViewById(R.id.v_btn_divider);
        vContentDivider = findViewById(R.id.v_content_divider);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        ivClose = (ImageView) findViewById(R.id.iv_close);
    }

    /**
     * 显示进度视图，用户点击升级调用
     */
    private void showDownloadProgress(){
        rlUpdateDialogPbOuter.setVisibility(View.VISIBLE);
        tvUpdateDialogTip.setVisibility(View.GONE);
        btnConfirm.setVisibility(View.GONE);
        vBtnDivider.setVisibility(View.GONE);
        if(isForceUpdate) {
            vContentDivider.setVisibility(View.GONE);
        }

        pbUpdateDialog.setMax(apkFileSize);
        fileSize = NumberUtils.formatDecimalsOnePoint(apkFileSize / 1024.0 / 1024.0) + "MB";
        tvUpdateDialogFileSize.setText(fileSize + " / 0.0MB");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:   //取消
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.iv_close:     //右上解关闭叉
                finish();
                overridePendingTransition(R.anim.activity_traffic_filter_in, R.anim.activity_traffic_filter_out);
                break;
            case R.id.btn_confirm:  //升级
                //判断网络
                if(!NetworkUtils.isNetworkAvailable(this)){
                    ToastUtils.showToast(getResources().getString(R.string.network_error));
                    return;
                }

                //判断下载地址
                if(TextUtils.isEmpty(downloadUrl)){
                    ToastUtils.showToast(getResources().getString(R.string.update_check_fail));
                    return;
                }

                //如果运行在6.0及以上的手机，需先请求手机存储读/写权限
                if(Build.VERSION.SDK_INT >= 23){
                    PermissionUtils.requestPermission(this, new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            PermissionUtils.REQUEST_STORAGE_PERMISSION, new PermissionUtils.OnGrantedListener() {
                                @Override
                                public void onGranted() {
                                    startDownloadNewVersion();
                                }
                            }, null);
                }else {
                    startDownloadNewVersion();
                }
                break;
        }
    }

    /**
     * 开始下载新版本的apk文件
     */
    private void startDownloadNewVersion(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {


            //注册网络状态变化的广播，如果已注册过，先注销
            if(null != netWorkStateReceiver){
                unregisterReceiver(netWorkStateReceiver);
            }
            netWorkStateReceiver = new NetWorkStateReceiver();
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkStateReceiver, intentFilter);

            //显示下载相关的UI
            showDownloadProgress();

            downloadPresenter.downloadApp(downloadUrl, downloadAppDir, downloadAppName, 0, 0);
        }else {
            ToastUtils.showToast(getResources().getString(R.string.update_sd_error));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionUtils.REQUEST_STORAGE_PERMISSION:
                if(Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startDownloadNewVersion();
                }
                break;
        }
    }

    @Override
    public void downloadFail() {
        rlUpdateDialogPbOuter.setVisibility(View.GONE);
        tvUpdateDialogTip.setText(getResources().getString(R.string.update_download_error));
        tvUpdateDialogTip.setVisibility(View.VISIBLE);
        btnConfirm.setVisibility(View.VISIBLE);
        vBtnDivider.setVisibility(View.VISIBLE);
    }

    @Override
    public void reFreshDownloadUI(long currentSize) {
        pbUpdateDialog.setProgress((int) currentSize);
        String currentFileSize = NumberUtils.formatDecimalsOnePoint(currentSize / 1024.0 / 1024.0) + "MB";
        tvUpdateDialogFileSize.setText(fileSize + " / " + currentFileSize);
        String downloadFilePer = NumberUtils.formatDecimalsOnePoint(currentSize * 100.0 / apkFileSize);
        tvUpdateDialogProgress.setText(downloadFilePer + "%");
        this.currentSize = (int)currentSize;
    }

    @Override
    public void jumpToInstall(final String downloadPath) {
        reFreshDownloadUI(apkFileSize);
        File file = new File(downloadPath);
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        if(isForceUpdate){
            btnConfirm.setVisibility(View.VISIBLE);
            vContentDivider.setVisibility(View.VISIBLE);
            rlUpdateDialogPbOuter.setVisibility(View.GONE);
            tvUpdateDialogTip.setVisibility(View.VISIBLE);
            tvUpdateDialogTip.setText(getResources().getString(R.string.update_has_download_new_version));
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reFreshDownloadUI(apkFileSize);
                    File file = new File(downloadPath);
                    Uri uri = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }else {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadPresenter.cancel();
        if(null != netWorkStateReceiver){
            unregisterReceiver(netWorkStateReceiver);
        }
    }

    /**
     * 网络状态变化的广播接收者
     * 网络断开时暂停下载
     * 网络连上时继续下载
     */
    private class NetWorkStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(!mobileNetworkInfo.isConnected() && !wifiNetworkInfo.isConnected()){
                //网络断开
                downloadPresenter.cancel();
                ToastUtils.showToast(getResources().getString(R.string.network_error));
            }else {
                //网络连上
                if(downloadPresenter.isCancel()) {
                    downloadPresenter.downloadApp(downloadUrl, downloadAppDir, downloadAppName, currentSize, apkFileSize);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!isForceUpdate) {
            super.onBackPressed();
        }
    }
}