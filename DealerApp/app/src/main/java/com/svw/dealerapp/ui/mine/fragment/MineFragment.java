package com.svw.dealerapp.ui.mine.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.svw.dealerapp.BuildConfig;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.entity.mine.MineHomeEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.adapter.StringCustomDialogAdapter;
import com.svw.dealerapp.ui.bindpushdevice.BindPushDeviceContract;
import com.svw.dealerapp.ui.bindpushdevice.BindPushDeviceModel;
import com.svw.dealerapp.ui.bindpushdevice.BindPushDevicePresenter;
import com.svw.dealerapp.ui.login.LoginActivity;
import com.svw.dealerapp.ui.login.contract.SMLoginoutContract;
import com.svw.dealerapp.ui.login.contract.SMUploadImageContract;
import com.svw.dealerapp.ui.login.model.SMLoginoutModel;
import com.svw.dealerapp.ui.login.model.SMUploadImageModel;
import com.svw.dealerapp.ui.login.presenter.SMLoginoutPresenter;
import com.svw.dealerapp.ui.login.presenter.SMUploadImagePresenter;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.mine.activity.AboutActivity;
import com.svw.dealerapp.ui.mine.activity.ApproveActivity;
import com.svw.dealerapp.ui.mine.activity.ChangePawActivity;
import com.svw.dealerapp.ui.mine.activity.NotificationActivity;
import com.svw.dealerapp.ui.mine.activity.ScheduleActivity;
import com.svw.dealerapp.ui.mine.adapter.MineRecyclerViewAdapter;
import com.svw.dealerapp.ui.mine.contract.MineContract;
import com.svw.dealerapp.ui.mine.entity.MineListItemEntity;
import com.svw.dealerapp.ui.mine.model.MineModel;
import com.svw.dealerapp.ui.mine.presenter.MinePresenter;
import com.svw.dealerapp.ui.task.activity.TaskActivity;
import com.svw.dealerapp.ui.usedcar.UsedCarActivity;
import com.svw.dealerapp.ui.widget.BottomListDialog;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.AesUtils;
import com.svw.dealerapp.util.dbtools.DBHelper;
import com.svw.dealerapp.util.FileUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.PermissionUtils;
import com.svw.dealerapp.util.ScreenUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.svw.dealerapp.util.dbtools.DBUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lijinkui on 2017/5/17.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener, MineContract.View, BindPushDeviceContract.View, SMLoginoutContract.View, SMUploadImageContract.View {

    private static final String TAG = "MineFragment";
    private static final int takePicAction = 2001;              //拍照的requestCode
    private static final int selectFromAlbumAction = 2002;      //从相册选择的requestCode
    private static final int cropPicAction = 2003;              //裁剪图片

    private ImageView ivHeaderBg;
    private SimpleDraweeView sdvUserHeader;
    private IRecyclerView rvMine;
    private BottomListDialog takePicDialog;
    private CustomDialog logoutDialog;
    private TextView tvUserName;
    private TextView tvUserShop;

    private List<MineListItemEntity> dataList = new ArrayList<>();
    private MineRecyclerViewAdapter adapter;
    private int newNotificationNum;

    private Uri headerImageFileUri;
    private String headerImageFilePath;
    private Uri headerCropImageFileUri;
    private String headerCropImageFilePath;

    private List<String> headerPicSelectItemStrings = new ArrayList<>();

    private NewMassageReceiver newMassageReceiver;

    private MinePresenter minePresenter;

    private OptionsPickerView mEnvPicker;
    private List<String> mEnvDataList;

    private CustomDialog aliPushDeviceIdDialog;

    private NoticeCenterBackReceiver noticeCenterBackReceiver;
    private boolean isFirstStart = true;

    private BindPushDevicePresenter bindPushDevicePresenter;

    private SMLoginoutPresenter smLoginoutPresenter;
    private SMUploadImagePresenter mSmUploadImagePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        minePresenter = new MinePresenter(new MineModel(), this);
        bindPushDevicePresenter = new BindPushDevicePresenter(new BindPushDeviceModel(), this);
        smLoginoutPresenter = new SMLoginoutPresenter(new SMLoginoutModel(), this);
        mSmUploadImagePresenter = new SMUploadImagePresenter(new SMUploadImageModel(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_mine, null);
        rvMine = (IRecyclerView) rootView.findViewById(R.id.rv_mine);

        initDataList();

        rvMine.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvMine.setRefreshEnabled(false);
        rvMine.setLoadMoreEnabled(false);
        adapter = new MineRecyclerViewAdapter(getActivity(), dataList);
        rvMine.setIAdapter(adapter);

        initHeaderView();
        if ("1".equals(BuildConfig.ENV_CHANGE_ALLOW)) {
            initPickerViews();
        }

        sdvUserHeader.setImageURI(Uri.parse("res://" + getActivity().getPackageName() + "/" + R.mipmap.ic_default_header));
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("select * from SMUser", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String url = AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, cursor.getString(cursor.getColumnIndex("avatarUrl")));
                String userName = AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, cursor.getString(cursor.getColumnIndex("displayName")));
                String userShop = AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, cursor.getString(cursor.getColumnIndex("orgName")));
                if (null != url && !TextUtils.isEmpty(url)) {
                    url = url.concat("?access_token=").concat(DealerApp.ACCESS_TOKEN);
                    JLog.d(TAG, "onCreateView url: " + url);
                    Uri uri = Uri.parse(url);
                    DraweeController draweeController =
                            Fresco.newDraweeControllerBuilder()
                                    .setUri(uri)
                                    .setAutoPlayAnimations(false)
                                    .build();
                    sdvUserHeader.setController(draweeController);
                }
                tvUserName.setText(userName);
                tvUserShop.setText(userShop);
            }
            cursor.close();
        }

        sdvUserHeader.setOnClickListener(this);

        setItemClickListener();

        //注册接收新消息的广播接收者
        newMassageReceiver = new NewMassageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.svw.dealerapp.newMassage");
        getActivity().registerReceiver(newMassageReceiver, intentFilter);

        Map<String, Object> options = new HashMap<>();
        options.put("userId", UserInfoUtils.getUserId());
        minePresenter.getMineHomeData(getActivity(), options, MinePresenter.REQUEST_BY_INIT);

        rvMine.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, Object> options = new HashMap<>();
                options.put("userId", UserInfoUtils.getUserId());
                minePresenter.getMineHomeData(getActivity(), options, MinePresenter.REQUEST_BY_PULL_DOWN);
            }
        });

        return rootView;
    }

    /**
     * 初始化header
     */
    private void initHeaderView() {
        View headerView = View.inflate(getActivity(), R.layout.ui_mine_header, null);
        sdvUserHeader = (SimpleDraweeView) headerView.findViewById(R.id.sdv_user_header);
        ivHeaderBg = (ImageView) headerView.findViewById(R.id.iv_header_bg);
        tvUserName = (TextView) headerView.findViewById(R.id.tv_user_name);
        tvUserShop = (TextView) headerView.findViewById(R.id.tv_user_shop);
        setHeaderBgImageViewHeight();
        rvMine.addHeaderView(headerView);
    }

    private void setItemClickListener() {
        adapter.setOnItemClickListener(new MineRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        TalkingDataUtils.onEvent(getActivity(), "任务列表", "我的");
                        Intent taskIntent = new Intent(getActivity(), TaskActivity.class);
                        taskIntent.putExtra("fromFlag", TaskActivity.FROM_MINE);
                        startActivity(taskIntent);
                        break;
                    case 1:
                        TalkingDataUtils.onEvent(getActivity(), "预约列表", "我的");
                        Intent scheduleIntent = new Intent(getActivity(), ScheduleActivity.class);
                        startActivity(scheduleIntent);
                        break;
                    case 2:
                        TalkingDataUtils.onEvent(getActivity(), "审批列表", "我的");
                        Intent approveIntent = new Intent(getActivity(), ApproveActivity.class);
                        startActivity(approveIntent);
                        break;
                    case 3:
                        TalkingDataUtils.onEvent(getActivity(), "通知中心", "我的");
                        noticeCenterBackReceiver = new NoticeCenterBackReceiver();
                        IntentFilter filter = new IntentFilter("com.svw.dealerapp.notice.center.back");
                        getActivity().registerReceiver(noticeCenterBackReceiver, filter);

                        Intent notificationIntent = new Intent(getActivity(), NotificationActivity.class);
                        startActivity(notificationIntent);
                        break;
                    case 4:
                        TalkingDataUtils.onEvent(getActivity(), "更改密码", "我的");
                        Intent pawkIntent = new Intent(getActivity(), ChangePawActivity.class);
                        startActivity(pawkIntent);
                        break;
                    case 5:
                        TalkingDataUtils.onEvent(getActivity(), "关于", "我的");
                        Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                        startActivity(aboutIntent);
                        break;
                    case 6:
                        TalkingDataUtils.onEvent(getActivity(), "注销", "我的");
                        if (null == logoutDialog) {
                            StringCustomDialogAdapter adapter = new StringCustomDialogAdapter();
                            logoutDialog = new CustomDialog(getActivity(), adapter);
                            adapter.setContent(getResources().getString(R.string.mine_logout_dialog_content));
                        }
                        logoutDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                            @Override
                            public void onCancelBtnClick() {
                                logoutDialog.dismiss();
                            }

                            @Override
                            public void onConfirmBtnClick() {
                                TalkingDataUtils.onEvent(getActivity(), "确认注销", "我的-注销");
//                                unBindPushDevice();
//                                minePresenter.logout();
                                Map<String, Object> options = new HashMap<>();
                                smLoginoutPresenter.smLoginout(options);
                            }
                        });
                        logoutDialog.show();
                        break;
                    case 7:
                        String url = "";
                        goToSubReport(url);
                        break;
                    case 8:
                        mEnvPicker.show();
                        break;
                    case 9:
                        SharedPreferences aliPushDeviceIdSP = DealerApp.getContext().getSharedPreferences("appSettingSP", Activity.MODE_PRIVATE);
                        String aliPushDeviceId = aliPushDeviceIdSP.getString("aliPushDeviceId", "");
                        StringCustomDialogAdapter adapter = new StringCustomDialogAdapter();
                        aliPushDeviceIdDialog = new CustomDialog(getActivity(), adapter);
                        adapter.setContent(aliPushDeviceId);
                        aliPushDeviceIdDialog.hideShowCancelBtn();
                        aliPushDeviceIdDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
                            @Override
                            public void onCancelBtnClick() {

                            }

                            @Override
                            public void onConfirmBtnClick() {
                                aliPushDeviceIdDialog.dismiss();
                            }
                        });
                        aliPushDeviceIdDialog.show();
                        break;


                }
            }
        });
    }


    /**
     * 根据手机屏幕宽度调头部背景图片的高度
     */
    private void setHeaderBgImageViewHeight() {
        float rate = 300f / 960f;
        float height = ScreenUtils.getScreenWidth(getActivity()) * rate;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtils.getScreenWidth(getActivity()), (int) height);
        ivHeaderBg.setLayoutParams(params);
    }

    private void initDataList() {
        dataList.add(new MineListItemEntity(R.mipmap.ic_mine_task_list,
                getResources().getString(R.string.mine_task_list), "", false));
        dataList.add(new MineListItemEntity(R.mipmap.ic_mine_appointment_list,
                getResources().getString(R.string.mine_oppointment_list), "", false));
        dataList.add(new MineListItemEntity(R.mipmap.ic_mine_approve_list,
                getResources().getString(R.string.mine_approve_list), "", false));
        dataList.add(new MineListItemEntity(R.mipmap.ic_mine_notification,
                getResources().getString(R.string.mine_notice_center), "", false));
        dataList.add(new MineListItemEntity(R.mipmap.ic_mine_change_password,
                getResources().getString(R.string.mine_change_paw), "", false));
        dataList.add(new MineListItemEntity(R.mipmap.ic_mine_about,
                getResources().getString(R.string.mine_about), "", false));
        dataList.add(new MineListItemEntity(R.mipmap.ic_mine_logout,
                getResources().getString(R.string.mine_logout), "", false));

        if ("1".equals(BuildConfig.ENV_CHANGE_ALLOW)) {
            //for used car
            dataList.add(new MineListItemEntity(R.mipmap.ic_mine_logout,
                    getResources().getString(R.string.mine_used_car), "", false));
            //for change env
            dataList.add(new MineListItemEntity(R.mipmap.ic_mine_logout,
                    getResources().getString(R.string.mine_change_env), "", false));
            //show ali push device id
            dataList.add(new MineListItemEntity(R.mipmap.ic_mine_logout,
                    "阿里推送设备号", "", false));
        }

        headerPicSelectItemStrings.add(getResources().getString(R.string.mine_take_pic));
        headerPicSelectItemStrings.add(getResources().getString(R.string.mine_photo_album));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdv_user_header:
                if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    ToastUtils.showToast(getResources().getString(R.string.mine_take_pic_sd_error));
                    return;
                }
                TalkingDataUtils.onEvent(getActivity(), "头像", "我的");
                if (null == takePicDialog) {
                    takePicDialog = new BottomListDialog(getActivity(), headerPicSelectItemStrings);
                }
                takePicDialog.setOnItemClickListener(new BottomListDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (position) {
                            case 0:     //点击 拍照
                                //如果运行在6.0及以上的手机需先请求权限
                                if (Build.VERSION.SDK_INT >= 23) {
                                    PermissionUtils.requestPermission(getActivity(),
                                            new String[]{Manifest.permission.CAMERA,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                                            PermissionUtils.REQUEST_CAMERA_PERMISSION, new PermissionUtils.OnGrantedListener() {
                                                @Override
                                                public void onGranted() {
                                                    jumpToCamera();
                                                }
                                            }, MineFragment.this);
                                } else {
                                    jumpToCamera();
                                }
                                break;
                            case 1:     //点击 从相册选择
                                //如果运行在6.0及以上的手机需先请求权限
                                if (Build.VERSION.SDK_INT >= 23) {
                                    PermissionUtils.requestPermission(getActivity(),
                                            new String[]{
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                                            PermissionUtils.REQUEST_STORAGE_PERMISSION, new PermissionUtils.OnGrantedListener() {
                                                @Override
                                                public void onGranted() {
                                                    jumpToAlbum();
                                                }
                                            }, MineFragment.this);
                                } else {
                                    jumpToAlbum();
                                }

                                break;
                        }
                    }
                });

                takePicDialog.show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_CAMERA_PERMISSION:
                if (Manifest.permission.CAMERA.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    jumpToCamera();
                }
                break;
            case PermissionUtils.REQUEST_STORAGE_PERMISSION:
                if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    jumpToAlbum();
                }
                break;
        }
    }

    /**
     * 跳到相册
     */
    private void jumpToAlbum() {
        String headerImageDir = Environment.getExternalStorageDirectory() + "/DealerApp"; // 头像的存储目录
        File file = new File(headerImageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        headerImageFilePath = headerImageDir + "/cacheHeader_" + System.currentTimeMillis() + ".jpg"; //最终头像的路径
        file = new File(headerImageFilePath);
        headerImageFileUri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, selectFromAlbumAction);
    }

    /**
     * 跳转到拍照
     */
    private void jumpToCamera() {
        String headerImageDir = Environment.getExternalStorageDirectory() + "/DealerApp"; // 头像的存储目录
        File file = new File(headerImageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        headerImageFilePath = headerImageDir + "/cacheHeader_" + System.currentTimeMillis() + ".jpg"; //最终头像的路径
        file = new File(headerImageFilePath);
        headerImageFileUri = Uri.fromFile(file);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, headerImageFileUri);
        startActivityForResult(cameraIntent, takePicAction);
    }

    //跳到系统图片剪切页
    private void jumpToCrop(Uri uri) {
        headerCropImageFilePath = Environment.getExternalStorageDirectory() +
                "/DealerApp/cacheHeader_crop_" + System.currentTimeMillis() + ".jpg"; //最终头像的路径
        File file = new File(headerCropImageFilePath);
        headerCropImageFileUri = Uri.fromFile(file);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, headerCropImageFileUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("crop", "true");

        intent.putExtra("scale", true);             // 去黑边
        intent.putExtra("scaleUpIfNeeded", true);   // 去黑边

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, cropPicAction);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case takePicAction:
                if (!TextUtils.isEmpty(headerImageFilePath)) {
                    File headerFile = new File(headerImageFilePath);
                    if (headerFile.exists() && null != headerImageFileUri) {
                        jumpToCrop(headerImageFileUri);
                    } else {
                        takePicDialog.dismiss();
                    }
                } else {
                    ToastUtils.showToast(getResources().getString(R.string.mine_take_pic_error));
                }
                break;
            case selectFromAlbumAction:
                if (null != data && null != data.getData()) {
                    Uri uri = data.getData();
                    jumpToCrop(uri);
                } else {
                    takePicDialog.dismiss();
                }
                break;
            case cropPicAction:
                if (!TextUtils.isEmpty(headerCropImageFilePath)) {
                    File headerCropFile = new File(headerCropImageFilePath);
                    if (headerCropFile.exists()) {
                        takePicDialog.dismiss();
//                        minePresenter.updateResetHeader(headerCropFile);
                        mSmUploadImagePresenter.smUploadImage(headerCropFile);
                    }
                } else {
                    ToastUtils.showToast(getResources().getString(R.string.mine_take_pic_error));
                }
                break;
        }
    }

    @Override
    public void updateHeaderSuccess(String url) {
        Fresco.getImagePipeline().clearCaches();
        String result_url = url.concat("?access_token=").concat(DealerApp.ACCESS_TOKEN);
        JLog.d(TAG, "updateHeaderSuccess: " + result_url);
        Uri uri = Uri.parse(result_url);
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(false)
                        .build();
        sdvUserHeader.setController(draweeController);
//        saveHeaderImg(url);
        ToastUtils.showToast(getResources().getString(R.string.mine_photo_change_success));
        deleteHeaderFile();
    }

    @Override
    public void updateHeaderFail() {
//        ToastUtils.showToast(getResources().getString(R.string.mine_photo_change_fail));
        deleteHeaderFile();
    }

    /**
     * 数梦更新头像失败的回调
     *
     * @param msg
     */
    @Override
    public void updateHeaderFail(String msg) {
        ToastUtils.showToast(msg);
        updateHeaderFail();
    }

    /**
     * 删除上传头像生成的相关图片文件
     */
    private void deleteHeaderFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileUtils.deleteFile(headerCropImageFilePath);
                FileUtils.deleteFile(headerImageFilePath);
            }
        }).start();
    }

    @Override
    public void getMineDataSuccess(MineHomeEntity entity) {
        dataList.get(0).setNumber(String.valueOf(entity.getTaskCount()));
        dataList.get(1).setNumber(String.valueOf(entity.getAppoCount()));
        dataList.get(2).setNumber(String.valueOf(entity.getApprCount()));
        adapter.notifyDataSetChanged();
        try {
            if (!TextUtils.isEmpty(entity.getNoticeCount())) {
                newNotificationNum = Integer.parseInt(entity.getNoticeCount());
                if (newNotificationNum > 0) {
                    dataList.get(3).setHasNew(true);
                    ((RdMainActivity) getActivity()).isShowMeRedTip(true);
                } else {
                    dataList.get(3).setHasNew(false);
                    ((RdMainActivity) getActivity()).isShowMeRedTip(false);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getMineDataFail() {
        ToastUtils.showToast(getResources().getString(R.string.mine_get_data_fail));
    }

    @Override
    public void showServerErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.server_error));
    }

    @Override
    public void showTimeOutToast() {
        ToastUtils.showToast(getResources().getString(R.string.timeout_error));
    }

    @Override
    public void showNetWorkErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void setHeaderRefreshing(boolean isRefreshing) {
        rvMine.setRefreshing(isRefreshing);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != takePicDialog) {
            takePicDialog.dismiss();
        }
        if (null != logoutDialog) {
            logoutDialog.dismiss();
        }
        if (null != newMassageReceiver) {
            getActivity().unregisterReceiver(newMassageReceiver);
        }
        if (null != minePresenter) {
            minePresenter.onDestroy();
        }
        if (null != noticeCenterBackReceiver) {
            getActivity().unregisterReceiver(noticeCenterBackReceiver);
        }
        if (null != smLoginoutPresenter) {
            smLoginoutPresenter.onDestroy();
        }
        if (null != mSmUploadImagePresenter) {
            mSmUploadImagePresenter.onDestroy();
        }
    }

    @Override
    public void logoutSuccess() {

    }

    @Override
    public void logoutFail() {

    }

    @Override
    public void onBindPushDeviceSuccess() {
        enterLoginActivity();
    }

    @Override
    public void onBindPushDeviceFail() {
        enterLoginActivity();
    }

    @Override
    public void onLogoutSuccess() {
        unBindPushDevice();
        logoutDialog.dismiss();
        ToastUtils.showToast(getResources().getString(R.string.mine_logout_success));
    }

    @Override
    public void onLogoutFail() {
        logoutDialog.dismiss();
        ToastUtils.showToast(getResources().getString(R.string.mine_logout_fail));
    }

    private void enterLoginActivity() {
        Intent logoutIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(logoutIntent);
        getActivity().finish();
    }

    private class NewMassageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != dataList && dataList.size() > 3 && null != adapter) {
                dataList.get(3).setHasNew(true);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void unBindPushDevice() {
        bindPushDevicePresenter.aliPushUnregisterDevice();
    }

    private void initPickerViews() {
        mEnvPicker = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                dataList.get(8).setNumber(mEnvDataList.get(options1));
                adapter.notifyDataSetChanged();
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
                        dataList.get(8).setNumber(cursor.getString(cursor.getColumnIndex("name")));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            cursor.close();
        }
        mEnvPicker.setNPicker(mEnvDataList, null, null);
    }

    /**
     * 从通知中心返加的广播接收都
     */
    private class NoticeCenterBackReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Map<String, Object> options = new HashMap<>();
            options.put("userId", UserInfoUtils.getUserId());
            minePresenter.getMineHomeData(getActivity(), options, MinePresenter.REQUEST_BY_INIT);

            getActivity().unregisterReceiver(noticeCenterBackReceiver);
            noticeCenterBackReceiver = null;
        }
    }

    private void saveHeaderImg(String url) {
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from SMUser", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (tvUserName.getText().equals(AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, cursor.getString(cursor.getColumnIndex("displayName"))))) {
                    ContentValues values = new ContentValues();
                    values.put("avatarUrl", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, url));
                    DealerApp.dbHelper.update("SMUser", values, "displayName=?", new String[]{cursor.getString(cursor.getColumnIndex("displayName"))});
                }
            }
            cursor.close();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isFirstStart) {
            rvMine.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Map<String, Object> options = new HashMap<>();
                    options.put("userId", UserInfoUtils.getUserId());
                    minePresenter.getMineHomeData(getActivity(), options, MinePresenter.REQUEST_BY_INIT);
                }
            }, 1000);
        }
        isFirstStart = false;

    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "我的");
        JLog.i("talkingDataFlag-show", "我的");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "我的");
        JLog.i("talkingDataFlag-hide", "我的");
    }

    private void goToSubReport(String url) {
        //check current env
        url = Constants.API_BASE_URL_USED_CAR_SERVICE + url;
        Intent intent = new Intent(getActivity(), UsedCarActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }
}
