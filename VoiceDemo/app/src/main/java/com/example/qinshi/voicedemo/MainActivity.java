package com.example.qinshi.voicedemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.speech.EventListener;
import com.baidu.speech.asr.SpeechConstant;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LoadingDialog loadingDialog;
    private Button btn;

    private EventListener eventListener;
//    private String inputJson = "{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true,\"pid\":15361,\"decoder\":0,\"vad_endpoint_timeout\":1000}";
    private String inputJson = "{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":false,\"pid\":1536}";

    private static final int finishInitBaiDu = 1001;
    private DealHandler handler = new DealHandler(this);
    private class DealHandler extends Handler {

        WeakReference<MainActivity> activityWeakReference;
        DealHandler(MainActivity activityWeakReference){
            this.activityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case finishInitBaiDu:
                    if(null != loadingDialog) {
                        loadingDialog.setText("请输入语音...");
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaiDuVoiceUtils.init(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rlv);
        btn = (Button) findViewById(R.id.btn);

        CustomAdapter adapter = new CustomAdapter(this, initData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btn.setOnClickListener(this);

        registerEventListener();

//        HttpUtils.Get("http://www.hao123.com", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.e("ee", response.body().toString());
//            }
//        });

    }

    private void showLoadingDialog(String text) {
        if(null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setOnFinishClickListener(new LoadingDialog.OnFinishClickListener() {
                @Override
                public void onClick() {
                    BaiDuVoiceUtils.sendEvent(SpeechConstant.ASR_STOP, null);
                    if(null != loadingDialog) {
                        loadingDialog.setText("识别中，请稍候...");
                    }
                }
            });
        }
        loadingDialog.setText(text);
        loadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if(null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private List<CustomDataEntity> initData() {
        List<CustomDataEntity> dataList = new ArrayList<>();
        dataList.add(new CustomDataEntity("姓名", "请填写姓名"));
        dataList.add(new CustomDataEntity("性别", "请填写性别"));
        dataList.add(new CustomDataEntity("电话", "请填写电话"));
        dataList.add(new CustomDataEntity("意向车系", "请填写意向车系"));
        dataList.add(new CustomDataEntity("意向车型", "请填写意向车型"));
        dataList.add(new CustomDataEntity("外饰颜色", "请填写外饰颜色"));
        dataList.add(new CustomDataEntity("内饰颜色", "请填写内饰颜色"));
        dataList.add(new CustomDataEntity("选装包", "请填写选装包"));
        dataList.add(new CustomDataEntity("购买区分", "请填写购买区分"));
        dataList.add(new CustomDataEntity("购买性质", "请填写购买性质"));
        return dataList;
    }

    private void registerEventListener(){
        eventListener = new EventListener() {
            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {

                Log.e("ee", name);

                if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)){
                    // 引擎就绪，可以说话，一般在收到此事件后通过UI通知用户可以说话了
                    handler.sendEmptyMessage(finishInitBaiDu);
                } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_END)) {
                    // 检测到用户的已经停止说话
                    if(null != loadingDialog) {
                        loadingDialog.setText("识别中，请稍候...");
                    }
                }else if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)){
                    // 识别结束
                    dismissLoadingDialog();
                }else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
                    if(!TextUtils.isEmpty(params) && BaiDuVoiceUtils.isFinalResult(params)) {
                        Log.e("params: ", BaiDuVoiceUtils.getFinalResult(params));
                        dismissLoadingDialog();
                    }
                }
            }
        };
        BaiDuVoiceUtils.registerEventListener(eventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaiDuVoiceUtils.release(eventListener);
        if(null != loadingDialog) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                //如果运行在6.0及以上的手机需先请求权限
                if(Build.VERSION.SDK_INT >= 23){
                    PermissionUtils.requestPermission(this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION, new PermissionUtils.OnGrantedListener() {
                                @Override
                                public void onGranted() {
                                    BaiDuVoiceUtils.sendEvent(SpeechConstant.ASR_START, inputJson);
                                    showLoadingDialog("启动中，请稍候...");
                                }
                            }, null);
                }else {
                    BaiDuVoiceUtils.sendEvent(SpeechConstant.ASR_START, inputJson);
                    showLoadingDialog("启动中，请稍候...");
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION:
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    BaiDuVoiceUtils.sendEvent(SpeechConstant.ASR_START, inputJson);
                    showLoadingDialog("启动中，请稍候...");
                }
                break;
        }
    }
}
