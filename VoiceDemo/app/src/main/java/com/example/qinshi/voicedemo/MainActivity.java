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
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.asr.SpeechConstant;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomAdapter adapter;
    private LoadingDialog loadingDialog;
    private Button btn;
    private TextView textView;

    private boolean isRequesting = false;
    private EventListener eventListener;
//    private String inputJson = "{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true,\"pid\":15361,\"decoder\":0,\"vad_endpoint_timeout\":1000}";
    private String inputJson = "{\"accept-audio-data\":false," +
                                "\"disable-punctuation\":false," +
                                "\"accept-audio-volume\":false," +
                                "\"pid\":15361}";
    private Map<String, ResultEntity> resultMap = new HashMap<>();

    private static final int finishInitBaiDu = 1001;  //语音引擎就绪
    private static final int requestFailed = 1002;    //请求分词接口失败
    private static final int requestSuccess = 1003;   //请求分词接口成功
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
                        loadingDialog.setButtonVisibility(true);
                    }
                    break;
                case requestFailed:
                    dismissLoadingDialog();
                    Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_LONG).show();
                    break;
                case requestSuccess:
                    if(msg.arg1 > 0) {
                        adapter.notifyDataSetChanged();
                    }
                    dismissLoadingDialog();
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
        textView = (TextView) findViewById(R.id.result);

        adapter = new CustomAdapter(this, initData(), resultMap);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btn.setOnClickListener(this);

        registerEventListener();
    }

    /**
     * 请求分词接口
     * @param path
     */
    private void request(String path) {
        HttpUtils.Get(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(requestFailed);
                isRequesting = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("ee", result);
                Gson gson = new Gson();
                ReturnEntity returnEntity = gson.fromJson(result, ReturnEntity.class);
                if(null != returnEntity && "200".equals(returnEntity.getRetCode())){
                    List<ResultEntity> dataList = returnEntity.getRetData();
                    Message msg = Message.obtain();
                    msg.what = requestSuccess;
                    if(dataList.size() > 0) {
                        resultMap.clear();
                        resultMap = dealResultData(dataList);
                        msg.arg1 = dataList.size();
                    }else {
                        msg.arg1 = 0;
                    }
                    handler.sendMessage(msg);
                }else {
                    handler.sendEmptyMessage(requestFailed);
                }
                isRequesting = false;
            }
        });
    }

    /**
     * 显示加载对话框
     * @param text
     * @param isShowButton
     */
    private void showLoadingDialog(String text, boolean isShowButton) {
        if(null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setOnFinishClickListener(new LoadingDialog.OnFinishClickListener() {
                @Override
                public void onClick() {
                    BaiDuVoiceUtils.sendEvent(SpeechConstant.ASR_STOP, null);
                    if(null != loadingDialog) {
                        loadingDialog.setText("识别中，请稍候...");
                        loadingDialog.setButtonVisibility(false);
                    }
                }
            });
        }
        loadingDialog.setButtonVisibility(isShowButton);
        loadingDialog.setText(text);
        loadingDialog.show();
    }

    /**
     * 关闭加载对话框
     */
    private void dismissLoadingDialog() {
        if(null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 初始化数据
     * @return
     */
    private List<CustomDataEntity> initData() {
        List<CustomDataEntity> dataList = new ArrayList<>();
        dataList.add(new CustomDataEntity("姓名", "NAME", "请填写姓名"));
        dataList.add(new CustomDataEntity("性别", "SEX", "请填写性别"));
        dataList.add(new CustomDataEntity("电话", "TEL", "请填写电话"));
        dataList.add(new CustomDataEntity("意向车系", "SERIES", "请填写意向车系"));
        dataList.add(new CustomDataEntity("意向车型", "MODEL", "请填写意向车型"));
        dataList.add(new CustomDataEntity("外饰颜色", "COLOR_OUT", "请填写外饰颜色"));
        dataList.add(new CustomDataEntity("内饰颜色", "COLOR_IN", "请填写内饰颜色"));
        dataList.add(new CustomDataEntity("选装包", "OPTIONAL_PACKAGE", "请填写选装包"));
        dataList.add(new CustomDataEntity("购买区分", "DIFFERENTIATE", "请填写购买区分"));
        dataList.add(new CustomDataEntity("购买性质", "PROPERTY", "请填写购买性质"));
        return dataList;
    }

    /**
     * 加分词接口返回的数据，转成Map方便读取
     * @param resultList
     * @return
     */
    private Map<String, ResultEntity> dealResultData(List<ResultEntity> resultList) {
        for(int i = 0; i < resultList.size(); i++) {
            ResultEntity entity = resultList.get(i);
            resultMap.put(entity.getKey(), entity);
        }
        return resultMap;
    }

    /**
     * 注册语音识别的事件监听
     */
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
                        loadingDialog.setButtonVisibility(false);
                    }
                }else if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)){
                    // 识别结束
                    if(!isRequesting){
                        dismissLoadingDialog();
                    }
                }else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
                    if(!TextUtils.isEmpty(params) && BaiDuVoiceUtils.isFinalResult(params)) {
                        String result = BaiDuVoiceUtils.getFinalResult(params);
                        List<String> numStrList = StringUtils.getMatchStrings("[一二三四五六七八九零]*点[一二三四五六七八九零]*", result);
                        if(null != numStrList && numStrList.size() > 0) {
                            result = StringUtils.transformNumStrListToNumStr(numStrList, result);
                        }
                        textView.setText("结果： " + result);
                        Log.e("params: ", result);

                        request("http://192.168.62.234:8889/api/nlp/createOpportunity/baseInfo?spellingText=" + result);
                        isRequesting = true;
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
        if(null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
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
                                    showLoadingDialog("启动中，请稍候...", false);
                                }
                            }, null);
                }else {
                    BaiDuVoiceUtils.sendEvent(SpeechConstant.ASR_START, inputJson);
                    showLoadingDialog("启动中，请稍候...", false);
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
                    showLoadingDialog("启动中，请稍候...", false);
                }
                break;
        }
    }
}
