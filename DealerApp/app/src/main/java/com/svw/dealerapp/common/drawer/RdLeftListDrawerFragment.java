package com.svw.dealerapp.common.drawer;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.svw.dealerapp.BuildConfig;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.entity.mine.MineHomeEntity;
import com.svw.dealerapp.ui.main.activity.RdMainActivity;
import com.svw.dealerapp.ui.mine.activity.ApproveActivity;
import com.svw.dealerapp.ui.mine.activity.NotificationActivity;
import com.svw.dealerapp.ui.mine.activity.ScheduleActivity;
import com.svw.dealerapp.ui.mine.contract.MineContract;
import com.svw.dealerapp.ui.mine.model.MineModel;
import com.svw.dealerapp.ui.mine.presenter.MinePresenter;
import com.svw.dealerapp.ui.task.activity.TaskActivity;
import com.svw.dealerapp.ui.widget.BottomListDialog;
import com.svw.dealerapp.util.AesUtils;
import com.svw.dealerapp.util.FileUtils;
import com.svw.dealerapp.util.PermissionUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinshi on 1/11/2018.
 */

public class RdLeftListDrawerFragment extends BaseFragment implements View.OnClickListener, MineContract.View {

    private static final int takePicAction = 2001;              //拍照的requestCode
    private static final int selectFromAlbumAction = 2002;      //从相册选择的requestCode
    private static final int cropPicAction = 2003;              //裁剪图片

    private RelativeLayout rlHeader;
    private DraweeView sdvHeaderIcon;
    private TextView tvHeaderName;
    private TextView tvHeaderOrg;
    private RecyclerView recyclerView;

    private RdLeftDrawerAdapter drawerAdapter;
    private List<RdLeftDrawerItemEntity> itemList = new ArrayList();
    private boolean isFirstStart = true;
    private MinePresenter minePresenter;

    private Uri headerImageFileUri;
    private String headerImageFilePath;
    private Uri headerCropImageFileUri;
    private String headerCropImageFilePath;

    private BottomListDialog takePicDialog;
    private List<String> headerPicSelectItemStrings = new ArrayList<>();

    RdMainActivity leftListDrawerActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leftListDrawerActivity = (RdMainActivity) getActivity();
        minePresenter = new MinePresenter(new MineModel(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rd_right_list_drawer_fragment, null);

        assignViews(view);

        initUserData();

        return view;
    }

    private void assignViews(View view) {
        rlHeader = (RelativeLayout) view.findViewById(R.id.rl_header);
        sdvHeaderIcon = (DraweeView) view.findViewById(R.id.sdv_header_icon);
        tvHeaderName = (TextView) view.findViewById(R.id.tv_header_name);
        tvHeaderOrg = (TextView) view.findViewById(R.id.tv_header_org);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        initItemData();
        drawerAdapter = new RdLeftDrawerAdapter(getActivity(), itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(drawerAdapter);

        sdvHeaderIcon.setOnClickListener(this);

        drawerAdapter.setOnItemClickListener(new RdLeftDrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        changePageByPosition(0);
                        break;
                    case 1:

                        break;
                    case 2:
                        changePageByPosition(1);
                        break;
                    case 3:
                        changePageByPosition(2);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), TaskActivity.class);
                        break;
                    case 5:
                        intent = new Intent(getActivity(), ScheduleActivity.class);
                        break;
                    case 6:
                        intent = new Intent(getActivity(), ApproveActivity.class);
                        break;
                    case 7:
                        intent = new Intent(getActivity(), NotificationActivity.class);
                        break;
                }
                if (null != intent) {
                    jumpDelayedByIntent(intent);
                }
            }
        });
    }

    /**
     * 跳转Activity
     *
     * @param intent
     */
    private void jumpDelayedByIntent(final Intent intent) {
        leftListDrawerActivity.toggleLeftDrawer(false);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 150);
    }

    /**
     * MainActivity中的Fragment切换
     *
     * @param position
     */
    private void changePageByPosition(int position) {
        leftListDrawerActivity.toggleLeftDrawer(false);
        leftListDrawerActivity.changeFragmentByPosition(position);
    }

    private void initUserData() {
        String headerUrl = UserInfoUtils.getUserHeaderUrl();
        if (TextUtils.isEmpty(headerUrl)) {
            sdvHeaderIcon.setImageURI(Uri.parse("res://" + getActivity().getPackageName() + "/" + R.mipmap.ic_default_header));
        } else {
            Uri uri = Uri.parse(headerUrl);
            DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(uri)
                            .setAutoPlayAnimations(false)
                            .build();
            sdvHeaderIcon.setController(draweeController);
        }

        String userName = UserInfoUtils.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            tvHeaderName.setText(userName);
        }

        String userOrg = UserInfoUtils.getUserOrg();
        if (!TextUtils.isEmpty(userOrg)) {
            tvHeaderOrg.setText(userOrg);
        }
    }

    private void initItemData() {
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_home, "首页", true));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.ic_report_home_tab_ic, "客源", false));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_yellow_card, "潜客", false));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_report, "看板", false));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_task, "任务", false));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_appointment, "预约", false));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_approve, "审批", false));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_notifycation, "通知", false));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.ic_report_home_tab_ic, "阳光易手车", false));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_help, "帮助", true));
        itemList.add(new RdLeftDrawerItemEntity(R.mipmap.rd_ic_slider_settings, "设置", false));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isFirstStart) {
            recyclerView.postDelayed(new Runnable() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdv_header_icon:
                if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    ToastUtils.showToast(getResources().getString(R.string.mine_take_pic_sd_error));
                    return;
                }
                TalkingDataUtils.onEvent(getActivity(), "头像", "我的");
                if (null == takePicDialog) {
                    headerPicSelectItemStrings.add(getResources().getString(R.string.mine_take_pic));
                    headerPicSelectItemStrings.add(getResources().getString(R.string.mine_photo_album));
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
                                            }, RdLeftListDrawerFragment.this);
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
                                            }, RdLeftListDrawerFragment.this);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        minePresenter.updateResetHeader(headerCropFile);
                    }
                } else {
                    ToastUtils.showToast(getResources().getString(R.string.mine_take_pic_error));
                }
                break;
        }
    }

    @Override
    public void updateHeaderSuccess(String url) {
        Uri uri = Uri.parse(url);
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(false)
                        .build();
        sdvHeaderIcon.setController(draweeController);
        saveHeaderImg(url);
        ToastUtils.showToast(getResources().getString(R.string.mine_photo_change_success));
        deleteHeaderFile();
    }

    /**
     * 保存头像url到本地db
     *
     * @param url
     */
    private void saveHeaderImg(String url) {
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from User", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (tvHeaderName.getText().equals(AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, cursor.getString(cursor.getColumnIndex("name"))))) {
                    ContentValues values = new ContentValues();
                    values.put("image", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, url));
                    DealerApp.dbHelper.update("User", values, "name=?", new String[]{cursor.getString(cursor.getColumnIndex("name"))});
                }
            }
            cursor.close();
        }
    }

    @Override
    public void updateHeaderFail() {
        ToastUtils.showToast(getResources().getString(R.string.mine_photo_change_fail));
        deleteHeaderFile();
    }

    @Override
    public void getMineDataSuccess(MineHomeEntity entity) {

    }

    @Override
    public void getMineDataFail() {

    }

    @Override
    public void showServerErrorToast() {

    }

    @Override
    public void showTimeOutToast() {

    }

    @Override
    public void showNetWorkErrorToast() {

    }

    @Override
    public void setHeaderRefreshing(boolean isRefreshing) {

    }

    @Override
    public void logoutSuccess() {

    }

    @Override
    public void logoutFail() {

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
}
